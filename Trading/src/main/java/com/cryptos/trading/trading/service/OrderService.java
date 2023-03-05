package com.cryptos.trading.trading.service;

import com.cryptos.trading.trading.exception.PaymentsException;
import com.cryptos.trading.trading.model.CustomerTradingAccount;
import com.cryptos.trading.trading.model.OrderRequest;
import com.cryptos.trading.trading.model.OrderSaveRequest;
import com.cryptos.trading.trading.model.OrderStatus;
import com.cryptos.trading.trading.model.OrderType;
import com.cryptos.trading.trading.model.SupportedCurrency;
import com.cryptos.trading.trading.model.payments.CustomerWithFreeBalanceAndCurrencyResponse;
import com.cryptos.trading.trading.repository.CustomerTradingAccountRepository;
import com.cryptos.trading.trading.repository.OrderRepository;
import com.cryptos.trading.trading.rest.PaymentsRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService
{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final OrderRepository orderRepository;
	private final CustomerTradingAccountService customerTradingAccountService;
	private final CustomerTradingAccountRepository customerTradingAccountRepository;
	private final PaymentsRestService paymentsRestService;
	private final OrderValidationService orderValidationService;

	public OrderService(OrderRepository orderRepository, CustomerTradingAccountService customerTradingAccountService, CustomerTradingAccountRepository customerTradingAccountRepository, PaymentsRestService paymentsRestService, OrderValidationService orderValidationService)
	{
		this.orderRepository = orderRepository;
		this.customerTradingAccountService = customerTradingAccountService;
		this.customerTradingAccountRepository = customerTradingAccountRepository;
		this.paymentsRestService = paymentsRestService;
		this.orderValidationService = orderValidationService;
	}

	public void makeOrder(long customerId, OrderRequest orderRequest)
	{

		BigDecimal currentPrice = currencyCurrentPrice(orderRequest.supportedCurrency());
		BigDecimal totalOrderCost = calculateTotalCost(orderRequest.orderSize(), currentPrice);

		long orderId = saveOrder(customerId, orderRequest, currentPrice, totalOrderCost);
		logger.info(String.format("Order successfully stored: orderId {%d}", orderId));
		try
		{
			executeOrder(orderId, customerId, orderRequest, totalOrderCost);
		}catch (Exception ex)
		{
			logger.error("Error while executing order: " + orderId, ex);
			orderRepository.updateStatus(orderId, OrderStatus.FAILED_EXECUTION.name());
			return;
		}
		logger.info(String.format("Order successfully executed: orderId {%d}", orderId));
		orderRepository.updateStatus(orderId, OrderStatus.EXECUTED.name());
	}

	private void executeOrder(long orderId, long customerId, OrderRequest orderRequest, BigDecimal totalOrderCost)
	{
		orderRepository.updateStatus(orderId, OrderStatus.IN_EXECUTION.name());

		logger.info(String.format("Order in execution: orderId {%d}", orderId));

		CustomerWithFreeBalanceAndCurrencyResponse customerWithFreeBalance = paymentsRestService.getCustomerWithFreeBalanceAndCurrency(customerId);

		if (orderRequest.orderType().equals(OrderType.BUY))
		{
			executeBuyOrder(orderId, customerId, totalOrderCost, orderRequest, customerWithFreeBalance);
		}
		else
		{
			executeSellOrder(orderId, customerId, totalOrderCost, orderRequest, customerWithFreeBalance);
		}
	}

	private void executeBuyOrder(long orderId, long customerId, BigDecimal totalOrderCost, OrderRequest orderRequest,
								 CustomerWithFreeBalanceAndCurrencyResponse customerWithFreeBalance)
	{
		orderValidationService.validateBuyOrder(customerWithFreeBalance.currency(),
												customerWithFreeBalance.freeBalance(),
												totalOrderCost);

		subtractTotalCostFromCustomerFreeFunds(orderId, customerId, totalOrderCost, customerWithFreeBalance);
		logger.info("Updating customer assets after buy order execution, orderId: " + orderId);
		customerTradingAccountService.updateCustomerAssets(customerId, orderRequest);
	}

	private void executeSellOrder(long orderId, long customerId, BigDecimal totalOrderCost, OrderRequest orderRequest,
								  CustomerWithFreeBalanceAndCurrencyResponse customerWithFreeBalance)
	{
		addSellOrderCostToFreeFunds(orderId, customerId, totalOrderCost);
		Optional<CustomerTradingAccount> customerTradingAccount = customerTradingAccountRepository.getCustomerTradingAccountsByCustomerIdAndCurrency(customerId, orderRequest.supportedCurrency());

		try
		{
			customerTradingAccountService.validateCustomerSellOrderRequest(customerTradingAccount, customerId, orderRequest, orderId);
		}
		catch (RuntimeException ex)
		{
			logger.info(String.format("Failed to execute order {%d}, reason: %s ", orderId, ex.getMessage()));
			orderRepository.updateStatus(orderId, OrderStatus.FAILED_EXECUTION.name());
			throw ex;
		}

		customerTradingAccountService.updateCurrencyAmount(customerId, orderRequest.supportedCurrency(), customerTradingAccount.get().currencyAmount().subtract(orderRequest.orderSize()));
		paymentsRestService.modifyCustomerFreeBalance(customerId, customerWithFreeBalance.freeBalance().add(totalOrderCost));

		orderRepository.updateStatus(orderId, OrderStatus.EXECUTED.name());
	}

	private void addSellOrderCostToFreeFunds(long orderId, long customerId, BigDecimal totalOrderCost)
	{
		try
		{
			paymentsRestService.modifyCustomerFreeBalance(customerId, totalOrderCost);
		}
		catch (PaymentsException ex)
		{
			logger.info(String.format("Failed to execute order {%d}, reason: %s ", orderId, ex.getMessage()));
			orderRepository.updateStatus(orderId, OrderStatus.FAILED_EXECUTION.name());
		}
	}

	private void subtractTotalCostFromCustomerFreeFunds(long orderId, long customerId, BigDecimal fundsForInvestment,
														CustomerWithFreeBalanceAndCurrencyResponse customerWithFreeBalance)
	{
		try
		{
			paymentsRestService.modifyCustomerFreeBalance(customerId, customerWithFreeBalance.freeBalance().subtract(fundsForInvestment));
		}
		catch (PaymentsException ex)
		{
			logger.info(String.format("Failed to execute order {%d}, reason: %s ", orderId, ex.getMessage()));
			orderRepository.updateStatus(orderId, OrderStatus.FAILED_EXECUTION.name());
		}
	}

	private long saveOrder(long customerId, OrderRequest orderRequest, BigDecimal currentPrice, BigDecimal totalOrderCost)
	{
		OrderSaveRequest orderSaveRequest = new OrderSaveRequest(customerId,
																 orderRequest.supportedCurrency(),
																 orderRequest.orderType(),
																 orderRequest.orderSize(),
																 currentPrice,
																 OrderStatus.PENDING,
																 totalOrderCost,
																 LocalDateTime.now());
		return orderRepository.save(orderSaveRequest);
	}

	//should implement the flow of getting currentPrice for currency from marketDataService
	private BigDecimal currencyCurrentPrice(SupportedCurrency currency)
	{
		return BigDecimal.valueOf(10);
	}

	private BigDecimal calculateTotalCost(BigDecimal orderSize, BigDecimal price)
	{
		return orderSize.multiply(price);
	}
}
