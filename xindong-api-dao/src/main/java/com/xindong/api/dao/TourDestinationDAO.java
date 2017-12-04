package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.TourDestination;
import com.xindong.api.domain.query.ActivityQuery;
import com.xindong.api.domain.query.TourDestinationQuery;

public interface TourDestinationDAO {
	Integer insert(TourDestination record);

    int updateByPrimaryKey(TourDestination record);

    TourDestination selectByPrimaryKey(Integer destinationId);

    int deleteByPrimaryKey(Integer destinationId);
    
    int countByCondition(TourDestinationQuery tourDestinationQuery);
    
	List<TourDestination> selectByCondition(TourDestination tourDestinationQuery);
	
	List<TourDestination> selectByConditionForPage(TourDestinationQuery tourDestinationQuery);
	
}