package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.OrderRemarkDAO;
import com.xindong.api.domain.OrderRemark;
@Repository(value="orderRemarkDao")
public class OrderRemarkDAOImpl extends SqlMapClientTemplate implements OrderRemarkDAO {

    public OrderRemarkDAOImpl() {
        super();
    }

    public Integer insert(OrderRemark record) {
    	return (Integer) insert("order_remark.insert", record);
    }

    public int updateByPrimaryKey(OrderRemark record) {
        int rows = update("order_remark.updateByPrimaryKey", record);
        return rows;
    }


    public OrderRemark selectByPrimaryKey(Integer orderRemarkId) {
        OrderRemark key = new OrderRemark();
        key.setOrderRemarkId(orderRemarkId);
        OrderRemark record = (OrderRemark) queryForObject("order_remark.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer orderRemarkId) {
        OrderRemark key = new OrderRemark();
        key.setOrderRemarkId(orderRemarkId);
        int rows = delete("order_remark.deleteByPrimaryKey", key);
        return rows;
    }

    
}