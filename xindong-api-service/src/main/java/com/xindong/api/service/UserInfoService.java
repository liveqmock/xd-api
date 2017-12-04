package com.xindong.api.service;

import javax.servlet.http.HttpServletRequest;

import com.xindong.api.domain.PassengerGroup;
import com.xindong.api.domain.UserCollection;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.query.PassengerGroupQuery;
import com.xindong.api.domain.query.UserBalanceDetailQuery;
import com.xindong.api.domain.query.UserCollectionQuery;
import com.xindong.api.service.result.Result;

public interface UserInfoService {
	/**
	 * 登陆状态下，修改登陆密码
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	public Result updatePwd(Integer userId, String oldPwd, String newPwd);
	
	/**
	 * 获取登陆用户信息
	 * @param userId
	 * @return
	 */
	public Result getUserInfoByUserId(Integer userId);
	
	/**
	 * 获取用户绑定银行卡信息
	 * @param userId
	 * @return
	 */
	public Result getPaymentBindbankcard(Integer userId);
	/**
	 * 修改用户头像信息
	 * @param userId
	 * @param headUrl
	 * @return
	 */
	public Result updateUserHeadImg(Integer userId, String headUrl);
	/**
	 * 完善用户信息
	 * @param userInfo
	 * @return
	 */
	public Result completeUserInfo(UserInfo userInfo);

	/**
	 * 头像上传
	 * @param request 
	 * @return
	 */
	public Result updateuploadUserHeadImgUserHeadImg(HttpServletRequest request);

	UserInfo selectByUserId(Integer userId);

	public Result sendAccountData(String phone);

	public Result getUserInfoBalancePage(UserBalanceDetailQuery detailQuery);

	public Result getCollectionByUserId(UserCollectionQuery userCollectionQuery);

	public Result addCollectionByUserId(UserCollection userCollection);

	public Result getPassengerByUserId(PassengerGroupQuery passengerGroupQuery);

	public Result updatePassengerByUserId(PassengerGroup passengerGroup);

	public Result delPassengerByUserId(PassengerGroup passengerGroup);

	Result cancleCollectionByUserId(UserCollection userCollection);

	public Result getCollectionAll(Integer userId);
	
}
