package com.gav.j17.ptc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories(basePackageClasses = TaskRepository.class)
@SpringBootApplication
public class PtcTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtcTaskApplication.class, args);
	}
}
