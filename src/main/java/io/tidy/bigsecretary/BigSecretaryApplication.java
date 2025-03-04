package io.tidy.bigsecretary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class BigSecretaryApplication {

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    SpringApplication.run(BigSecretaryApplication.class, args);
  }
}
