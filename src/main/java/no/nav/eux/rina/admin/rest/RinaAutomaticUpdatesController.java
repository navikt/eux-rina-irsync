package no.nav.eux.rina.admin.rest;

import com.vdurmont.semver4j.Semver;
import eu.ec.dgempl.eessi.sdk.cpi.model.ResourceDto;
import eu.ec.dgempl.eessi.sdk.cpi.model.SyncInitialDocumentDto;
import lombok.extern.slf4j.Slf4j;
import no.nav.eux.rina.admin.http.RestTemplateFactory;
import no.nav.eux.rina.admin.rina.RinaCpiSynchronizationService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static no.nav.eux.rina.admin.rest.ExceptionUtil.notFound;

@Slf4j
@RestController
@RequestMapping ("/rina/ir")

public class RinaAutomaticUpdatesController {
  private Map<String, RinaCpiSynchronizationService> rinaCpiSynchronizationsServiceMap;
  @Autowired
  private RestTemplateFactory restTemplateFactory;
  
  private @Value ("${update.wait}")
  int waitInSeconds;
  
  private @Value("${server.port}")
  int serverPort;
  
  public RinaAutomaticUpdatesController(Map<String, RinaCpiSynchronizationService> rinaCpiSynchronizationsServiceMap) {
    this.rinaCpiSynchronizationsServiceMap = rinaCpiSynchronizationsServiceMap;
  }
  
  @Scheduled (cron = "${cron.expression}")
  
  public void scheduledAutomaticUpdate() {
    log.info("running scheduled");
    RestTemplate restTemplate = restTemplateFactory.getObject();
    ResponseEntity<Void> response = restTemplate.getForEntity("http://localhost:"  + serverPort + "/rina/ir/silentUpdate", Void.class);
  }
  
  //@ApiOperation (value = "Silent automatic update")
  @GetMapping ("/silentUpdate")
  public void silentUpdate() {
    log.info("calling automaticUpdate() ...");
    automaticUpdate();
  }
  
  //@ApiOperation (value = "Automatic update")
  @GetMapping ("/automaticUpdate")
  public ResponseEntity<Map<String, Map<String, String>>> automaticUpdate() {
    int waitInMilliseconds = waitInSeconds * 1000;
  
    log.info("starting actual automaticUpdate()");
    this.orderNewInstitutionVersions();
    log.info("Waiting for {} minutes for new versions to arrive in RINAs", waitInSeconds / 60);
    try {
      Thread.sleep(waitInMilliseconds);
    } catch (InterruptedException e) {
      log.error("Somebody woke me up! " + e.getMessage());
    }
    log.info("Done waiting. Installing new versions.");
    this.installNewInstitutionVersion();
    log.info("Done installing.");
  
    return getInstitutionsAndVersionsMapResponseEntity();
  }
  
  //@ApiOperation (value = "list all institutions and their versions")
  @GetMapping ("/listInstitutionsAndVersions")
  public ResponseEntity<Map<String, Map<String, String>>> listInstitutionsAndVersions() {
    return getInstitutionsAndVersionsMapResponseEntity();
  }
  
  private ResponseEntity<Map<String, Map<String, String>>> getInstitutionsAndVersionsMapResponseEntity() {
    List<String> institutions = new ArrayList<>(rinaCpiSynchronizationsServiceMap.keySet());
    Map<String, Map<String, String>> resultVersions = institutions.stream()
      .parallel()
      .collect(Collectors.toMap(
        Function.identity(),
        ci -> {
          try {
            return getRinaCpiSynchronizationsService(ci).getInstitutionVersions();
          } catch (Exception ex) {
            log.error("Server error: [" + ci + "] not responding to calls: " + ex.toString());
            Map<String, String> resultCiVersions = new HashMap<>();
            resultCiVersions.put("ACTION", "RETRY");
            resultCiVersions.put("AVAILABLE", "UNKNOWN");
            resultCiVersions.put("INSTALLED", "UNKNOWN");
            resultCiVersions.put("ERROR", "SERVER (" + ex.getMessage() + ")");
            return resultCiVersions;
          }
        }
      ));
    return new ResponseEntity<>(resultVersions, HttpStatus.OK);
  }
  
  //@ApiOperation (value = "install new version in all institutions (if available)")
  @GetMapping ("/installNewInstitutionVersions")
  public ResponseEntity<Map<String, Map<String, String>>> installNewInstitutionVersion() {
    String resourceId = "dataorganisations";
    String resourceType = "organisation";
    
    List<String> institutions = new ArrayList<>(rinaCpiSynchronizationsServiceMap.keySet());
    Map<String, Map<String, String>> resultVersions = institutions.stream()
      .parallel()
      .collect(Collectors.toMap(
        Function.identity(),
        ci -> {
          Map<String, String> resultCiVersions = new HashMap<>();
          Semver syn002Version = new Semver(getCurrentIrVersionFromSYN002(getRinaCpiSynchronizationsService(ci).getInitialDocument()), Semver.SemverType.STRICT);
          resultCiVersions.put("SYN002", syn002Version.toString());
          
          ResourceDto installedDto = getRinaCpiSynchronizationsService(ci)
            .getResources("SERVER", true, Arrays.asList("organisation")).stream()
            .reduce((first, second) -> second)
            .get(); // List.last()
          Semver installedVersion = new Semver(installedDto.getVersion(), Semver.SemverType.STRICT);
          resultCiVersions.put("INSTALLED", installedVersion.toString());
          
          ResourceDto availableDto = getRinaCpiSynchronizationsService(ci)
            .getResources("DISK", true, Arrays.asList("organisation")).stream()
            .reduce((first, second) -> second)
            .get(); // List.last()
          Semver availableVersion = new Semver(availableDto.getVersion(), Semver.SemverType.STRICT);
          resultCiVersions.put("AVAILABLE", availableVersion.toString());
          
          if (installedVersion.withClearedSuffixAndBuild().isLowerThan(availableVersion.withClearedSuffixAndBuild())) {
            log.info("CI: " + ci + " Installing now... resource version: " + availableVersion);
            getRinaCpiSynchronizationsService(ci).updateResource(resourceId, resourceType, availableVersion.toString());
          } else {
            //log.info("UP TO DATE : " + ci + " installedVersion " + installedVersion + " is GREATER THAN OR EQUAL TO available " + availableVersion);
          }
          return resultCiVersions;
        }));
    return new ResponseEntity<>(resultVersions, HttpStatus.OK);
  }
  
  //@ApiOperation (value = "order new version to be made available for all institutions (no installation)")
  @GetMapping ("/orderNewInstitutionVersions")
  public ResponseEntity<Map<String, String>> orderNewInstitutionVersions() {
    final String defaultVersion = "0.0.1";
    List<String> institutions = new ArrayList<>(rinaCpiSynchronizationsServiceMap.keySet());
    log.info("institutions = " + institutions);
    Map<String, String> ciVersions = institutions.stream()
      .parallel()
      .collect(Collectors.toMap(
        Function.identity(),
        ci -> getCurrentIrVersionFromSYN002(getRinaCpiSynchronizationsService(ci).getInitialDocument())));
    ciVersions.forEach((k, v) -> log.info(("ciVersions: " + k + ":" + v)));
    ciVersions.forEach((ci, ver) -> {
      SyncInitialDocumentDto dto = getRinaCpiSynchronizationsService(ci).getInitialDocument();
      setIrVersionInSYN002(getRinaCpiSynchronizationsService(ci).getInitialDocument(), ver);
      try {
        log.info("Institution [{}] is configured with activeSubscription [{}]", ci, rinaCpiSynchronizationsServiceMap.get(ci).getActiveSubscription() );
        log.info("Requesting version [" + ver + "|as|" + getCurrentIrVersionFromSYN002(dto) + "] for CI [" + ci + "]");
        getRinaCpiSynchronizationsService(ci).submitDocumentIR(dto, ver);
      } catch (Exception e) {
        log.error("Could not submit IR sync request! " + e.getMessage());
      }
    });
    return new ResponseEntity<>(ciVersions, HttpStatus.OK);
  }
  
  private String getCurrentIrVersionFromSYN002(SyncInitialDocumentDto dto) {
    String currentVersion = "0.0.1";
  
    try {
      currentVersion = (String) PropertyUtils.getProperty(dto, "initialDocument.SYN002.RequestForIRSync.currentVersion");
    } catch (IllegalAccessException e) {
      log.error("IllegalAccessException " + e.getMessage());
    } catch (InvocationTargetException e) {
      log.error("InvocationTargetException " + e.getMessage());
    } catch (NoSuchMethodException e) {
      log.error("NoSuchMethodException " + e.getMessage());
    }
    return currentVersion;
  }
  
  private void setIrVersionInSYN002(SyncInitialDocumentDto dto, String newVersion) {
    String currentVersion = getCurrentIrVersionFromSYN002(dto);
  
    try {
      PropertyUtils.setProperty(dto, "initialDocument.SYN002.RequestForIRSync.currentVersion", newVersion);
    } catch (IllegalAccessException e) {
      log.error("IllegalAccessException " + e.getMessage());
    } catch (InvocationTargetException e) {
      log.error("InvocationTargetException " + e.getMessage());
    } catch (NoSuchMethodException e) {
      log.error("NoSuchMethodException " + e.getMessage());
    }
  }
  
  private RinaCpiSynchronizationService getRinaCpiSynchronizationsService(String institutionId) {
    return Optional.ofNullable(rinaCpiSynchronizationsServiceMap.get(institutionId))
      .orElseThrow(notFound("institutionId not found"));
  }
}
