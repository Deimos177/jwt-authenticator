package com.deimos.jwtaauthenticator.security;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.deimos.jwtaauthenticator.data.UserDetailsData;
import com.deimos.jwtaauthenticator.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {

	public static final String TOKEN_SECRET = "82537c99-2018-47a4-86a8-a9a05ebd52ba";
	private final Integer JWT_EXPIRATION_TIME = 600_000;
	public final Algorithm ALGORITHM = Algorithm.HMAC512(TOKEN_SECRET);

	private final AuthenticationManager authenticationManager;

	public JwtAuthenticatorFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
		} catch (IOException e) {

			throw new RuntimeException("Falha ao autenticar usuário", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		UserDetailsData userData = (UserDetailsData) authResult.getPrincipal();

		String accessToken = JWT.create().withSubject(userData.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
				.withClaim("roles", userData.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(ALGORITHM);

		response.getWriter().write(String.format("Access token: %s", accessToken));
	}
}