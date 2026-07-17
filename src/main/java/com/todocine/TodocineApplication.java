package com.todocine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //CRON
public class TodocineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodocineApplication.class, args);
	}

}
