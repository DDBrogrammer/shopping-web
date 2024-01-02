package edu.daidp.shoppingwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ShoppingWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingWebAppApplication.class, args);
	}

}
