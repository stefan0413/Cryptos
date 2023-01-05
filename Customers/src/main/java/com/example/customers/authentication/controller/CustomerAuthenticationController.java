package com.example.customers.authentication.controller;

import com.example.customers.authentication.config.JwtUtils;
import com.example.customers.authentication.exceptions.InvalidCredentialsException;
import com.example.customers.authentication.exceptions.NoSuchCustomerException;
import com.example.customers.authentication.model.AuthenticationRequest;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.service.CustomerService;
import com.example.customers.authentication.service.RegistrationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerAuthenticationController
{

	private final AuthenticationManager authenticationManager;
	private RegistrationService registrationService;
	private final CustomerService customerService;
	private final JwtUtils jwtUtils;

	public CustomerAuthenticationController(AuthenticationManager authenticationManager,
											CustomerService customerService,
											JwtUtils jwtUtils,
											RegistrationService registrationService)
	{
		this.authenticationManager = authenticationManager;
		this.customerService = customerService;
		this.jwtUtils = jwtUtils;
		this.registrationService = registrationService;
	}

	@PostMapping("/authentication")
	public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest authenticationRequest)
	{
		try
		{
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.email(),
															authenticationRequest.password())
											  );
			final UserDetails customer = customerService.loadUserByUsername(authenticationRequest.email());

			return ResponseEntity.ok(jwtUtils.generateToken(customer));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/register")
	public Customer registrateCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		return registrationService.registrateCustomer(registrationRequest);
	}

	@ExceptionHandler({InvalidCredentialsException.class, NoSuchCustomerException.class})
	public ResponseEntity handleClientBasedException(Exception ex)
	{
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleServerErrors(Exception ex)
	{
		return ResponseEntity.internalServerError().body(ex.getMessage());
	}
}
