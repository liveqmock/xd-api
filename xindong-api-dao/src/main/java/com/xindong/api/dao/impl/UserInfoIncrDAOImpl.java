package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.UserInfoIncrDAO;
import com.xindong.api.domain.UserInfoIncr;
@Repository(value="userInfoIncrDao")
public class UserInfoIncrDAOImpl extends SqlMapClientTemplate implements UserInfoIncrDAO {


    public Integer insert(UserInfoIncr record) {
    	return (Integer) insert("user_info_incr.insert", record);
    }

    public int updateByPrimaryKey(UserInfoIncr record) {
        int rows = update("user_info_incr.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKeySelective(UserInfoIncr record) {
        int rows = update("user_info_incr.updateByPrimaryKeySelective", record);
        return rows;
    }

    public UserInfoIncr selectByPrimaryKey(Integer id) {
        UserInfoIncr key = new UserInfoIncr();
        key.setId(id);
        UserInfoIncr record = (UserInfoIncr) queryForObject("user_info_incr.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        UserInfoIncr key = new UserInfoIncr();
        key.setId(id);
        int rows = delete("user_info_incr.deleteByPrimaryKey", key);
        return rows;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfoIncr> selectByCondition(UserInfoIncr incr) {
		return (List<UserInfoIncr>) queryForList("user_info_incr.selectByCondition", incr);
	}

	@Override
	public void updateByCondition(UserInfoIncr incr) {
		update("user_info_incr.updateByCondition", incr);
	}
}