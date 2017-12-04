package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.PassengerGroupDAO;
import com.xindong.api.domain.PassengerGroup;
import com.xindong.api.domain.query.PassengerGroupQuery;
@Repository(value="passengerGroupDao")
public class PassengerGroupDAOImpl extends SqlMapClientTemplate implements PassengerGroupDAO {

    public PassengerGroupDAOImpl() {
        super();
    }

    public Integer insert(PassengerGroup record) {
    	return (Integer) insert("passenger_group.insert", record);
    }

    public int updateByPrimaryKey(PassengerGroup record) {
        int rows = update("passenger_group.updateByPrimaryKey", record);
        return rows;
    }

    public int updateByPrimaryKeySelective(PassengerGroup record) {
        int rows = update("passenger_group.updateByPrimaryKeySelective", record);
        return rows;
    }

    public PassengerGroup selectByPrimaryKey(Integer passengerId) {
        PassengerGroup key = new PassengerGroup();
        key.setPassengerId(passengerId);
        PassengerGroup record = (PassengerGroup) queryForObject("passenger_group.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer passengerId) {
        PassengerGroup key = new PassengerGroup();
        key.setPassengerId(passengerId);
        int rows = delete("passenger_group.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public int countByCondition(PassengerGroupQuery passengerGroupQuery) {
		return (Integer) queryForObject("passenger_group.countByCondition", passengerGroupQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PassengerGroup> selectByConditionForPage(
			PassengerGroupQuery passengerGroupQuery) {
		return queryForList("passenger_group.selectByConditionForPage", passengerGroupQuery);
	}

	@Override
	public int countByHavePassenger(PassengerGroup passengerGroup) {
		return (Integer) queryForObject("passenger_group.countByHavePassenger", passengerGroup);
	}

}