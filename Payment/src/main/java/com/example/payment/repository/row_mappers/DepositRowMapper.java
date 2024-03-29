package com.example.payment.repository.row_mappers;

import com.example.payment.model.deposit.Deposit;
import com.example.payment.model.deposit.DepositStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class DepositRowMapper implements RowMapper<Deposit>
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
						   rs.getTimestamp("executed_at") == null ? null :
						   LocalDateTime.ofInstant(rs.getTimestamp("executed_at").toInstant(),
												   ZoneId.systemDefault()));
	}
}
