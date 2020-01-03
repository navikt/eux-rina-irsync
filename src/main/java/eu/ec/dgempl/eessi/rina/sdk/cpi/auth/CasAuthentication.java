package eu.ec.dgempl.eessi.rina.sdk.cpi.auth;

import eu.ec.dgempl.eessi.sdk.cpi.auth.Authentication;
import eu.ec.dgempl.eessi.sdk.cpi.model.UserDto;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class CasAuthentication implements Authentication {
  private static final Logger logger = LogManager.getLogger(Authentication.class);
  public static final String COOKIE_JSESSIONID = "JSESSIONID";
  public static final String COOKIE_XSRF_TOKEN = "XSRF-TOKEN";
  public static final String COOKIE_XSRF_TOKEN_HEADER = "X-Auth-Cookie";
  public static final String HEADER_XSRF_TOKEN = "X-XSRF-TOKEN";
  public static final String PARAM_TICKET = "ticket";
  public static final String PARAM_SERVICE_ID = "serviceId";
  public static final String PARAM_SERVICE = "service";
  public static final String PARAM_PASSWORD = "password";
  public static final String PARAM_USERNAME = "username";
  private String ticketGrantingTicket;
  private String jsessionId;
  private String xsrfToken;
  private String username;
  private String password;
  private String casHost;
  private String casService;
  private String serviceBaseUrl;
  private UserDto authenticatedUser;
  private final RestTemplate restTemplate;
  
  public CasAuthentication(String username, String password, String casHost, String casService, String serviceBaseUrl, RestTemplate restTemplate) {
    this.username = username;
    this.password = password;
    this.casHost = casHost;
    this.casService = casService;
    this.serviceBaseUrl = serviceBaseUrl;
    this.restTemplate = restTemplate;
  }
  
  public void applyToParams(MultiValueMap<String, String> queryParams, HttpHeaders headerParams) {
    if (StringUtils.isBlank(this.getJsessionId())) {
      this.authenticate();
    }
    
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.add("Cookie", String.format("%s=%s;%s=%s;", "JSESSIONID", this.getJsessionId(), "XSRF-TOKEN", this.getXsrfToken()));
    requestHeaders.add("X-XSRF-TOKEN", this.getXsrfToken());
  }
  
  public void authenticate() {
    this.acceptAllCerts();
    logger.debug("Retrieving the TGT from CAS");
    MultiValueMap<String, String> params = new LinkedMultiValueMap();
    params.set("username", this.getUsername());
    params.set("password", this.getPassword());
    URI stUrl = this.restTemplate.postForLocation(this.casHost + "/v1/tickets", params, Collections.emptyMap());
    if (stUrl != null) {
      this.setTicketGrantingTicket(stUrl.toString().substring(stUrl.toString().lastIndexOf(47)));
    }
    
    logger.debug("Retrieving the ST from CAS");
    params.clear();
    params.set("service", this.casService);
    ResponseEntity<String> st = this.restTemplate.postForEntity(stUrl, params, String.class);
    logger.debug("Loging in into CPI with the ST");
    UriComponentsBuilder loginUrlBuilder = UriComponentsBuilder.fromHttpUrl(this.serviceBaseUrl + "/login/cas").queryParam("ticket", new Object[]{st.getBody()}).queryParam("serviceId", new Object[]{this.casService});
    ResponseEntity<UserDto> result = this.restTemplate.getForEntity(loginUrlBuilder.toUriString(), UserDto.class, new Object[0]);
    logger.debug("Setting the authentication parameters.");
    this.setJsessionId(this.getCookieValue(result.getHeaders(), "JSESSIONID"));
    this.setXsrfToken(result.getHeaders().getFirst("X-Auth-Cookie"));
    this.authenticatedUser = (UserDto)result.getBody();
  }
  
  public void logout() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", String.format("%s=%s;%s=%s;", "JSESSIONID", this.getJsessionId(), "XSRF-TOKEN", this.getXsrfToken()));
    headers.set("X-Auth-Cookie", this.getXsrfToken());
    HttpEntity<Void> request = new HttpEntity((Object)null, headers);
    this.restTemplate.postForEntity(String.format("%s/logout", this.serviceBaseUrl), request, Void.class, new Object[0]);
    this.restTemplate.delete(String.format("%s/v1/tickets/%s", this.casHost, this.getTicketGrantingTicket()), new Object[0]);
  }
  
  protected String getCookieValue(HttpHeaders httpHeaders, String cookieName) {
    Pattern cookieNamePattern = Pattern.compile(String.format("^%s=(.*?);", cookieName));
    List<String> cookies = httpHeaders.get("Set-Cookie");
    Iterator var5 = cookies.iterator();
    
    Matcher matcher;
    do {
      if (!var5.hasNext()) {
        return null;
      }
      
      String cookie = (String)var5.next();
      matcher = cookieNamePattern.matcher(cookie);
    } while(!matcher.find());
    
    return matcher.group(1);
  }
  
  private void acceptAllCerts() {
    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }
      
      public void checkClientTrusted(X509Certificate[] certs, String authType) {
      }
      
      public void checkServerTrusted(X509Certificate[] certs, String authType) {
      }
    }};
    
    try {
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init((KeyManager[])null, trustAllCerts, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    } catch (Exception var3) {
    }
    
  }
  
  public String getTicketGrantingTicket() {
    return this.ticketGrantingTicket;
  }
  
  public void setTicketGrantingTicket(String ticketGrantingTicket) {
    this.ticketGrantingTicket = ticketGrantingTicket;
  }
  
  public String getJsessionId() {
    return this.jsessionId;
  }
  
  public void setJsessionId(String jsessionId) {
    this.jsessionId = jsessionId;
  }
  
  public String getXsrfToken() {
    return this.xsrfToken;
  }
  
  public void setXsrfToken(String xsrfToken) {
    this.xsrfToken = xsrfToken;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public String getCasHost() {
    return this.casHost;
  }
  
  public void setCasHost(String casHost) {
    this.casHost = casHost;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public UserDto getAuthenticatedUser() {
    return this.authenticatedUser;
  }
  
  public RestTemplate getRestTemplate() {
    return this.restTemplate;
  }
}
