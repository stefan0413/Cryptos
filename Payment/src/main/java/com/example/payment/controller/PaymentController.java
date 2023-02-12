package com.example.payment.controller;

import com.example.payment.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/payment")
public class PaymentController
{

	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService)
	{
		this.paymentService = paymentService;
	}

	@PostMapping("/create")
	public String createPaymentIntent(@RequestParam Integer amount,
									  @RequestParam String currency) throws Exception
	{
		return paymentService.createPaymentIntent(amount, currency);
	}

	@PostMapping("/confirm")
	public String confirmPaymentIntent(@RequestParam String id) throws Exception
	{
		return paymentService.confirmPaymentIntent(id);
	}
}
