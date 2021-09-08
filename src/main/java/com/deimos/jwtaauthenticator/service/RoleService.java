package com.deimos.jwtaauthenticator.service;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.springframework.http.ResponseEntity;

import com.deimos.jwtaauthenticator.entities.Role;

public interface RoleService {
	
	ResponseEntity<Void> createRole(String rolename);
	ResponseEntity<Void> deleteRole(String rolename) throws RoleNotFoundException;
	ResponseEntity<Void> updateRole(String rolename, String oldRolename) throws RoleNotFoundException;
	ResponseEntity<List<Role>> findAllRoles();
}