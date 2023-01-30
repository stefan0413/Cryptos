package com.example.customers.authentication.service;

import com.example.customers.authentication.config.JwtUtils;
import com.example.customers.authentication.model.AuthenticationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class AuthenticationServiceTest
{
	private static String EMAIL = "email@example.com";
	private static String PASSWORD = "A!@#&()–a1";
	private AuthenticationManager authenticationManager;
	private CustomerService customerService;
	private JwtUtils jwtUtils;
	private AuthenticationService authenticationService;

	@BeforeEach
	void setUp()
	{
		authenticationManager = Mockito.mock(AuthenticationManager.class);
		customerService = Mockito.mock(CustomerService.class);
		jwtUtils = Mockito.mock(JwtUtils.class);

		authenticationService = new AuthenticationService(authenticationManager, customerService, jwtUtils);
	}

	@Test
	void testAuthenticateCustomerWhenRequestIsValid()
	{
		authenticationService.authenticateCustomer(buildAuthenticationRequest());

		verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(EMAIL,
																						   PASSWORD));

		verify(customerService).loadUserByUsername(EMAIL);
		verify(jwtUtils).generateToken(any());
	}

	private AuthenticationRequest buildAuthenticationRequest()
	{
		return new AuthenticationRequest(EMAIL, PASSWORD);
	}
}