package no.nav.eux.rina.admin.rina;

import com.vdurmont.semver4j.Semver;
import eu.ec.dgempl.eessi.rina.sdk.cpi.RinaCpiClient;
import eu.ec.dgempl.eessi.sdk.cpi.api.ResourcesApi;
import eu.ec.dgempl.eessi.sdk.cpi.api.SynchronizationsApi;
import eu.ec.dgempl.eessi.sdk.cpi.model.ResourceDto;
import eu.ec.dgempl.eessi.sdk.cpi.model.SyncInitialDocumentDto;
import lombok.extern.slf4j.Slf4j;
import no.nav.eux.rina.admin.config.RinaTenantProperties.RinaTenant;
import no.nav.eux.rina.admin.http.InboundHttpHeaders;
import no.nav.eux.rina.admin.http.RestTemplateFactory;
import no.nav.eux.rina.admin.rina.security.RinaCpiAuthenticationClientInterceptor;
import no.nav.eux.rina.admin.rina.security.RinaCpiAuthenticationService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static no.nav.eux.rina.admin.rest.ExceptionUtil.notFound;

@Slf4j
public class RinaCpiSynchronizationService {

  private RinaTenant rinaTenant;
  private SynchronizationsApi syncApi;
  private ResourcesApi resourcesApi;
  
  public RinaCpiSynchronizationService(RinaTenant rinaTenant, RinaCpiAuthenticationService rinaCpiAuthenticationService, InboundHttpHeaders inboundHttpHeaders) {
    this.rinaTenant = rinaTenant;
    RinaCpiClient rinaCpiClient = new RinaCpiClient();
    rinaCpiClient.setBasePath(rinaTenant.getCpiBaseUrl());
    rinaCpiClient.getRestTemplate()
            .getInterceptors()
            .add(new RinaCpiAuthenticationClientInterceptor(rinaTenant, rinaCpiAuthenticationService,inboundHttpHeaders));
    syncApi = new SynchronizationsApi(rinaCpiClient);
    resourcesApi = new ResourcesApi(rinaCpiClient);
  }
  
  public SyncInitialDocumentDto getInititalDocument() {
    return syncApi.retrieveInitialDocumentIR();
  }
  
  public void submitDocumentIR(Object syn002) {
    try {
      syncApi.submitDocumentIR(syn002);
    } catch (RestClientException e) {
      log.error("Could not submit IR document!" + e);
    }
  }
  
  public Map<String, String> getInstitutionVersions() {
    String ci = rinaTenant.getInstitutionId();
    log.debug("rinaTenant.institutionId = [" + ci + "]");
    Map<String, String> resultCiVersions = new HashMap<>();
    Semver syn002Version = new Semver(getCurrentIrVersionFromSYN002(getInititalDocument()), Semver.SemverType.STRICT);
    // syn002Version seems to be ALWAYS identical availableVersion.withClearedSuffixAndBuild()
    resultCiVersions.put("SYN002", syn002Version.toString());
    
    ResourceDto installedDto = getResources("SERVER", true, Collections.singletonList("organisation")).stream()
      .reduce((first, second) -> second)
      .get(); // List.last()
    Semver installedVersion = new Semver(installedDto.getVersion(), Semver.SemverType.STRICT);
    resultCiVersions.put("INSTALLED", installedVersion.toString());
    
    ResourceDto availableDto = getResources("DISK", true, Collections.singletonList("organisation")).stream()
      .reduce((first, second) -> second)
      .get(); // List.last()
    Semver availableVersion = new Semver(availableDto.getVersion(), Semver.SemverType.STRICT);
    resultCiVersions.put("AVAILABLE", availableVersion.toString());
    
    if (installedVersion.withClearedSuffixAndBuild().isLowerThan(availableVersion.withClearedSuffixAndBuild())) {
      log.debug("CI: " + ci + " OUT OF DATE : installedVersion " + installedVersion + " < availableVersion " + availableVersion);
      resultCiVersions.put("ACTION", "UPDATE");
    } else {
      log.debug(" UP TO DATE : " + ci + " installedVersion " + installedVersion + " >= availableVersion " + availableVersion);
      resultCiVersions.put("ACTION", "NONE");
    }
    return resultCiVersions;
  }
  
  public List<ResourceDto> getResources(String resourceLocation, Boolean hardRefresh, List<String> resourceIds) {
    return resourcesApi.getResources(resourceLocation, hardRefresh, resourceIds);
  }
  
  /* this is the method that performs the actual installation of a new version */
  public void updateResource(String resourceId, String resourceType, String resourceVersion) {
    try {
      resourcesApi.updateResource(resourceId, resourceType, resourceVersion);
    } catch (RestClientException rcex) {
      log.error("Server error: [" + this.rinaTenant.getInstitutionId() + "] not responding to call in time: " + rcex.toString());
    }
  }
  
  public String getUsernameAndPassword() {
    String ci = rinaTenant.getInstitutionId();
    String result = "[" + ci + "] un =[" + rinaTenant.getAdminuser() + "]  pw =[" + rinaTenant.getAdminpwd()  + "]";
    log.debug("rinaTenant.institutionId = " + result);
    return result;
  }
  
  private String getCurrentIrVersionFromSYN002(SyncInitialDocumentDto dto) {
    String currentVersion = "";
    try {
      currentVersion = (String) PropertyUtils.getProperty(dto, "initialDocument.SYN002.RequestForIRSync.currentVersion");
    } catch (IllegalAccessException e) {
      log.error("IllegalAccessException " + e.getMessage());
    } catch (InvocationTargetException e) {
      log.error("InvocationTargetException " + e.getMessage());
    } catch (NoSuchMethodException e) {
      log.error("NoSuchMethodException " + e.getMessage());
    }
    log.debug("using PropertyUtils, IR has " + currentVersion + " version.");
    return currentVersion;
  }
}
