package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.TourDestinationDAO;
import com.xindong.api.domain.TourDestination;
import com.xindong.api.domain.query.TourDestinationQuery;
@Repository(value="tourDestinationDao")
@SuppressWarnings("unchecked")
public class TourDestinationDAOImpl extends SqlMapClientTemplate implements TourDestinationDAO {

    public TourDestinationDAOImpl() {
        super();
    }

    public Integer insert(TourDestination record) {
    	return (Integer) insert("tour_destination.insert", record);
    }

    public int updateByPrimaryKey(TourDestination record) {
        int rows = update("tour_destination.updateByPrimaryKey", record);
        return rows;
    }


    public TourDestination selectByPrimaryKey(Integer destinationId) {
        TourDestination key = new TourDestination();
        key.setDestinationId(destinationId);
        TourDestination record = (TourDestination) queryForObject("tour_destination.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer destinationId) {
        TourDestination key = new TourDestination();
        key.setDestinationId(destinationId);
        int rows = delete("tour_destination.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<TourDestination> selectByCondition(TourDestination tourDestinationQuery) {
		return queryForList("tour_destination.selectByCondition", tourDestinationQuery);
	}

	@Override
	public int countByCondition(TourDestinationQuery tourDestinationQuery) {
		return (Integer)queryForObject("tour_destination.countByCondition", tourDestinationQuery);
	}
	
	@Override
	public List<TourDestination> selectByConditionForPage(TourDestinationQuery tourDestinationQuery) {
		return queryForList("tour_destination.selectByConditionForPage", tourDestinationQuery);
	}
}