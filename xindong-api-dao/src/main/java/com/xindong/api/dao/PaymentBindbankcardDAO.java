package com.xindong.api.dao;

import com.xindong.api.domain.PaymentBindbankcard;

public interface PaymentBindbankcardDAO {
	Integer insert(PaymentBindbankcard record);

    int updateByPrimaryKey(PaymentBindbankcard record);

    PaymentBindbankcard selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

}