package no.nav.eux.rina.admin.http;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/* This class is only used by {@link no.nav.eux.rina.admin.rest.RinaAutomaticUpdatesController.Class } to all itself with correct username and password */

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {
  @Value("${server.port}")
  private String serverPort;
  @Value("${server.address}")
  private String serverAddress;
  @Value("${rina.tenants.[0].adminuser}")
  private String adminuser;
  @Value("${rina.tenants.[0].adminpwd}")
  private String adminpwd;
  
  private RestTemplate restTemplate;
  
  public RestTemplateFactory() {
    super();
  }
  
  @Override
  public RestTemplate getObject() {
    return restTemplate;
  }
  
  @Override
  public Class<RestTemplate> getObjectType() {
    return RestTemplate.class;
  }
  
  @Override
  public boolean isSingleton() {
    return true;
  }
  
  @Override
  public void afterPropertiesSet() {

    HttpHost host = new HttpHost(serverAddress, Integer.valueOf(serverPort), "http");
    final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(host);
    restTemplate = new RestTemplate(requestFactory);
    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(adminuser, adminpwd));
  }
}