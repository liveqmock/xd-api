package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.SystemConstantValueDao;
import com.xindong.api.domain.SystemConstantValue;

@SuppressWarnings("unchecked")
@Repository(value="systemConstantValueDao")
public class SystemConstantValueDaoImpl extends SqlMapClientTemplate implements SystemConstantValueDao {

    public void insert(SystemConstantValue record) {
        insert("system_constant_value.insert", record);
    }

    public int updateByPrimaryKey(SystemConstantValue record) {
        int rows = update("system_constant_value.updateByPrimaryKey", record);
        return rows;
    }

    public int updateByPrimaryKeySelective(SystemConstantValue record) {
        int rows = update("system_constant_value.updateByPrimaryKeySelective", record);
        return rows;
    }

    public SystemConstantValue selectByPrimaryKey(Integer id) {
        SystemConstantValue key = new SystemConstantValue();
        key.setId(id);
        SystemConstantValue record = (SystemConstantValue) queryForObject("system_constant_value.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer id) {
        SystemConstantValue key = new SystemConstantValue();
        key.setId(id);
        int rows = delete("system_constant_value.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<SystemConstantValue> selectByCondition(SystemConstantValue systemConstantValue) {
		return queryForList("system_constant_value.selectByCondition", systemConstantValue);
	}

}