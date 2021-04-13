package no.nav.eux.rina.admin.rina;

import com.vdurmont.semver4j.Semver;
import eu.ec.dgempl.eessi.rina.sdk.cpi.RinaCpiClient;
import eu.ec.dgempl.eessi.sdk.cpi.api.ResourcesApi;
import eu.ec.dgempl.eessi.sdk.cpi.api.SynchronizationsApi;
import eu.ec.dgempl.eessi.sdk.cpi.model.ResourceDto;
import eu.ec.dgempl.eessi.sdk.cpi.model.SyncInitialDocumentDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import no.nav.eux.rina.admin.config.RinaTenantProperties.RinaTenant;
import no.nav.eux.rina.admin.http.InboundHttpHeaders;
import no.nav.eux.rina.admin.rina.security.RinaCpiAuthenticationClientInterceptor;
import no.nav.eux.rina.admin.rina.security.RinaCpiAuthenticationService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.client.RestClientException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
public class RinaCpiSynchronizationService {
  //Changed from pre Nov 2018 Release of Rina
  //Keep for doc purposes - it is not working so have to use old impl class for now
  //public static final String NIE_CLIENT_IMPL_CLASS ="eu.ec.dgempl.nieclient.ms.client.NieServiceClientImpl";
  public static final String NIE_CLIENT_IMPL_CLASS = "eu.ec.dgempl.nieclient.NieClientImpl";
  private RinaTenant rinaTenant;
  private SynchronizationsApi syncApi;
  private ResourcesApi resourcesApi;
  
  public RinaCpiSynchronizationService(RinaTenant rinaTenant,
                                       RinaCpiAuthenticationService rinaCpiAuthenticationService,
                                       InboundHttpHeaders inboundHttpHeaders) {
    this.rinaTenant = rinaTenant;
    RinaCpiClient rinaCpiClient = new RinaCpiClient();
    rinaCpiClient.setBasePath(rinaTenant.getCpiBaseUrl());
    rinaCpiClient.getRestTemplate().getInterceptors()
      .add(new RinaCpiAuthenticationClientInterceptor(rinaTenant, rinaCpiAuthenticationService,
        inboundHttpHeaders));
    syncApi = new SynchronizationsApi(rinaCpiClient);
    resourcesApi = new ResourcesApi(rinaCpiClient);
  }
  
  @SneakyThrows
  public SyncInitialDocumentDto getInititalDocument() {
    SyncInitialDocumentDto syncInitialDocumentDto = syncApi.retrieveInitialDocumentIR();
    List<ResourceDto> resourceDtoList = resourcesApi.getResources("SERVER", true, Collections.singletonList("organisation"));
    resourceDtoList.sort(new Comparator<ResourceDto>() {
      @Override
      public int compare(ResourceDto o1, ResourceDto o2) {
        Semver v1 = new Semver(o1.getVersion());
        Semver v2 = new Semver(o2.getVersion());
        return v1.compareTo(v2);
      }
    });
    String currentVersion = resourceDtoList.get(resourceDtoList.size() - 1).getVersion();
    Semver semver = new Semver(currentVersion);
    PropertyUtils.setProperty(syncInitialDocumentDto, "initialDocument.SYN002.RequestForIRSync.currentVersion", semver.withClearedSuffixAndBuild().toString());
    return syncInitialDocumentDto;
  }
  
  public void submitDocumentIR(SyncInitialDocumentDto dto, String defaultVersion) {
    try {
      PropertyUtils.setProperty(dto, "initialDocument.SYN002.RequestForIRSync.currentVersion", defaultVersion);
    } catch (IllegalAccessException e) {
      log.error("IllegalAccessException " + e.getMessage());
    } catch (InvocationTargetException e) {
      log.error("InvocationTargetException " + e.getMessage());
    } catch (NoSuchMethodException e) {
      log.error("NoSuchMethodException " + e.getMessage());
    }
    try {
      syncApi.submitDocumentIR(dto.getInitialDocument());
    } catch (RestClientException e) {
      log.error("Could not submit IR document!" + e);
    }
  }
  
  public Map<String, String> getInstitutionVersions() {
    String ci = rinaTenant.getInstitutionId();
    Map<String, String> resultCiVersions = new HashMap<>();
    Semver syn002Version = new Semver(getCurrentIrVersionFromSYN002(getInititalDocument()), Semver.SemverType.STRICT);
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
      log.info("CI: " + ci + " OUT OF DATE : availableVersion " + availableVersion + " is > installed " + installedVersion);
      resultCiVersions.put("ACTION", "UPDATE");
    } else {
      log.info(" UP TO DATE : " + ci + " installedVersion " + installedVersion + " is >= available " + availableVersion);
      resultCiVersions.put("ACTION", "NONE");
    }
    return resultCiVersions;
  }
  
  public List<ResourceDto> getResources(String resourceLocation, Boolean hardRefresh, List<String> resourceIds) {
    return resourcesApi.getResources(resourceLocation, hardRefresh, resourceIds);
  }
  
  public void updateResource(String resourceId, String resourceType, String resourceVersion) {
    try {
      resourcesApi.updateResource(resourceId, resourceType, resourceVersion);
    } catch (RestClientException rcex) {
      log.error("Server error: [" + this.rinaTenant.getInstitutionId() + "] not responding to call in time: " + rcex.toString());
    }
  }
  
  public String getUsernameAndPassword() {
    String ci = rinaTenant.getInstitutionId();
    String result = "[" + ci + "] un =[" + rinaTenant.getAdminuser() + "]  pw =[" + rinaTenant.getAdminpwd() + "]";
    log.info("rinaTenant.institutionId = " + result);
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
