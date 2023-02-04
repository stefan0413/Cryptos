package com.example.customers.authentication.repository;

import com.example.customers.authentication.model.Customer;
import com.example.customers.authentication.model.FinaliseRegistrationRequest;
import com.example.customers.authentication.model.RegistrationRequest;
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
																			  "password")
																.usingGeneratedKeyColumns("id");
		;
	}

	public Optional<Customer> getCustomerById(long id)
	{
		final String sql = "SELECT * FROM customer WHERE id = :id";

		Map<String, Object> params = Map.of("id", id);
			return Optional.of(namedParameterJdbcTemplate.queryForObject(sql, params,
																				 new DataClassRowMapper<>(Customer.class)));
	}

	public Optional<Customer> getCustomerByEmail(String email)
	{
		final String sql = "SELECT * FROM customer WHERE email = :email";

		Map<String, Object> params = Map.of("email", email);

		return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params,
																			 new DataClassRowMapper<>(Customer.class)));
	}

	public Optional<Customer> registerCustomer(RegistrationRequest registrationRequest)
	{
		Map<String, Object> parameters = Map.of("email", registrationRequest.email(),
												"password", registrationRequest.password());

		long customerId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
		return getCustomerById(customerId);
	}

	public void finaliseRegistration(long customerId,FinaliseRegistrationRequest finaliseRegistrationRequest)
	{
		final String sql = "UPDATE customer SET " +
						   "first_name = :first_name, " +
						   "second_name = :second_name, " +
						   "last_name = :last_name, " +
						   "mobile_number = :mobile_number, " +
						   "active = TRUE " +
						   "WHERE id = :id";

		Map<String, Object> parameters = Map.of("id", customerId,
												"first_name", finaliseRegistrationRequest.firstName(),
												"second_name", finaliseRegistrationRequest.secondName(),
												"last_name", finaliseRegistrationRequest.lastName(),
												"mobile_number", finaliseRegistrationRequest.mobileNumber());

		namedParameterJdbcTemplate.update(sql, parameters);
	}
}
