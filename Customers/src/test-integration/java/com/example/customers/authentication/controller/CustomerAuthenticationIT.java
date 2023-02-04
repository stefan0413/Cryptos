package com.example.customers.authentication.controller;

import com.example.customers.BaseIntegrationTest;
import com.example.customers.authentication.config.JwtUtils;
import com.example.customers.authentication.model.AuthenticationRequest;
import com.example.customers.authentication.model.CustomerResponse;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerAuthenticationIT extends BaseIntegrationTest
{

	private final String AUTHENTICATION_URL = "/api/v1/customers/authentication/authenticate";
	private final String REGISTRATION_URL = "/api/v1/customers/authentication/register";
	private final String FINALIZE_REGISTRATION_URL = "/api/v1/customers/authentication/{customerId}/finalise-registration";

	private final String EMAIL = "test@email.com";
	private final String PASSWORD = "W%mG9dt#jjni@x";

	@Autowired
	private CustomerRepository customerRepository;

	@MockBean
	private JwtUtils jwtUtils;

	@BeforeEach
	void setUp()
	{
		when(jwtUtils.generateToken(any())).thenReturn("jwtToken");
	}

	@Test
	void authenticateRegisteredCustomerShouldReturnJWT()
	{

		testRestTemplate.postForObject(REGISTRATION_URL, new RegistrationRequest(EMAIL, PASSWORD), CustomerResponse.class);
		CustomerResponse response = testRestTemplate.postForObject(AUTHENTICATION_URL, new AuthenticationRequest(EMAIL, PASSWORD), CustomerResponse.class);

		assertEquals("jwtToken", response.value());
	}

	@Test
	void authenticateNotExistingCustomerShouldReturnInvalid()
	{
		CustomerResponse authenticationResponse = testRestTemplate.postForObject(AUTHENTICATION_URL, new AuthenticationRequest(EMAIL, PASSWORD), CustomerResponse.class);

		assertFalse(authenticationResponse.isSuccessful());
		assertEquals("Invalid credentials", authenticationResponse.value());
	}
}
