package com.bitsathy.quarters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class QuartersApplication {

	// TODO : UPDATE ACTIVE COUNT
	// TODO : Designation in user update
	// TODO : USERNAME must be unique
	public static void main(String[] args) {
		SpringApplication.run(QuartersApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner runner (UserRepo userRepo, CompliantRepo compliantRepo){
	// 	return args -> {
			
	// 	};
	// }

}
