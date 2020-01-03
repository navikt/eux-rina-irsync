package no.nav.eux.rina.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;

@Configuration
public class ServletConfig {
  
  @Autowired
  RequestContextFilter filter;
  
  @Autowired
  DispatcherServlet servlet;
  
  @PostConstruct
  public void init() {
    // Normal mode
    filter.setThreadContextInheritable(true);
    
    // Debug mode
    servlet.setThreadContextInheritable(true);
    
    servlet.setThrowExceptionIfNoHandlerFound(true);
  }
}