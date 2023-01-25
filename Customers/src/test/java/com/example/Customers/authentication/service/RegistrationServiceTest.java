package com.example.customers.authentication.service;


import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegistrationServiceTest
{

	private CustomerRepository customerRepository;
	private AuthenticationService authenticationService;

	private RegistrationService registrationService;

	@BeforeEach
	void setUp()
	{
		authenticationService = Mockito.mock(AuthenticationService.class);
		customerRepository = Mockito.mock(CustomerRepository.class);

		registrationService = new RegistrationService(customerRepository, authenticationService);
	}

	@Test
	void testRegisterCustomerWithValidRegistrationRequestShouldVerify()
	{
		when(customerRepository.registrateCustomer(any())).thenReturn(Optional.of(buildCustomer()));
		registrationService.registerCustomer(buildRegistrationRequest());

		verify(customerRepository).registrateCustomer(any());
		verify(authenticationService).authenticateCustomer(any());
	}

	private static RegistrationRequest buildRegistrationRequest()
	{
		return new RegistrationRequest("email@example.com",
									   "A!@#&()–a1",
									   "John", "James", "Smith",
									   "+359 888787654");
	}

	private static Customer buildCustomer()
	{
		return new Customer(1,
							"email@example.com",
							"A!@#&()–a1",
							"John", "James", "Smith",
							"+359 888787654");
	}
}