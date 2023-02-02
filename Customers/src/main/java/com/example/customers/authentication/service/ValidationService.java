package com.example.customers.authentication.service;

import com.example.customers.authentication.exceptions.InvalidCredentialsException;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

public class ValidationService
{

	private static final String VALID_PASSWORD_REGEX_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
	private static final String VALID_MOBILE_NUMBER_REGEX_PATTERN = "^(?:\\+\\d{1,3}[- ]?)?\\d{9,12}$";
	private static final String VALID_ADDRESS_REGEX = "^[a-zA-Z,.]{1,64}$";
	private static final String VALID_NAME_REGEX = "^[a-zA-Z-.' ]+$";

	public static void validateRegistrationRequest(RegistrationRequest registrationRequest)
	{
		validateRegistrationRequestFieldsAreNotEmpty(registrationRequest);
		validateEmail(registrationRequest.email());
		validatePassword(registrationRequest.password());
	}

	public static void validateFinaliseRegistrationRequest(FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		validateFinaliseRegistrationRequestFieldsAreNotEmpty(finaliseRegistrationRequest);
		validateName(finaliseRegistrationRequest.firstName());
		validateName(finaliseRegistrationRequest.secondName());
		validateName(finaliseRegistrationRequest.lastName());
		validateMobileNumber(finaliseRegistrationRequest.mobileNumber());
		//validateAddress(finaliseRegistrationRequest.address());
	}

	private static void validateRegistrationRequestFieldsAreNotEmpty(RegistrationRequest registrationRequest)
	{
		if (registrationRequest.email() == null ||
			registrationRequest.password() == null )
		{
			throw (new InvalidCredentialsException("Invalid registration request! \nAll fields should be filled!"));
		}
	}

	private static void validateFinaliseRegistrationRequestFieldsAreNotEmpty(
			FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		if (finaliseRegistrationRequest.firstName() == null ||
			finaliseRegistrationRequest.secondName() == null ||
			finaliseRegistrationRequest.lastName() == null ||
			finaliseRegistrationRequest.mobileNumber() == null )
		{
			throw (new InvalidCredentialsException("Invalid finalise registration request! " +
												   "\nAll fields should be filled!"));
		}
	}

	private static void validatePassword(String password)
	{
		if (!Pattern.compile(VALID_PASSWORD_REGEX_PATTERN).matcher(password).matches())
		{
			throw new InvalidCredentialsException(
					"""
							Invalid password!
							Password should be:
							between 8 and 20 characters,
							containing at least one number,
							upper case letter,
							lower case letter and symbol.
							""");
		}
	}

	private static void validateEmail(String email)
	{
		if (!EmailValidator.getInstance().isValid(email))
		{
			throw new InvalidCredentialsException("Invalid email.");
		}
	}

	private static void validateMobileNumber(String mobileNumber)
	{
		if (!Pattern.compile(VALID_MOBILE_NUMBER_REGEX_PATTERN).matcher(mobileNumber).matches())
		{
			throw new InvalidCredentialsException("Invalid mobile number");
		}
	}

	private static void validateAddress(String address)
	{
		if (!Pattern.compile(VALID_ADDRESS_REGEX).matcher(address).matches())
		{
			throw new InvalidCredentialsException("Invalid address format");
		}
	}

	private static void validateName(String name)
	{
		if (!Pattern.compile(VALID_NAME_REGEX).matcher(name).matches())
		{
			throw new InvalidCredentialsException("Invalid name");
		}
	}
}
