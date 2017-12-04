package com.xindong.api.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.DateUtil;
import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.OrderInvoice;
import com.xindong.api.domain.OrderPassenger;
import com.xindong.api.domain.query.OrderInfoQuery;
import com.xindong.api.domain.vo.OrderPassengerList;
import com.xindong.api.service.OrderInfoService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.web.base.BaseController;

/** 订单信息 */

@Controller
@RequestMapping("/orderInfo")
public class OrderInfoController extends BaseController {
	@Autowired
	private OrderInfoService orderInfoService;
	
	/**
	 * 行程个性化（废弃）
	 * @return
	 */
	@RequestMapping(value="submitSpecialOrder", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result submitSpecialOrder(OrderInfo order ,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		order.setUserId(userId);
		if(order.getItemId() ==null){
			result.setResultCode("1001");
			result.setResultMessage("请求参数错误");
			return result;
		}
		if(StringUtils.isBlank(order.getStartDateStr())){
			result.setResultCode("1001");
			result.setResultMessage("请您选择出发日期");
			return result;
		}
		if(StringUtils.isBlank(order.getReturnDateStr())){
			result.setResultCode("1001");
			result.setResultMessage("请您选择返程日期");
			return result;
		}
		
		if(order.getAdultNum() ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您选择成人数量");
			return result;
		}
		if(order.getChildrenNum() ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您选择儿童数量");
			return result;
		}
		if(StringUtils.isBlank(order.getOriginName())){
			result.setResultCode("1001");
			result.setResultMessage("请您填写出发城市");
			return result;
		}
		if(order.getBalanceId() ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您选择旅行人群");
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
		if(order.getFromWhere() ==null){
			order.setFromWhere(Constants.FromWhere.PC);
		}
		//日期转换
		order.setStartDate(DateUtil.toDate(order.getStartDateStr(), "yyyy-MM-dd"));
		order.setReturnDate(DateUtil.toDate(order.getReturnDateStr(), "yyyy-MM-dd"));
		order.setIp(getRemoteIp(request));
		return orderInfoService.submitSpecialOrder(order);
	}
	/**
	 * 提交订单
	 * useBalance 是否使用余额（0不使用1使用  默认0）
	 * @return
	 */
	@RequestMapping(value="submitOrder", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result submitOrder(OrderInfo order ,String token,OrderInvoice invoice, String passenges
			, Integer useBalance,String coupons,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		order.setUserId(userId);
		if(order.getAdultNum() ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您选择成人数量");
			return result;
		}
		if(order.getSkuRoomsNum() ==null){
			order.setSkuRoomsNum(0);
		}
		if(order.getChildrenNum() ==null){
			order.setChildrenNum(0);
		}
		if(order.getSkuRoomsNum() > order.getChildrenNum()+order.getAdultNum()){
			result.setResultCode("1001");
			result.setResultMessage("单房差数量必须小于预定总人数");
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
		if(StringUtils.isBlank(order.getContactEmail())){
			result.setResultCode("1001");
			result.setResultMessage("请您填写邮箱");
			return result;
		}
		if(order.getFromWhere() ==null){
			order.setFromWhere(Constants.FromWhere.PC);
		}
		if(invoice.getInvoiceStatus() ==null){
			invoice.setInvoiceStatus(Constants.OrderInvoiceStatus.NOTNEED);
		}else{
		    if(Constants.OrderInvoiceStatus.UNINVOICED.equals(invoice.getInvoiceStatus())){
		    	String invoiceTitle = invoice.getInvoiceTitle() ;
				String invoiceContent = invoice.getInvoiceContent();
		    	if(StringUtils.isBlank(invoiceTitle)){
					result.setResultCode("1001");
					result.setResultMessage("发票抬头不能为空");
					return result;
				}
				if(StringUtils.isBlank(invoiceContent)){
					result.setResultCode("1001");
					result.setResultMessage("发票内容不能为空");
					return result;
				}
		    }
		}
		if(order.getSkuId() ==null||order.getItemId() ==null){
			result.setResultCode("1001");
			result.setResultMessage("商品信息错误");
			return result;
		}
		String listString = "{\"list\":"+passenges+"}";
		List<OrderPassenger> list =null;
		try{
			OrderPassengerList passengerList = null;
			passengerList = JsonUtils.readValue(listString, OrderPassengerList.class);
			list =passengerList.getList();
			if(list ==null){
				result.setResultCode("1001");
				result.setResultMessage("旅客信息错误");
				return result;
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("1001");
			result.setResultMessage("订单旅客信息错误");
			return result;
		}
		order.setIp(getRemoteIp(request));
		if(useBalance ==null){
			useBalance=0;
		}
		return orderInfoService.submitOrder(order,invoice,list,useBalance,coupons);
	}
	
	/**
	 *我的订单列表
	 * @return
	 */
	@RequestMapping(value="getOrderInfosByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getOrderInfosByPage(OrderInfoQuery orderInfoQuery ,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId =ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		orderInfoQuery.setUserId(userId);
		if(orderInfoQuery.getOrderType() ==null){
			orderInfoQuery.setOrderType(Constants.OrderType.TRAVEL);
		}
		return orderInfoService.getOrderInfosByPage(orderInfoQuery);
	}
	/**
	 *我的订单详情
	 * @return
	 */
	@RequestMapping(value="getOrderInfoByOrderIdAndUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getOrderInfoByOrderIdAndUserId(Integer orderId, String token, Integer fromWhere, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId =ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(orderId ==null){
			result.setResultCode("1001");
			result.setResultMessage("订单编号不能为空");
			return result;
		}
		
		return orderInfoService.getOrderInfoByOrderIdAndUserId(orderId,userId,fromWhere);
	}
	/**
	 *我的订单取消
	 * @return
	 */
	@RequestMapping(value="cancelOrder", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result cancelOrder(Integer orderId ,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId =ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(orderId ==null){
			result.setResultCode("1001");
			result.setResultMessage("订单编号不能为空");
			return result;
		}
		return orderInfoService.cancelOrder(orderId, userId);
	}
	/**
	 *订单完成出行
	 * @return
	 */
	@RequestMapping(value="doneOrderByOrderId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result doneOrderByOrderId(Integer orderId ,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId =ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(orderId ==null){
			result.setResultCode("1001");
			result.setResultMessage("订单编号不能为空");
			return result;
		}
		OrderInfo info =new OrderInfo();
		info.setOrderId(orderId);
		info.setUserId(userId);
		info.setOrderStatus(Constants.OrderStatus.YWC);
		return orderInfoService.doneOrderByOrderId(info);
	}
	
	/**
	 * 计算订单金额
	 * @return
	 */
	@RequestMapping(value="calculateOrderMoney", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result calculateOrderMoney(OrderInfo order ,String coupons,String token,Integer useBalance,
			HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		order.setUserId(userId);
		if(order.getAdultNum() ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您选择成人数量");
			return result;
		}
		if(order.getSkuRoomsNum() ==null){
			order.setSkuRoomsNum(0);
		}
		if(order.getChildrenNum() ==null){
			order.setChildrenNum(0);
		}
		if(order.getSkuRoomsNum() > order.getChildrenNum()+order.getAdultNum()){
			result.setResultCode("1001");
			result.setResultMessage("单房差数量必须小于预定总人数");
			return result;
		}
		
		if(order.getSkuId() ==null||order.getItemId() ==null){
			result.setResultCode("1001");
			result.setResultMessage("商品信息错误");
			return result;
		}
		if(useBalance ==null){
			useBalance=0;
		}
		return orderInfoService.calculateOrderMoney(order,useBalance,coupons);
	}
}
