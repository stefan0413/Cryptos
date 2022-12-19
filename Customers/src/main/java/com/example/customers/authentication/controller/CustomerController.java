package com.example.customers.authentication.controller;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.CustomerRegistration;
import com.example.customers.authentication.service.CustomerService;
import com.example.customers.authentication.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/customer")
public class CustomerController
{

	private CustomerService customerService;
	private RegistrationService registrationService;

	public CustomerController(CustomerService customerService, RegistrationService registrationService)
	{
		this.customerService = customerService;
		this.registrationService = registrationService;
	}
	@GetMapping
	public Customer getCustomerById(@RequestParam Long customerId)
	{
		return customerService.getCustomerById(customerId);
	}

	@PostMapping("/registration")
	public Customer registrateCustomer(@RequestBody CustomerRegistration customerRegistration)
	{
		return registrationService.registrateCustomer(customerRegistration);
	}
}
