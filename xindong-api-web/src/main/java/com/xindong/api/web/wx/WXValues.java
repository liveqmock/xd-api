package com.xindong.api.web.wx;

public class WXValues {
	public static String url = "http://www.xindong8.com";
//	public static String redirectUrl = "http://www.xindong8.com/wx/init";
	
	//微信分配的公众账号ID
	public static final String AppID = "wxdefc0a3f6b14116a";
	//key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	public static final String AppSecret = "35d31c0697d0fe879c78a7778db571d0";
	public static final String AppPaySecret = "13554683021355468302201500201500";
	//微信支付分配的商户号
	public static final String AppMchId = "1355468302";
	
	
	public static final String AccessTokenStr = "access_token_qx";
	public static final String JsapiTicketStr = "jsapi_ticket_qx";
	
	public static final Integer WxTime = 7100;
	//微信支付地址
	public static final String WX_PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//微信支付成功回调地址
//	public static final String WX_NOTIFY_URL = "http://www.xindong8.com/payOrder/payWX";
	//获取支付access_token
	public static final String WX_PAY_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	//获取支付jsapi_ticket
	public static final String WX_PAY_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	//用户获取access_token 地址
	public static final String H5_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	//用户信息
	public static final String H5_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	//用户授权
	public static final String H5_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
}
