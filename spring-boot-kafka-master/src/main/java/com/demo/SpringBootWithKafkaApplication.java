package com.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootWithKafkaApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootWithKafkaApplication.class, args);
	}

	@Bean
	public NewTopic adviceTopic() {
		return new NewTopic("testtopic", 3, (short) 1);
	}
}
