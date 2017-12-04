package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.OrderPaymentDAO;
import com.xindong.api.domain.OrderPayment;
import com.xindong.api.domain.TbCoupon;
import com.xindong.api.domain.query.OrderPaymentQuery;
@Repository(value="orderPaymentDao")
public class OrderPaymentDAOImpl extends SqlMapClientTemplate implements OrderPaymentDAO {

    public Integer insert(OrderPayment record) {
    	return (Integer) insert("order_payment.insert", record);
    }

    public int updateByPrimaryKey(OrderPayment record) {
        int rows = update("order_payment.updateByPrimaryKey", record);
        return rows;
    }

    public OrderPayment selectByPrimaryKey(Integer paymentId) {
        OrderPayment key = new OrderPayment();
        key.setPaymentId(paymentId);
        OrderPayment record = (OrderPayment) queryForObject("order_payment.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer paymentId) {
        OrderPayment key = new OrderPayment();
        key.setPaymentId(paymentId);
        int rows = delete("order_payment.deleteByPrimaryKey", key);
        return rows;
    }
    @Override
	public OrderPayment selectOrderPamentByOne(OrderPayment orderPayment) {
		return (OrderPayment)queryForObject("order_payment.selectOrderPamentByOne", orderPayment);
	}

	@Override
	public List<OrderPayment> selectByCondition(OrderPaymentQuery orderPaymentQuery) {
		return (List<OrderPayment>)queryForList("order_payment.selectByCondition",orderPaymentQuery);
	}
   
}