package com.xindong.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.domain.OrderInfo;
import com.xindong.api.service.OrderInfoService;
import com.xindong.api.service.result.Result;

/** 订单信息 */

@Controller
@RequestMapping("/custom")
public class CustomController {
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	/**
	 * 行程个性化
	 * @return
	 */
	@RequestMapping(value="submitCustomOrder", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result submitCustomOrder(OrderInfo order ,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		/*Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}*/
		if(StringUtils.isBlank(order.getOriginName())){
			result.setResultCode("1001");
			result.setResultMessage("请您填写出发城市");
			return result;
		}
		if(StringUtils.isBlank(order.getContactName())){
			result.setResultCode("1001");
			result.setResultMessage("请您填写姓名");
			return result;
		}
		if(StringUtils.isBlank(order.getContactMobile())){
			result.setResultCode("1001");
			result.setResultMessage("请您填写手机号");
			return result;
		}
		return orderInfoService.submitCustomOrder(order);
	}
}
