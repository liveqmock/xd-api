package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.TourFlagDAO;
import com.xindong.api.domain.TourFlag;
@Repository(value="tourFlagDao")
@SuppressWarnings("unchecked")
public class TourFlagDAOImpl extends SqlMapClientTemplate implements TourFlagDAO {

    public TourFlagDAOImpl() {
        super();
    }

    public Integer insert(TourFlag record) {
    	return (Integer) insert("tour_flag.insert", record);
    }

    public int updateByPrimaryKey(TourFlag record) {
        int rows = update("tour_flag.updateByPrimaryKey", record);
        return rows;
    }

    public TourFlag selectByPrimaryKey(Integer flagId) {
        TourFlag key = new TourFlag();
        key.setFlagId(flagId);
        TourFlag record = (TourFlag) queryForObject("tour_flag.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer flagId) {
        TourFlag key = new TourFlag();
        key.setFlagId(flagId);
        int rows = delete("tour_flag.deleteByPrimaryKey", key);
        return rows;
    }
    
	@Override
	public List<TourFlag> selectByCondition(TourFlag tourFlagQuery) {
		return queryForList("tour_flag.selectByCondition", tourFlagQuery);
	}
   
}