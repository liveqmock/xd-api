package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.TourSubjectDAO;
import com.xindong.api.domain.TourSubject;
@Repository(value="tourSubjectDao")
@SuppressWarnings("unchecked")
public class TourSubjectDAOImpl extends SqlMapClientTemplate implements TourSubjectDAO {

    public TourSubjectDAOImpl() {
        super();
    }

    public Integer insert(TourSubject record) {
    	return (Integer) insert("tour_subject.insert", record);
    }

    public int updateByPrimaryKey(TourSubject record) {
        int rows = update("tour_subject.updateByPrimaryKey", record);
        return rows;
    }

    public int updateByPrimaryKeySelective(TourSubject record) {
        int rows = update("tour_subject.updateByPrimaryKeySelective", record);
        return rows;
    }

    public TourSubject selectByPrimaryKey(Integer subjectId) {
        TourSubject key = new TourSubject();
        key.setSubjectId(subjectId);
        TourSubject record = (TourSubject) queryForObject("tour_subject.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer subjectId) {
        TourSubject key = new TourSubject();
        key.setSubjectId(subjectId);
        int rows = delete("tour_subject.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<TourSubject> selectByCondition(TourSubject tourSubjectQuery) {
		return queryForList("tour_subject.selectByCondition", tourSubjectQuery);
	}

}