package com.example.payment.repository.row_mappers;

import com.example.payment.model.CustomerStripeAccountRequest;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class CustomerStripeAccountRowMapper implements RowMapper<CustomerStripeAccountRequest>
{

	@Override public CustomerStripeAccountRequest mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return null;
	}
}
