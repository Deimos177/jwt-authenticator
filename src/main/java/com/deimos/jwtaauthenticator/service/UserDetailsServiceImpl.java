package com.deimos.jwtaauthenticator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.deimos.jwtaauthenticator.data.UserDetailsData;
import com.deimos.jwtaauthenticator.repository.UserRepository;
import com.deimos.jwtaauthenticatorentities.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findUserByLogin(username);
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException(String.format("Usuário %s não encontrado", username));
		}
		
		return new UserDetailsData(user);
	}
}