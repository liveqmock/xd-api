package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.PaymentInfoDAO;
import com.xindong.api.domain.PaymentInfo;
@Repository(value="paymentInfoDao")
public class PaymentInfoDAOImpl extends SqlMapClientTemplate implements PaymentInfoDAO {

    public PaymentInfoDAOImpl() {
        super();
    }

    public void insert(PaymentInfo record) {
       insert("payment_info.insert", record);
    }

    public int updateByPrimaryKey(PaymentInfo record) {
        int rows = update("payment_info.updateByPrimaryKey", record);
        return rows;
    }

    public PaymentInfo selectByPrimaryKey(Integer paymentId) {
        PaymentInfo key = new PaymentInfo();
        key.setPaymentId(paymentId);
        PaymentInfo record = (PaymentInfo) queryForObject("payment_info.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer paymentId) {
        PaymentInfo key = new PaymentInfo();
        key.setPaymentId(paymentId);
        int rows = delete("payment_info.deleteByPrimaryKey", key);
        return rows;
    }

}