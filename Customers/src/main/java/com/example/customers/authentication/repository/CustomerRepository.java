package com.example.customers.authentication.repository;

import com.example.customers.authentication.model.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerRepository
{
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public CustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
}
