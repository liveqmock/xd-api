package com.xindong.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xindong.api.dao.OrderPaymentDAO;
import com.xindong.api.domain.OrderPayment;
import com.xindong.api.domain.query.OrderPaymentQuery;
import com.xindong.api.service.OrderPaymentService;

@Service(value="orderPaymentService")
public class OrderPaymentServiceImpl implements OrderPaymentService {
	@Autowired
	private OrderPaymentDAO orderPaymentDao;
	@Override
	public Integer insert(OrderPayment orderPayment) {
		return orderPaymentDao.insert(orderPayment);
	}

	@Override
	public Integer modify(OrderPayment orderPayment) {
		return orderPaymentDao.updateByPrimaryKey(orderPayment);
	}
	@Override
	public void delete(Integer paymentId) {
		orderPaymentDao.deleteByPrimaryKey(paymentId);
	}
	@Override
	public OrderPayment selectOrderPamentByOne(OrderPayment orderPayment) {
		return orderPaymentDao.selectOrderPamentByOne(orderPayment);
	}

	@Override
	public List<OrderPayment> selectByCondition(OrderPaymentQuery orderPaymentQuery) {
		return orderPaymentDao.selectByCondition(orderPaymentQuery);
	}
	
	
	
}
