package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.OrderInfoDAO;
import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.query.OrderInfoQuery;
@Repository(value="orderInfoDao")
@SuppressWarnings("unchecked")
public class OrderInfoDAOImpl extends SqlMapClientTemplate implements OrderInfoDAO {

    public OrderInfoDAOImpl() {
        super();
    }

    public Integer insert(OrderInfo record) {
    	return (Integer) insert("order_info.insert", record);
    }

    public int updateByPrimaryKey(OrderInfo record) {
        int rows = update("order_info.updateByPrimaryKey", record);
        return rows;
    }

    public int updateByPrimaryKeySelective(OrderInfo record) {
        int rows = update("order_info.updateByPrimaryKeySelective", record);
        return rows;
    }

    public OrderInfo selectByPrimaryKey(Integer orderId) {
        OrderInfo record = (OrderInfo) queryForObject("order_info.selectByPrimaryKey", orderId);
        return record;
    }


    public int deleteByPrimaryKey(Integer orderId) {
        int rows = delete("order_info.deleteByPrimaryKey", orderId);
        return rows;
    }

	@Override
	public int countByCondition(OrderInfoQuery orderInfoQuery) {
		return (Integer) queryForObject("order_info.countByCondition", orderInfoQuery);
	}

	
	@Override
	public List<OrderInfo> selectByConditionForPage(OrderInfoQuery orderInfoQuery) {
		return queryForList("order_info.selectByConditionForPage", orderInfoQuery);
	}

	@Override
	public List<OrderInfo> selectByCondition(OrderInfoQuery orderInfoQuery) {
		return queryForList("order_info.selectByCondition", orderInfoQuery);
	}

	@Override
	public OrderInfo selectByOrderInfo(OrderInfo orderInfo) {
		return (OrderInfo) queryForObject("order_info.selectByOrderInfo", orderInfo);
	}

    
}