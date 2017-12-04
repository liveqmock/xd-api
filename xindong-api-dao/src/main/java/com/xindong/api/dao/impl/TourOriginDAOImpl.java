package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.TourOriginDAO;
import com.xindong.api.domain.TourOrigin;
@Repository(value="tourOriginDao")
@SuppressWarnings("unchecked")
public class TourOriginDAOImpl extends SqlMapClientTemplate implements TourOriginDAO {

    public TourOriginDAOImpl() {
        super();
    }

    public Integer insert(TourOrigin record) {
    	return (Integer) insert("tour_origin.insert", record);
    }

    public int updateByPrimaryKey(TourOrigin record) {
        int rows = update("tour_origin.updateByPrimaryKey", record);
        return rows;
    }

    public TourOrigin selectByPrimaryKey(Integer originId) {
        TourOrigin key = new TourOrigin();
        key.setOriginId(originId);
        return (TourOrigin) queryForObject("tour_origin.updateByPrimaryKey", key);
    }

    public int deleteByPrimaryKey(Integer originId) {
        TourOrigin key = new TourOrigin();
        key.setOriginId(originId);
        int rows = delete("tour_origin.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<TourOrigin> selectByCondition(TourOrigin tourOriginQuery) {
		return queryForList("tour_origin.selectByCondition", tourOriginQuery);
	}

}