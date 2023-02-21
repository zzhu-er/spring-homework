package com.example.springhomework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class SpringHomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringHomeworkApplication.class, args);
	}

	@Autowired
	private UserRepository repository;

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		List<User> allUsers = this.repository.findAll();
		Logger logger
				= Logger.getLogger(
				SpringHomeworkApplication.class.getName());

		logger.info("Number of users: " + allUsers.size());

		User newUser = User.builder().name("A").age(18L).build();
		logger.info("Saving new user...");

		this.repository.save(newUser);

		allUsers = this.repository.findAll();
		logger.info("Number of users: " + allUsers.size());
	}

}
