package com.example.customers.authentication.service;

import com.example.customers.authentication.exceptions.InvalidCredentialsException;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

public class ValidationService
{

	private static final String VALID_MOBILE_NUMBER_REGEX_PATTERN = "^(?:\\+\\d{1,3}[- ]?)?\\d{9,12}$";
	private static final String VALID_NAME_REGEX = "^[a-zA-Z-.' ]+$";

	public static void validateRegistrationRequest(RegistrationRequest registrationRequest)
	{
		validateRegistrationRequestFieldsAreNotEmpty(registrationRequest);
		validateEmail(registrationRequest.email());
	}

	public static void validateFinaliseRegistrationRequest(FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		validateFinaliseRegistrationRequestFieldsAreNotEmpty(finaliseRegistrationRequest);
		validateName(finaliseRegistrationRequest.firstName());
		validateName(finaliseRegistrationRequest.secondName());
		validateName(finaliseRegistrationRequest.lastName());
		validateMobileNumber(finaliseRegistrationRequest.mobileNumber());
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

	private static void validateName(String name)
	{
		if (!Pattern.compile(VALID_NAME_REGEX).matcher(name).matches())
		{
			throw new InvalidCredentialsException("Invalid name");
		}
	}
}
