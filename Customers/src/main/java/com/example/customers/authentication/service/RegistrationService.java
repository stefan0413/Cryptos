package com.example.customers.authentication.service;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.CustomerRegistration;
import com.example.customers.authentication.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RegistrationService
{

	private static final String regexValidEmailPattern = "(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	private CustomerRepository customerRepository;

	public RegistrationService(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
	}

	public Customer registrateCustomer(CustomerRegistration customerRegistration)
	{

//		CustomerRegistration customerWithHashedPass = new CustomerRegistration(customerRegistration.email(),
//																			   BCrypt.hashpw(customerRegistration.password(), BCrypt.gensalt(10)),
//																			   customerRegistration.firstName(),
//																			   customerRegistration.secondName(),
//																			   customerRegistration.lastName(),
//																			   customerRegistration.mobileNumber());

		return customerRepository.registrateCustomer(customerRegistration).get();
	}


	private static boolean emailIsValid(String email)
	{
		return Pattern.compile(regexValidEmailPattern).matcher(email).matches();
	}
}
