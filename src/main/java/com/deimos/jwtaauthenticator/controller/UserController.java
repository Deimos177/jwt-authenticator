package com.deimos.jwtaauthenticator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deimos.jwtaauthenticator.dto.RequestAddRoleDto;
import com.deimos.jwtaauthenticator.service.UserService;
import com.deimos.jwtaauthenticatorentities.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "/listall", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<List<User>> listAllUsers() {

		return userService.listAll();
	}

	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> create(@RequestBody User user) {

		return userService.createUser(user);
	}

	@GetMapping(path = "/validatePassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Boolean> validatePassword(@RequestParam String login, @RequestParam String password) {

		return userService.validatePassword(login, password);
	}
	
	@PostMapping("/addRole")
	public ResponseEntity<Void> addRoleToUser(@RequestBody RequestAddRoleDto request){
		
		return userService.addRoleToUser(request.getLogin(), request.getRole());
	}
}