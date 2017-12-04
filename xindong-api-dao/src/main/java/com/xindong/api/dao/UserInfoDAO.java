package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.query.UserInfoQuery;

public interface UserInfoDAO {
    Integer insert(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectByPrimaryKey(Integer userId);


    int deleteByPrimaryKey(Integer userId);
    /**
	 * 根据相应的条件查询满足条件的用户账号信息的总数
	 * @param userInfoQuery
	 * @return
	 */
	public int countByCondition(UserInfoQuery userInfoQuery);
	
	/**
	 * 根据相应的条件查询用户账号信息
	 * @param userInfoQuery
	 * @return
	 */
	public List<UserInfo> selectByCondition(UserInfoQuery userInfoQuery);

	UserInfo selectByInvitationCode(String invitationCode);

	Integer countByRegisterInvitationCode(String registerInvitationCode);

	UserInfo selectByUnionid(String unionid);
}