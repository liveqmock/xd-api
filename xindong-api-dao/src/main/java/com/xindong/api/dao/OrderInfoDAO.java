package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.query.OrderInfoQuery;

public interface OrderInfoDAO {
	Integer insert(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);


    OrderInfo selectByPrimaryKey(Integer orderId);


    int deleteByPrimaryKey(Integer orderId);

	int countByCondition(OrderInfoQuery orderInfoQuery);

	List<OrderInfo> selectByConditionForPage(OrderInfoQuery orderInfoQuery);

	List<OrderInfo> selectByCondition(OrderInfoQuery orderInfoQuery);

	OrderInfo selectByOrderInfo(OrderInfo orderInfo);


}