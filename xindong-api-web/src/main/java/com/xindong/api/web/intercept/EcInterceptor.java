package com.xindong.api.web.intercept;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.DESUtil;
import com.xindong.api.common.utils.DateUtil;
import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.common.utils.MD5Util;
import com.xindong.api.service.result.Result;


public class EcInterceptor implements Filter {
	private static final Logger log = LoggerFactory.getLogger(EcInterceptor.class);
	
	private final static String secret = "d18ed7feb9db4621b95da86943f7717f";
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private void printResult(String resultCode, String resultMessage, HttpServletResponse response) throws IOException{
		Result result = new Result();
		PrintWriter pr = response.getWriter();
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		result.setSuccess(false);
		result.setResultCode(resultCode);
		result.setResultMessage(resultMessage);
		pr.print(JsonUtils.writeValue(result));
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Cache-Control","no-cache");   
	    response.setHeader("Pragma","no-cache");   
	    response.setDateHeader ("Expires", -1);
	    response.setHeader("Content-Type", "application/json;charset=UTF-8");
	    response.setHeader("Access-Control-Allow-Origin", "*");
        //'*'表示允许所有域名访问，可以设置为指定域名访问，多个域名中间用','隔开
	    
	    HttpServletRequest request = (HttpServletRequest)servletRequest;
	    
	    String requestPath = request.getRequestURI();
	    System.out.println(sdf.format(new Date()) + "本次请求地址：" + requestPath+ "?" + request.getQueryString());
	 
        if(requestPath.contains("/v/getVersion")){
	    	chain.doFilter(request, response);
	    	return;
	    }
        if(requestPath.contains("/orderPay/unionPayResult")
        		|| requestPath.contains("/orderPay/payWX")
        		||requestPath.contains("/orderPay/payAlipay")){
        	String requestUrl = request.getRequestURI();
  		  	String requestStr = request.getQueryString();
        	log.info("支付回调路径==="+requestUrl);
        	log.info("支付回调参数==="+requestStr);
        	chain.doFilter(request, response);
	    	return;
        }
//        String requestType = req.getHeader("Content-Type");  
//        log.debug("requestType=="+requestType);
        if (requestPath.contains("/userInfo/uploadUserHeadImg")) { // 这里是处理文件上传请求  ,不验证
        	chain.doFilter(request, response);
            return ; 
        }
        if (requestPath.contains("/category/uploadCommentImg")) { // 这里是处理文件上传请求  ,不验证
    	   chain.doFilter(request, response);
           return ; 
        }
        if(requestPath.contains("/page/") || requestPath.contains("/css/") || requestPath.contains("/js/") || requestPath.contains("/img/")){
	    	chain.doFilter(request, response);
	    	return;
        }
        
		String v = request.getParameter("v");
		String timestamp = request.getParameter("timestamp");
		String sign = request.getParameter("sign");
		
		if(StringUtils.isBlank(v)){
			this.printResult("1001", "v不能为空", response);
			return;
		}
		
		if(StringUtils.isBlank(timestamp)){
			this.printResult("1001", "timestamp不能为空", response);
			return;
		}
		
		if(StringUtils.isBlank(sign)){
			this.printResult("1001", "sign不能为空", response);
			return;
		}
		
		String mysign = MD5Util.md5Hex(secret + timestamp + v + secret).toUpperCase();
		if(!mysign.equals(sign)){
			this.printResult("1001", "sign不正确", response);
			return;
		}
		
		chain.doFilter(request, response);
	}

	public static void main(String[] args) {
		String timestamp ="2015-12-24 17:34:48";
		String v2 ="1.0";
		System.out.println(MD5Util.getMD5Str(secret + timestamp + v2 + secret).toUpperCase());
		//?v=1.0&sign=6EBD866547D8065DBE16AEC925F73881&timestamp=2015-12-24 17:34:48&token=c5b71aac54a51dd01b39797821fcbdd6
		
		String token = 10000092 + "-" + "xindong" + "-" + 2;
		 try {
			 System.out.println("token="+DESUtil.encrypt(token, Constants.TOKEN_DES));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	@Override
	public void destroy() {
		
	}
}
