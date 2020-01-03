package no.nav.eux.rina.admin.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("EESSI - Rina Admin REST API")
      .select()
      .apis(RequestHandlerSelectors.basePackage("no.nav.eux.rina.admin"))
      .paths(regex("/.*"))
      .build()
      .apiInfo(metaData())
      .securitySchemes(Collections.singletonList(new BasicAuth("rinaAdminBasicAuth")))
      .securityContexts(Collections.singletonList(SecurityContext.builder()
        .securityReferences(
          Collections
            .singletonList(new SecurityReference("rinaAdminBasicAuth", new AuthorizationScope[0])))
        .build()));
  }

  private ApiInfo metaData() {
    return new ApiInfoBuilder()
      .title("EESSI - Rina IR SYNC REST API")
      .description("REST API to call RINA's REST API (CPI) IR sync operations")
      .contact(new Contact("Team EESSI NAV", "", "torsten.kirschner@nav.no"))
      .build();
  }
}