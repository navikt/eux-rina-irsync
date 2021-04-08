package no.nav.eux.rina.admin.config;

import no.nav.eux.rina.admin.config.RinaTenantProperties.RinaTenant;
import no.nav.eux.rina.admin.http.InboundHttpHeaders;
import no.nav.eux.rina.admin.rina.RinaCpiSynchronizationService;
import no.nav.eux.rina.admin.rina.security.RinaCpiAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class RinaTenantConfig {
  
  @Bean
  public Map<String, RinaCpiSynchronizationService> rinaCpiSynchronizationsServiceMap(RinaTenantProperties rinaTenantProperties,
                                                                                      RinaCpiAuthenticationService rinaCpiAuthenticationService,
                                                                                      InboundHttpHeaders inboundHttpHeaders) {
    Map<String, RinaCpiSynchronizationService> map = new LinkedHashMap<>();
    for (RinaTenant rinaTenant : rinaTenantProperties.getTenants()) {
      map.put(rinaTenant.getInstitutionId(),
        new RinaCpiSynchronizationService(rinaTenant, rinaCpiAuthenticationService, inboundHttpHeaders));
    }
    return map;
  }
  
}
