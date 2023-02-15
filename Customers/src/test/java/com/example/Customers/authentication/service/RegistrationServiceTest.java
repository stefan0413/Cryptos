package com.example.customers.authentication.service;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegistrationServiceTest
{

	private CustomerRepository customerRepository;

	private RegistrationService registrationService;
	private CustomerService customerService;

	@BeforeEach
	void setUp()
	{
		customerRepository = Mockito.mock(CustomerRepository.class);

		registrationService = new RegistrationService(customerRepository, customerService);
	}

	@Test
	void testRegisterCustomerWithValidRegistrationRequestShouldVerify()
	{
		when(customerRepository.registerCustomer(any())).thenReturn(1L);
		registrationService.registerCustomer(buildRegistrationRequest());

		verify(customerRepository).registerCustomer(any());
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