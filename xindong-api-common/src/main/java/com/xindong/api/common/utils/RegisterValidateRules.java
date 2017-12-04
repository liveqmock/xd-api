package com.xindong.api.common.utils;

import org.apache.commons.lang.StringUtils;


/**
 * 正则表达式
 */
public class RegisterValidateRules {

    //用户名正则
    public static final String PATTERN_PIN = "^[a-zA-Z0-9_\\-\\u4e00-\\u9fa5]*";
    //邮箱正则
    public static final String PATTERN_EMAIL = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    //密码正则
    public static final String PATTERN_PASSWORD = "^[A-Za-z0-9_\\-\\W]+$";
    //手机格式
    public static final String PATTERN_MOBILE = "1[34578]{1}[0-9]{9}";
    //用户名长度
    public static final int MIN_LENGTH_PIN = 4;
    public static final int MAX_LENGTH_PIN = 20;
    //密码长度
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MAX_LENGTH_PASSWORD = 20;
    //email最大长度
    public static final int MAX_LENGTH_EMAIL = 50;
    //推荐人最大长度
    public static final int MAX_LENGTH_COMMENDER = 20;
    //10分钟内最大注册用户数--基于ip
    public static final int MAX_REG_NUM_IN_10_MINUTE = 2;
    //一天内最大注册用户数--基于cookie
    public static final int MAX_REG_NUM_IN_24_HOUR = 5;
    /**
     * 企业联系人姓名
     */
    public static final int MIN_LENGTH_REAL_NAME = 2;
    public static final int MAX_LENGTH_REAL_NAME = 20;
    public static final String PATTERN_REAL_NAME = "^[a-zA-Z0-9\\u4e00-\\u9fa5]*";
    /**
     * 固定电话
     */
    public static final String PATTERN_TEL = "^[0-9\\-()（）]{7,18}$";
    /**
     * 企业名字
     */
    public static final int MAX_LENGTH_COMPANY_NAME = 40;

    public static final String PATTERN_COMPANY_NAME = "^[A-Za-z0-9()（）_\\\\-\\u4e00-\\u9fa5]*";
    /**
     * 地址
     */
    public static final int MAX_LENGTH_COMPANY_ADDR = 50;
    public static final String PATTERN_COMPANY_ADDR="^[A-Za-z0-9_()（）#\\\\-\\u4e00-\\u9fa5]*" ;
    /**
     * 网址
     */
    public static final int MAX_LENGTH_COMPANY_SITE = 50;
    public static final String PATTERN_COMPANY_SITE ="^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\s*)?$";

    public static boolean isMobile(String mobile){
    	return mobile.matches(PATTERN_MOBILE);
    }
    public static boolean isInvalidPassword(String pwd) {
		return StringUtils.isEmpty(pwd)
				|| pwd.length() < RegisterValidateRules.MIN_LENGTH_PASSWORD
				|| pwd.length() > RegisterValidateRules.MAX_LENGTH_PASSWORD
				|| (!pwd.matches(RegisterValidateRules.PATTERN_PASSWORD));

	}
}
