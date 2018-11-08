package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Demo1Application {
  @SuppressWarnings("unused")
  public static void main(String[] args) throws Exception {
    ApplicationContext applicationContext = new SpringApplicationBuilder(Demo1Application.class).web(true).run(args);
  }
}
