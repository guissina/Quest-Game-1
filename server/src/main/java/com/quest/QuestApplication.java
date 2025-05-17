package com.quest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestApplication.class, args);
	}
}
