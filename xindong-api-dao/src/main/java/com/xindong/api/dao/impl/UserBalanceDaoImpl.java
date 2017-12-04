package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.UserBalanceDao;
import com.xindong.api.domain.UserBalance;
@Repository(value="userBalanceDao")
public class UserBalanceDaoImpl extends SqlMapClientTemplate implements UserBalanceDao {


    public void insert(UserBalance record) {
        insert("user_balance.insert", record);
    }

    public int updateByPrimaryKey(UserBalance record) {
        int rows = update("user_balance.updateByPrimaryKey", record);
        return rows;
    }

    public UserBalance selectByPrimaryKey(Integer balanceId) {
        UserBalance key = new UserBalance();
        key.setBalanceId(balanceId);
        UserBalance record = (UserBalance) queryForObject("user_balance.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer balanceId) {
        UserBalance key = new UserBalance();
        key.setBalanceId(balanceId);
        int rows = delete("user_balance.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public UserBalance selectByUserId(Integer userId) {
		return  (UserBalance) queryForObject("user_balance.selectByUserId", userId);
	}

	@Override
	public int updateByBalance(UserBalance usebalance) {
		 int rows = update("user_balance.updateByBalance", usebalance);
	     return rows;
	}

}