package com.deimos.jwtaauthenticator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestAddRoleDto {

	@JsonProperty("role")
	private String role;
	@JsonProperty("login")
	private String login;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}