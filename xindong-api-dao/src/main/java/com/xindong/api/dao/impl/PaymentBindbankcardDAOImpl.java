package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.PaymentBindbankcardDAO;
import com.xindong.api.domain.PaymentBindbankcard;
@Repository(value="paymentBindbankcardDao")
public class PaymentBindbankcardDAOImpl extends SqlMapClientTemplate implements PaymentBindbankcardDAO {

    public PaymentBindbankcardDAOImpl() {
        super();
    }

    public Integer insert(PaymentBindbankcard record) {
    	return (Integer) insert("payment_bindbankcard.insert", record);
    }

    public int updateByPrimaryKey(PaymentBindbankcard record) {
        int rows = update("payment_bindbankcard.updateByPrimaryKey", record);
        return rows;
    }


    public PaymentBindbankcard selectByPrimaryKey(Integer id) {
        PaymentBindbankcard key = new PaymentBindbankcard();
        key.setId(id);
        PaymentBindbankcard record = (PaymentBindbankcard) queryForObject("payment_bindbankcard.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer id) {
        PaymentBindbankcard key = new PaymentBindbankcard();
        key.setId(id);
        int rows = delete("payment_bindbankcard.deleteByPrimaryKey", key);
        return rows;
    }

   
}