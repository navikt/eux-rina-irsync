package no.nav.eux.rina.admin.rina.security;

import static no.nav.eux.rina.admin.rest.ExceptionUtil.unauthenticated;

import eu.ec.dgempl.eessi.rina.sdk.cpi.auth.CasAuthentication;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import no.nav.eux.rina.admin.config.RinaTenantProperties.RinaTenant;
import no.nav.eux.rina.admin.http.InboundHttpHeaders;
import no.nav.eux.rina.admin.http.InboundHttpHeaders.BasicAuthCredentials;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
public class RinaCpiAuthenticationClientInterceptor implements ClientHttpRequestInterceptor {
  private RinaCpiAuthenticationService rinaCpiAuthenticationService;
  private InboundHttpHeaders inboundHttpHeaders;
  private RinaTenant rinaTenant;

  public RinaCpiAuthenticationClientInterceptor(RinaTenant rinaTenant, RinaCpiAuthenticationService rinaCpiAuthenticationService, InboundHttpHeaders inboundHttpHeaders) {
    this.rinaTenant = rinaTenant;
    this.rinaCpiAuthenticationService = rinaCpiAuthenticationService;
    this.inboundHttpHeaders = inboundHttpHeaders;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
    log.debug("extracting basic auth credentials from inbound request in order to authenticate against rina");
    BasicAuthCredentials basicAuthCredentials = inboundHttpHeaders.basicAuthCredentials()
      .orElseThrow(unauthenticated("could not find basic auth header"));

    CasAuthentication casAuthentication = rinaCpiAuthenticationService
      .authenticate(basicAuthCredentials.getUsername(), basicAuthCredentials.getPassword(), rinaTenant);
    request.getHeaders()
      .add(HttpHeaders.COOKIE, String.format("%s=%s", CasAuthentication.COOKIE_JSESSIONID, casAuthentication.getJsessionId()));
    request.getHeaders().add(CasAuthentication.HEADER_XSRF_TOKEN, casAuthentication.getXsrfToken());
    return execution.execute(request, bytes);
  }
}
