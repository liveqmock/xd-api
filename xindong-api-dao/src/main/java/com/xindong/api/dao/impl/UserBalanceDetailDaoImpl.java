package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.UserBalanceDetailDao;
import com.xindong.api.domain.UserBalanceDetail;
import com.xindong.api.domain.query.UserBalanceDetailQuery;
@Repository(value="userBalanceDetailDao")
public class UserBalanceDetailDaoImpl extends SqlMapClientTemplate implements UserBalanceDetailDao {


    public void insert(UserBalanceDetail record) {
        insert("user_balance_detail.insert", record);
    }

    public int updateByPrimaryKey(UserBalanceDetail record) {
        int rows = update("user_balance_detail.updateByPrimaryKey", record);
        return rows;
    }

    public UserBalanceDetail selectByPrimaryKey(Integer balanceDetailId) {
        UserBalanceDetail key = new UserBalanceDetail();
        key.setBalanceDetailId(balanceDetailId);
        UserBalanceDetail record = (UserBalanceDetail) queryForObject("user_balance_detail.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer balanceDetailId) {
        UserBalanceDetail key = new UserBalanceDetail();
        key.setBalanceDetailId(balanceDetailId);
        int rows = delete("user_balance_detail.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public int countByCondition(UserBalanceDetailQuery detailQuery) {
		return (Integer) queryForObject("user_balance_detail.countByCondition", detailQuery);
	}

	@Override
	public List<UserBalanceDetail> selectByConditionForPage(UserBalanceDetailQuery detailQuery) {
		return queryForList("user_balance_detail.selectByConditionForPage", detailQuery);
	}

}