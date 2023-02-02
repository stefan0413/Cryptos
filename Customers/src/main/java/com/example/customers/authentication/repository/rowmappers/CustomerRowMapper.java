package com.example.customers.authentication.repository.rowmappers;

import com.example.customers.authentication.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer>
{

	@Override
	public Customer mapRow(ResultSet resultSet, int i) throws SQLException
	{
		return new Customer(resultSet.getLong("id"),
							resultSet.getString("email"),
							resultSet.getString("password"),
							resultSet.getString("first_name"),
							resultSet.getString("second_name"),
							resultSet.getString("last_name"),
							resultSet.getString("mobile_number"),
							resultSet.getBoolean("active")
		);
	}
}
