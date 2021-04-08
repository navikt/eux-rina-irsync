package no.nav.eux.rina.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateConfig {
  @Value ("${cron.expression}")
  public String cronExpression;
  
  @Value ("${update.wait}")
  public long updateWait;
  
  @Bean
  public String cronExpression() {
    return cronExpression;
  }
  
  @Bean
  public long updateWait() {
    return updateWait;
  }
  
}
