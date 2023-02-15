package com.example.customers.authentication.controller;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.CustomerData;
import com.example.customers.authentication.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/customers/")
public class CustomerController
{

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService)
	{
		this.customerService = customerService;
	}

	@GetMapping("/{customerId}")
	private Customer getCustomerById(@PathVariable Long customerId)
	{
		return customerService.getCustomerById(customerId);
	}

	@GetMapping("/search")
	private Customer getCustomerByEmail(@RequestParam String email)
	{
		System.out.println("Email: " + email);
		return customerService.getCustomerByEmail(email);
	}

	@GetMapping("/data/{customerId}")
	private CustomerData getCustomerDataById(@PathVariable long customerId)
	{
		return customerService.getCustomerDataById(customerId);
	}
}
