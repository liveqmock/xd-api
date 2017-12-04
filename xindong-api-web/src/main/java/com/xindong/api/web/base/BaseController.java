package com.xindong.api.web.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.domain.wx.WXTicketInfo;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.RedisUtils;
import com.xindong.api.web.wx.WXUtils;
import com.xindong.api.web.wx.WXValues;

public class BaseController {
	
	@InitBinder
	public void initDateBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	protected String getRemoteIp(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
			ip = ip.substring(0, ip.indexOf(","));
		}
		if (ip != null) {
			return ip.split(":")[0];
		}
		return null;
	}

	protected Integer getUserId(HttpServletRequest request) {
		return Integer.parseInt(request.getAttribute("userId").toString());
	}
	
	protected String getAPIToken(HttpServletRequest request) {
		return (String) request.getAttribute("token");
	}
	
	protected String getAPITokenByOpenId(HttpServletRequest request) {
		String openId = request.getParameter("openId");
		return RedisUtils.get(openId);
	}
	
	protected String getAPIOpenId(HttpServletRequest request) {
		return (String) request.getParameter("openId");
	}
	
	protected String getToken() {
		return WXUtils.getToken();
	}
	
	protected String getTicket() {
		return WXUtils.getTicket(getToken());
	}
	
	protected WXTicketInfo shaTicket(String url) {
		return WXUtils.shaTicket(getTicket(), url);
	}
	
	protected String getUrl(HttpServletRequest request) {
		return WXValues.url+request.getRequestURI();
	}
	
	protected String getAPIUrl(HttpServletRequest request) {
		return WXValues.url+request.getRequestURI();
	}
	
	protected String httpPostData(String url, String data){
		return BaseValues.httpPostData(url, data);
	}
	
	protected Result httpGetDataResult(String path, String data){
		Result result = new Result();
		String datas = BaseValues.httpGetData(path, data);
		result = JsonUtils.readValue(datas, Result.class);
		return result;
	}
	protected Result httpGetDataResult(String path, String data, String token){
		Result result = new Result();
		String datas = BaseValues.httpGetData(path, data, token);
		result = JsonUtils.readValue(datas, Result.class);
		return result;
	}
	
	protected void setExceptionResult(Result result){
		result.setSuccess(false);
		result.setResultCode("500");
		result.setResultMessage("服务异常，请稍后重试");
	}
	
	protected void setSuccessResult(Result result){
		result.setSuccess(true);
		result.setResultCode("200");
		result.setResultMessage("");
	}
	

    @ExceptionHandler({Exception.class}) 
    public String exception(HttpServletRequest reuqest, Exception e){
    	System.out.println(reuqest.getRequestURI()+"出现异常====>>"+e.getMessage());
        e.printStackTrace();
        return "exception";
    }
	
}
