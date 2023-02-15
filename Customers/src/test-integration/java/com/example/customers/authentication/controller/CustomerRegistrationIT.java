package com.example.customers.authentication.controller;

import com.example.customers.BaseIntegrationTest;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.CustomerResponse;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class CustomerRegistrationIT extends BaseIntegrationTest
{

	private final String REGISTRATION_URL = "/api/v1/customers/authentication/register";
	private final String FINALIZE_REGISTRATION_URL = "/api/v1/customers/authentication/{customerId}/finalise-registration";
	private final String EMAIL = "test@email.com";
	private final String PASSWORD = "W%mG9dt#jjni@x";

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void registerCustomerWithCorrectDataShouldSaveCustomer()
	{
		testRestTemplate.postForObject(REGISTRATION_URL, new RegistrationRequest(EMAIL, PASSWORD), CustomerResponse.class);
		Customer actualCustomer = customerRepository.getCustomerByEmail(EMAIL).get();

		assertTrue(areCustomersEqual(buildExpectedCustomer(), actualCustomer));
	}

	@Test
	void fullyRegisterCustomerWithCorrectDataShouldSaveCustomerAndFinalizeTheRegistration()
	{
		Long customerId = Long.valueOf(
				testRestTemplate.postForObject(REGISTRATION_URL,
											   new RegistrationRequest(EMAIL, PASSWORD),
											   CustomerResponse.class).value()
									  );

		String responseJWT = testRestTemplate.postForObject(FINALIZE_REGISTRATION_URL,
									   new FinaliseRegistrationRequest("firstName",
																	   "secondName",
																	   "lastName",
																	   "+359 888504407"),
									   CustomerResponse.class, customerId).value();

		Customer actualFinalisedCustomer = customerRepository.getCustomerById(customerId).get();

		assertTrue(areFinalisedCustomersEqual(buildExpectedFinalisedCustomer(), actualFinalisedCustomer));
		assertEquals("jwtToken", responseJWT);
	}

	private boolean areCustomersEqual(Customer expected, Customer actual)
	{
		return expected.email().equals(actual.email()) &&
			   expected.password().equals(actual.password()) &&
			   expected.active().equals(actual.active());
	}

	private boolean areFinalisedCustomersEqual(Customer expected, Customer actual)
	{
		return expected.email().equals(actual.email()) &&
			   expected.password().equals(actual.password())  &&
			   expected.firstName().equals(actual.firstName()) &&
			   expected.secondName().equals(actual.secondName()) &&
			   expected.lastName().equals(actual.lastName()) &&
			   expected.mobileNumber().equals(actual.mobileNumber()) &&
			   expected.active().equals(actual.active());
	}

	private Customer buildExpectedCustomer()
	{
		return new Customer(1, EMAIL, PASSWORD, null, null, null, null, false);
	}

	private Customer buildExpectedFinalisedCustomer()
	{
		return new Customer(1, EMAIL, PASSWORD, "firstName", "secondName", "lastName", "+359 888504407", true);
	}
}