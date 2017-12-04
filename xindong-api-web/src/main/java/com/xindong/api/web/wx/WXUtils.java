package com.xindong.api.web.wx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.HttpUtils;
import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.domain.wx.WXTicketInfo;
import com.xindong.api.domain.wx.WXUserInfo;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.RedisUtils;

public class WXUtils {
	private static final Logger log = LoggerFactory.getLogger(WXUtils.class);
	// 获取服务号access_token
	public static String getToken(){
		String access_token = RedisUtils.get(WXValues.AccessTokenStr);
		if(StringUtils.isBlank(access_token)){
			// 获取token
			String param = "grant_type=client_credential&appid="+WXValues.AppID+"&secret="+WXValues.AppSecret;
			String date = HttpUtils.httpGetData("https://api.weixin.qq.com/cgi-bin/token", param, "UTF-8");
			access_token = (String) JsonUtils.readValue(date).get("access_token");
			RedisUtils.set(WXValues.AccessTokenStr, WXValues.WxTime, access_token);
			System.out.println("access_token==="+access_token);
		}
		return access_token;
	}
	
	// 获取服务号jsapi_ticket
	public static String getTicket(String access_token){
		String jsapi_ticket = RedisUtils.get(WXValues.JsapiTicketStr);
		if(StringUtils.isBlank(jsapi_ticket)){
			// 获取jsticket
			String param = "access_token="+access_token+"&type=jsapi";
			String date = HttpUtils.httpGetData("https://api.weixin.qq.com/cgi-bin/ticket/getticket", param, null);
			jsapi_ticket = (String) JsonUtils.readValue(date).get("ticket");
			RedisUtils.set(WXValues.JsapiTicketStr, WXValues.WxTime, jsapi_ticket);
			System.out.println("jsapi_ticket==="+jsapi_ticket);
		}
		return jsapi_ticket;
	}
	
	// 加密jsapi_ticket
	public static WXTicketInfo shaTicket(String jsapi_ticket, String url){
		WXTicketInfo wxTicketInfo = new WXTicketInfo();
		
		String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            System.out.println("signature==="+signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        wxTicketInfo.setAppId(WXValues.AppID);
        wxTicketInfo.setTimestamp(timestamp);
        wxTicketInfo.setNonceStr(nonce_str);
        wxTicketInfo.setSignature(signature);
		
        return wxTicketInfo;
	}
	
	// 获取用户授权code
	public static void getCode(String action, HttpServletResponse response) throws Exception{
		/*String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
		String param = "appid="+WXValues.AppID+"&redirect_uri="+
				ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.wx_notify_url) +"&response_type=code&scope=snsapi_base&state="+action+"#wechat_redirect";
		response.sendRedirect(url+"?"+param);*/
	}
		
	// 获取用户openId
	public static String getOpenId(String code){
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String param = "appid="+WXValues.AppID+"&secret="+WXValues.AppSecret+"&code="+code+"&grant_type=authorization_code";
		String date = HttpUtils.httpGetData(url, param, null);
		String openId = (String) JsonUtils.readValue(date).get("openid");
		return openId;
	}
	
	// 获取用户信息
	public static WXUserInfo getWXUser(String token, String openId){
		System.out.println("获取用户信息！");
		String url = "https://api.weixin.qq.com/cgi-bin/user/info";
		String param = "access_token="+token+"&openid="+openId;
		String data = HttpUtils.httpGetData(url, param, null);
		return JsonUtils.readValue(data, WXUserInfo.class);
	}
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    /**
     * 静默授权 获取openid
     * @return
     * @throws IOException 
     */
	@SuppressWarnings("unchecked")
	public static String getOpenid(Integer orderId,HttpServletRequest request, HttpServletResponse response) throws IOException{
		String openid ="";
		 String redirect_uri =request.getRequestURL().toString();
		 String code  = request.getParameter("code");
		 log.debug("==code====="+code);
		 if(StringUtils.isNotBlank(code)){
			 log.debug("==code="+code);
			 //2 获取code后，请求以下链接获取access_token
			 String xmlResult = HttpUtils.httpGetData("https://api.weixin.qq.com/sns/oauth2/access_token"
					 , "appid="+WXValues.AppID+"&secret="+WXValues.AppSecret+"&code="+code+"&grant_type=authorization_code","UTF-8");
			 log.debug("==xmlResult====="+xmlResult);
			Map<String, String> map =JsonUtils.readValue(xmlResult, Map.class);
			  openid =map.get("openid");
			  log.debug("==openid====="+openid);
		 }else{
			 //1,获取code 需要在 微信公众账号 -- 网页账号	网页授权获取用户基本信息 配置回掉地址
			    String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
				String param = "appid="+WXValues.AppID+"&redirect_uri="+URLEncoder.encode(redirect_uri,"UTF-8")+"&response_type=code&scope=snsapi_base&state="+orderId+"#wechat_redirect";
				log.debug("==url="+url+"?"+param);
				response.sendRedirect(url+"?"+param);
				return null;
		 }
		return openid;
	}
	
	
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
