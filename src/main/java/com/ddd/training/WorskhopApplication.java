package com.ddd.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = { "com.ddd.training.application" })
public class WorskhopApplication {

	public static void main(final String[] args) {
		SpringApplication.run(WorskhopApplication.class, args);
	}

}
