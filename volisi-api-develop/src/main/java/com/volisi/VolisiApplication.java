package com.volisi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VolisiApplication {
  private static final Logger log = LoggerFactory.getLogger(VolisiApplication.class);

  public static void main(String[] args) {
    log.info("Volisi Application is started successfully.");
    SpringApplication.run(VolisiApplication.class, args);
  }
}
