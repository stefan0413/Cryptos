package com.cryptos.apigateway.security.controllers;

import com.cryptos.apigateway.security.model.requests.AuthenticationRequest;
import com.cryptos.apigateway.security.model.requests.FinaliseRegistrationRequest;
import com.cryptos.apigateway.security.model.requests.RegistrationRequest;
import com.cryptos.apigateway.security.service.AuthenticationService;
import com.cryptos.apigateway.security.service.RegistrationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication/")
public class AuthenticationController
{
	private final RegistrationService registrationService;
	private final AuthenticationService authenticationService;


	public AuthenticationController(RegistrationService registrationService,
											AuthenticationService authenticationService)
	{
		this.registrationService = registrationService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/authenticate")
	public String authenticate(@RequestBody AuthenticationRequest authenticationRequest)
	{
		System.out.println("AuthenticationRequest: " + authenticationRequest);
		return authenticationService.authenticateCustomer(authenticationRequest);
	}

	@PostMapping("/register")
	public Long registerCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		return registrationService.registerCustomer(registrationRequest);
	}

	@PostMapping("/{customerId}/finalise-registration")
	public String finaliseRegistration(@PathVariable Long customerId,
												 @RequestBody FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		return registrationService.finaliseRegistration(customerId, finaliseRegistrationRequest);
	}

}
