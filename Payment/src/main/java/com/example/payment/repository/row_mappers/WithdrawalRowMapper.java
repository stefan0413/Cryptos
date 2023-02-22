package com.example.payment.repository.row_mappers;

import com.example.payment.model.withdrawal.Withdrawal;
import com.example.payment.model.withdrawal.WithdrawalStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class WithdrawalRowMapper implements RowMapper<Withdrawal>
{
	@Override public Withdrawal mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return new Withdrawal(rs.getLong("id"),
							  rs.getLong("customer_id"),
							  rs.getBigDecimal("amount"),
							  rs.getString("iban"),
							  WithdrawalStatus.valueOf(rs.getString("status")),
							  LocalDateTime.ofInstant(rs.getTimestamp("created_at").toInstant(),
												   ZoneId.systemDefault()));
	}
}
