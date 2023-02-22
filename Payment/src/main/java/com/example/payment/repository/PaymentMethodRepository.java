package com.example.payment.repository;

import com.example.payment.model.payment_method.CustomerPaymentMethod;
import com.example.payment.repository.row_mappers.PaymentMethodRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PaymentMethodRepository
{

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final PaymentMethodRowMapper paymentMethodRowMapper;

	public PaymentMethodRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PaymentMethodRowMapper paymentMethodRowMapper)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.paymentMethodRowMapper = paymentMethodRowMapper;
	}

	public void save(long customerId, String methodToken, String stripePaymentMethodId)
	{
		String sql = """
						    INSERT INTO customer_payment_method (customer_id, payment_method_token, stripe_payment_method_id)
						    VALUES(:customer_id, :payment_method_token, :stripe_payment_method_id)
				""";
		Map<String, Object> params = Map.of("customer_id", customerId,
											"payment_method_token", methodToken,
											"stripe_payment_method_id", stripePaymentMethodId);

		namedParameterJdbcTemplate.update(sql, params);
	}

	public List<CustomerPaymentMethod> getPaymentMethodsForCustomer(Long customerId)
	{
		String sql = "SELECT * FROM customer_payment_method WHERE customer_id = :customer_id";

		return namedParameterJdbcTemplate.query(sql, Map.of("customer_id", customerId), paymentMethodRowMapper);
	}
}
