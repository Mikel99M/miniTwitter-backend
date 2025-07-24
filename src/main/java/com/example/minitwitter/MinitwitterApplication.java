package com.example.minitwitter;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MinitwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinitwitterApplication.class, args);

	}

}
