package com.example.payment.repository;

import com.example.payment.model.CustomerStripeAccountRequest;
import com.example.payment.repository.row_mappers.CustomerStripeAccountRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CustomerStripeAccountRepository
{

	private static final String BASE_SELECT_QUERY = "SELECT * FROM customer_stripe_accounts ";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final CustomerStripeAccountRowMapper customerStripeAccountRowMapper;

	public CustomerStripeAccountRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
										   CustomerStripeAccountRowMapper customerStripeAccountRowMapper)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.customerStripeAccountRowMapper = customerStripeAccountRowMapper;
	}

	public void save(CustomerStripeAccountRequest customerStripeAccountRequest)
	{
		String sql = """
    		    INSERT INTO customer_stripe_accounts (customer_id, free_balance, invested_balance, email, names)
    		    VALUES(:customer_id, :free_balance, :invested_balance, :email, :names)
				""";
		Map<String, Object> params = Map.of("customer_id", customerStripeAccountRequest.customerId(),
											"free_balance", customerStripeAccountRequest.freeBalance(),
											"invested_balance", customerStripeAccountRequest.investedBalance(),
											"email", customerStripeAccountRequest.email(),
											"names", customerStripeAccountRequest.names());
		namedParameterJdbcTemplate.update(sql, params);
	}

	public CustomerStripeAccountRequest getCustomerStripeAccountByCustomerId(long customerId)
	{
		String sql = BASE_SELECT_QUERY + "WHERE customer_id=:customer_id";
		return namedParameterJdbcTemplate.queryForObject(sql, Map.of("customer_id", customerId), customerStripeAccountRowMapper);
	}
}
