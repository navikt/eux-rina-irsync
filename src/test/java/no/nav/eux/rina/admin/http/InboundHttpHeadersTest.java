package no.nav.eux.rina.admin.http;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import no.nav.eux.rina.admin.http.InboundHttpHeaders.BasicAuthCredentials;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

public class InboundHttpHeadersTest {

  @Mock
  private HttpServletRequest httpServletRequest;

  private InboundHttpHeaders inboundHttpHeaders;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    this.inboundHttpHeaders = new InboundHttpHeaders(httpServletRequest);
  }

  @Test
  public void getHeader() {
    when(httpServletRequest.getHeader(eq("myheader"))).thenReturn("someheader");
    assertEquals("someheader", inboundHttpHeaders.getHeader("myheader"));
  }

  @Test
  public void basicAuthCredentialsOk() {
    when(httpServletRequest.getHeader(eq(HttpHeaders.AUTHORIZATION)))
      .thenReturn("Basic " + Base64.getEncoder().encodeToString("foo:bar".getBytes(StandardCharsets.UTF_8)));

    assertEquals(new BasicAuthCredentials("foo", "bar"),inboundHttpHeaders.basicAuthCredentials().orElse(null));

  }
}