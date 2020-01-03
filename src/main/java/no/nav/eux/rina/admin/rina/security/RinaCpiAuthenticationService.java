package no.nav.eux.rina.admin.rina.security;

import static no.nav.eux.rina.admin.config.CacheConfig.CPI_SESSION_CACHE;

import eu.ec.dgempl.eessi.rina.sdk.cpi.auth.CasAuthentication;
import lombok.extern.slf4j.Slf4j;
import no.nav.eux.rina.admin.config.RinaTenantProperties.RinaTenant;
import no.nav.eux.rina.admin.rest.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RinaCpiAuthenticationService {

  private static final String CAS_SERVICE_ID = "../portal/cas/cpi";
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
      CAS_SERVICE_ID,
      rinaTenant.getCpiBaseUrl(),
      restTemplate);
    casAuthentication.authenticate();
    return casAuthentication;
  }
}

