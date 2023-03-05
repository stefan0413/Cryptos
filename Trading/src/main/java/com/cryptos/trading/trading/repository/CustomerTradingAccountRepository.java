package com.cryptos.trading.trading.repository;

import com.cryptos.trading.trading.model.CustomerTradingAccount;
import com.cryptos.trading.trading.model.CustomerTradingAccountSaveRequest;
import com.cryptos.trading.trading.model.SupportedCurrency;
import com.cryptos.trading.trading.repository.row_mappers.CustomerTradingAccountRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerTradingAccountRepository
{

	private final static String BASE_SELECT_QUERY = "SELECT customer_trading_account.id as id, customer_id, n_supported_currencies.code as supported_currency, currency_amount FROM customer_trading_account " +
													"LEFT JOIN n_supported_currencies ON customer_trading_account.supported_currency_id = n_supported_currencies.id";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public CustomerTradingAccountRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public long save(CustomerTradingAccountSaveRequest customerTradingAccountSaveRequest)
	{
		String sql = "INSERT INTO customer_trading_account (customer_id, supported_currency_id, currency_amount) " +
					 "VALUES (:customer_id, (SELECT id from n_supported_currencies WHERE code = :currency), :currency_amount)";

		final SqlParameterSource params = new MapSqlParameterSource()
				.addValue("customer_id", customerTradingAccountSaveRequest.customerId())
				.addValue("currency", customerTradingAccountSaveRequest.supportedCurrency().name())
				.addValue("currency_amount", customerTradingAccountSaveRequest.currencyAmount());

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, params, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public List<CustomerTradingAccount> getCustomerTradingAccountsByCustomerId(long customerId)
	{
		String sql = BASE_SELECT_QUERY + " WHERE customer_id = :customer_id";

		return namedParameterJdbcTemplate.query(sql, Map.of("customer_id", customerId), new CustomerTradingAccountRowMapper());
	}

	public Optional<CustomerTradingAccount> getCustomerTradingAccountsByCustomerIdAndCurrency(long customerId, SupportedCurrency currency)
	{
		String sql = BASE_SELECT_QUERY + " WHERE customer_id = :customer_id";

		return namedParameterJdbcTemplate.query(sql, Map.of("customer_id", customerId, "currency", currency.name()), new CustomerTradingAccountRowMapper()).stream().filter(customerTradingAccount -> customerTradingAccount.supportedCurrency().equals(currency)).findFirst();
	}

	public void updateCurrencyAmount(long customerId, SupportedCurrency currency, BigDecimal currencyAmount)
	{
		String sql = "UPDATE customer_trading_account SET currency_amount = :currency_amount WHERE customer_id = :customer_id AND supported_currency_id = (SELECT n_supported_currencies.id from n_supported_currencies WHERE code = :currency)";

		final SqlParameterSource params = new MapSqlParameterSource()
				.addValue("customer_id", customerId)
				.addValue("currency", currency.name())
				.addValue("currency_amount", currencyAmount);

		namedParameterJdbcTemplate.update(sql, params);
	}
}
