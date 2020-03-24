package com.marcosbarbero.lab.sec.oauth.jwt;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OAuth2ServerJwtApplication {

    public static void main(String... args) {
        SpringApplication.run(OAuth2ServerJwtApplication.class, args);
    }


}
