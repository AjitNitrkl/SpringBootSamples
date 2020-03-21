package de.codeboje.tutorials.feignintroduction.interceptor;

import java.nio.charset.Charset;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import static feign.Util.ISO_8859_1;
import static feign.Util.checkNotNull;

/**
 * An interceptor that adds the request header needed to use HTTP basic authentication.
 */
@Component
public class FeignClientBasicAuthReqInterceptor implements RequestInterceptor {

  public FeignClientBasicAuthReqInterceptor(){
  }

  private  String headerValue;
  private static final String AUTHORIZATION_HEADER="Authorization";
  private static final String X_AUTH_TOKEN = "X-Auth-Token";


  public FeignClientBasicAuthReqInterceptor(String username, String password) {
    checkNotNull(username, "username");
    checkNotNull(password, "password");
    String authString = username + ":" + password;
    System.out.println("auth string: " + authString);
    byte[] authEncBytes = Base64Utils.encode(authString.getBytes());
    this.headerValue = "Basic " + new String(authEncBytes);
    System.out.println("Header val "+this.headerValue);
  }

  @Override
  public void apply(RequestTemplate template) {
    template.header(AUTHORIZATION_HEADER, headerValue);
    System.out.println("Template val "+template);
  }
}