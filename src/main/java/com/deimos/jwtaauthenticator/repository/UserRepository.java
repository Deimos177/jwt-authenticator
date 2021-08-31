package com.deimos.jwtaauthenticator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deimos.jwtaauthenticatorentities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findUserByLogin(String login);
}