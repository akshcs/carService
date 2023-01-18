package com.assignment.converzai.carService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan
public class CarServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

}
