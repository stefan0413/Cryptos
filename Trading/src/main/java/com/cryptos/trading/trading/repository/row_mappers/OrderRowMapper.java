package com.cryptos.trading.trading.repository.row_mappers;

import com.cryptos.trading.trading.model.Order;
import com.cryptos.trading.trading.model.OrderStatus;
import com.cryptos.trading.trading.model.OrderType;
import com.cryptos.trading.trading.model.SupportedCurrency;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class OrderRowMapper implements RowMapper<Order>
{

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		return new Order(rs.getLong("id"),
						 rs.getLong("customer_id"),
						 SupportedCurrency.valueOf(rs.getString("supported_currency")),
						 OrderType.valueOf(rs.getString("type")),
						 rs.getBigDecimal("order_size"),
						 rs.getBigDecimal("order_price"),
						 OrderStatus.valueOf(rs.getString("status")),
						 rs.getBigDecimal("total_cost"),
						 LocalDateTime.ofInstant(rs.getTimestamp("created_at").toInstant(),
												 ZoneId.systemDefault()),
						 rs.getTimestamp("executed_at") == null ? null :
								 LocalDateTime.ofInstant(rs.getTimestamp("executed_at").toInstant(),
														 ZoneId.systemDefault()));
	}
}
