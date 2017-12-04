package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.OrderPayment;
import com.xindong.api.domain.query.OrderPaymentQuery;

public interface OrderPaymentDAO {
	Integer insert(OrderPayment record);

	int updateByPrimaryKey(OrderPayment record);

	int deleteByPrimaryKey(Integer paymentId);

    OrderPayment selectByPrimaryKey(Integer paymentId);
	OrderPayment selectOrderPamentByOne(OrderPayment orderPayment);

	public List<OrderPayment> selectByCondition(OrderPaymentQuery orderPaymentQuery); 
}