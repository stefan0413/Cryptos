package com.example.customers;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")

public abstract class BaseIntegrationTest
{
	@Autowired
	protected TestRestTemplate testRestTemplate;

	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@AfterEach
	void tearDown()
	{
		namedParameterJdbcTemplate.update("delete from customer", Collections.emptyMap());
	}

}
