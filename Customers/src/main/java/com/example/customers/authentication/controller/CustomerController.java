package com.example.customers.authentication.controller;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/customer")
public class CustomerController
{
	private CustomerService customerService;

	public CustomerController(CustomerService customerService)
	{
		this.customerService = customerService;
	}

	@GetMapping
	public Customer getCustomerById(@RequestParam Long customerId)
	{
		return customerService.getCustomerById(customerId);
	}
}
