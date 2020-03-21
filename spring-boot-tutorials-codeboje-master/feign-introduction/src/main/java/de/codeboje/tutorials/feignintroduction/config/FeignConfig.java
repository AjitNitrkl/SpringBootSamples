package de.codeboje.tutorials.feignintroduction.config;

import de.codeboje.tutorials.feignintroduction.client.FeignKanbanClient;
import de.codeboje.tutorials.feignintroduction.interceptor.FeignClientBasicAuthReqInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

//@Configuration
public class FeignConfig {

/*
creating bean here is not working need to find out why
so added the below code in main class
 */
  /*  @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return new FeignClientBasicAuthReqInterceptor("testmy1234","mytestpaswd");
    }*/
}
