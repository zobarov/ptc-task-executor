package com.gav.j17.ptc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PtcTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtcTaskApplication.class, args);
	}
}
