package com.example.customers.authentication.config;

import com.example.customers.authentication.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig
{

	private final CustomerService customerService;
	private final JWTAuthenticationFilter jwtAuthenticationFilter;

	public SpringSecurityConfig(CustomerService customerService, JWTAuthenticationFilter jwtAuthenticationFilter)
	{
		this.customerService = customerService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

		authenticationProvider.setUserDetailsService(customerService);
		authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

		return authenticationProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.csrf().disable()
			.authorizeHttpRequests((request) -> request.requestMatchers(
					new AntPathRequestMatcher("/api/v1/customers/authentication/**")).permitAll()
					.anyRequest().authenticated())
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
