package com.xindong.api.web.controller;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.common.utils.Sha1Util;
import com.xindong.api.common.utils.TenpayUtil;
import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.OrderPayment;
import com.xindong.api.domain.Sku;
import com.xindong.api.domain.query.OrderPaymentQuery;
import com.xindong.api.service.OrderInfoService;
import com.xindong.api.service.OrderPaymentService;
import com.xindong.api.service.SkuService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;
import com.xindong.api.service.utils.HttpUtils;
import com.xindong.api.service.utils.RedisUtils;
import com.xindong.api.service.utils.RedisValue;
import com.xindong.api.web.alipay.config.AlipayConfig;
import com.xindong.api.web.alipay.config.AlipayWapConfig;
import com.xindong.api.web.alipay.util.AlipayNotify;
import com.xindong.api.web.alipay.util.AlipaySubmit;
import com.xindong.api.web.base.BaseController;
import com.xindong.api.web.chinapay.util.ChinapayConfig;
import com.xindong.api.web.chinapay.util.SignUtil;
import com.xindong.api.web.chinapay.util.StringUtil;
import com.xindong.api.web.wx.H5;
import com.xindong.api.web.wx.H5ScencInfo;
import com.xindong.api.web.wx.RequestHandler;
import com.xindong.api.web.wx.SignH5;
import com.xindong.api.web.wx.WXPayUtils;
import com.xindong.api.web.wx.WXUtils;
import com.xindong.api.web.wx.WXValues;

/** 订单支付 */

@Controller
@RequestMapping("/orderPay")
@SuppressWarnings("unchecked")
public class OrderPayController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(OrderPayController.class);
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private OrderPaymentService orderPaymentService;
	@Autowired
	private SkuService skuService;
	/**
	 * 银联支付
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getUnionPay", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getUnionPay(Integer orderId,String token,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("操作订单号："+orderId+" 进行getUnionPay支付");
		Result result = new Result();
		if(orderId == null||StringUtils.isEmpty(token)){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("请求数据错误！");
			return result;
		}
		result = this.jgWxPay(orderId, token);
		if(!result.getSuccess()){
			return result;
		}	
		BigDecimal oughtPayMoney=(BigDecimal) result.getResult() ;
		/**
		 * 获取订单支付编号
		 * 订单号-支付表主键
		 */
		OrderPayment orderPayment =new OrderPayment();
		orderPayment.setOrderId(orderId);
		//防止产生多少数据
		orderPayment = orderPaymentService.selectOrderPamentByOne(orderPayment);
		Integer paymentId =null;
		if(orderPayment!=null && orderPayment.getPaymentId()!=null){
			paymentId =orderPayment.getPaymentId();
		}else{
			 orderPayment =new OrderPayment();
			 orderPayment.setOrderId(orderId);
			 paymentId = orderPaymentService.insert(orderPayment);
		}
		if(paymentId ==null){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("系统异常，请稍后重试！");
			return result;
		}
		Map<String, Object> map =new HashMap<String, Object>();
		String orderNo =String.valueOf(orderId); //+Constants.ORDER_PAYMENT_SPIT+ paymentId;//交易号
		
		//提供默认值
		String merId = ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.chinay_merId) ;
		String orderAmt = oughtPayMoney.multiply(new BigDecimal(100)).setScale(0).toString();
		//日期时间
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String tranDate = StringUtil.getRelevantDate(date);
		String tranTime = StringUtil.getRelevantTime(date);
		//支付版本号
		String version ="20140728";
		//前台接收地址
		String pageReturnUrl =ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.h5_pay_success)
		       +"?search="+URLEncoder.encode("type=suc&orderId="+orderId,"UTF-8");
		//后台接收地址
		String bgReturnUrl = ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.union_notify_url) ;
		map.put("MerId", merId);
		map.put("MerOrderNo", orderNo);
		map.put("TranDate", tranDate);
		map.put("TranTime", tranTime);
		map.put("Version", version);
		map.put("OrderAmt", orderAmt);
		map.put("MerPageUrl", pageReturnUrl);
		map.put("MerBgUrl", bgReturnUrl);
		map.put("BusiType", "0001");
		map.put("CommodityMsg", "心动工场产品");
		Map<String, Object> param=SignUtil.signParam(map);
		
		log.debug("==alipay11=params=="+param);
		result.setSuccess(true);
		result.setResult(param);
		EcUtils.setSuccessResult(result);
		return result;
	}
	/**
	 * 银联支付 回调通知
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="unionPayResult", method={RequestMethod.GET, RequestMethod.POST})
	public void unionPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		String result="";
		//解析 返回报文
		Enumeration<String> requestNames = request.getParameterNames();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		while(requestNames.hasMoreElements()){
			String name = requestNames.nextElement();
			String value = request.getParameter(name);
			value = URLDecoder.decode(value, "UTF-8");
			resultMap.put(name, value);
			log.error("=resultMap==name="+name+";value="+value);
			
		}
		String OrderStatus = (String) resultMap.get("OrderStatus");
		if(!ChinapayConfig.UnionPayStatus.SUCCESS.equals(OrderStatus)){
			log.error("====支付失败=="+resultMap.get("MerId")+"===orderId="+resultMap.get("MerOrderNo")
					+"===OrderStatus=="+OrderStatus);
			result ="支付失败";
		}else{
			//验证签名
			if(!SignUtil.verify(resultMap)){
				log.error("======签名验证失败"+resultMap.get("MerId")+"===orderId="+resultMap.get("MerOrderNo"));
				result ="签名验证失败";
			}else{
				resultMap.put("orderId", resultMap.get("MerOrderNo"));
				orderInfoService.unionPay(resultMap);
				result ="操作成功";
			}
		}
//		System.out.println("=====result===="+result);
		out.write(result.getBytes()); 
		out.flush();
		out.close();
	
	}
	
	/**
	 *线下转账信息
	 * @return
	 * 订单类型（1旅行订单;2咨询订单  ）
	 */
	@RequestMapping(value="getOffLineData", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getOffLineData(String token,Integer type,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId =ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(type ==null){
			type=1;
		}
		return orderInfoService.getOffLineData(type);
	}
	
	/**
	 * 微信支付初始化
	 * @param request
	 * @param url 请求的url
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getWxConfig", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getWxConfig(String url,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("==========进行Wx初始化===============");
		log.info("==========url:" + url +"===============");
		Result result = new Result();
		if(StringUtils.isBlank(url)){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("请求参数错误！");
			return result;
		}
		Map<String, String> map = SignH5.sign(getJsapiTicket(), URLDecoder.decode(url,"UTF-8"));
		result.setSuccess(true);
		result.setResult(map);
		log.info("==========map:" + map +"===============");
		EcUtils.setSuccessResult(result);
		return result;
	}
	/**
	 * 获取微信h5支付的参数
	 * @return
	 */
	private String getJsapiTicket(){
		String jsapi_ticket =	RedisUtils.get(RedisValue.jsapi_ticket);
		if(StringUtils.isBlank(jsapi_ticket)){
			//1获取access_token
			String xmlResult = HttpUtils.httpGetData(WXValues.WX_PAY_TOKEN_URL
		       , "grant_type=client_credential&appid="+WXValues.AppID+"&secret="+WXValues.AppSecret,"UTF-8");
			Map<String, String> map =JsonUtils.readValue(xmlResult, Map.class);
			log.info("获取支付编号access_token=="+map.get("access_token"));
			String access_token = map.get("access_token"); 
			//2 获取jsapi_ticket 有效期7200秒
//				https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
			String xmlResultTicket = httpPostData(WXValues.WX_PAY_TICKET_URL
				       , "type=jsapi&access_token="+access_token);
			Map<String, String> mapTick = JsonUtils.readValue(xmlResultTicket, Map.class);
			log.info("获取支付编号jsapi_ticket=="+mapTick.get("ticket"));
			jsapi_ticket = mapTick.get("ticket");
			
			RedisUtils.set(RedisValue.jsapi_ticket,RedisValue.jsapi_ticket_time,jsapi_ticket);
		}
		return 	jsapi_ticket;
	}
	
	/**
	 * 获取微信h5支付的参数
	 * isBase 是否静默授权 默认是
	 * url 跳转地址
	 * @return
	 */
	@RequestMapping(value="getAuthorizeCode", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result  getAuthorizeCode( String scope,String url ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.debug("==进入用户 ==getAuthorizeCode=="+url);
		Result result =new Result();
		try{
		//如果是支付则是静默授权
		 if(StringUtils.isBlank(scope)){
			 scope ="snsapi_base";
		 }
		 String openid ="";
		 String redirect_uri =request.getRequestURL().toString();
		 String code  = request.getParameter("code");
		 if(StringUtils.isBlank(url)){
			 url  = request.getParameter("state");//获取code回掉 设置的值
			 log.debug("==url=="+url);
			 if(StringUtils.isBlank(url)){
			    result.setSuccess(false);
				EcUtils.setExceptionResult(result);
				result.setResultMessage("请求参数错误！");
				return result;
			 }
		 }
		 log.debug("==code====="+code+"==url=="+url);
		 if(StringUtils.isNotBlank(code)){
			 log.debug("==code="+code);
			 //2 获取code后，请求以下链接获取access_token
			 String xmlResult = HttpUtils.httpGetData(WXValues.H5_ACCESS_TOKEN_URL
					 , "appid="+WXValues.AppID+"&secret="+WXValues.AppSecret+"&code="+code+"&grant_type=authorization_code","UTF-8");
			 log.debug("==xmlResult====="+xmlResult);
			 Map<String, String> map =JsonUtils.readValue(xmlResult, Map.class);
			  openid =map.get("openid");
			  log.debug("==openid====="+openid);
			  //还可以获取用户其它信息 
				  String user = HttpUtils.httpGetData(WXValues.H5_USERINFO_URL
							 , "access_token="+map.get("access_token")+"&openid="+openid+"&lang=zh_CN","UTF-8");
				  log.debug("==user====="+user);
				 Map<String, Object> userMap =JsonUtils.readValue(user, Map.class);
				    int sex =  (Integer) (userMap.get("sex")==null ?"0":userMap.get("sex"));
					result.setSuccess(true);
					result.setResult(userMap);
					EcUtils.setSuccessResult(result);
					String name =(String) userMap.get("nickname");
					response.sendRedirect(url+"?"+"openid="+openid+"&name="+URLEncoder.encode(name,"UTF-8")+"&type=1&sex="+
							+sex+"&headimgurl="+userMap.get("headimgurl"));
					return result;
		 }else{
			 //1,获取code 需要在 微信公众账号 -- 网页账号	网页授权获取用户基本信息 配置回掉地址
			    String urlP = WXValues.H5_AUTHORIZE_URL;
				String param = "appid="+WXValues.AppID+"&redirect_uri="+URLEncoder.encode(redirect_uri,"UTF-8")+"&response_type=code&scope="+scope+
						"&state="+url+"#wechat_redirect";
				log.debug("==url="+urlP+"?"+param);
				response.sendRedirect(urlP+"?"+param);
				return result; 
		 }
	  }catch(Exception e){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("微信用户授权出现错误！");
			return result;
	  }
	}
	
	/**
	 * code 获取微信 用户openid
	 * noIndex 默认false  不获取其它信息
	 * @return
	 */
	@RequestMapping(value="getUserOpenid", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result  getUserOpenid( String code,boolean noIndex	,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.debug("==进入用户 openid==getUserOpenid==code="+code);
		Result result =new Result();
	   try{
		  if(StringUtils.isBlank(code)){
			    result.setSuccess(false);
				EcUtils.setExceptionResult(result);
				result.setResultMessage("请求参数错误！");
				return result;
		  }
			 log.debug("==code="+code);
			 //2 获取code后，请求以下链接获取access_token
			 String xmlResult = HttpUtils.httpGetData(WXValues.H5_ACCESS_TOKEN_URL
					 , "appid="+WXValues.AppID+"&secret="+WXValues.AppSecret+"&code="+code+"&grant_type=authorization_code","UTF-8");
			 log.debug("==xmlResult====="+xmlResult);
			 Map<String, String> map =JsonUtils.readValue(xmlResult, Map.class);
			 String openid =map.get("openid");
			 log.debug("==openid====="+openid);
			 if(StringUtils.isBlank(openid)){
				    result.setSuccess(false);
					EcUtils.setExceptionResult(result);
					result.setResultMessage("获取微信用户出现错误！");
					return result;
			 }
			 Map<String, Object> returnMap =new HashMap<String, Object>();
			 returnMap.put("openid", openid);
			 if(noIndex){
				//还可以获取用户其它信息 
				  String user = HttpUtils.httpGetData(WXValues.H5_USERINFO_URL
							 , "access_token="+map.get("access_token")+"&openid="+openid+"&lang=zh_CN","UTF-8");
				  log.debug("==user====="+user);
				 Map<String, Object> userMap =JsonUtils.readValue(user, Map.class);
				 returnMap.put("name", userMap.get("nickname"));
				 returnMap.put("headimgurl", userMap.get("headimgurl"));
				 returnMap.put("sex", userMap.get("sex"));
				 returnMap.put("type", 1);
				 returnMap.put("unionid", userMap.get("unionid"));
			 }
			result.setSuccess(true);
			result.setResult(returnMap);
			EcUtils.setSuccessResult(result);
			return result;
	  }catch(Exception e){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("获取微信用户出现错误！");
			return result;
	  }
	}
	
	
	/**
	 * 微信H5支付 JSAPI
	 * @param orderId
	 * @param type 支付来源（1:NATIVE 2:JSAPI 3:APP）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getWxH5Pay", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getWxH5Pay(Integer orderId,String token,String openid,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("操作订单号："+orderId+" 进行WxH5支付");
		Result result = new Result();
		if(orderId == null){
			String  orderIds =request.getParameter("state");
			log.info("操作订单号orderIds："+orderIds+" 进行WxH5支付");
			if(StringUtils.isNotBlank(orderIds)){
				orderId =Integer.valueOf(orderIds);
			}else{
				result.setSuccess(false);
				EcUtils.setExceptionResult(result);
				result.setResultMessage("订单数据错误！");
				return result;
			}
		}
		//获取openid
		if(StringUtils.isBlank(openid)){
						openid =WXUtils.getOpenid(orderId,request, response);
						log.info("操作订单号orderId："+orderId+" 进行WxH5支付;openid="+openid);
						if(StringUtils.isBlank(openid)){
							result.setSuccess(false);
							EcUtils.setExceptionResult(result);
							result.setResultMessage("用户数据错误！");
							return result;
						}
		}
		result = this.jgWxPay(orderId, token);
		if(!result.getSuccess()){
			return result;
		}	
		BigDecimal oughtPayMoney=(BigDecimal) result.getResult() ;
		
		String total_fee = String.valueOf((int)(oughtPayMoney .doubleValue()*100));
		String charSet = TenpayUtil.getCharacterEncoding(request, response);
		String appid = WXValues.AppID;
		String mch_id = WXValues.AppMchId;
		String nonce_str = Sha1Util.getNonceStr();
		String body = "心动工场产品";
		String spbill_create_ip = "127.0.0.1";
		String notify_url = ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_wx_notify_url) ;
		String trade_type = "JSAPI";
		
		RequestHandler reqHandler = new RequestHandler(request, response);
		SortedMap<String, String> signParameters = new TreeMap<String, String>();
		signParameters.put("appid", appid);  						//appid
		signParameters.put("body", body);	 						//商品介绍
		signParameters.put("mch_id", mch_id); 						//商家号 
		signParameters.put("nonce_str", nonce_str); 	  			//随机数
		signParameters.put("notify_url", notify_url); 				//回调地址
		signParameters.put("spbill_create_ip", spbill_create_ip);	//ip地址
		signParameters.put("total_fee", total_fee); 				//总价
		signParameters.put("trade_type", trade_type); 				//支付方式
		signParameters.put("openid", openid); 				//用户标识trade_type=JSAPI，此参数必传
		
		//防止商户订单号重复不同的支付方式生产新的paymentId
		Integer paymentId =null;
		OrderPaymentQuery orderPaymentQuery =new OrderPaymentQuery();
		orderPaymentQuery.setOrderId(orderId);
		orderPaymentQuery.setPayType(2); //0-alipay 1-银联 2-微信公众号 3-先下支付 4-微信wap 5-微信扫码
		List<OrderPayment> orderPayments = orderPaymentService.selectByCondition(orderPaymentQuery);
		if(orderPayments == null || orderPayments.isEmpty()){
			OrderPayment orderPayment =new OrderPayment();
			orderPayment.setOrderId(orderId);
			orderPayment.setPayType(2);
			paymentId = orderPaymentService.insert(orderPayment);
		}else{
			paymentId = orderPayments.get(0).getPaymentId();
		}
		String out_trade_no =orderId +"-"+ paymentId;
		signParameters.put("out_trade_no", out_trade_no);
		log.info("out_trade_no=="+out_trade_no);
		
		String sign = WXPayUtils.createSign(charSet, signParameters,2);
		signParameters.put("sign", sign);
		log.info("sign加密=="+sign);
		log.info("signParameters加密=="+signParameters);
		reqHandler.setParameters(signParameters);
		String xml = reqHandler.parseXML();
		String xmlResult = httpPostData(WXValues.WX_PAY_URL, xml);
		
		Map<String, String> map = WXPayUtils.doXMLParse(xmlResult);
		log.info("获取支付编号prepay_id=="+map.get("prepay_id"));
		String prepay_id = map.get("prepay_id");
		
		if(StringUtils.isNotEmpty(prepay_id)){
			//设置package订单参数
			String noncestr = Sha1Util.getNonceStr();
			String timeStamp = Sha1Util.getTimeStamp();
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appId", appid);//请注意参数大小写问题！！！！被坑
			packageParams.put("nonceStr", noncestr);
			packageParams.put("package", "prepay_id="+prepay_id);
			packageParams.put("signType", "MD5");
			packageParams.put("timeStamp", timeStamp);
			String paySign =WXPayUtils.createSign(charSet, packageParams,Constants.PayFromType.H5);
			log.info("paySign===="+paySign);
			
			packageParams.put("paySign", paySign);
			
			
			packageParams.put("package", prepay_id);//前端使用此
			/*String h5_url =ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.h5_pay_success) ;//异步通知
			response.sendRedirect(h5_url+"?orderId="+orderId+"&openid="+openid+"&noncestr="+noncestr+"&package="+
					prepay_id+"&timestamp="+timeStamp+"&signType=MD5&paySign="+paySign);*/
			result.setSuccess(true);
			result.setResult(packageParams);
			EcUtils.setSuccessResult(result);
		}else{
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			String error =map.get("err_code_des");
			if(StringUtils.isEmpty(error)){
				error="微信支付失败,请稍后再试！";
			}
			result.setResultMessage(error);
			return result;
		}
		return result;
	}
	
	/**
	 * 微信H5支付 MWEB
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="getWxWebPay", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getWxWebPay(Integer orderId, String token, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("操作订单号："+orderId+" 进行微信WAP支付");
		Result result = new Result();
		
		//判断订单商品是否可以支付
		result = jgWxPay(orderId, token);
		if(!result.getSuccess()){
			return result;
		}	
		
		BigDecimal oughtPayMoney=(BigDecimal) result.getResult() ;
		String total_fee = String.valueOf((int)(oughtPayMoney .doubleValue()*100)); //总金额
		String charSet = TenpayUtil.getCharacterEncoding(request, response);
		String appid = WXValues.AppID; //公众账号ID
		String mch_id = WXValues.AppMchId; //商户号
		String nonce_str = Sha1Util.getNonceStr(); //随机字符串
		String body = "心动工场产品"; //商品描述
		String spbill_create_ip = getRemoteIp(request); //终端IP
		String notify_url = ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_wx_notify_url) ; //通知地址
		String trade_type = "MWEB"; //交易类型

		//组装请求参数
		RequestHandler reqHandler = new RequestHandler(request, response);
		SortedMap<String, String> signParameters = new TreeMap<String, String>();
		signParameters.put("appid", appid);  						//appid
		signParameters.put("body", body);	 						//商品介绍
		signParameters.put("mch_id", mch_id); 						//商家号 
		signParameters.put("nonce_str", nonce_str); 	  			//随机数
		signParameters.put("notify_url", notify_url); 				//回调地址
		signParameters.put("spbill_create_ip", spbill_create_ip);	//ip地址
		signParameters.put("total_fee", total_fee); 				//总价
		signParameters.put("trade_type", trade_type); 				//支付方式
		
		//防止商户订单号重复不同的支付方式生产新的paymentId 生产商户号
		Integer paymentId =null;
		OrderPaymentQuery orderPaymentQuery =new OrderPaymentQuery();
		orderPaymentQuery.setOrderId(orderId);
		orderPaymentQuery.setPayType(4); //0-alipayPC 1-银联 2-微信公众号 3-线下支付 4-微信wap 5-微信扫码 6-aliPayWap
		List<OrderPayment> orderPayments = orderPaymentService.selectByCondition(orderPaymentQuery);
		if(orderPayments == null || orderPayments.isEmpty()){
			OrderPayment orderPayment =new OrderPayment();
			orderPayment.setOrderId(orderId);
			orderPayment.setPayType(4);
			paymentId = orderPaymentService.insert(orderPayment);
		}else{
			paymentId = orderPayments.get(0).getPaymentId();
		}
		String out_trade_no =orderId +"-"+ paymentId; //商户订单号 
		signParameters.put("out_trade_no", out_trade_no);
		log.info("out_trade_no=="+out_trade_no);	
		H5ScencInfo sceneInfo = new H5ScencInfo();
		sceneInfo.setH5_info(new H5("Wap","http://m.xindong8.com/","心动工场"));
		signParameters.put("scene_info", JsonUtils.writeValue(sceneInfo));	//场景信息
		//请求的参数进行签名
		String sign = WXPayUtils.createSign(charSet, signParameters,Constants.PayFromType.H5);
		signParameters.put("sign", sign);
		log.info("signParameters加密=="+signParameters);
		reqHandler.setParameters(signParameters);
		
		String xml = reqHandler.parseXML();
		//发送支付请求
		String xmlResult = httpPostData(WXValues.WX_PAY_URL, xml);
		
		//解析返回结果
		Map<String, String> resultMap = WXPayUtils.doXMLParse(xmlResult);
		if("SUCCESS".equals(resultMap.get("return_code"))){
			if("SUCCESS".equals(resultMap.get("result_code"))){
				String prepay_id = resultMap.get("prepay_id");
				String redirectUrl = "http://www.xindong8.com/user/my/";
				//支付成功返回地址
				String mweb_url = resultMap.get("mweb_url") + "&redirect_url=" + URLEncoder.encode(redirectUrl, "UTF-8");
				
				log.info("mweb_url:" + mweb_url);
				if(StringUtils.isNotEmpty(mweb_url) && StringUtils.isNotEmpty(prepay_id)){
					SortedMap<String, String> packageParams = new TreeMap<String, String>();
					packageParams.put("prepay_id", prepay_id);
					packageParams.put("mweb_url", mweb_url);
					result.setSuccess(true);
					result.setResult(packageParams);
					EcUtils.setSuccessResult(result);
				}
			}
		}else{
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			String error =resultMap.get("err_code_des");
			log.info("微信web支付失败错误：" + error);
			if(StringUtils.isEmpty(error)){
				error="微信支付失败,请稍后再试！";
			}
			result.setResultMessage(error);
			return result;
		}
		return result;
	}
	
	
	/**
	 * 微信扫描支付 NATIVE
	 * @param orderId 
	 * @param type 支付来源（1:NATIVE 2:JSAPI 3:APP）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getWxPay", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getWxPay(Integer orderId,String token,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("操作订单号："+orderId+" 进行Wx支付");
		Result result = new Result();
		if(orderId == null){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("订单数据错误！");
			return result;
		}
		
		result = this.jgWxPay(orderId, token);
		if(!result.getSuccess()){
			return result;
		}	
		BigDecimal oughtPayMoney=(BigDecimal) result.getResult() ;
		
		String total_fee = String.valueOf((int)(oughtPayMoney.doubleValue()*100));
		String charSet = TenpayUtil.getCharacterEncoding(request, response);
		String appid = WXValues.AppID;
		String mch_id = WXValues.AppMchId;
		
		String nonce_str = Sha1Util.getNonceStr();
		String body = "心动工场产品";
		String spbill_create_ip = "127.0.0.1";
		String notify_url =ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_wx_notify_url) ;
		String trade_type = "NATIVE";
		
		RequestHandler reqHandler = new RequestHandler(request, response);
		SortedMap<String, String> signParameters = new TreeMap<String, String>();
		signParameters.put("appid", appid);  						//appid
		signParameters.put("body", body);	 						//商品介绍
		signParameters.put("mch_id", mch_id); 						//商家号 
		signParameters.put("nonce_str", nonce_str); 	  			//随机数
		signParameters.put("notify_url", notify_url); 				//回调地址
		signParameters.put("spbill_create_ip", spbill_create_ip);	//ip地址
		signParameters.put("total_fee", total_fee); 				//总价
		signParameters.put("trade_type", trade_type); 				//支付方式
	
		//防止商户订单号重复不同的支付方式生产新的paymentId
		Integer paymentId =null;
		OrderPaymentQuery orderPaymentQuery =new OrderPaymentQuery();
		orderPaymentQuery.setOrderId(orderId);
		orderPaymentQuery.setPayType(5); //0-alipay 1-银联 2-微信公众号 3-先下支付 4-微信wap 5-微信扫码
		List<OrderPayment> orderPayments = orderPaymentService.selectByCondition(orderPaymentQuery);
		if(orderPayments == null || orderPayments.isEmpty()){
			OrderPayment orderPayment =new OrderPayment();
			orderPayment.setOrderId(orderId);
			orderPayment.setPayType(5);
			paymentId = orderPaymentService.insert(orderPayment);
		}else{
			paymentId = orderPayments.get(0).getPaymentId();
		}
		
		String out_trade_no =orderId +"-"+ paymentId;
		signParameters.put("out_trade_no", out_trade_no);
		signParameters.put("product_id", out_trade_no); 		//NATIVE 必须传
		
		String sign = WXPayUtils.createSign(charSet, signParameters,Constants.PayFromType.H5);
		signParameters.put("sign", sign);
		log.info("out_trade_no=="+out_trade_no);
		reqHandler.setParameters(signParameters);
		String xml = reqHandler.parseXML();
		String xmlResult = httpPostData(WXValues.WX_PAY_URL, xml);
		
		Map<String, String> map = WXPayUtils.doXMLParse(xmlResult);
		log.info("获取支付编号prepay_id=="+map.get("prepay_id"));
		String prepay_id = map.get("prepay_id");
		String code_url = map.get("code_url");
		log.info("获取支付编号code_url=="+map.get("code_url"));
		if(StringUtils.isNotEmpty(prepay_id)){
			//根据微信返回的链接生成支付二维码
			String logo ="http:/"+ ComFunction.createQrcodeLogo(code_url);
			result.setSuccess(true);
			result.setResult(logo);
			EcUtils.setSuccessResult(result);
			
		}else{
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			String error =map.get("err_code_des");
			if(StringUtils.isEmpty(error)){
				error="微信支付失败,请稍后再试！";
			} 
			result.setResultMessage(error);
			return result;
		}
		return result;
	}
	/**
	 * 判断下单
	 * @param orderId 
	 * @param type  订单支付来源 0 普通; 1 企业  2 h5 3 alipay
	 * @param payType 订单支付类型  1 微信支付; 2 支付宝支付 3银联支付 4线下
	 * @return resuSt [] 0 应该支付的金额 1 openid
	 * @throws Exception
	 */
	private Result  jgWxPay(Integer orderId,String token) throws Exception{
		Result result = new Result();
		EcUtils.setSuccessResult(result);
		Integer userId =ComFunction.getUserId(token);
		OrderInfo order =new OrderInfo();
		order.setUserId(userId);
		order.setOrderId(orderId);
		OrderInfo orderInfo = orderInfoService.selectByOrderInfo(order );
		if(orderInfo == null ){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("订单数据错误！");
			return result;
		}
		//1，判断库存
		if(orderInfo.getOrderType().equals(Constants.OrderType.TRAVEL)){
			Sku sku =skuService.selectByPrimaryKey(orderInfo.getSkuId());
			if(sku ==null){
				result.setSuccess(false);
				result.setResultCode("1002");
				result.setResultMessage("产品属性不存在");
				return result;
			}
			if(new Date().after(orderInfo.getStartDate()) 
					&& Constants.OrderType.TRAVEL.equals(orderInfo.getOrderType())){
				result.setResultCode("1002");
				result.setSuccess(false);
				result.setResultMessage("行程已经开始,不能支付");
				return result;
			}
		}
		
		Integer status= orderInfo.getOrderStatus();
		if(!(Constants.OrderStatus.DFK.equals(status))){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("该订单状态不能进行支付！");
			return result;
		}
		
		BigDecimal oughtPayMoney =orderInfo.getOughtPayMoney();
		result.setResult(oughtPayMoney );
		return result;
	}
	
	/**
	 * 支付宝PC支付（即时支付）
	 * @param orderId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getAlipayPay", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getAlipayPay(Integer orderId,String token,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("操作订单号："+orderId+" 进行Alipay支付");
		Result result = new Result();
		if(orderId == null){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("订单数据错误！");
			return result;
		}
		result = this.jgWxPay(orderId, token);
		if(!result.getSuccess()){
			return result;
		}	
		BigDecimal oughtPayMoney=(BigDecimal) result.getResult() ;
		String total_fee =oughtPayMoney.toString();
		String notify_url =ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.alipay_notify_url) ;//异步通知
		String return_url=ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.h5_pay_success);//返回地址
		//前台接收地址  需要带参数 参数不能参与签名
		String return_url_param =return_url+"?search="+URLEncoder.encode("type=suc&orderId="+orderId,"UTF-8");
		/**
		 * 获取订单支付编号
		 * 订单号-支付表主键
		 */
		OrderPayment orderPayment =new OrderPayment();
		orderPayment.setOrderId(orderId);
		//防止产生多少数据
		orderPayment = orderPaymentService.selectOrderPamentByOne(orderPayment);
		log.debug("==orderPayment=="+orderPayment);
		Integer paymentId =null;
		if(orderPayment!=null && orderPayment.getPaymentId()!=null){
			paymentId =orderPayment.getPaymentId();
		}else{
			 orderPayment =new OrderPayment();
			 orderPayment.setOrderId(orderId);
			 orderPayment.setPayType(0);//支付方式（0-alipayPC 1-银联 2-微信公众号 3-线下支付 4-微信wap 5-微信扫码 6-alipayWap）
			 paymentId = orderPaymentService.insert(orderPayment);
		}
		String out_trade_no =orderId +"-"+ paymentId;//交易号
		String subject ="心动工场产品";//
//		String show_url ="";//
		String body ="心动工场产品";//
//		String it_b_pay ="20m";//超时时间 20分钟
//		String extern_token ="";//
//		String app_id=AlipayConfig.app_id;
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("seller_id", AlipayConfig.seller_id);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
	    sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url_param);
		sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		log.info("==alipay=sHtmlText=="+sHtmlText);
		result.setSuccess(true);
		result.setResult(sHtmlText);
		EcUtils.setSuccessResult(result);
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//		System.out.println("=====result===="+result);
		out.write(sHtmlText.getBytes()); 
		out.flush();
		out.close();
		return result;
	}
	
	/**
	 * 支付宝WAP支付（移动支付）
	 * @param orderId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="wapAlipayPay", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result wapAlipayPay(Integer orderId,String token,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("操作订单号："+orderId+" 进行Alipay WAP支付");
		Result result = new Result();
		if(orderId == null){
			result.setSuccess(false);
			EcUtils.setExceptionResult(result);
			result.setResultMessage("订单数据错误！");
			return result;
		}
		result = jgWxPay(orderId, token);
		if(!result.getSuccess()){
			return result;
		}	
		BigDecimal oughtPayMoney=(BigDecimal) result.getResult() ;
		String total_amount = String.valueOf(oughtPayMoney .doubleValue()); //总金额
		
		//防止商户订单号重复不同的支付方式生产新的paymentId
		Integer paymentId =null;
		OrderPaymentQuery orderPaymentQuery =new OrderPaymentQuery();
		orderPaymentQuery.setOrderId(orderId);
		orderPaymentQuery.setPayType(6); //0-alipayPC 1-银联 2-微信公众号 3-线下支付 4-微信wap 5-微信扫码 6-aliPayWap
		List<OrderPayment> orderPayments = orderPaymentService.selectByCondition(orderPaymentQuery);
		if(orderPayments == null || orderPayments.isEmpty()){
			OrderPayment orderPayment =new OrderPayment();
			orderPayment.setOrderId(orderId);
			orderPayment.setPayType(6);
			paymentId = orderPaymentService.insert(orderPayment);
		}else{
			paymentId = orderPayments.get(0).getPaymentId();
		}
		log.info("paymentId===" + paymentId);
		String out_trade_no = orderId +"-"+ paymentId;
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayWapConfig.APPID , AlipayWapConfig.RSA_PRIVATE_KEY, "json", AlipayWapConfig.CHARSET, AlipayWapConfig.ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
		alipayRequest.setReturnUrl(AlipayWapConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayWapConfig.notify_url);//在公共参数中设置回跳和通知地址
		alipayRequest.setBizContent("{" +
		" \"out_trade_no\":\""+ out_trade_no +"\"," +
		" \"total_amount\":\"" + total_amount +"\"," +
		" \"subject\":\"心动产品\"," +
		" \"product_code\":\"QUICK_WAP_PAY\"" +
		" }");//填充业务参数
		String form="";
		try {
			form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		result.setSuccess(true);
		result.setResult(form);
		EcUtils.setSuccessResult(result);
		return result;
	}
	
	//微信支付回调接口
	@RequestMapping(value="payWX", method={RequestMethod.GET, RequestMethod.POST})
	public void payWX(HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info("支付成功,修改订单状态");
		boolean flag = false;
		String inputLine;
		String notityXml = "";
		String resXml = "";
		while ((inputLine = request.getReader().readLine()) != null) {
			notityXml += inputLine;
		}
		request.getReader().close();
		if(StringUtils.isNotEmpty(notityXml)){
			Map<String, String> map = WXPayUtils.doXMLParse(notityXml);
			log.info("notityXml==="+notityXml);
			if("SUCCESS".equals(map.get("result_code"))){
				flag = true;
				String orderPaymentId = (String) map.get("out_trade_no");//支付号  订单号-支付表主键
				log.info("orderPaymentId===" + orderPaymentId);
				if(orderPaymentId ==null){
					flag=false;
				}else{
					String order_payment[] = orderPaymentId.split("[-]");
					if(order_payment!=null && order_payment.length ==2){
						Integer orderId = Integer.parseInt(order_payment[0]);
						Integer paymentId = Integer.parseInt( order_payment[1]);//支付表主键
						if(paymentId==null ||orderId ==null){
							flag=false;
							log.error("payWX回调=paymentId为空==="+paymentId);
						}else{
							OrderInfo info= orderInfoService.selectByOrderId(orderId);
							if(info !=null){
								String appid =(String) map.get("appid");
								log.info("appid==="+appid);
								map.put("orderId", orderId.toString());
								map.put("paymentId", paymentId.toString());
								orderInfoService.payWX(map);
							}else{
								log.error("payWX回调=order为空==="+info);
								flag=false;
							}
						}
					}
				}
			}else{
				flag = false;
			}
		}else{
			flag = false;
		}
		if(flag){
			//支付成功 //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		log.info("flag===" + flag);
		log.info("微信支付回调数据结束");

		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
	}
	
	//支付宝支付回调接口
	@RequestMapping(value="payAlipay", method={RequestMethod.GET, RequestMethod.POST})
	public void payAlipay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info("支付宝支付成功,修改订单状态");
		boolean flag = false;
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
			log.info("支付宝支付回调数据:"+name+"=="+valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号   订单号-支付表主键
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		log.info("支付宝支付回调数据:out_trade_no"+out_trade_no+";trade_status="+trade_status); 
		PrintWriter out=response.getWriter();
		log.info("验证verify=="+AlipayNotify.verify(params));
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
//				if(AlipayNotify.verify(params)){//验证成功
			//请在这里加上商户的业务逻辑程序代码
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			/**
			 * 交易结束。支付宝会主动通知。正常支付下，会返回TRADE_SUCCESS状态
			 */
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序。付款完成后，支付宝系统发送该交易状态通知
				/**	
				 * 支付成功，修改订单状态。插入支付
				 */
				String order_payment[] = out_trade_no.split("[-]");
				if(order_payment!=null && order_payment.length ==2){
					Integer orderId = Integer.parseInt(order_payment[0]);
					Integer paymentId = Integer.parseInt( order_payment[1]);//支付表主键
					if(paymentId==null ||orderId ==null){
						flag=false;
						log.error("payWX回调=paymentId为空==="+paymentId);
					}else{
						OrderInfo info= orderInfoService.selectByOrderId(orderId);
						if(info !=null){
							params.put("orderId", orderId.toString());
							params.put("paymentId", paymentId.toString());
							orderInfoService.payAlipay(params);
						}else{
							flag=false;
						}
					}
				}else{
					out.println("fail");
				}
				
			}
			out.println("success");	//请不要修改或删除
		/*}else{//验证失败
			out.println("fail");
		}*/
		log.info("支付宝支付回调数据结束");
	/*	BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();*/
	}
	
	/**
	 * 支付宝WAP支付回调接口
	 */
	@RequestMapping(value="payAlipayWap", method={RequestMethod.GET, RequestMethod.POST})
	public void payAlipayWap(HttpServletRequest request,HttpServletResponse response){
		log.info("支付宝移动支付成功,修改订单状态");
		try {
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
					log.info("支付宝支付回调数据:"+name+"=="+valueStr);
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			log.info("total_amount===" + total_amount);
			log.info("trade_status===" + trade_status);
			
			//计算得出通知验证结果
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayWapConfig.ALIPAY_PUBLIC_KEY, AlipayWapConfig.CHARSET, "RSA2");
			log.info("verify_result===" + verify_result);
			PrintWriter out=response.getWriter();	
			if(verify_result){//验证成功
				//支付成功，修改订单状态。插入支付
				/**
				 * 交易结束。支付宝会主动通知。正常支付下，会返回TRADE_SUCCESS状态
				 */
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					//注意：
					//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序。付款完成后，支付宝系统发送该交易状态通知
					/**	
					 * 支付成功，修改订单状态。插入支付
					 */
					log.info("out_trade_no===" + out_trade_no);
					String order_payment[] = out_trade_no.split("[-]");
					if(order_payment!=null && order_payment.length ==2){
						Integer orderId = Integer.parseInt(order_payment[0]);
						Integer paymentId = Integer.parseInt( order_payment[1]);//支付表主键
						if(paymentId==null ||orderId ==null){
							log.error("payWX回调=paymentId为空==="+paymentId);
						}else{
							OrderInfo orderInfo= orderInfoService.selectByOrderId(orderId);
							if(null != orderInfo){
								params.put("orderId", orderId.toString());
								params.put("paymentId", paymentId.toString());
								params.put("total_fee", total_amount);//保证接口统一性 进行替换
								orderInfoService.payAlipay(params);
							}
						}
					}else{
						out.println("fail");
					}
				}
				out.println("success");	//请不要修改或删除
			}else{//验证失败
				out.println("fail");
			}
		} catch (Exception e) {
			log.error("OrderPay.wapAlipay",e);
		}
		log.info("支付宝支付回调数据结束");
	}
	
}
