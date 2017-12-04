package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.OrderPassengerDAO;
import com.xindong.api.domain.OrderPassenger;
import com.xindong.api.domain.query.OrderPassengerQuery;
@Repository(value="orderPassengerDao")
@SuppressWarnings("unchecked")
public class OrderPassengerDAOImpl extends SqlMapClientTemplate implements OrderPassengerDAO {

    public OrderPassengerDAOImpl() {
        super();
    }

    public Integer insert(OrderPassenger record) {
       return  (Integer) insert("order_passenger.insert", record);
    }

    public int updateByPrimaryKey(OrderPassenger record) {
        int rows = update("order_passenger.updateByPrimaryKey", record);
        return rows;
    }

    public OrderPassenger selectByPrimaryKey(Integer orderPassengerId) {
        OrderPassenger key = new OrderPassenger();
        key.setOrderPassengerId(orderPassengerId);
        OrderPassenger record = (OrderPassenger) queryForObject("order_passenger.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer orderPassengerId) {
        OrderPassenger key = new OrderPassenger();
        key.setOrderPassengerId(orderPassengerId);
        int rows = delete("order_passenger.deleteByPrimaryKey", key);
        return rows;
    }

	
	@Override
	public List<OrderPassenger> selectByCondition(
			OrderPassengerQuery passengerQuery) {
		return queryForList("order_passenger.selectByCondition", passengerQuery);
	}

	@Override
	public void insertBatch(List<OrderPassenger> passengers) {
		 insert("order_passenger.insertBatch", passengers);
	}

    
    
}