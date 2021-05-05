package no.nav.eux.rina.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties (prefix = "rina")
public class RinaTenantProperties {
  
  private List<RinaTenant> tenants = new ArrayList<>();
  
  @Data
  public static class RinaTenant {
    
    private String institutionId;
    private String casBaseUrl;
    private String casServiceId;
    private String cpiBaseUrl;
    private String adminuser;
    private String adminpwd;
  }
}
