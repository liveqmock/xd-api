package com.xindong.api.web.intercept;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.RedisUtils;


public class ItemInterceptor  implements HandlerInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(ItemInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		try{
			String  uri =  request.getRequestURI();
			String type = request.getParameter("type");
			String itemId = request.getParameter("itemId");
			String resultType = "1" .equals(type) ?"1":"0";	
			if("/item/getItemByItemId".equals(uri)){
				//拼接SortedSet成员  10075_0
				StringBuffer sb = new StringBuffer();
				sb.append(itemId);
				sb.append("_");
				sb.append(resultType);
				String member = sb.toString();
				
				String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				if("0".equals(resultType)){//pc
					RedisUtils.zincrby("item_pv_pc_" + currentDate, 1, member);
					//RedisUtils.zincrby("item_pv_pc_day_" + currentDate, 1, "pc_day_all");
				}else{//h5 item_pv_h5_20170601
					RedisUtils.zincrby("item_pv_h5_" + currentDate, 1, member);
					//RedisUtils.zincrby("item_pv_h5_day_" + currentDate, 1, "h5_day_all");
				}
				RedisUtils.zincrby("item_pv_all_" + currentDate, 1, itemId);//按天统计
				RedisUtils.zincrby("item_pv_all", 1, itemId);//所有
			}
		}catch (Exception e) {
			log.error("", e);
			return false;
		}
		return true;
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
