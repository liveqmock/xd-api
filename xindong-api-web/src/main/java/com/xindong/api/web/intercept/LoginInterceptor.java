package com.xindong.api.web.intercept;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.DESUtil;
import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.service.UserInfoService;
import com.xindong.api.service.result.Result;


public class LoginInterceptor  implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	@Autowired
	private UserInfoService userInfoService;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try{
			String requestPath = request.getRequestURI();
			//文件上传不校验
            if(requestPath.contains("/v/getVersion") || requestPath.contains("/userInfo/uploadUserHeadImg")){
		    	return true;
		    }
			String token = request.getParameter("token");
			if(StringUtils.isBlank(token)){
				this.printResult("1001", "token不能为空", response);
				return false;
			}
			String value = DESUtil.decrypt(token, Constants.TOKEN_DES);
			if(StringUtils.isBlank(value)){
				this.printResult("1001", "token不正确", response);
				return false;
			}
			
			String [] tokenArr = value.split("-");
			if(tokenArr == null || tokenArr.length != 3){
				this.printResult("1001", "token不正确", response);
				return false;
			}
			
			request.setAttribute("userId", tokenArr[0]);
			request.setAttribute("mobile", tokenArr[1]);
			request.setAttribute("userType", tokenArr[2]);
			
			UserInfo info =  userInfoService.selectByUserId(Integer.parseInt(tokenArr[0]));
			if(info == null){
				this.printResult("1001", "请您重新登录", response);
				return false;
			}
			return true;
		}catch (Exception e) {
			log.error("", e);
			this.printResult("1001", "token不正确", response);
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
	private void printResult(String resultCode, String resultMessage, HttpServletResponse response) throws Exception{
		Result result = new Result();
		PrintWriter pr = response.getWriter();
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		result.setSuccess(false);
		result.setResultCode(resultCode);
		result.setResultMessage(resultMessage);
		pr.print(JsonUtils.writeValue(result));
	}
}
