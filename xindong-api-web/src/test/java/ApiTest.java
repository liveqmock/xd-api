

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindong.api.common.utils.HttpUtils;
import com.xindong.api.common.utils.MD5Util;

public class ApiTest {
	private static final Logger log = LoggerFactory.getLogger(ApiTest.class);
	
	private static String domain = "http://localhost:80";
//	private static String domain = "http://t.api.xingdong8.com/";
	private static String secret = "6EBD866547D8065DBE16AEC925F73881";
	private static String token = "5a91e8aa29f9b35da22c42f6679a690c";//132-18511802117-3

	//
	
	public static void main(String[] args) throws Exception {
		String url = null;
		//获得商品详情
//		url = "/orderInfo/submitSpecialOrder";
	//	url = "/orderInfo/submitOrder";
	//	url = "/orderInfo/getOrderInfosByPage";
	//	url = "/orderInfo/getOrderInfoByOrderIdAndUserId";
	//	url = "/orderPay/getUnionPay";
		String dUrl = domain+url;
		String baseData = "v=1.0&sign="+secret+"&timestamp=2015-12-24 17:34:48&token="+token;
		//System.out.println(domain + url+"&" +getSign() + "&token="+token);
		String data ="&startDate=2015-12-24 17:34:48&returnDate=2015-12-24 17:34:48&itemId=1&originName=呗就&fromWhere=" +
				"&adultNum=2&childrenNum=1&contactName=大&contactMobile=123&balanceId=1&personalizationRemark=123sd";
			String dataO ="&skuId=2&passenges=[{\"passengerIdentityType\":1,\"passengerIdentityNumber\":430621,\"passengerMobile\":18601280163,\"passengerName\":\"利差\"}" +
					",{\"passengerIdentityType\":2,\"passengerIdentityNumber\":430621312,\"passengerMobile\":18601280131,\"passengerName\":\"sad\"}]" +
					"&invoiceStatus=1&invoiceTitle=123&invoiceContent=123123s大声道";
//		data =baseData+data+dataO+"&orderType=2";
		data =baseData+"&orderId=100002";
		System.out.println(HttpUtils.httpPostData(dUrl, data,"UTF-8"));
		log.error("OK");                        
		
	}
	
	private static String getSign() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		String v = "1.0";
		String sign = MD5Util.md5Hex(secret + timestamp + v + secret).toUpperCase();
		
		StringBuilder sb = new StringBuilder();
		sb.append("timestamp=").append(URLEncoder.encode(timestamp, "utf-8"));
		sb.append("&").append("v=").append(v);
		sb.append("&").append("sign=").append(sign);
		
		return sb.toString();
	}
	
	
}
