package com.example.springhomework.configuration;

import com.example.springhomework.utilities.Generated;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Generated
@Configuration
public class FeignConfig {

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor("admin", "123456");
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.HEADERS;
  }
}
