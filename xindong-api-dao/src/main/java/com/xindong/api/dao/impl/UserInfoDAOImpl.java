package com.xindong.api.dao.impl;

import java.util.List;


import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.UserInfoDAO;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.query.UserInfoQuery;
@SuppressWarnings("unchecked")
@Repository(value="userInfoDao")
public class UserInfoDAOImpl extends SqlMapClientTemplate implements UserInfoDAO {

    public UserInfoDAOImpl() {
        super();
    }

    public Integer insert(UserInfo record) {
    	return (Integer) insert("user_info.insert", record);
    }

    public int updateByPrimaryKey(UserInfo record) {
        int rows = update("user_info.updateByPrimaryKey", record);
        return rows;
    }

    public UserInfo selectByPrimaryKey(Integer userId) {
        UserInfo key = new UserInfo();
        key.setUserId(userId);
        UserInfo record = (UserInfo) queryForObject("user_info.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer userId) {
        UserInfo key = new UserInfo();
        key.setUserId(userId);
        int rows = delete("user_info.deleteByPrimaryKey", key);
        return rows;
    }
    @Override
	public int countByCondition(UserInfoQuery userInfoQuery) {
		return (Integer)queryForObject("user_info.countByCondition",userInfoQuery);
	}


	@Override
	public List<UserInfo> selectByCondition(UserInfoQuery userInfoQuery) {
		return queryForList("user_info.selectByCondition",userInfoQuery);
	}

	@Override
	public UserInfo selectByInvitationCode(String invitationCode) {
		return (UserInfo) queryForObject("user_info.selectByInvitationCode",invitationCode);
	}

	@Override
	public Integer countByRegisterInvitationCode(String registerInvitationCode) {
		return (Integer) queryForObject("user_info.countByRegisterInvitationCode",registerInvitationCode);
	}

	@Override
	public UserInfo selectByUnionid(String unionid) {
		return (UserInfo) queryForObject("user_info.selectByUnionid",unionid);
	}

}