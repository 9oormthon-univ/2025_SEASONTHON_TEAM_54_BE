package com.example.ssg_tab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SsgTabApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsgTabApplication.class, args);
	}

}
