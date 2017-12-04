package demo;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindong.api.common.utils.MD5Util;



public class HttpTest {
private static final Logger log = LoggerFactory.getLogger(HttpTest.class);
	
//	private static String domain = "www.tbny.net";
	private static String domain = "http://localhost:80";
//	private static String domain = "http://t.api.xingdong8.com";
	private static String secret = "d18ed7feb9db4621b95da86943f7717f";
	private static String token = "1ebbccb0ca0341025733edcc770db37c533aaf870e68be34"; //10000092-xindong-2
												   
	
	public static void main(String[] args) throws Exception {
		
		// 筛选列表页面
		//String url = "/search/getSearchItems?n=四川";
		//筛选列表页面-查看更多
		//String url = "/search/getSearchItemsByPage?n=xx";
		
		//搜索关键词
		//String url ="/search/getKeyword";
		//String url = "/search/autoComplete?keyword=北";
		
		//首页轮播广告图 
		//String url = "/index/getIndexImages?type=3";
		//PC搜索推荐商品
		//String url  ="/index/getIndexRecommendPcItems";
		
		//视频专区
		//String url  = "/video/videoList?pageNo=1&pageSize=9";
		
		//PC首页
		//String url = "/index/getIndex";
		//首页轮播图
		//String url = "/index/getIndexImages?type=1";
		//PC首页广告接口
		//String url = "/index/getIndexAds";
		//PC首页系列（1、系列 2、发现）
		//String url = "/index/getPcIndexSeries?type=1";
		//PC 首页系列详情信息
		//String url = "/index/getPcIndexSeriesDetail?seriesId=1013";
		//H5首页发现
		//String url = "/index/getH5IndexFind";
		//H5首页信息
		//String url = "/index/getH5Index";
		//H5详情
		//String url = "/index/getIndexSeriesDetail?seriesId=1013";
		//心动特选
		//String url = "/index/getIndexSelected";
		
		//商品详情
		//String url ="/item/getItemByItemId?itemId=10175&type=0";
		//商品热搜
		//String url = "/item/itemHeat";
		
		//用户登录
		//String url = "/user/buy/login/?mobile=13488710918&loginType=2&code=386526";
		//用户注册
		//String url = "/user/buy/reg/?name=袁龙&mobile=15801245654&password=e10adc3949ba59abbe56e057f20f883e&userFlag=&code&openIdregisterInvitationCode";
		//第三方登录 用户绑定 手机号
		//String url = "/userInfo/setPhone?mobile=13488710916&password=e10adc3949ba59abbe56e057f20f883e&registerInvitationCode=136744&userFlag=#1#-#2#-#3#";
		//获取用户信息
		//String url = "/userInfo/getUserInfo";
		//获取用户银行卡信息
		//String url = "/userInfo/getPaymentBindbankcard?userId=10000011";
		//发送验证码
		//String url = "/user/sendCode?mobile=13488710918&sendType=6";
		
		
		//提交订单
		//String url = "/orderInfo/submitOrder?adultNum=1&contactName=单良&contactMobile=13488710918&contactEmail=312@qq.com";
		//提交个性订单
		String url = "/custom/submitCustomOrder?originName=北京&contactName=单良&contactMobile=13488710918";
		//我的订单
		//String url = "/orderInfo/getOrderInfoByOrderIdAndUserId?orderId=100514";
		//取消订单
		//String url = "/orderInfo/cancelOrder?orderId=100432&10000032";
		//我的
		//String url = "/orderInfo/getOrderInfosByPage?orderType=1";
		//计算订单金额
		//String url = "/orderInfo/calculateOrderMoney?skuId=1380&itemId=10176&orderType=1&adultNum=3&childrenNum=2&skuRoomsNum=0&useBalance=0&coupons=";
		
		//领取优惠券
		//String url = "/coupon/receiveCoupon?couponPromoId=1033";
		//查询我的优惠券
		//String url ="/coupon/getMyCoupon"; 
		
		
		//发送验证码
		//String url = "/user/sendCode?mobile=13488712113&sendType=2";
		
		//新品看板
		//String url = "/activity/getActivityByPage?activityId=10000&type=1";
		//查询活动专区
		//String url = "/activity/activityList";
		//活动详情页
		//String url = "/activity/getActivityDetail?activityId=10000&type=1";
		//看板详情
		//String url = "/activity/getKanBanDetail?activityId=10000&type=1";
		
		//点赞
		//String url = "/activity/addActivityCount?activityId=10004&type=2";
		
		//支付
		//String url = "/orderPay/getUnionPay?orderId=101010";
		
		//目的地
		//String url = "/index/getDestinations";
		
		//评论
		//String url = "/comment/addComment?itemId=10190&content=非常好&businessId=100514&imgOne=http://i.xindong8.com/img/2016/6/17/pb518357.jpg&imgTwo=http://i.xindong8.com/img/2016/6/17/pb518357.jpg&imgThree=http://i.xindong8.com/img/2016/6/17/pb518357.jpg";
		//评论规则
		//String url = "/comment/index";
		
		
		String dUrl = "";
		String data = "";
		//System.out.println(domain + url+"&" +getSign() + "&token="+token);
		if(url.indexOf("?") <= 0){
			System.out.println(domain + url+"?" +getSign() + "&token="+token);
			dUrl = domain + url;
			data = getSign() + "&token="+token;
		}else{
			System.out.println(domain + url+"&" +getSign() + "&token="+token);
			dUrl = domain + url.substring(0, url.indexOf("?"));
			data = url.substring(url.indexOf("?")+1, url.length()) + "&" + getSign() + "&token="+token;
		}
		System.out.println(HttpUtils.httpPostData(dUrl, data, null));
		
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
