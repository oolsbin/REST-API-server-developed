package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.annotations.Contact;
import io.swagger.annotations.License;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
//swagger-ui title, version, description 설정
@OpenAPIDefinition(info = @Info(title = "the title", version = "0.0", description = "My API"))
public class WebfluxApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);

	}

}
