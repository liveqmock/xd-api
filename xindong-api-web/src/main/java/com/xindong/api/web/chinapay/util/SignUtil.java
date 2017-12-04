package com.xindong.api.web.chinapay.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.chinapay.secss.SecssUtil;

public class SignUtil {
	
//	private static String verifyFilePathKey = "verify.file";
//	private static String certFilePath = "sign.filePath";
//	private static String signFile = "sign.file";
//	private static String signPwd = "sign.file.password";
//	private static String signCertType ="sign.cert.type";
//	private static String signInvalidFields="sign.invalid.fields";
//	private static String signatureField = "signature.field";
	
	private static SecssUtil secssUtil = null;
	static{
		/*
		 * 初始化security.properties属性文件
		 * 
		 */
		secssUtil = new SecssUtil();
		InputStream is = PathUtil.class.getResourceAsStream("/security.properties");
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.setProperty("", "");
		
		
		secssUtil.init(p);
	}
	
	public static String sign(Map signMap){
		secssUtil = new SecssUtil();
		secssUtil.init();
		secssUtil.sign(signMap);
		
		System.out.println(secssUtil.getErrCode());
		System.out.println(secssUtil.getErrMsg());
		
		return secssUtil.getSign();
	}
	
	public static String sign(String merId,String signData){
		return null;
	}
	
	public static boolean verify(Map map){
		secssUtil.verify(map);
		if("00".equals(secssUtil.getErrCode()))
			return true;
		return false;
	}
	
	public static String decode(String merId,String decData){
		
		return null;
	}
	
	public static String decryptData(String encData){
		secssUtil.decryptData(encData);
		return secssUtil.getSign();
	}
	private static final String transResvered = "trans_";
	private static final String cardResvered = "card_"; 
	private static final String transResveredKey = "TranReserved";
	private static final String cardResveredKey = "CardTranData"; 
	private static final String signatureField = "Signature";
	//签名 
	public static Map<String, Object> signParam(Map<String, Object> map){
		//交易扩展域信息
		JSONObject transResvedJson = new JSONObject();
		//有卡信息域信息-需要签名加密
		JSONObject cardInfoJson = new JSONObject();
		Map<String, Object> sendMap = new HashMap<String,Object>();
		
		for(Map.Entry<String, Object> entry:map.entrySet()){
			String key = entry.getKey();
			String value = (String) entry.getValue();
			//过滤空值
			if(StringUtil.isEmpty(value)){
				continue;
			}
			try {
				if(key.startsWith(transResvered)){
					//组装交易扩展域
					key = key.substring(transResvered.length());
					
						transResvedJson.put(key, value);
					
				}else if(key.startsWith(cardResvered)){
					//组装有卡交易信息域
					key = key.substring(cardResvered.length());
					cardInfoJson.put(key, value);
				}else{
					sendMap.put(key, value);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String transResvedStr = null;
			String cardResvedStr = null;
			if(cardInfoJson.length()!=0)
				cardResvedStr=cardInfoJson.toString();
			if(transResvedJson.length()!=0)
				transResvedStr = transResvedJson.toString();
			//敏感信息加密处理
			if(StringUtil.isNotEmpty(cardResvedStr)){
				cardResvedStr = SignUtil.decryptData(cardResvedStr);
				sendMap.put(cardResveredKey, cardResvedStr);
			}
			if(StringUtil.isNotEmpty(transResvedStr)){
				sendMap.put(transResveredKey, transResvedStr);
			}
			//商户签名
			String signature = SignUtil.sign(sendMap);
			sendMap.put(signatureField, signature);
		}
		return sendMap;
	}
	
	
}
