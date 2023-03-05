package com.cryptos.trading.trading.repository.row_mappers;

import com.cryptos.trading.trading.model.CustomerTradingAccount;
import com.cryptos.trading.trading.model.SupportedCurrency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerTradingAccountRowMapper implements RowMapper<CustomerTradingAccount>
{

	@Override
	public CustomerTradingAccount mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		final CustomerTradingAccount account = new CustomerTradingAccount(
				rs.getLong("id"),
				rs.getLong("customer_id"),
				SupportedCurrency.valueOf(rs.getString("supported_currency")),
				rs.getBigDecimal("currency_amount")
		);


		return account;
	}
}
