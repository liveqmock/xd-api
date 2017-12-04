package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.SystemConstantTypeDao;
import com.xindong.api.domain.SystemConstantType;

@SuppressWarnings("unchecked")
@Repository(value="systemConstantTypeDao")
public class SystemConstantTypeDaoImpl extends SqlMapClientTemplate implements SystemConstantTypeDao {

    public void insert(SystemConstantType record) {
       insert("system_constant_type.insert", record);
    }

    public int updateByPrimaryKey(SystemConstantType record) {
        int rows =update("system_constant_type.updateByPrimaryKey", record);
        return rows;
    }

    public int updateByPrimaryKeySelective(SystemConstantType record) {
        int rows =update("system_constant_type.updateByPrimaryKeySelective", record);
        return rows;
    }

    public SystemConstantType selectByPrimaryKey(Integer id) {
        SystemConstantType key = new SystemConstantType();
        key.setId(id);
        SystemConstantType record = (SystemConstantType)queryForObject("system_constant_type.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        SystemConstantType key = new SystemConstantType();
        key.setId(id);
        int rows =delete("system_constant_type.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<SystemConstantType> selectByCondition(SystemConstantType systemConstantType) {
		return queryForList("system_constant_type.selectByCondition",systemConstantType);
	}

}