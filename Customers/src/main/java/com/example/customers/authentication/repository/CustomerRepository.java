package com.example.customers.authentication.repository;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.RegistrationRequest;
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

	public Optional<Customer> getCustomerByEmail(String email)
	{
		final String sql = "SELECT * FROM customer WHERE email = :email";

		Map<String, Object> params = Map.of("email", email);

		return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params,
																			 new DataClassRowMapper<>(Customer.class)));
	}

	public Optional<Customer> registrateCustomer(RegistrationRequest registrationRequest)
	{
		Map<String, Object> parameters = Map.of("email", registrationRequest.email(),
												"password", registrationRequest.password(),
												"first_name", registrationRequest.firstName(),
												"second_name", registrationRequest.secondName(),
												"last_name", registrationRequest.lastName(),
												"mobile_number", registrationRequest.mobileNumber());
		long customerId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
		return getCustomerById(customerId);
	}
}
