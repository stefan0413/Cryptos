package com.example.customers.authentication.service;

import com.example.customers.authentication.exceptions.InvalidCredentialsException;
import com.example.customers.authentication.model.RegistrationRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest
{

	@ParameterizedTest
	@MethodSource("validEmails")
	void testValidateRegistrationRequestWhenInvalidEmailShouldThrow(String email)
	{
		assertDoesNotThrow(() ->
				ValidationService.validateRegistrationRequest(buildRegistrationRequest(email,"A!@#&()–a1", "name", "+359 888787654")));
	}
	@ParameterizedTest
	@MethodSource("invalidEmails")
	void testValidateRegistrationRequestWhenRegistrationRequestIsNotShouldThrow(String email)
	{
		assertThrows(InvalidCredentialsException.class, () ->
				ValidationService.validateRegistrationRequest(buildRegistrationRequest(email,"A!@#&()–a1", "name", "+359 888787654")));
	}

	private RegistrationRequest buildRegistrationRequest(String email, String password, String name, String mobileNumber)
	{
		return new RegistrationRequest(email,
									   password);
	}

	private static Stream<String> validEmails(){
		return Stream.of("email@example.com",
						 "firstname.lastname@example.com",
						 "email@subdomain.example.com",
						 "firstname+lastname@example.com",
						 "email@[123.123.123.123]",
						 "“email”@example.com",
						 "1234567890@example.com",
						 "email@example-one.com",
						 "_______@example.com",
						 "email@example.name",
						 "email@example.museum",
						 "email@example.co.jp",
						 "firstname-lastname@example.com");
	}

	private static Stream<String> invalidEmails(){
		return Stream.of("plainaddress",
						 "#@%^%#$@#$@#.com",
						 "@example.com",
						 "Joe Smith <email@example.com>",
						 "email.example.com",
						 "email@example@example.com",
						 ".email@example.com",
						 "email.@example.com",
						 "email..email@example.com",
						 "email@example.com (Joe Smith)",
						 "email@example",
						 "email@-example.com",
						 "email@example.web",
						 "email@111.222.333.44444",
						 "email@example..com",
						 "Abc..123@example.com");
	}
}