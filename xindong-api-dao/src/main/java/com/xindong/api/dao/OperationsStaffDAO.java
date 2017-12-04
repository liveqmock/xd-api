package com.xindong.api.dao;


import java.util.List;

import com.xindong.api.domain.OperationsStaff;
import com.xindong.api.domain.query.OperationsStaffQuery;

public interface OperationsStaffDAO {
    void insert(OperationsStaff record);

    int updateByPrimaryKey(OperationsStaff record);

    int updateByPrimaryKeySelective(OperationsStaff record);

    OperationsStaff selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	int countByCondition(OperationsStaffQuery query);

	List<OperationsStaff> selectByConditionForPage(OperationsStaffQuery query);

	List<OperationsStaff> selectByCondition(OperationsStaffQuery query);

}