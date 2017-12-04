package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.PassengerGroup;
import com.xindong.api.domain.query.PassengerGroupQuery;

public interface PassengerGroupDAO {
	Integer insert(PassengerGroup record);

    int updateByPrimaryKey(PassengerGroup record);

    PassengerGroup selectByPrimaryKey(Integer passengerId);

    int deleteByPrimaryKey(Integer passengerId);

	int countByCondition(PassengerGroupQuery passengerGroupQuery);

	List<PassengerGroup> selectByConditionForPage(
			PassengerGroupQuery passengerGroupQuery);

	int countByHavePassenger(PassengerGroup passengerGroup);


}