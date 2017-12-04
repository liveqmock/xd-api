package com.xindong.api.service.utils;
import java.util.HashMap;
import java.util.Map;

public class BankUtils {
	/**
	　* 通过银行码获取银行银行名称
　　  	 */
	public static String getBankName(String bank){
		Map<String, String> map = new HashMap<String, String>();
		map.put("BOC", "中国银行");
		map.put("CCB", "建设银行");
		map.put("ABC", "农业银行");
		map.put("ICBC", "工商银行");
		map.put("CMSB", "民生银行");
		map.put("CMBC", "招商银行");
		map.put("CTB", "兴业银行");
		map.put("BCM", "交通银行");
		map.put("CEB", "光大银行");
		map.put("CCB", "中信银行");
		map.put("BCCB", "北京商业银行");
		map.put("HSBC", "汇丰银行");
		map.put("HXB", "华夏银行");
		map.put("SPDB", "浦发银行");
		map.put("CGD", "广发银行");
		
		return map.get(bank);
    }
	
	public static void main(String[] args) {
		System.out.println(BankUtils.getBankName("BCCB"));
	}
}
