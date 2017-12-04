package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.TourSubject;

public interface TourSubjectDAO {
	Integer insert(TourSubject record);

    int updateByPrimaryKey(TourSubject record);

    TourSubject selectByPrimaryKey(Integer subjectId);

    int deleteByPrimaryKey(Integer subjectId);

	List<TourSubject> selectByCondition(TourSubject tourSubjectQuery);

}