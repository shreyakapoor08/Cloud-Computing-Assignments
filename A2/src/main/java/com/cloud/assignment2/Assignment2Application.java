package com.cloud.assignment2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.cloud.assignment2"
})
@EnableJpaRepositories
public class Assignment2Application {
	public static void main(String[] args) {
		SpringApplication.run(Assignment2Application.class, args);
	}
}
