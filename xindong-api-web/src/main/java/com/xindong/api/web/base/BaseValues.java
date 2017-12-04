package com.xindong.api.web.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xindong.api.common.utils.HttpUtils;
import com.xindong.api.common.utils.MD5Util;

public class BaseValues {
//	private static String domain = "http://localhost:80/ec-api-web";
	private static String domain = "http://api.51wot.com";
	private static String secret = "d18ed7feb9db4621b95da86943f7717f";
	private static String tokens = "91d422d97baff27cca78876d065199ff";
	private static String v = "1.0";
	private static String charSet = "utf-8";
	
	public static String httpPostData(String url, String data){
		return HttpUtils.httpPostData(url, data, charSet);
	}
	
	public static String httpGetData(String path, String data){
		return HttpUtils.httpPostData(domain+path, getSign(data, null), charSet);
	}
	
	public static String httpGetData(String path, String data, String token){
		return HttpUtils.httpPostData(domain+path, getSign(data, token), charSet);
	}
	
	private static String getSign(String data, String token){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		String sign = MD5Util.md5Hex(secret + timestamp + v + secret).toUpperCase();
		
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("timestamp=").append(URLEncoder.encode(timestamp, "utf-8"));
			sb.append("&").append("v=").append(v);
			sb.append("&").append("sign=").append(sign);
			if(token != null){
				sb.append("&").append("token=").append(token);
			}
			if(data != "" && data != null){
				sb.append("&").append(data);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
