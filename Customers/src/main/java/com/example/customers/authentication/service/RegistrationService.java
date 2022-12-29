package com.example.customers.authentication.service;

import com.example.customers.authentication.exceptions.InvalidCredentialsException;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RegistrationService
{

	private static final String regexValidEmailPattern = "(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final String regexValidPasswordPattern = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";

	private CustomerRepository customerRepository;

	public RegistrationService(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
	}

	public Customer registrateCustomer(RegistrationRequest registrationRequest)
	{
		validateRegistrationRequest(registrationRequest);

		return customerRepository.registrateCustomer(getCustomerWithHashedPassword(registrationRequest)).get();
	}

	private static void validateRegistrationRequest(RegistrationRequest registrationRequest)
	{
		validateEmail(registrationRequest.email());
		//validatePassword(registrationRequest.password()); should be fixed
	}

	private static void validatePassword(String password)
	{
		if (!Pattern.compile(regexValidPasswordPattern).matcher(password).matches())
		{
			throw new InvalidCredentialsException("""
                    Invalid password. Password should be:
		          	  between 8 and 20 characters,
					  containing at least one number, upper case letter, lower case letter and symbol.
				    """);
		}
	}

	private static void validateEmail(String email)
	{
		if (!Pattern.compile(regexValidEmailPattern).matcher(email).matches())
		{
			throw new InvalidCredentialsException("Invalid email.");
		}
	}

	private RegistrationRequest getCustomerWithHashedPassword(RegistrationRequest registrationRequest)
	{
		return new RegistrationRequest(registrationRequest.email(),
									   BCrypt.hashpw(registrationRequest.password(), BCrypt.gensalt(10)),
									   registrationRequest.firstName(),
									   registrationRequest.secondName(),
									   registrationRequest.lastName(),
									   registrationRequest.mobileNumber());
	}
}
