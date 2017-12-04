package com.xindong.api.service.utils;

public class RedisValue {
	
	/**
	 * 登陆错误限制
	 */
	public final static String LoginErroTime = "LoginErroTime_";			// 记录登陆错误时间
	public final static String LoginErroCount = "LoginErroCount_";			// 记录连续登陆错误次数
	public final static String LoginFrozenName = "LoginFrozen_";			// 记录冻结用户
	public final static Integer LoginFrozenTime = 1200;						// 冻结时间
	public final static Integer LoginFrozenCount = 11;						// 错误最大次数(11-1=10次错误)
	
	/**
	 * 注册时验证码相关
	 */
	public final static String RegCodeName = "RegCode_";					// 验证码名称
	public final static Integer RegCodeTime = 360;							// 验证码有效时间
	public final static String RegCodeSendTime = "RegCodeSendTime_";		// 记录第一次发送验证码时间
	public final static String RegCodeSendCount = "RegCodeSendCount_";		// 记录发送验证码次数
	public final static String RegCodeFrozenName = "RegCodeFrozen_";		// 记录冻结用户
	public final static Integer RegCodeFrozenTime = 86400;					// 冻结时间
	public final static Integer RegCodeFrozenCount = 10;					// 发送最大次数
	
	/**
	 * 找回密码连续发送验证码相关
	 */
	public final static String ResetCodeName = "ResetCode_";				// 验证码名称
	public final static Integer ResetCodeTime = 360;						// 验证码有效时间
	public final static String ResetValidCodeName = "ResetValidCode_";		// 校验后的验证码名称
	public final static Integer ResetValidCodeTime = 600;					// 校验后的验证码有效时间
	public final static String ResetCodeSendTime = "ResetCodeSendTime_";	// 记录第一次发送验证码时间
	public final static String ResetCodeSendCount = "ResetCodeSendCount_";	// 记录发送验证码次数
	public final static String ResetCodeFrozenName = "ResetCodeFrozen_";	// 记录冻结用户
	public final static Integer ResetCodeFrozenTime = 86400;				// 冻结时间
	public final static Integer ResetCodeFrozenCount = 5;					// 发送最大次数
	
	/**
	 * 修改原手机验证码相关
	 */
	public final static String OldMCodeName = "OldMCode_";					// 验证码名称
	public final static Integer OldMCodeTime = 360;							// 验证码有效时间
	public final static String OldMValidCodeName = "OldMValidCode_";		// 校验后的验证码名称
	public final static Integer OldMValidCodeTime = 600;					// 校验后的验证码有效时间
	public final static String OldMCodeSendTime = "OldMCodeSendTime_";		// 记录第一次发送验证码时间
	public final static String OldMCodeSendCount = "OldMCodeSendCount_";	// 记录发送验证码次数
	public final static String OldMCodeFrozenName = "OldMCodeFrozen_";		// 记录冻结用户
	public final static Integer OldMCodeFrozenTime = 86400;					// 冻结时间
	public final static Integer OldMCodeFrozenCount = 5;					// 发送最大次数
	
	/**
	 * 修改新手机验证码相关
	 */
	public final static String NewMCodeName = "NewMCode_";					// 验证码名称
	public final static Integer NewMCodeTime = 360;							// 验证码有效时间
	public final static String NewMCodeSendTime = "NewMCodeSendTime_";		// 记录第一次发送验证码时间
	public final static String NewMCodeSendCount = "NewMCodeSendCount_";	// 记录发送验证码次数
	public final static String NewMCodeFrozenName = "NewMCodeFrozen_";		// 记录冻结用户
	public final static Integer NewMCodeFrozenTime = 86400;					// 冻结时间
	public final static Integer NewMCodeFrozenCount = 5;					// 发送最大次数
	
	public final static String  CategoryName = "CategoryName_";			// 记录分类名称
	public final static Integer CategoryNameTime = 60*60*24*5;				// 缓存存时间  5天
	
	public final static String SystemStatusName = "SystemStatusName_";			// 系统状态名称
	public final static Integer SystemStatusNameTime = 60*60*24*5;			// 缓存存时间  5天
	
	public final static String  jsapi_ticket = "jsapi_ticket";			// 记录微信支付的时间
	public final static Integer jsapi_ticket_time = 60*50*2;			// 缓存存时间小于2小时
	
	public final static String UserName = "UserName_";			// 系统状态名称
	public final static Integer UserNameTime = 60*60*24*5;			// 缓存存时间  5天
	
	
	/**
	 * 发送验证码时间限制
	 */
	public final static String CodeIntervalName = "CodeInterval_";			// 验证码限制时间名称
	public final static Integer CodeIntervalTime = 120;    //发送验证码间隔时间	
	
	/**
	 * 动态密码相关
	 */
	public final static String DynamicCodeName = "DynamicCode_";					// 动态密码名称
	public final static Integer DynamicCodeTime = 120;							// 动态密码有效时间
	public final static String DynamicCodeSendTime = "DynamicCodeSendTime_";		// 记录第一次发送动态密码时间
	public final static String DynamicCodeSendCount = "DynamicCodeSendCount_";		// 记录发送动态密码次数
	public final static String DynamicCodeFrozenName = "DynamicCodeFrozen_";		// 记录冻结用户
	public final static Integer DynamicCodeFrozenTime = 86400;					// 冻结时间
	public final static Integer DynamicCodeFrozenCount = 10;					// 发送最大次数
}
