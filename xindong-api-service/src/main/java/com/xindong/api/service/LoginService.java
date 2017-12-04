package com.xindong.api.service;

import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.UserInfoIncr;
import com.xindong.api.service.result.Result;

public interface LoginService {
	/**
	 * 买家用户登陆
	 * @param mobile 手机号
	 * @param loginType 登录类型（1、账号密码登录；2、手机动态密码登录） 
	 * @param password 密码
	 * @return
	 */
	public Result buyerLogin(String mobile, String password, Integer loginType, String code, String ip);
	
	/**
	 * 买家找回密码
	 * @param k	手机校验码
	 * @param password 新密码
	 * @return
	 */
	public Result retrievePwd(String k, String password);
	
	/**
	 * 重置手机号
	 * @param k	旧手机校验码
	 * @param code	新手机验证码
	 * @param mobile 新手机
	 * @return
	 */
	public Result resetMobile(String k, String code, String mobile, Integer userId);
	
	/**
	 * 发送验证码
	 * @param mobile 手机号
	 * @param sendType 发送类型(1:找回密码时验证码,2:注册时验证码,3:修改原手机验证码,4:修改新手机验证码)
	 * @return
	 */
	public Result sendCode(String mobile, Integer sendType);
	
	/**
	 * 校验找回密码发送的验证码
	 * @param mobile 手机号
	 * @param code 验证码
	 * @return
	 */
	public Result validCode(String mobile, String code, Integer validType);
	
	/**
	 * 卖家用户注册
	 * @param mobile 手机号
	 * @param password 密码
	 * @param code 验证码
	 * @return
	 */
	public Result sellerReg(String mobile, String password, String code, String ip, String shopName);
	
	/**
	 * 卖家用户登陆
	 * @param mobile 手机号
	 * @param password 密码
	 * @return
	 */
	public Result sellerLogin(String mobile, String password);
	
	/**
	 * 卖家找回密码
	 * @param k 手机校验码
	 * @param password 密码
	 * @return
	 */
	public Result findPwd(String k, String password);
	
	/**
	 * 判断手机是否可以注册
	 * @param mobile 手机号
	 * @return
	 */
	public Result checkMobile(String mobile);
	
	/**
	 * 判断店铺名称是否可以注册
	 * @param mobile 手机号
	 * @return
	 */
	public Result checkShopName(String shopName);

	public Result thirdLogin(UserInfoIncr incr, String ip);

	public Result setPhone(String code, String mobile,String password,Integer userId, String registerInvitationCode,String userFlag);

	public Result setPhoneByWeb(String code, String mobile,String password, String registerInvitationCode,String userFlag, String ip);
	
	public Result buyerReg(UserInfo info, String code);

	public Result getUserMobile(String mobile);
}
