package com.xindong.api.dao;

import com.xindong.api.domain.PaymentInfo;

public interface PaymentInfoDAO {
	void insert(PaymentInfo record);

    int updateByPrimaryKey(PaymentInfo record);

    PaymentInfo selectByPrimaryKey(Integer paymentId);

    int deleteByPrimaryKey(Integer paymentId);

}