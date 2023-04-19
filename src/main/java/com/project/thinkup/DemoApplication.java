package com.project.thinkup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.project.thinkup.model.User;
import com.project.thinkup.service.UserService;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		return (args) -> {

			System.out.println("\nMi usuario");
			userService.addUser(new User("Alejandro", "Huertas", "steven.huertas@mail.escuelaing.edu.co", "12345678",
					"up", "admin", "sistemas"));
			userService.addUser(new User("Andres", "Oñate", "andres.onate@mail.escuelaing.edu.co", "12345678", "up",
					"user", "sistemas"));

			System.out.println("\nGetting all Users....");
			userService.getAllUsers().forEach(User -> System.out.println(User));

		};
	}

}
