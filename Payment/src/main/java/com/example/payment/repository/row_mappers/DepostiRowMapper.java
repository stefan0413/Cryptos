package com.example.payment.repository.row_mappers;

import com.example.payment.model.CustomerStripeAccount;
import com.example.payment.model.Deposit;
import com.example.payment.model.DepositStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DepostiRowMapper implements RowMapper<Deposit>
{

	@Override public Deposit mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return new Deposit(rs.getLong("id"),
						   rs.getLong("customer_id"),
						   rs.getString("stripe_payment_method_id"),
						   rs.getString("stripe_payment_intent_id"),
						   rs.getBigDecimal("amount"),
						   rs.getString("currency_code"),
						   DepositStatus.valueOf(rs.getString("status")),
						   LocalDateTime.ofInstant(rs.getTimestamp("created_at").toInstant(),
												   ZoneId.systemDefault()),
						   LocalDateTime.ofInstant(rs.getTimestamp("executed_at").toInstant(),
												   ZoneId.systemDefault()));
	}
}
