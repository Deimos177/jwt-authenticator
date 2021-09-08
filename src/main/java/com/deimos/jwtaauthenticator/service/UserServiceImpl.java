package com.deimos.jwtaauthenticator.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deimos.jwtaauthenticator.entities.Role;
import com.deimos.jwtaauthenticator.entities.User;
import com.deimos.jwtaauthenticator.repository.RoleRepository;
import com.deimos.jwtaauthenticator.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public ResponseEntity<List<User>> listAll() {
		return ResponseEntity.ok(userRepository.findAll());
	}

	@Override
	public ResponseEntity<User> createUser(User user) {

		logger.info(String.format("Saving new user: %s on database", user.getLogin()));

		user.setPassword(encoder.encode(user.getPassword()));
		
		userRepository.save(user);
		return ResponseEntity.created(null).body(userRepository.save(user));
	}

	@Override
	public ResponseEntity<Boolean> validatePassword(String login, String password) {

		logger.info(String.format("Validating user: %s", login));

		Optional<User> optUser = userRepository.findUserByLogin(login);

		if (optUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}

		Boolean valid = encoder.matches(password, optUser.get().getPassword());
		HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

		return ResponseEntity.status(status).body(valid);
	}

	@Override
	public ResponseEntity<Void> addRoleToUser(String username, String roleName) {

		logger.info(String.format("Adding role %s to user %s", roleName, username));
		
		Optional<User> optUser = userRepository.findUserByLogin(username);
		Optional<Role> optRole = roleRepository.findByName(roleName);

		if (optUser.isEmpty() || optRole.isEmpty()) {
			throw new UsernameNotFoundException("usuário ou permissão não encontrado!");
		}

		User user = optUser.get();
		Role role = optRole.get();

		user.getRoles().add(role);
		
		return ResponseEntity.created(null).build();
	}
}