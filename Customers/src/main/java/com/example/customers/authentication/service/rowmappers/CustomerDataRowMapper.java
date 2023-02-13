package com.example.customers.authentication.service.rowmappers;

import com.example.customers.authentication.model.CustomerData;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class CustomerDataRowMapper implements RowMapper<CustomerData>
{

	@Override
	public CustomerData mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return new CustomerData(rs.getLong("id"),
								rs.getString("email"),
								rs.getString("first_name"),
								rs.getString("second_name"),
								rs.getString("last_name"),
								rs.getString("mobile_number"));
	}
}
