package com.xindong.api.dao;

import com.xindong.api.domain.OrderRemark;

public interface OrderRemarkDAO {
	Integer insert(OrderRemark record);

    int updateByPrimaryKey(OrderRemark record);

    OrderRemark selectByPrimaryKey(Integer orderRemarkId);

    int deleteByPrimaryKey(Integer orderRemarkId);

}