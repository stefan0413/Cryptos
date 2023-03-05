package com.example.payment.repository.row_mappers;

import com.example.payment.model.customer_stripe_account.CustomerWithFreeBalanceAndCurrencyResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerFreeBalanceWithCurrencyRowMapper implements RowMapper<CustomerWithFreeBalanceAndCurrencyResponse>
{

	@Override
	public CustomerWithFreeBalanceAndCurrencyResponse mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return new CustomerWithFreeBalanceAndCurrencyResponse(rs.getLong("customer_id"),
															 rs.getString("currency_code"),
															 rs.getBigDecimal("free_balance"));
	}
}
