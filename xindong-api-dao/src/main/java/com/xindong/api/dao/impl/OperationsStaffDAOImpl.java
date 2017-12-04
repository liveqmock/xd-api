package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;


import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.OperationsStaffDAO;
import com.xindong.api.domain.OperationsStaff;
import com.xindong.api.domain.query.OperationsStaffQuery;

@Repository(value="operationsStaffDAO")
public class OperationsStaffDAOImpl extends SqlMapClientTemplate implements OperationsStaffDAO {

    public OperationsStaffDAOImpl() {
        super();
    }

    public void insert(OperationsStaff record) {
        insert("operations_staff.insert", record);
    }

    public int updateByPrimaryKey(OperationsStaff record) {
        int rows = update("operations_staff.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKeySelective(OperationsStaff record) {
        int rows = update("operations_staff.updateByPrimaryKeySelective", record);
        return rows;
    }

    public OperationsStaff selectByPrimaryKey(Integer id) {
        OperationsStaff key = new OperationsStaff();
        key.setId(id);
        OperationsStaff record = (OperationsStaff) queryForObject("operations_staff.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        OperationsStaff key = new OperationsStaff();
        key.setId(id);
        int rows = delete("operations_staff.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public int countByCondition(OperationsStaffQuery query) {
		return (Integer) queryForObject("operations_staff.countByCondition",query);
		}

	@Override
	public List<OperationsStaff> selectByConditionForPage(
			OperationsStaffQuery query) {
		return (List<OperationsStaff>)queryForList("operations_staff.selectByConditionForPage",query);
		}

	@Override
	public List<OperationsStaff> selectByCondition(OperationsStaffQuery query) {
		return (List<OperationsStaff>)queryForList("operations_staff.selectByCondition",query);
		}

}