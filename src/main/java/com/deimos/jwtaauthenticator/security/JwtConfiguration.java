package com.deimos.jwtaauthenticator.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.deimos.jwtaauthenticator.service.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
public class JwtConfiguration extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsServiceImpl detailsServiceImpl;
	private final PasswordEncoder encoder;
	
	public JwtConfiguration(UserDetailsServiceImpl detailsServiceImpl, PasswordEncoder encoder) {
		this.detailsServiceImpl = detailsServiceImpl;
		this.encoder = encoder;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		auth.userDetailsService(detailsServiceImpl).passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, "/login", "/security-poc/swagger-ui.html").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(new JwtValidateFilter(authenticationManager()))
		.addFilter(new JwtAuthenticatorFilter(authenticationManager()))
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}