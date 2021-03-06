package no.nav.eux.rina.admin.rina.security;

import eu.ec.dgempl.eessi.rina.sdk.cpi.auth.CasAuthentication;
import lombok.extern.slf4j.Slf4j;
import no.nav.eux.rina.admin.config.RinaTenantProperties.RinaTenant;
import no.nav.eux.rina.admin.rest.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static no.nav.eux.rina.admin.config.CacheConfig.CPI_SESSION_CACHE;

@Slf4j
@Service
public class RinaCpiAuthenticationService {

  private final RestTemplate restTemplate;
  
  public RinaCpiAuthenticationService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
  }

  @Cacheable(CPI_SESSION_CACHE)
  public CasAuthentication authenticate(String username, String password, RinaTenant rinaTenant) {
    CasAuthentication casAuthentication = new CasAuthentication(
      username,
      password,
      rinaTenant.getCasBaseUrl(),
      rinaTenant.getCasServiceId(),
      rinaTenant.getCpiBaseUrl(),
      restTemplate);
    casAuthentication.authenticate();
    return casAuthentication;
  }
}

