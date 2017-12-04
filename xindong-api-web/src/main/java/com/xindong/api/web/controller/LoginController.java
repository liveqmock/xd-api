package com.xindong.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.RegisterValidateRules;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.UserInfoIncr;
import com.xindong.api.service.LoginService;
import com.xindong.api.service.UserInfoService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.RedisUtils;
import com.xindong.api.service.utils.RedisValue;
import com.xindong.api.web.base.BaseController;

@Controller
@RequestMapping("user")
public class LoginController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	private LoginService loginService;
	private UserInfoService userInfoService;
	
	/**
	 * 买家用户登录
	 * @param mobile 手机号
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="buy/login", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result buyerLogin(String mobile, String password, Integer loginType, String code, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		
		Integer type = loginType == null ? 1:loginType;
		if(type == 2){//动态密码
			if(StringUtils.isEmpty(code)){
				result.setResultCode("1004");
				result.setResultMessage("动态密码不能为空");
				return result;
			}
		}else{//账号密码
			if(StringUtils.isEmpty(password)){
				result.setResultCode("1002");
				result.setResultMessage("password不能为空");
				return result;
			}
		}
		
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.buyerLogin(mobile, password, type, code, getRemoteIp(reuqest));
	}
	
	
	@RequestMapping(value="setPhoneByWeb", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result setPhoneByWeb(String code, String mobile,String password,String registerInvitationCode, String userFlag,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("手机号不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1002");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1001");
			result.setResultMessage("验证码不能为空");
			return result;
		}
		if(StringUtils.isEmpty(password)){
			result.setResultCode("1001");
			result.setResultMessage("密码不能空");
			return result;
		}
		if(StringUtils.isEmpty(userFlag)){
			result.setResultCode("1001");
			result.setResultMessage("旅游偏好不能为空");
			return result;
		}
		return loginService.setPhoneByWeb(code, mobile, password,registerInvitationCode,userFlag,getRemoteIp(request));
	}
	
	
	/**
	 * 买家用户注册
	 * @param name 名称
	 * @param mobile 手机号
	 * @param password 密码
	 * @param code 验证码
	 * @param openId
	 * userFlag 旅行偏好（1人文艺术、2亲子女性、3户外运动、4商旅交流、5教育公益、6历史军事、7美食养生、8行修国学、9自驾游艇）
	 * registerInvitationCode 好友的邀请码
	 * @return
	 */
	@RequestMapping(value="buy/reg", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result buyerReg(String name, String mobile, String password,String userFlag, String code, String openId,
			String registerInvitationCode,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isEmpty(password)){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		if(StringUtils.isEmpty(name)){
			result.setResultCode("1005");
			result.setResultMessage("用户昵称不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		if(StringUtils.isEmpty(userFlag)){
			result.setResultCode("1003");
			result.setResultMessage("旅行偏好不能为空");
			return result;
		}
		UserInfo info =new UserInfo();
		info.setUserName(name);
		info.setMobile(mobile);
		info.setPassword(password);
		info.setLastLoginIp(getRemoteIp(request));
		info.setRegisterIp(getRemoteIp(request));
		info.setUserFlag(userFlag.replaceAll(" ", ""));//替换空格
		info.setRegisterInvitationCode(registerInvitationCode);
		return loginService.buyerReg(info, code);
	}
	
	/**
	 * 买家找回密码
	 * @param k 手机校验码
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="retrievePwd", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result retrievePwd(String k, String password, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(k)){
			result.setResultCode("1006");
			result.setResultMessage("k不能为空");
			return result;
		}
		if(StringUtils.isEmpty(password)){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		
		return loginService.retrievePwd(k, password);
	}
	
	/**
	 * 自定义找回密码
	 * @param mobile
	 * @param code
	 * @return
	 */
	@RequestMapping(value="zhaoHui",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Result zhaoHui(String mobile,String code,HttpServletRequest request,HttpServletResponse response,ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isBlank(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
		}
		
		return result;
	}
	
	/**
	 * 发送验证码
	 * @param mobile 手机号
	 * @param sendType 验证描述
	 * @return
	 */
	@RequestMapping(value="sendCode", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result sendCode(String mobile, Integer sendType, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(sendType == null){
			result.setResultCode("1007");
			result.setResultMessage("sendType不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.sendCode(mobile, sendType);
	}
	
	/**
	 * 校验找回密码发送的验证码
	 * @param mobile 手机号
	 * @param code 验证码
	 * @return
	 */
	@RequestMapping(value="validFindPwdCode", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result validFindPwdCode(String mobile, String code, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.validCode(mobile, code, 1);
	}
	
	/**
	 * 校验原手机号时验证码
	 * @param mobile 手机
	 * @param code 验证码
	 * @return
	 */
	@RequestMapping(value="validOldMobileCode", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result validOldMobileCode(String mobile, String code, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.validCode(mobile, code, 2);
	}
	
	/**
	 * 卖家登陆
	 * @param mobile 登录手机号
	 * @param password 登录密码
	 * @return
	 */
	@RequestMapping(value="sell/login", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result sellerLogin(String mobile, String password, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isBlank(password)){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		return loginService.sellerLogin(mobile, password);
	}
	
	/**
	 * 卖家注册
	 * @param mobile 注册卖家手机号
	 * @param password 注册卖家密码
	 * @param code 注册卖家验证码
	 * @param shopName 注册卖家名称
	 * @return
	 */
	@RequestMapping(value="sell/reg", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result sellerReg(String mobile, String password, String code,String shopName, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(StringUtils.isBlank(password)){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		if(StringUtils.isBlank(code)){
			result.setResultCode("1004");
			result.setResultMessage("code不能为空");
			return result;
		}
		if(StringUtils.isBlank(shopName)){
			result.setResultCode("1005");
			result.setResultMessage("shopName不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		return loginService.sellerReg(mobile, password, code, getRemoteIp(request), shopName);
	}
	
	/**
	 * 卖家找回密码
	 * @param k 手机校验码
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value="findPwd", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result findPwd(String k, String password, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(k)){
			result.setResultCode("1006");
			result.setResultMessage("k不能为空");
			return result;
		}
		if(StringUtils.isEmpty(password)){
			result.setResultCode("1002");
			result.setResultMessage("password不能为空");
			return result;
		}
		
		return loginService.findPwd(k, password);
	}
	
	/**
	 * 手机号码验证
	 * @param mobile 手机号
	 * @return
	 */
	@RequestMapping(value="checkMobile", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result checkMobile(String mobile, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("mobile不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1003");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		return this.loginService.checkMobile(mobile);
	}
	
	/**
	 * 验证买家用户名
	 * @param shopName 买家用户名
	 * @return
	 */
	@RequestMapping(value="checkShopName", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result checkShopName(String shopName, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(shopName)){
			result.setResultCode("1005");
			result.setResultMessage("shopName不能为空");
			return result;
		}
		return this.loginService.checkShopName(shopName);
	}
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId 用户ID
	 * @return
	 */
	@RequestMapping(value="getUserInfoByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getUserInfoByUserId(Integer userId, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(userId == null){
			result.setResultCode("1008");
			result.setResultMessage("userId不能为空");
			return result;
		}
		return userInfoService.getUserInfoByUserId(userId);
	}
	
	/**
	 * 清楚缓存
	 * @param mobile 手机
	 * @return
	 */
	@RequestMapping(value="test", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result test(String mobile, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		RedisUtils.del(RedisValue.LoginFrozenName+mobile);
		RedisUtils.del(RedisValue.LoginErroCount+mobile);
		RedisUtils.del(RedisValue.LoginErroTime+mobile);
		
		RedisUtils.del(RedisValue.RegCodeSendTime+mobile);
		RedisUtils.del(RedisValue.RegCodeSendCount+mobile);
		RedisUtils.del(RedisValue.RegCodeFrozenName+mobile);
		RedisUtils.del(RedisValue.RegCodeName+mobile);
		
		RedisUtils.del(RedisValue.ResetValidCodeName+mobile);
		RedisUtils.del(RedisValue.ResetCodeSendTime+mobile);
		RedisUtils.del(RedisValue.ResetCodeSendCount+mobile);
		RedisUtils.del(RedisValue.ResetCodeFrozenName+mobile);
		RedisUtils.del(RedisValue.ResetCodeName+mobile);
		
		RedisUtils.del(RedisValue.OldMCodeName+mobile);
		RedisUtils.del(RedisValue.OldMValidCodeName+mobile);
		RedisUtils.del(RedisValue.OldMCodeSendTime+mobile);
		RedisUtils.del(RedisValue.OldMCodeSendCount+mobile);
		RedisUtils.del(RedisValue.OldMCodeFrozenName+mobile);
		
		RedisUtils.del(RedisValue.NewMCodeName+mobile);
		RedisUtils.del(RedisValue.NewMCodeSendTime+mobile);
		RedisUtils.del(RedisValue.NewMCodeSendCount+mobile);
		RedisUtils.del(RedisValue.NewMCodeFrozenName+mobile);
		
		result.setResultMessage("成功清楚缓存");
		result.setSuccess(true);
		result.setResult(true);
		return result;
	}
	/**
	 * 第三方用户注册登录
	 * @param name 名称
	 * @param openid 第三方主键
	 * headImageurl 
	 * @param type 第三方平台类型(1微信;2QQ 3新浪微博)
	 * @param fromWhere 来源(0-H5  1-android 2-IOS 3-PC)
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="third/login", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result ThirdLogin(UserInfoIncr incr, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(incr.getOpenid())){
			result.setResultCode("1001");
			result.setResultMessage("请求参数错误");
			return result;
		}
		String name =incr.getName();
		if(StringUtils.isEmpty(incr.getName())){
			result.setResultCode("1001");
			result.setResultMessage("请求参数错误");
			return result;
		}
		incr.setName(ComFunction.setValue(name));
		if(incr.getType() == null ){
			result.setResultCode("1001");
			result.setResultMessage("请求参数错误");
			return result;
		}
		if(incr.getType() == 1){//微信
			if(StringUtils.isEmpty(incr.getUnionid())){
				result.setResultCode("1001");
				result.setResultMessage("请求参数错误");
				return result;
			}
		}
		
		if(incr.getFromWhere() == null ){
			incr.setFromWhere(Constants.FromWhere.PC);//默认pc
		}
		Integer sex = incr.getSex();//接口传0男1女  数据库存1男2女
		if(sex==null || 1== sex ){
			incr.setSex(2);
		}else{
			incr.setSex(1);
		}
		return loginService.thirdLogin(incr,getRemoteIp(request));
	}
	
	/**
	 * 判断用户是否已经注册
	   mobile
	 */
	@RequestMapping(value="getUserMobile", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getUserMobile(String mobile, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("请求参数错误");
			return result;
		}
		
		return loginService.getUserMobile(mobile);
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

}
