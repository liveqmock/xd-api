package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.TourDateDAO;
import com.xindong.api.domain.TourDate;
@Repository(value="tourDateDao")
@SuppressWarnings("unchecked")
public class TourDateDAOImpl extends SqlMapClientTemplate implements TourDateDAO {

    public TourDateDAOImpl() {
        super();
    }

    public Integer insert(TourDate record) {
    	return (Integer) insert("tour_date.insert", record);
    }

    public int updateByPrimaryKey(TourDate record) {
        int rows = update("tour_date.updateByPrimaryKey", record);
        return rows;
    }

    public TourDate selectByPrimaryKey(Integer dateId) {
        TourDate key = new TourDate();
        key.setDateId(dateId);
        TourDate record = (TourDate) queryForObject("tour_date.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer dateId) {
        TourDate key = new TourDate();
        key.setDateId(dateId);
        int rows = delete("tour_date.deleteByPrimaryKey", key);
        return rows;
    }

	
	@Override
	public List<TourDate> selectByCondition(TourDate tourDateQuery) {
		return queryForList("tour_date.selectByCondition",tourDateQuery);
	}


}