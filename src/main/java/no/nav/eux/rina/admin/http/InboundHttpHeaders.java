package no.nav.eux.rina.admin.http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

@Component
//@RequestScope //@SessionScope ... no, maybe we need ApplicationScope
//@Scope (value= WebApplicationContext.SCOPE_SESSION, proxyMode= ScopedProxyMode.TARGET_CLASS)
@Scope (value= WebApplicationContext.SCOPE_REQUEST, proxyMode= ScopedProxyMode.TARGET_CLASS)
public class InboundHttpHeaders {

  private HttpServletRequest httpServletRequest;

  public InboundHttpHeaders(HttpServletRequest httpServletRequest) {
    this.httpServletRequest = httpServletRequest;
  }

  public String getHeader(String name){
    return httpServletRequest.getHeader(name);
  }

  public Optional<BasicAuthCredentials> basicAuthCredentials(){
    return Optional.ofNullable(getHeader(HttpHeaders.AUTHORIZATION))
      .filter(s -> s.startsWith("Basic "))
      .map(this::decodeHeader);
  }

  private BasicAuthCredentials decodeHeader(String header) {
      byte[] decoded = Base64.getDecoder().decode(header.substring(6));
      String token = new String(decoded, StandardCharsets.UTF_8);
      String[] usernamePwd = token.split(":");
      return usernamePwd.length == 2 ? new BasicAuthCredentials(usernamePwd[0],usernamePwd[1]) : null;
  }

  @Data
  @AllArgsConstructor
  public static class BasicAuthCredentials {
    private String username;
    private String password;
  }
}
