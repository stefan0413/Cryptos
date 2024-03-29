package com.cryptos.apigateway.controllers.customers;

import com.cryptos.apigateway.jwt.JwtBlacklistService;
import com.cryptos.apigateway.model.customers.AuthenticationRequest;
import com.cryptos.apigateway.model.customers.FinaliseRegistrationRequest;
import com.cryptos.apigateway.model.customers.RegistrationRequest;
import com.cryptos.apigateway.service.customers.AuthenticationService;
import com.cryptos.apigateway.service.customers.RegistrationService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CustomersController
{
	private final RegistrationService registrationService;
	private final AuthenticationService authenticationService;
	private final JwtBlacklistService jwtBlacklistService;


	public CustomersController(RegistrationService registrationService,
							   AuthenticationService authenticationService, JwtBlacklistService jwtBlacklistService)
	{
		this.registrationService = registrationService;
		this.authenticationService = authenticationService;
		this.jwtBlacklistService = jwtBlacklistService;
	}

	@PostMapping("/public/log-in")
	public String authenticate(@RequestBody AuthenticationRequest authenticationRequest)
	{
		return authenticationService.authenticateCustomer(authenticationRequest);
	}

	@PostMapping("/public/sign-up")
	public Long registerCustomer(@RequestBody RegistrationRequest registrationRequest)
	{
		return registrationService.registerCustomer(registrationRequest);
	}

	@PostMapping("/public/finalise-registration/{customerId}")
	public String finaliseRegistration(@PathVariable Long customerId,
									   @RequestBody FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		return registrationService.finaliseRegistration(customerId, finaliseRegistrationRequest);
	}

	@PostMapping("/private/log-out")
	public void logOut(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken)
	{
		jwtBlacklistService.addTokenToBlacklist(jwtToken.substring(7));
	}
}
