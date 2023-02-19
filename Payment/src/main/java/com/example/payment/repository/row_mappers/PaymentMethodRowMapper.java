package com.example.payment.repository.row_mappers;

import com.example.payment.model.CustomerPaymentMethod;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class PaymentMethodRowMapper implements RowMapper<CustomerPaymentMethod>
{

	@Override
	public CustomerPaymentMethod mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return new CustomerPaymentMethod(rs.getLong("id"),
										 rs.getLong("customer_id"),
										 rs.getString("payment_method_token"),
										 rs.getString("stripe_payment_method_id"));
	}
}
