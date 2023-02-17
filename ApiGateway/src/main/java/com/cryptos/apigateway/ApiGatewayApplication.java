package com.cryptos.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiGatewayApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
