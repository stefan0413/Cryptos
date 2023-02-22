package com.example.payment.model.payment_method;

import com.example.payment.model.payment_method.CustomerPaymentMethod;

import java.util.List;

public record CustomerPaymentMethodResponseWrapper(List<CustomerPaymentMethod> customerPaymentMethods)
{

}
