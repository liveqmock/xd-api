package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.OrderPassenger;
import com.xindong.api.domain.query.OrderPassengerQuery;

public interface OrderPassengerDAO {
    Integer insert(OrderPassenger record);

    int updateByPrimaryKey(OrderPassenger record);

    OrderPassenger selectByPrimaryKey(Integer orderPassengerId);

    int deleteByPrimaryKey(Integer orderPassengerId);

	List<OrderPassenger> selectByCondition(OrderPassengerQuery passengerQuery);

	void insertBatch(List<OrderPassenger> passengers);

}