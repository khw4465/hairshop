package com.example.hairshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HairshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(HairshopApplication.class, args);
	}

}
