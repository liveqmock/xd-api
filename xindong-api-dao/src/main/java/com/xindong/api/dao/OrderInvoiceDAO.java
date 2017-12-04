package com.xindong.api.dao;

import com.xindong.api.domain.OrderInvoice;

public interface OrderInvoiceDAO {
	Integer insert(OrderInvoice record);

    int updateByPrimaryKey(OrderInvoice record);

    OrderInvoice selectByPrimaryKey(Integer invoiceId);

    int deleteByPrimaryKey(Integer invoiceId);

}