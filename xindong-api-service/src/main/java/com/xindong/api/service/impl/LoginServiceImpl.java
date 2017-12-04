package com.xindong.api.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.DESUtil;
import com.xindong.api.common.utils.MD5Util;
import com.xindong.api.common.utils.MobileLocationUtil;
import com.xindong.api.common.utils.RandomUtil;
import com.xindong.api.dao.BusinessUserExtDao;
import com.xindong.api.dao.SmsDAO;
import com.xindong.api.dao.UserBalanceDao;
import com.xindong.api.dao.UserBalanceLockDao;
import com.xindong.api.dao.UserInfoDAO;
import com.xindong.api.dao.UserInfoIncrDAO;
import com.xindong.api.domain.BusinessUserExt;
import com.xindong.api.domain.Sms;
import com.xindong.api.domain.UserBalance;
import com.xindong.api.domain.UserBalanceLock;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.UserInfoIncr;
import com.xindong.api.domain.query.BusinessUserExtQuery;
import com.xindong.api.domain.query.UserInfoQuery;
import com.xindong.api.service.LoginService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;
import com.xindong.api.service.utils.RedisUtils;
import com.xindong.api.service.utils.RedisValue;

@Service(value="loginService")
public class LoginServiceImpl implements LoginService {
	
	private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	@Autowired
	private UserInfoDAO userInfoDao;
	@Autowired
	private BusinessUserExtDao businessUserExtDao;
	@Autowired
	private SmsDAO smsDao;
	@Autowired
	private DataSourceTransactionManager transactionManager;
	@Autowired
	private UserBalanceDao userBalanceDao;
	@Autowired
	private UserBalanceLockDao userBalanceLockDao;
	@Autowired
	private UserInfoIncrDAO userInfoIncrDao;

	/**
	 * 买家用户登录
	 */
	@Override
	public Result buyerLogin(String mobile, String password, Integer loginType, String code, String ip) {
		Result result = new Result();
		try{
			// 判断手机号是否冻结
			String loginFrozen = RedisUtils.get(RedisValue.LoginFrozenName+mobile);
			if(!StringUtils.isBlank(loginFrozen)){
				result.setResultCode("3001");
				result.setResultMessage("账号被冻结信息。冻结账号20分钟后可以正常登录");
				return result;
			}
			
			// 判断手机号是否存在
			UserInfo userInfo = this.getUserInfoByMobile(mobile);
			if(userInfo.getUserId() == null){
				result.setResultCode("3002");
				result.setResultMessage("您输入的手机号不存在，请核对后重新输入");
				return result;
			}
			
			//判断登录类型
			if(loginType == 1){ //手机账号密码登录
				// 判断密码是否正确
				if(!userInfo.getPassword().equals(password)){
					// 添加限制，是否冻结
					Integer loginCount = this.isLimitRedis(mobile, RedisValue.LoginErroTime, RedisValue.LoginErroCount, 
							RedisValue.LoginFrozenName, RedisValue.LoginFrozenTime, RedisValue.LoginFrozenCount);
					if(loginCount == -1){	// 账号冻结,提示冻结信息
						result.setResultCode("3001");
						result.setResultMessage("账号被冻结信息。冻结账号20分钟后可以正常登录");
					}else{	// 账号未冻结,提示错误信息并返回错误次数
						result.setResultCode("4002");
						result.setResult(loginCount+"");
						result.setResultMessage("您输入的手机号和密码不匹配，请重新输入");
					}
					return result;
				}
			}else if(loginType == 2){//手机动态密码登录
				String dCode = RedisUtils.get(RedisValue.DynamicCodeName+mobile );
				// 判断动态密码是否过期
				if(StringUtils.isEmpty(dCode)){
					result.setResultCode("5001");
					result.setResultMessage("动态密码已过期，请重新获取动态密码");
					return result;
				}
				// 判断动态密码是否正确
				if(!code.equals(dCode)){
					// 添加限制，是否冻结
					Integer loginCount = this.isLimitRedis(mobile, RedisValue.LoginErroTime, RedisValue.LoginErroCount, 
							RedisValue.LoginFrozenName, RedisValue.LoginFrozenTime, RedisValue.LoginFrozenCount);
					if(loginCount == -1){	// 账号冻结,提示冻结信息
						result.setResultCode("3001");
						result.setResultMessage("账号被冻结信息。冻结账号20分钟后可以正常登录");
					}else{	// 账号未冻结,提示错误信息并返回错误次数
						result.setResultCode("5002");
						result.setResultMessage("动态密码错误，请重新输入");
						return result;
					}
				}
			}
			
			// 更新用户登录信息
			UserInfo userInfos = new UserInfo();
			userInfos.setUserId(userInfo.getUserId());
			userInfos.setLastLoginTime(new Date());
			userInfos.setLastLoginIp(ip);
			userInfoDao.updateByPrimaryKey(userInfos);
			
			// 准备返回结果
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", userInfo.getUserId());
			userMap.put("mobile", userInfo.getMobile());
			userMap.put("userName", userInfo.getUserName());
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		// 成功登陆，清楚缓存信息
		RedisUtils.del(RedisValue.LoginErroTime+mobile);
		RedisUtils.del(RedisValue.LoginErroCount+mobile);
		RedisUtils.del(RedisValue.LoginFrozenName+mobile);
		return result;
	}

	/**
	 * 买家用户注册
	 */
	@Override
	public Result buyerReg(final UserInfo info, String code) {
		Result result = new Result();
		final String mobile =info.getMobile();
		try{
			String reg_code = RedisUtils.get(RedisValue.RegCodeName+mobile );
			
			// 判断验证码是否过期
			if(StringUtils.isEmpty(reg_code)){
				result.setResultCode("4004");
				result.setResultMessage("验证码已过期，请重新获取验证码");
				return result;
			}
			
			// 判断验证码是否正确
			if(!code.equals(reg_code)){
				result.setResultCode("4004");
				result.setResultMessage("验证码错误，请重新输入");
				return result;
			}
			
			/*// 判断商铺名称是否注册
			final BusinessUserExt businessUserExt = this.getBusinessUserByShopName(shopName);
			if(businessUserExt.getId() != null){
				result.setResultCode("4007");
				result.setResultMessage("该商铺名称已被使用，请重新输入");
				return result;
			}
			*/
			// 判断手机号是否注册
			final UserInfo userInfo = this.getUserInfoByMobile(mobile);
			if(userInfo.getUserId() != null){
				result.setResultCode("4003");
				result.setResultMessage("该手机号已被使用，请更换手机号");
				return result;
			}
			//生成自己的邀请码
			String invitationCode = this.createInvitationCode(mobile);
			userInfo.setInvitationCode(invitationCode);
			
			final UserBalanceLock lock =new UserBalanceLock();
			final String registerInvitationCode = info.getRegisterInvitationCode();
			if(StringUtils.isNotBlank(registerInvitationCode)){
				UserInfo userFather = userInfoDao.selectByInvitationCode(registerInvitationCode);
				if(userFather ==null){
					result.setResultCode("4003");
					result.setResultMessage("请您填写正确的邀请码");
					return result;
				}
				userInfo.setRegisterInvitationCode(registerInvitationCode);
				//判断 一个人邀请的 最多人数
				Integer mostNum = userInfoDao.countByRegisterInvitationCode(registerInvitationCode);
				String mostInvitationNum =ComFunction.getSystemConstantTypeValue(
						Constants.SystemConstantTypeValue.xd_invitation_num);
				if(mostNum > Integer.valueOf(mostInvitationNum)){
					result.setResultCode("2003");
					result.setResultMessage("该邀请码已分享完成");
					return result;
				}
				//邀请人获得 余额。异步处理
				BigDecimal balanceTotal = new BigDecimal(ComFunction.getSystemConstantTypeValue(
							Constants.SystemConstantTypeValue.xd_register_invitation_money));//邀请用户注册获得的钱
						//添加锁定表数据，进行异步添加余额
						//获得该用户的余额对象
						UserBalance balance = userBalanceDao.selectByUserId(userFather.getUserId());
						if(balance !=null){//如果没有，则说明是第一次获得余额，在定时任务时进行新增用户余额
							lock.setBalanceId(balance.getBalanceId());
						}
						lock.setUserId(userFather.getUserId());
						lock.setBalanceStatus(Constants.BalanceLockStatus.LOCK_DONE);
						lock.setWorkStatus(Constants.BalanceWorkStatus.UNTREATED);
						lock.setBalanceAmount(balanceTotal);
						lock.setGrantType(Constants.BalanceGrantType.yqhd);
						lock.setRemark("邀请好友奖励");
						
			}
			//注册人获得 余额。异步处理
			final UserBalanceLock lockMe =new UserBalanceLock();
			//邀请用户注册获得的钱
			BigDecimal balanceTotalMe = new BigDecimal(0);
			String retBalMin=  RedisUtils.get("RST_BAL_MIN"); //注册下限余额
			String retBalMax=  RedisUtils.get("RST_BAL_MAX");//注册上限余额
			if(StringUtils.isBlank(retBalMin) || StringUtils.isBlank(retBalMax)){//判断用户注册送金额是否随机
				balanceTotalMe =new BigDecimal(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_register_money));
			}else{
				Integer minBal = Integer.parseInt(retBalMin);
				Integer maxBal = Integer.parseInt(retBalMax);
				int ranBal = (int) (Math.random() * (maxBal-minBal) + minBal);
				balanceTotalMe = new BigDecimal(ranBal);
			}
			//添加锁定表数据，进行异步添加余额
			lockMe.setBalanceStatus(Constants.BalanceLockStatus.LOCK_DONE);
			lockMe.setWorkStatus(Constants.BalanceWorkStatus.UNTREATED);
			lockMe.setBalanceAmount(balanceTotalMe);
			lockMe.setGrantType(Constants.BalanceGrantType.zcjl);
			lockMe.setRemark("注册奖励");
				
			
			//设置手机号码归属地
			try{
				String location[] = MobileLocationUtil.getRequest(mobile);
				userInfo.setProvince(location[0]);
				userInfo.setCity(location[1]);
				userInfo.setSupplier(location[2]);
			}catch(Exception e){
				log.error("获取手机号码归属地出现错误");
			}
			final String PASSWORD = MD5Util.md5Hex(info.getPassword());
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					// 注册用户信息
					userInfo.setMobile(mobile);
					userInfo.setPassword(PASSWORD);
					userInfo.setUserType(Constants.UserType.COMMON);
					userInfo.setRegisterIp(info.getRegisterIp());
					userInfo.setLastLoginTime(new Date());
					userInfo.setLastLoginIp(info.getRegisterIp());
//					userInfo.setUserName(info.getUserName());
					userInfo.setRegisterTime(new Date());
					userInfo.setUserFlag(info.getUserFlag());
					Integer userId = userInfoDao.insert(userInfo);
					if(userId == null || userId == 0){
						throw new RuntimeException("添加用户信息失败");
					}
					userInfo.setUserId(userId);
					
					lockMe.setUserId(userId);
					lockMe.setBusinessValue(String.valueOf(userId));//获得积分的业务主键（订单号，注册用户，操作用户）
					userBalanceLockDao.insert(lockMe);//插入用户锁定积分
					if(StringUtils.isNotBlank(registerInvitationCode)){
						lock.setBusinessValue(String.valueOf(userId));//获得积分的业务主键（订单号，注册用户，操作用户）
						userBalanceLockDao.insert(lock);//插入用户锁定积分
					}
					/*// 注册用户补充信息
					businessUserExt.setUserId(userId);
					businessUserExt.setShopName(SHOPNAME);
					Integer businessUserExtId = businessUserExtDao.insert(businessUserExt);
					if(businessUserExtId == null || businessUserExtId == 0){
						throw new RuntimeException("添加用户商铺信息失败");
					}
					businessUserExt.setId(businessUserExtId);*/
				}
			});
			
			// 返回信息
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", userInfo.getUserId());
			userMap.put("mobile", userInfo.getMobile());
			userMap.put("invitationCode", invitationCode);
			userMap.put("userBalance", balanceTotalMe);
//			userMap.put("shopName", businessUserExt.getShopName());
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		// 成功注册，清除缓存信息
		RedisUtils.del(RedisValue.RegCodeName+mobile);
		RedisUtils.del(RedisValue.RegCodeSendCount+mobile);
		RedisUtils.del(RedisValue.RegCodeSendTime+mobile);
		RedisUtils.del(RedisValue.RegCodeFrozenName+mobile);
		return result;
	}
	
	/**
	 * 获取6位随机数
	 * @return
	 */
	private String getRandomNum(){
		Random random = new Random();
		int x = random.nextInt(899999);
	    x = x+100000;
	    return String.valueOf(x);
	}
	
	/**
	 * 生成唯一邀请码
	 * @param mobile
	 * @return
	 */
	private String createInvitationCode(String mobile) {
		String invitationCode="";
		//生成邀请码
	    String recommendNum = getRandomNum();
	    if(!StringUtils.isEmpty( recommendNum )){
	      UserInfo user = null ;
	      String registerInvitationCode = recommendNum;//临时邀请码
	      while(true){
	        user = userInfoDao.selectByInvitationCode(registerInvitationCode);
	        if(user == null){
	          invitationCode =  registerInvitationCode ;
	          break;
	        }else{
	        	registerInvitationCode = getRandomNum();
	        }
	      }
	    }
		return invitationCode;
	}

	/**
	 * 第三方注册登录
	 */
	@Override
	public Result thirdLogin(final UserInfoIncr incr,final String ip) {
		final Result result = new Result();
		new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
			try{
				UserInfo userInfos = new UserInfo();
				
				//1 查询该用户是否注册，如果已经注册就直接返回token；如果没有注册，先注册后返回登录成功
				//根据微信主键查询唯一    1个unionid 对应多个openid
				String unionid=incr.getUnionid();
				UserInfo info = userInfoDao.selectByUnionid(unionid);
				if(info !=null){
					Integer userId =info.getUserId();
					// 更新用户登录信息
					userInfos.setUserId(userId);
					userInfos.setHeadUrl(incr.getHeadImageurl());
					userInfos.setSex(incr.getSex());
					userInfos.setUserName(incr.getName());
					userInfos.setLastLoginTime(new Date());
					userInfos.setLastLoginIp(ip);
					userInfos.setUnionid(unionid);
					userInfoDao.updateByPrimaryKey(userInfos);
					
					UserInfoIncr in= new UserInfoIncr();
//					in.setFromWhere(incr.getFromWhere());
					in.setOpenid(incr.getOpenid());
					in.setUnionid(incr.getUnionid());
					in.setYn(Constants.YES);
				   List<UserInfoIncr> userInfoIncr  = userInfoIncrDao.selectByCondition(in);
				   if(userInfoIncr!=null && userInfoIncr.size() >0){
					    incr.setUserId(userId);
						incr.setModified(new Date());
						userInfoIncrDao.updateByCondition(incr);
				   }else{
					    incr.setUserId(userId);	
						incr.setYn(Constants.YES);
						Integer  num = userInfoIncrDao.insert(incr);
						if(num == 0){
							throw new RuntimeException("添加用户信息失败");
						}
				   }
				    userInfos.setUserName(incr.getName());
				    userInfos.setUserType(info.getUserType());
					userInfos.setMobile(info.getMobile());
				}else{
					UserInfo userInfo =new UserInfo();
					// 注册用户信息
					userInfo.setUserType(Constants.UserType.COMMON);
					userInfo.setRegisterIp(ip);
					userInfo.setRegisterTime(new Date());
					userInfo.setLastLoginTime(new Date());
					userInfo.setLastLoginIp(ip);
					userInfo.setUserName(incr.getName());
					userInfo.setYn(Constants.YES);
					userInfo.setSex(incr.getSex());
					userInfo.setUnionid(unionid);
					userInfo.setHeadUrl(incr.getHeadImageurl());
					Integer userId = userInfoDao.insert(userInfo);
					if(userId == null || userId == 0){
						throw new RuntimeException("添加用户信息失败");
					}
					userInfos.setUserId(userId);
					userInfos.setUserType(Constants.UserType.COMMON);
					userInfos.setUserName(incr.getName());
					
					incr.setUserId(userId);	
					incr.setYn(Constants.YES);
					Integer id = userInfoIncrDao.insert(incr);
					if(id == null || id == 0){
						throw new RuntimeException("添加用户信息失败");
					}
				}
				
				// 准备返回结果
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("userId", userInfos.getUserId());
				userMap.put("mobile", userInfos.getMobile());
				userMap.put("userName", userInfos.getUserName());
				map.put("userInfo", userMap);
				map.put("token", createLoginToken(userInfos));
				result.setResult(map);
				EcUtils.setSuccessResult(result);
			}catch (Exception e) {
				log.error("第三方注册登录出现异常", e);
				EcUtils.setExceptionResult(result);
				throw new RuntimeException("添加用户信息失败"); 
			}
			}
		});
		return result;
	}
	
	/**
	 * 买家找回密码
	 */
	@Override
	public Result retrievePwd(String k, String password) {
		Result result = new Result();
		String mobile = RedisUtils.get(RedisValue.ResetValidCodeName+k);
		//mobile = "13522849845";
		try{
			// 校验验证码是否有效
			if(StringUtils.isBlank(mobile)){
				result.setResultCode("4004");
				result.setResultMessage("短信验证码无效，请重新获取");
				return result;
			}
			
			// 获取手机号的用户信息
			UserInfo userInfo = this.getUserInfoByMobile(mobile);
			
			// 修改用户登陆密码
			userInfo.setPassword(MD5Util.md5Hex(password));
			userInfoDao.updateByPrimaryKey(userInfo);
			// 返回信息
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", userInfo.getUserId());
			userMap.put("mobile", userInfo.getMobile());
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		// 找回密码成功，清除缓存信息
		RedisUtils.del(RedisValue.ResetValidCodeName+k);
		RedisUtils.del(RedisValue.ResetCodeSendCount+mobile);
		RedisUtils.del(RedisValue.ResetCodeSendTime+mobile);
		RedisUtils.del(RedisValue.ResetCodeFrozenName+mobile);
		return result;
	}
	
	/**
	 * 重置手机号
	 */
	@Override
	public Result resetMobile(String k, String code, String mobile, Integer userId) {
		Result result = new Result();
		String oldValue = RedisUtils.get(RedisValue.OldMValidCodeName+k);
		String newValue = RedisUtils.get(RedisValue.NewMCodeName+mobile);
		
		try{
			// 判断旧验证码是否过期
			if(StringUtils.isEmpty(oldValue)){
				result.setResultCode("4004");
				result.setResultMessage("验证码不正确");
				return result;
			}
			
			// 判断新验证码是否有效
			if(!code.equals(newValue)){
				result.setResultCode("4004");
				result.setResultMessage("验证码不正确");
				return result;
			}
			
			// 修改用户手机号
			UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);
			userInfo.setMobile(mobile);
			userInfoDao.updateByPrimaryKey(userInfo);
			
			// 返回信息
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", userInfo.getUserId());
			userMap.put("mobile", userInfo.getMobile());
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
			
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		
		return result;
	}

	/**
	 * 用户发送验证码,发送类型(1:找回密码时验证码,2:注册时验证码,3:修改原手机验证码,4:修改新手机验证码,5:第三方登录绑定手机)
	 */
	@Override
	public Result sendCode(String mobile, Integer sendType) {
		Result result = new Result();
		
		if(sendType == 1){// 找回密码时验证码
			result = this.sendCode(mobile, sendType, RedisValue.ResetCodeName, RedisValue.ResetCodeTime, 
					RedisValue.ResetCodeSendTime, RedisValue.ResetCodeSendCount, 
					RedisValue.ResetCodeFrozenName, RedisValue.ResetCodeFrozenTime, RedisValue.ResetCodeFrozenCount);
		}else if(sendType == 2){	// 注册时验证码   
			result = this.sendCode(mobile, sendType, RedisValue.RegCodeName, RedisValue.RegCodeTime, 
		// RegCodeSendTime_ 记录第一次发送验证码时间        RegCodeSendCount_记录发送验证码次数
					RedisValue.RegCodeSendTime, RedisValue.RegCodeSendCount, 
					// RegCodeFrozen_  冻结用户            86400 冻结时间                                                    10 发送最大次数
					RedisValue.RegCodeFrozenName, RedisValue.RegCodeFrozenTime, RedisValue.RegCodeFrozenCount);
		}else if(sendType == 3){	// 修改原手机验证码
			result = this.sendCode(mobile, sendType, RedisValue.OldMCodeName, RedisValue.OldMCodeTime, 
					RedisValue.OldMCodeSendTime, RedisValue.OldMCodeSendCount, 
					RedisValue.OldMCodeFrozenName, RedisValue.OldMCodeFrozenTime, RedisValue.OldMCodeFrozenCount);
		}else if(sendType == 4){	// 修改新手机验证码
			
			result = this.sendCode(mobile, sendType, RedisValue.NewMCodeName, RedisValue.NewMCodeTime, 
					RedisValue.NewMCodeSendTime, RedisValue.NewMCodeSendCount, 
					RedisValue.NewMCodeFrozenName, RedisValue.NewMCodeFrozenTime, RedisValue.NewMCodeFrozenCount);
		}else if(sendType ==5){	 // 第三方登录绑定手机
			result = this.sendCode(mobile, sendType, RedisValue.RegCodeName, RedisValue.RegCodeTime, 
					// RegCodeSendTime_ 记录第一次发送验证码时间        RegCodeSendCount_记录发送验证码次数
								RedisValue.RegCodeSendTime, RedisValue.RegCodeSendCount, 
								// RegCodeFrozen_  冻结用户            86400 冻结时间                                                    10 发送最大次数
								RedisValue.RegCodeFrozenName, RedisValue.RegCodeFrozenTime, RedisValue.RegCodeFrozenCount);
		}else if(sendType == 6){ //动态密码登录
			result = this.sendCode(mobile, sendType, RedisValue.DynamicCodeName, RedisValue.DynamicCodeTime, 
					// RegCodeSendTime_ 记录第一次发送验证码时间        RegCodeSendCount_记录发送验证码次数
								RedisValue.DynamicCodeSendTime, RedisValue.DynamicCodeSendCount, 
								// RegCodeFrozen_  冻结用户            86400 冻结时间                                                    10 发送最大次数
								RedisValue.DynamicCodeFrozenName, RedisValue.DynamicCodeFrozenTime, RedisValue.DynamicCodeFrozenCount);
		}else{
			result.setResultCode("4001");
			result.setResultMessage("错误的信息");
		}
		return result;
	}
	
	/**
	 * 校验验证码
	 */
	@Override
	public Result validCode(String mobile, String code, Integer validType){
		Result result = new Result();
		
		// 校验找回密码发送的验证码
		if(validType == 1){
			result = validCode(mobile, code, RedisValue.ResetCodeName, 
					RedisValue.ResetValidCodeName, RedisValue.ResetValidCodeTime);
		}
		
		// 校验修改原手机号验证码
		if(validType == 2){
			result = validCode(mobile, code, RedisValue.OldMCodeName, 
					RedisValue.OldMValidCodeName, RedisValue.OldMValidCodeTime);
		}
		
		return result;
	}
	
	/** 卖家登录 */
	@Override
	public Result sellerLogin(String mobile, String password) {
		Result result = new Result();
		try{
			//查询用户信息
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setMobile(mobile);
			List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
			//判断是否查询到数据
			if(list == null || list.size() == 0){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
			
			UserInfo userInfo = list.get(0);
			if(!userInfo.getPassword().equals(password)){
				result.setResultCode("4002");
				result.setResultMessage("密码不正确");
				return result;
			}
			if(userInfo.getUserType() != 2){
				result.setResultCode("4006");
				result.setResultMessage("非商家用户不能登陆");
				return result;
			}
			//根据用户ID查询卖家信息
//			userInfo.setBusinessUserExt(this.businessUserExtDao.selectByUserId(userInfo.getUserId()));
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> userMap = new HashMap<String, Object>();
			//把查询到的卖家信息添加到userMap集合中
			userMap.put("userId", userInfo.getUserId());
			userMap.put("mobile", userInfo.getMobile());

			
			//添加businessType（卖家属性：1普通卖家；2认证商家；3vip商家）属性
//            if( null != userInfo.getBusinessUserExt() ){
//                userMap.put("businessType", userInfo.getBusinessUserExt().getBusinessType());
//            }
			//把userMap集合和token添加到map集合中
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
			//把map中数据用result（object）接收
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/** 
	 * 卖家注册
	 */
	@Override
	public Result sellerReg(String mobile, String password, String code,
			String ip, String shopName) {
		Result result = new Result();
		
		try{
			//查询数据库，判断该用户是否已经注册过
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setMobile(mobile);
			List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
			if(list != null && list.size() > 0){
				result.setResultCode("4003");
				result.setResultMessage("手机号已存在");
				return result;
			}
			//查询数据库，判断该用户的商家名称是否已经存在
			BusinessUserExtQuery businessUserExtQuery = new BusinessUserExtQuery();
			businessUserExtQuery.setShopName(shopName);
			List<BusinessUserExt> list2 = businessUserExtDao.selectByCondition(businessUserExtQuery);
			if(list2 != null && list2.size() > 0){
				result.setResultCode("4007");
				result.setResultMessage("商家名称已存在");
				return result;
			}
			
			// 注册用户信息
			final UserInfo userInfo = new UserInfo();
			userInfo.setMobile(mobile);
			userInfo.setUserType(2);
			userInfo.setYn(1);
			userInfo.setLastLoginIp(ip);
			userInfo.setLastLoginTime(new Date());
			userInfo.setRegisterIp(ip);
			userInfo.setRegisterTime(new Date());
			userInfo.setPassword(MD5Util.md5Hex(password));
			
			// 注册卖家信息
			final BusinessUserExt businessUserExt = new BusinessUserExt();
			businessUserExt.setCreated(new Date());
			businessUserExt.setShopName(shopName);
			businessUserExt.setStatus(1);
			businessUserExt.setMobile(mobile);
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					Integer userId = userInfoDao.insert(userInfo);
					
					businessUserExt.setUserId(userId);
					Integer id = businessUserExtDao.insert(businessUserExt);
					
					businessUserExt.setId(id);
					userInfo.setUserId(userId);
				}
			});
			
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", userInfo.getUserId());
			userMap.put("mobile", userInfo.getMobile());
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 卖家找回密码
	 */
	public Result findPwd(String k, String password){
		Result result = new Result();
		String mobile = RedisUtils.get(RedisValue.ResetValidCodeName+k);
		try{
			// 校验验证码是否有效
			if(StringUtils.isBlank(mobile)){
				result.setResultCode("4004");
				result.setResultMessage("短信验证码无效，请重新获取");
				return result;
			}
			// 获取手机号的用户信息
			UserInfo userInfo = this.getUserInfoByMobile(mobile);
			// 修改用户登陆密码
			userInfo.setPassword(MD5Util.md5Hex(password));
			userInfoDao.updateByPrimaryKey(userInfo);
			// 返回信息
			BusinessUserExt businessUserExt = businessUserExtDao.selectByUserId(userInfo.getUserId());
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("userId", userInfo.getUserId());
			userMap.put("mobile", userInfo.getMobile());
			userMap.put("shopName", businessUserExt.getShopName());
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
						
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		RedisUtils.del(RedisValue.ResetValidCodeName+k);
		RedisUtils.del(RedisValue.ResetCodeSendCount+mobile);
		RedisUtils.del(RedisValue.ResetCodeSendTime+mobile);
		RedisUtils.del(RedisValue.ResetCodeFrozenName+mobile);
		return result;
	}
	
	/**
	 * 验证手机号是否已注册
	 */
	@Override
	public Result checkMobile(String mobile) {
		Result result = new Result();
		try{
			UserInfoQuery userInfoQuery = new UserInfoQuery();
			userInfoQuery.setMobile(mobile);
			List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
			if(list != null && list.size() > 0){
				result.setResultCode("4003");
				result.setResultMessage("手机号已存在");
				return result;
			}
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 发送找回密码验证码
	 */
	@Override
	public Result checkShopName(String shopName) {
		Result result = new Result();
		try{
			BusinessUserExtQuery businessUserExtQuery = new BusinessUserExtQuery();
			businessUserExtQuery.setShopName(shopName);
			List<BusinessUserExt> list = businessUserExtDao.selectByCondition(businessUserExtQuery);
			if(list != null && list.size() > 0){
				result.setResultCode("4007");
				result.setResultMessage("商家名称已存在");
				return result;
			}
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 用户发送验证码
	 * // 注册时验证码                                                                                          "RegCode_"验证码名称              300  验证码有效时间
	 *		result = this.sendCode(mobile, sendType, RedisValue.RegCodeName, RedisValue.RegCodeTime, 
	 *	  RegCodeSendTime_ 记录第一次发送验证码时间        RegCodeSendCount_记录发送验证码次数
	 *				RedisValue.RegCodeSendTime, RedisValue.RegCodeSendCount, 
	 *				 RegCodeFrozen_  冻结用户            86400 冻结时间                                                    10 发送最大次数
	 *				RedisValue.RegCodeFrozenName, RedisValue.RegCodeFrozenTime, RedisValue.RegCodeFrozenCount);
	 * 
	 */
	private Result sendCode(String mobile, Integer sendType, String codeName, Integer codeTime, String erroTimeName, 
			String erroCountName, String frozenName, Integer frozenTime, Integer frozenCount){
		Result result = new Result();
		
		try{
			// 判断手机号是否存在
			UserInfo userInfo = this.getUserInfoByMobile(mobile);
			//买家发送验证码,发送类型(1:找回密码时验证码,2:注册时验证码,3:修改原手机验证码,4:修改新手机验证码,5:第三方登录绑定手机)
			if(sendType == 2 || sendType == 4){
				if(userInfo.getUserId() != null){
					result.setResultCode("4003");
					result.setResultMessage("该手机号已注册，请更换手机号");
					return result;
				}
			}else if(sendType == 1 || sendType == 3 || sendType == 6){
				if(userInfo.getUserId() == null){
					result.setResultCode("4003");
					result.setResultMessage("该手机号未注册，请更换手机号");
					return result;
				}
			}/*else if(sendType == 5){
				if(userInfo.getUserId() != null){
					result.setResultCode("4003");
					result.setResultMessage("该手机号已注册，请更换手机号或使用手机登录");
					return result;
				}
			}*/
			
			
			// 判断手机号是否冻结
			String resetCodeFrozen = RedisUtils.get(frozenName+mobile);
			if(!StringUtils.isBlank(resetCodeFrozen)){
				result.setResultCode("1004");
				result.setResultMessage("您申请获取短信验证码的次数过多，请24小时后再试");
				return result;
			}
			
			//限制用户通过刷新频繁发送
			if(sendType == 2 || sendType == 5 || sendType == 6)  {
				String regCodeintervalTime = RedisUtils.get(RedisValue.CodeIntervalName+mobile);
				if(!StringUtils.isBlank(regCodeintervalTime)){
					result.setResultCode("1004");
					result.setResultMessage("120秒内仅能获取一次短信验证码,请稍后重试");
					return result;
				}
			}
			
			// 发送验证码,添加短信记录
			int code = RandomUtil.randomRangeInt(100000, 999999);
			Sms sms = new Sms();
			sms.setContent(code+"");
			if(sendType == 2){
				sms.setType(Constants.SmsType.zc);//
			}else if(sendType == 5){
				sms.setType(Constants.SmsType.zc);//
			}else if(sendType == 6){
				sms.setType(Constants.SmsType.dtmm);//	
			}else{
				sms.setType(Constants.SmsType.mmzh);//
			}
			sms.setMobile(mobile);
			sms.setStatus(0);
			smsDao.insert(sms);
			
			RedisUtils.set(codeName+mobile, codeTime, code+"");
			
			if(sendType == 2 || sendType == 5){//注册
				//发送验证码时间限制
				String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				RedisUtils.set(RedisValue.CodeIntervalName+mobile, RedisValue.CodeIntervalTime, nowDate);
			}
			// 添加限制，是否冻结
			this.isLimitRedis(mobile, erroTimeName, erroCountName, 
					frozenName, frozenTime, frozenCount);
			
			result.setResult(true);
			if(null != userInfo){
				result.setResult(userInfo);
			}
			EcUtils.setSuccessResult(result);
			result.setResultMessage("验证码已发送，请查收短信");
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("验证码发送失败，请重新获取");
		}
		return result;
	}
	
	/**
	 * 校验验证码
	 */
	private Result validCode(String mobile, String code, String codeName, String validName, Integer validTime){
		Result result = new Result();
		
		try{
			String value = RedisUtils.get(codeName+mobile);
			//value="745185";
			// 判断验证码是否有效
			if(!code.equals(value)){
				result.setResultCode("4004");
				result.setResultMessage("验证码不正确");
				return result;
			}
			
			// 添加校验成功后的验证码到缓存中
			RedisUtils.del(codeName+mobile);
			String k = MD5Util.md5Hex(""+System.currentTimeMillis()+RandomUtil.randomRangeInt(100000, 999999));
			RedisUtils.set(validName+k, validTime, mobile);
			
			// 返回信息
			result.setResult(k);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("效验失败，请重新输入信息");
		}
		
		return result;
	}
	
	/**
	 * 添加redis进行操作限制
	 */
	private Integer isLimitRedis(String mobile, String erroTimeName, String erroCountName, 
			String frozenName, Integer frozenTime, Integer frozenCount){
		// 记录限制次数
		Integer count = 0;
		String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String erroTime = RedisUtils.get(erroTimeName+mobile);
		String erroCount = RedisUtils.get(erroCountName+mobile);
		// 判断是否第一次记录
		if(StringUtils.isBlank(erroTime)){			// 首次记录
			count = 1;
			erroTime = nowDate;
			RedisUtils.set(erroTimeName+mobile, frozenTime, nowDate);
		}else{										// 不是首次记录，记录限制次数
			count = Integer.parseInt(erroCount)+1;
		}
		RedisUtils.set(erroCountName+mobile, frozenTime, count.toString());
		// 判断限制次数,是否冻结
		if(count == frozenCount){					// 冻结账户
			RedisUtils.del(erroTimeName+mobile);
			RedisUtils.del(erroCountName+mobile);
			RedisUtils.set(frozenName+mobile, frozenTime, erroTime);
			return -1;
		}else{										// 不冻结账户
			return count;
		}
	}
	
	/**
	 * 通过手机号获取用户信息
	 */
	private UserInfo getUserInfoByMobile(String mobile){
		UserInfoQuery userInfoQuery = new UserInfoQuery();
		userInfoQuery.setMobile(mobile);
		List<UserInfo> list = userInfoDao.selectByCondition(userInfoQuery);
		UserInfo userInfo = new UserInfo();
		// 判断手机号是否存在
		if(list != null && list.size() > 0){
			userInfo = list.get(0);
		}
		return userInfo;
	}
	
/*	*//**
	 * 通过商铺名称获取用户补充信息
	 *//*
	private BusinessUserExt getBusinessUserByShopName(String shopName){
		BusinessUserExtQuery businessUserExtQuery = new BusinessUserExtQuery();
		businessUserExtQuery.setShopName(shopName);
		List<BusinessUserExt> list = businessUserExtDao.selectByCondition(businessUserExtQuery);
		BusinessUserExt businessUserExt = new BusinessUserExt();
		// 判断手机号是否存在
		if(list != null && list.size() > 0){
			businessUserExt = list.get(0);
		}
		return businessUserExt;
	}*/
	/**
	 * 创建用户登录令牌
	 */
	private static String createLoginToken(UserInfo userInfo) throws Exception{
		if(userInfo.getUserId() == null){
			return "";
		}
		String token = userInfo.getUserId() + "-" + userInfo.getMobile() + "-" + userInfo.getUserType();
		return DESUtil.encrypt(token, Constants.TOKEN_DES);
	}

	/**
	 * 第三方 登录 设置手机号
	 * registerInvitationCode 好友的邀请码
	 */
	@Override
	public Result setPhone(String code, final String mobile,final String password, final Integer userId,final String registerInvitationCode,final String userFlag) {

		Result result = new Result();
		String newValue = RedisUtils.get(RedisValue.RegCodeName+mobile);
		try{
			// 判断新验证码是否有效
			if(!code.equals(newValue)){
				result.setResultCode("4004");
				result.setResultMessage("验证码不正确");
				return result;
			}
			//说明已经有，关联用户
			boolean yesT =false;
			UserInfo userInfo = this.getUserInfoByMobile(mobile);
			if(userInfo !=null && userInfo.getUserId() !=null){
				yesT=true;
			}else{
				if(StringUtils.isEmpty(userFlag)){
					result.setResultCode("1001");
					result.setResultMessage("旅游偏好不能为空");
					return result;
				}
				if(StringUtils.isEmpty(password)){
					result.setResultCode("1001");
					result.setResultMessage("密码不能空");
					return result;
				}
				userInfo =new UserInfo();
			}
			final boolean yes=yesT;
			final UserBalanceLock lock =new UserBalanceLock();
			//注册人获得 余额。异步处理
			final UserBalanceLock lockMe =new UserBalanceLock();
			//邀请用户注册获得的钱
			BigDecimal balanceTotalMe = new BigDecimal(0);
			if(!yes){
				//设置手机号码归属地
				try{
					String location[] = MobileLocationUtil.getRequest(mobile);
					userInfo.setProvince(location[0]);
					userInfo.setCity(location[1]);
					userInfo.setSupplier(location[2]);
				}catch(Exception e){
					log.error("获取手机号码归属地出现错误");
				}
				
				//生成自己的邀请码
				if(StringUtils.isBlank(userInfo.getInvitationCode())){
					String invitationCode = this.createInvitationCode(mobile);
					userInfo.setInvitationCode(invitationCode);
				}
				
				if(StringUtils.isNotBlank(registerInvitationCode)){
					UserInfo userFather = userInfoDao.selectByInvitationCode(registerInvitationCode);
					if(userFather ==null){
						result.setResultCode("4003");
						result.setResultMessage("请您填写正确的邀请码");
						return result;
					}
					userInfo.setRegisterInvitationCode(registerInvitationCode);
					//判断 一个人邀请的 最多人数
					Integer mostNum = userInfoDao.countByRegisterInvitationCode(registerInvitationCode);
					String mostInvitationNum =ComFunction.getSystemConstantTypeValue(
							Constants.SystemConstantTypeValue.xd_invitation_num);
					if(mostNum > Integer.valueOf(mostInvitationNum)){
						result.setResultCode("2003");
						result.setResultMessage("该邀请码已分享完成");
						return result;
					}
					//邀请人获得 余额。异步处理
					BigDecimal balanceTotal = new BigDecimal(ComFunction.getSystemConstantTypeValue(
								Constants.SystemConstantTypeValue.xd_register_invitation_money));//邀请用户注册获得的钱
					//添加锁定表数据，进行异步添加余额
					//获得该用户的余额对象
					UserBalance balance = userBalanceDao.selectByUserId(userFather.getUserId());
					if(balance !=null){//如果没有，则说明是第一次获得余额，在定时任务时进行新增用户余额
						lock.setBalanceId(balance.getBalanceId());
					}
					lock.setUserId(userFather.getUserId());
					lock.setBalanceStatus(Constants.BalanceLockStatus.LOCK_DONE);
					lock.setWorkStatus(Constants.BalanceWorkStatus.UNTREATED);
					lock.setBalanceAmount(balanceTotal);
					lock.setGrantType(Constants.BalanceGrantType.yqhd);
					lock.setRemark("邀请好友奖励");
				}
				
				String retBalMin=  RedisUtils.get("RST_BAL_MIN"); //注册下限余额
				String retBalMax=  RedisUtils.get("RST_BAL_MAX");//注册上限余额
				if(StringUtils.isBlank(retBalMin) || StringUtils.isBlank(retBalMax)){//判断用户注册送金额是否随机
					balanceTotalMe =new BigDecimal(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_register_money));
				}else{
					Integer minBal = Integer.parseInt(retBalMin);
					Integer maxBal = Integer.parseInt(retBalMax);
					int ranBal = (int) (Math.random() * (maxBal-minBal) + minBal);
					balanceTotalMe = new BigDecimal(ranBal);
				}
				//添加锁定表数据，进行异步添加余额
				lockMe.setBalanceStatus(Constants.BalanceLockStatus.LOCK_DONE);
				lockMe.setWorkStatus(Constants.BalanceWorkStatus.UNTREATED);
				lockMe.setBalanceAmount(balanceTotalMe);
				lockMe.setGrantType(Constants.BalanceGrantType.zcjl);
				lockMe.setRemark("注册奖励");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			final Map<String, Object> userMap = new HashMap<String, Object>();
			final UserInfo  info =userInfo;
			
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					Integer lastUserId =null;//实际的用户
					//已经注册了
					if(yes){
						lastUserId = info.getUserId();
						//删除第三方注册使用的间接用户
						userInfoDao.deleteByPrimaryKey(userId);
						UserInfoIncr incr =new UserInfoIncr();
						incr.setUserId(userId);
						List<UserInfoIncr> incrs =  userInfoIncrDao.selectByCondition(incr);
						for(UserInfoIncr incrInfo:incrs){
							incr =new UserInfoIncr();
							incr.setId(incrInfo.getId());
							incr.setUserId(lastUserId);
							info.setHeadUrl(incrInfo.getHeadImageurl());
							info.setUserName(incrInfo.getName());
							info.setSex(incrInfo.getSex());
							userInfoIncrDao.updateByPrimaryKey(incr);
						}
						info.setUnionid(incrs.get(0).getUnionid());
						userInfoDao.updateByPrimaryKey(info);
						userMap.put("userId", lastUserId);
						userMap.put("mobile", info.getMobile());
					}else{
						// 修改用户手机号
						lastUserId =userId;
//						UserInfo user = new UserInfo();
						info.setUserId(userId);
						info.setMobile(mobile);
						info.setPassword(password);
						info.setUserFlag(userFlag);
						userInfoDao.updateByPrimaryKey(info);
						
						//只有新注册的用户才获得余额
						lockMe.setUserId(lastUserId);
						lockMe.setBusinessValue(String.valueOf(lastUserId));//获得积分的业务主键（订单号，注册用户，操作用户）
						userBalanceLockDao.insert(lockMe);//插入用户锁定积分
						
						userMap.put("userId", info.getUserId());
						userMap.put("mobile", info.getMobile());
					}
					
					if(StringUtils.isNotBlank(registerInvitationCode)){
						lock.setBusinessValue(String.valueOf(lastUserId));//获得积分的业务主键（订单号，注册用户，操作用户）
						userBalanceLockDao.insert(lock);//插入用户锁定积分
					}
				}
			});
			// 准备返回结果
			userInfo.setUserType(Constants.UserType.COMMON);
			
			map.put("userInfo", userMap);
			map.put("register", yesT);
			map.put("token", createLoginToken(userInfo));
			map.put("retBal", balanceTotalMe);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result setPhoneByWeb(String code, String mobile, String password, final String registerInvitationCode, String userFlag, String ip) {
		Result result = new Result();
		Map<String, Object> map = new HashMap<String, Object>();
		final Map<String, Object> userMap = new HashMap<String, Object>();
		try{
			// 判断验证码是否有效
			String regCode = RedisUtils.get(RedisValue.RegCodeName+mobile);
			if(!code.equals(regCode)){
				result.setResultCode("4004");
				result.setResultMessage("验证码不正确");
				return result;
			}
			//判断手机号是否注册
			UserInfo userInfo = getUserInfoByMobile(mobile);
			if(null != userInfo && null != userInfo.getUserId()){
				result.setResultCode("4004");
				result.setResultMessage("手机号码已注册");
				return result;
			}else{
				userInfo = new UserInfo();
				userInfo.setMobile(mobile);
				userInfo.setPassword(password);
				userInfo.setUserFlag(userFlag);
				userInfo.setUserType(Constants.UserType.COMMON);
				userInfo.setRegisterIp(ip);
				userInfo.setRegisterTime(new Date());
			}
			//设置手机号码归属地
			String location[] = MobileLocationUtil.getRequest(mobile);
			userInfo.setProvince(location[0]);
			userInfo.setCity(location[1]);
			userInfo.setSupplier(location[2]);
			
			//生成自己的邀请码
			if(StringUtils.isBlank(userInfo.getInvitationCode())){
				String invitationCode = createInvitationCode(mobile);
				userInfo.setInvitationCode(invitationCode);
			}
			
			final UserBalanceLock lock =new UserBalanceLock();
			//注册人获得 余额。异步处理
			final UserBalanceLock lockMe =new UserBalanceLock();
			//邀请用户注册获得的钱
			BigDecimal balanceTotalMe = new BigDecimal(0);
			if(StringUtils.isNotBlank(registerInvitationCode)){
				UserInfo userFather = userInfoDao.selectByInvitationCode(registerInvitationCode);
				if(userFather ==null){
					result.setResultCode("4003");
					result.setResultMessage("请您填写正确的邀请码");
					return result;
				}
				userInfo.setRegisterInvitationCode(registerInvitationCode);
				//判断 一个人邀请的 最多人数
				Integer mostNum = userInfoDao.countByRegisterInvitationCode(registerInvitationCode);
				String mostInvitationNum =ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_invitation_num);
				if(mostNum > Integer.valueOf(mostInvitationNum)){
					result.setResultCode("2003");
					result.setResultMessage("该邀请码已分享完成");
					return result;
				}
				//邀请人获得 余额。异步处理
				BigDecimal balanceTotal = new BigDecimal(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_register_invitation_money));//邀请用户注册获得的钱
				//添加锁定表数据，进行异步添加余额
				//获得该用户的余额对象
				UserBalance balance = userBalanceDao.selectByUserId(userFather.getUserId());
				if(balance !=null){//如果没有，则说明是第一次获得余额，在定时任务时进行新增用户余额
					lock.setBalanceId(balance.getBalanceId());
				}
				lock.setUserId(userFather.getUserId());
				lock.setBalanceStatus(Constants.BalanceLockStatus.LOCK_DONE);
				lock.setWorkStatus(Constants.BalanceWorkStatus.UNTREATED);
				lock.setBalanceAmount(balanceTotal);
				lock.setGrantType(Constants.BalanceGrantType.yqhd);
				lock.setRemark("邀请好友奖励");
			}
				
			String retBalMin=  RedisUtils.get("RST_BAL_MIN"); //注册下限余额
			String retBalMax=  RedisUtils.get("RST_BAL_MAX");//注册上限余额
			if(StringUtils.isBlank(retBalMin) || StringUtils.isBlank(retBalMax)){//判断用户注册送金额是否随机
				balanceTotalMe =new BigDecimal(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_register_money));
			}else{
				Integer minBal = Integer.parseInt(retBalMin);
				Integer maxBal = Integer.parseInt(retBalMax);
				int ranBal = (int) (Math.random() * (maxBal-minBal) + minBal);
				balanceTotalMe = new BigDecimal(ranBal);
			}
			//添加锁定表数据，进行异步添加余额
			lockMe.setBalanceStatus(Constants.BalanceLockStatus.LOCK_DONE);
			lockMe.setWorkStatus(Constants.BalanceWorkStatus.UNTREATED);
			lockMe.setBalanceAmount(balanceTotalMe);
			lockMe.setGrantType(Constants.BalanceGrantType.zcjl);
			lockMe.setRemark("注册奖励");
			
			final UserInfo  user =userInfo;
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					Integer userId = userInfoDao.insert(user);
					//只有新注册的用户才获得余额
					lockMe.setUserId(userId);
					lockMe.setBusinessValue(String.valueOf(userId));//获得积分的业务主键（订单号，注册用户，操作用户）
					userBalanceLockDao.insert(lockMe);//插入用户锁定积分
					
					userMap.put("userId", userId);
					userMap.put("mobile", user.getMobile());
					
					if(StringUtils.isNotBlank(registerInvitationCode)){
						lock.setBusinessValue(String.valueOf(userId));//获得积分的业务主键（订单号，注册用户，操作用户）
						userBalanceLockDao.insert(lock);//插入用户锁定积分
					}
				}
			});
			// 准备返回结果
			userInfo.setUserType(Constants.UserType.COMMON);
			map.put("userInfo", userMap);
			map.put("token", createLoginToken(userInfo));
			map.put("retBal", balanceTotalMe);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	
	@Override
	public Result getUserMobile(String mobile) {
		Result result = new Result();
		boolean noAccount =true;
		//判断手机号是否注册
		UserInfo userInfo = getUserInfoByMobile(mobile);
		if(null != userInfo && null != userInfo.getUserId()){
			result.setResultCode("4004");
			result.setResultMessage("手机号码已注册");
		}else{
			result.setResult(noAccount);
			EcUtils.setSuccessResult(result);
		}
		return result;
	}
}
