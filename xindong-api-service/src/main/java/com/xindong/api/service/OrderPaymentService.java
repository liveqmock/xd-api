package com.xindong.api.service;

import java.util.List;

import com.xindong.api.domain.OrderPayment;
import com.xindong.api.domain.query.OrderPaymentQuery;

public interface OrderPaymentService {
	Integer insert(OrderPayment orderPayment);
	Integer modify(OrderPayment orderPayment);
	void delete(Integer paymentId);
	OrderPayment selectOrderPamentByOne(OrderPayment orderPayment);
	
	public List<OrderPayment> selectByCondition(OrderPaymentQuery orderPaymentQuery); 
}
