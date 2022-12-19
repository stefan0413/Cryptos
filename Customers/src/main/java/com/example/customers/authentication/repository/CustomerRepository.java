package com.example.customers.authentication.repository;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.CustomerRegistration;
import jakarta.websocket.OnClose;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerRepository
{

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public CustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
							  DataSource dataSource)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("customer")
																.usingColumns("email",
																			  "password",
																			  "first_name",
																			  "second_name",
																			  "last_name",
																			  "mobile_number")
																.usingGeneratedKeyColumns("id");
		;
	}

	public Optional<Customer> getCustomerById(long id)
	{
		final String sql = "SELECT * FROM customer WHERE id = :id";

		Map<String, Object> params = Map.of("id", id);
		try
		{
			return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params,
																				 new DataClassRowMapper<>(Customer.class)));
		}
		catch (EmptyResultDataAccessException e)
		{
			return Optional.empty();
		}
	}

	public Optional<Customer> registrateCustomer(CustomerRegistration customerRegistration)
	{
		Map<String, Object> parameters = Map.of("email", customerRegistration.email(),
												"password", customerRegistration.password(),
												"first_name", customerRegistration.firstName(),
												"second_name", customerRegistration.secondName(),
												"last_name", customerRegistration.lastName(),
												"mobile_number", customerRegistration.mobileNumber());
		System.out.println("CustomerRegistration2: " + customerRegistration);
		long customerId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
		System.out.println("CustomerRegistration3: " + customerRegistration);
		return getCustomerById(customerId);
	}
}
