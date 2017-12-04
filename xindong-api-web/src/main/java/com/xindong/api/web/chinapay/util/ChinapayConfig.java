package com.xindong.api.web.chinapay.util;

/* *
 *功能：基础配置类
 */

public class ChinapayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，
//	public static String merId = "000001602235003";//测试
	public static String merId = "703211604200001";
	/**
	 * 银联支付状态0000为支付成功状态，0001为未支付  其它为失败状态
	 */
	public static class UnionPayStatus{
		public static final String SUCCESS ="0000";
		public static final String NOPAY ="0001";
		public static final String FAILE ="2";
	}
}
