package com.example.customers.authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig
{

	@Bean
	public OpenAPI customOpenAPI(@Value("${spring.application.name}") String appName)
	{
		Server server = new Server();
		server.setUrl(String.format("/%s", appName));
		return new OpenAPI().servers(List.of(server));
	}
}