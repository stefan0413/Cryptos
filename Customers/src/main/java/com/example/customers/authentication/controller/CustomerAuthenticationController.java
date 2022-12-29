package com.example.customers.authentication.controller;

import com.example.customers.authentication.config.JwtUtils;
import com.example.customers.authentication.model.AuthenticationRequest;
import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.RegistrationRequest;
import com.example.customers.authentication.service.CustomerService;
import com.example.customers.authentication.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

	@PostMapping("/authenticate")
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

	@PostMapping("/registrate")
	public Customer registrateCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		return registrationService.registrateCustomer(registrationRequest);
	}

	@PostMapping()
	public String testEndpoint()
	{
		return "Jessss, you made itttttt. Suiiiiiiiiiii!!!!";
	}
}
