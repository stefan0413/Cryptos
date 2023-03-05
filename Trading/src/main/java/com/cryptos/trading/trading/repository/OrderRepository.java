package com.cryptos.trading.trading.repository;

import com.cryptos.trading.trading.model.Order;
import com.cryptos.trading.trading.model.OrderSaveRequest;
import com.cryptos.trading.trading.repository.row_mappers.OrderRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class OrderRepository
{

	private final static String BASE_SELECT_QUERY = """
			SELECT order.id as id, customer_id, n_supported_currencies.code as supported_currency, n_order_type.type as type, order_size, order_price, total_cost, n_order_statuses.name as status, created_at, executed_at
			FROM `order`
			LEFT JOIN n_order_statuses ON order.status_id = n_order_statuses.id
			LEFT JOIN n_supported_currencies ON order.supported_currency_id = n_supported_currencies.id
			LEFT JOIN n_order_type ON order.type_id = n_order_type.id
			""";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final OrderRowMapper orderRowMapper;

	public OrderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, OrderRowMapper orderRowMapper)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.orderRowMapper = orderRowMapper;
	}

	public long save(OrderSaveRequest orderSaveRequest)
	{
		String sql = """
				  INSERT INTO `order` (customer_id, supported_currency_id, type_id, order_size, order_price, status_id, total_cost, created_at)
				  VALUES (:customer_id,
				  		(SELECT id as supported_currency_id FROM n_supported_currencies WHERE code = :currency),
					   	(SELECT id as type_id FROM n_order_type WHERE type = :type),
					  	:order_size, :order_price,
					  	(SELECT id as status_id FROM n_order_statuses WHERE name = :status),
					  	:total_cost, :created_at)
				                                                                                           				                                                                                           
				""";
		final SqlParameterSource params = new MapSqlParameterSource()
				.addValue("customer_id", orderSaveRequest.customerId())
				.addValue("currency", orderSaveRequest.currency().name())
				.addValue("type", orderSaveRequest.type().name())
				.addValue("order_size", orderSaveRequest.orderSize())
				.addValue("order_price", orderSaveRequest.orderPrice())
				.addValue("status", orderSaveRequest.status().name())
				.addValue("total_cost", orderSaveRequest.totalCost())
				.addValue("created_at", orderSaveRequest.createdAt());

		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sql, params, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public Order getOrderById(long orderId)
	{
		String sql = BASE_SELECT_QUERY + " WHERE order.id = :order_id";

		return namedParameterJdbcTemplate.query(sql, Map.of("order_id", orderId), orderRowMapper).stream().findFirst().orElseThrow(()-> new NoSuchElementException());
	}

	public List<Order> getCustomerOrders(long customerId)
	{
		String sql = BASE_SELECT_QUERY + " WHERE order.customer_id = :customer_id";

		return namedParameterJdbcTemplate.query(sql, Map.of("customer_id", customerId), orderRowMapper);
	}

	public void updateStatus(long orderId, String status)
	{
		String sql = "UPDATE `order` SET status_id = (SELECT id FROM n_order_statuses WHERE name = :name) WHERE id = :order_id";

		Map<String, Object> params = Map.of("order_id", orderId,
											"name", status);

		namedParameterJdbcTemplate.update(sql, params);
	}
}
