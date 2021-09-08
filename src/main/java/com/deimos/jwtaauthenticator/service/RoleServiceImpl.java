package com.deimos.jwtaauthenticator.service;

import java.util.List;
import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.deimos.jwtaauthenticator.entities.Role;
import com.deimos.jwtaauthenticator.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public ResponseEntity<Void> createRole(String rolename) {
		Role role = new Role();
		role.setName(rolename);
		
		roleRepository.save(role);
		
		return ResponseEntity.created(null).build();
	}

	@Override
	public ResponseEntity<Void> deleteRole(String rolename) throws RoleNotFoundException {
		
		Optional<Role> optRole = roleRepository.findByName(rolename);
		
		if(optRole.isEmpty()) {
			throw new RoleNotFoundException("Role not found!!!");
		}
		
		Role role = optRole.get();
		
		roleRepository.delete(role);
		
		return ResponseEntity.ok(null);
	}

	@Override
	public ResponseEntity<Void> updateRole(String rolename, String oldRolename) throws RoleNotFoundException{
		Optional<Role> optRole = roleRepository.findByName(oldRolename);
		
		if(optRole.isEmpty()) {
			throw new RoleNotFoundException("Role not found!!!");
		}
		
		Role role = optRole.get();
		
		role.setName(rolename);
		
		roleRepository.save(role);
		
		return ResponseEntity.of(null);
	}

	@Override
	public ResponseEntity<List<Role>> findAllRoles() {

		return ResponseEntity.ok(roleRepository.findAll());
	}

}