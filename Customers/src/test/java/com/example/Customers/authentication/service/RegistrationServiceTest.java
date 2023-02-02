package com.example.customers.authentication.service;


import com.example.customers.authentication.config.JwtUtils;
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
	private CustomerService customerService;
	private JwtUtils jwtUtils;

	private RegistrationService registrationService;

	@BeforeEach
	void setUp()
	{
		customerRepository = Mockito.mock(CustomerRepository.class);
		customerService = Mockito.mock(CustomerService.class);
		jwtUtils = Mockito.mock(JwtUtils.class);

		registrationService = new RegistrationService(customerRepository, customerService, jwtUtils);
	}

	@Test
	void testRegisterCustomerWithValidRegistrationRequestShouldVerify()
	{
		when(customerRepository.registrateCustomer(any())).thenReturn(Optional.of(buildCustomer()));
		registrationService.registerCustomer(buildRegistrationRequest());

		verify(customerRepository).registrateCustomer(any());
	}

	private static RegistrationRequest buildRegistrationRequest()
	{
		return new RegistrationRequest("email@example.com",
									   "A!@#&()–a1");
	}

	private static Customer buildCustomer()
	{
		return new Customer(1,
							"email@example.com",
							"A!@#&()–a1",
							"John", "James", "Smith",
							"+359 888787654",
							true);
	}
}