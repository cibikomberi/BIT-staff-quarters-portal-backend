package com.bitsathy.quarters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class QuartersApplication {

	// TODO : SET PASSWORD WHILE CREATING USER
	// TODO : CHANGE PASSWORD
	public static void main(String[] args) {
		SpringApplication.run(QuartersApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner runner (UserRepo userRepo, CompliantRepo compliantRepo){
	// 	return args -> {
			
	// 	};
	// }

}
