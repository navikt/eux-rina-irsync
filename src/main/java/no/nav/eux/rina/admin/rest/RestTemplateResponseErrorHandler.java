package no.nav.eux.rina.admin.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
  
  @Override
  public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
    return (httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR);
  }
  
  @Override
  public void handleError(ClientHttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusCode().series() == SERVER_ERROR) {
      log.error("Server error: {} {}", httpResponse.getStatusCode(), httpResponse.getStatusText());
    } else if (httpResponse.getStatusCode().series() == CLIENT_ERROR) {
      log.error("Client error: {} {}", httpResponse.getStatusCode(), httpResponse.getStatusText());
      if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
        log.error("Response error: {} {}", httpResponse.getStatusCode(), httpResponse.getStatusText());
      }
    }
  }
}