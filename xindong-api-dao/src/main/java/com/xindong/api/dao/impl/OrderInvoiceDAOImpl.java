package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.OrderInvoiceDAO;
import com.xindong.api.domain.OrderInvoice;
@Repository(value="orderInvoiceDao")
public class OrderInvoiceDAOImpl extends SqlMapClientTemplate implements OrderInvoiceDAO {

    public OrderInvoiceDAOImpl() {
        super();
    }

    public Integer insert(OrderInvoice record) {
       return  (Integer) insert("order_invoice.insert", record);
    }

    public int updateByPrimaryKey(OrderInvoice record) {
        int rows = update("order_invoice.updateByPrimaryKey", record);
        return rows;
    }

    public OrderInvoice selectByPrimaryKey(Integer invoiceId) {
        OrderInvoice key = new OrderInvoice();
        key.setInvoiceId(invoiceId);
        OrderInvoice record = (OrderInvoice) queryForObject("order_invoice.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer invoiceId) {
        OrderInvoice key = new OrderInvoice();
        key.setInvoiceId(invoiceId);
        int rows = delete("order_invoice.deleteByPrimaryKey", key);
        return rows;
    }



   
}