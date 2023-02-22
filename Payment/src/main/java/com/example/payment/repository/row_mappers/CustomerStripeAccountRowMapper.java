package com.example.payment.repository.row_mappers;

import com.example.payment.model.customer_stripe_account.CustomerStripeAccount;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class CustomerStripeAccountRowMapper implements RowMapper<CustomerStripeAccount>
{

	@Override public CustomerStripeAccount mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return new CustomerStripeAccount(rs.getLong("id"),
										 rs.getLong("customer_id"),
										 rs.getString("currency_code"),
										 rs.getBigDecimal("free_balance"),
										 rs.getBigDecimal("invested_balance"),
										 rs.getString("email"),
										 rs.getString("names"),
										 List.of());
	}
}
