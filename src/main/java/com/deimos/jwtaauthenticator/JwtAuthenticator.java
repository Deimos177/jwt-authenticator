package com.deimos.jwtaauthenticator;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deimos.jwtaauthenticator.entities.User;
import com.deimos.jwtaauthenticator.service.RoleService;
import com.deimos.jwtaauthenticator.service.UserService;

@SpringBootApplication
public class JwtAuthenticator {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticator.class, args);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder;
	}
	
	//Create user and roles when the application has spring security enabled
	
	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService) {
		return args -> {
			roleService.createRole("ROLE_USER");
			roleService.createRole("ROLE_MANAGER");
			roleService.createRole("ROLE_ADMIN");
			roleService.createRole("ROLE_SUPER_ADMIN");
			
			userService.createUser(new User(null, "john doe", "Test@1234", new ArrayList<>()));
			
			userService.addRoleToUser("john doe", "ROLE_USER");
			userService.addRoleToUser("john doe", "ROLE_MANAGER");
			userService.addRoleToUser("john doe", "ROLE_ADMIN");
			userService.addRoleToUser("john doe", "ROLE_SUPER_ADMIN");
		};
	}

}
