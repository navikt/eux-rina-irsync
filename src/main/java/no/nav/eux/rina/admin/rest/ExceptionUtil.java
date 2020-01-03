package no.nav.eux.rina.admin.rest;

import java.util.function.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExceptionUtil {

  public static Supplier<ResponseStatusException> notFound(String reason){
    return ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, reason);
  }

  public static Supplier<ResponseStatusException> unauthenticated(String reason){ return ()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, reason); }
}
