package com.deimos.jwtaauthenticator.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponseDto {

	@JsonProperty("name")
	private String name;
	@JsonProperty("token")
	private String token;
	@JsonProperty("roles")
	private List<String> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}