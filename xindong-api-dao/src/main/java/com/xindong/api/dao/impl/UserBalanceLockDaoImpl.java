package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.UserBalanceLockDao;
import com.xindong.api.domain.UserBalanceLock;
@Repository(value="userBalanceLockDao")
public class UserBalanceLockDaoImpl extends SqlMapClientTemplate implements UserBalanceLockDao {

    public void insert(UserBalanceLock record) {
        insert("user_balance_lock.insert", record);
    }

    public int updateByPrimaryKey(UserBalanceLock record) {
        int rows = update("user_balance_lock.updateByPrimaryKey", record);
        return rows;
    }

    public UserBalanceLock selectByPrimaryKey(Integer balanceLockId) {
        UserBalanceLock key = new UserBalanceLock();
        key.setBalanceLockId(balanceLockId);
        UserBalanceLock record = (UserBalanceLock) queryForObject("user_balance_lock.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer balanceLockId) {
        UserBalanceLock key = new UserBalanceLock();
        key.setBalanceLockId(balanceLockId);
        int rows = delete("user_balance_lock.deleteByPrimaryKey", key);
        return rows;
    }


}