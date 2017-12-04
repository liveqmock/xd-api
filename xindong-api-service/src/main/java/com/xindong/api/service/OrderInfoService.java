package com.xindong.api.service;

import java.util.List;
import java.util.Map;

import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.OrderInvoice;
import com.xindong.api.domain.OrderPassenger;
import com.xindong.api.domain.query.OrderInfoQuery;
import com.xindong.api.service.result.Result;

public interface OrderInfoService {
	public Result getOrderInfoByOrderIdAndUserId(Integer orderId,Integer userId, Integer fromWhere);
	public Result submitSpecialOrder(OrderInfo orderInfo);
	public Result submitOrder(OrderInfo orderInfo, OrderInvoice invoice,List<OrderPassenger> list, Integer useBalance, String coupons);
	public Result getOrderInfosByPage(OrderInfoQuery orderInfoQuery);
	public OrderInfo selectByOrderId(Integer orderId);
	public OrderInfo selectByOrderInfo(OrderInfo orderInfo);
	public void unionPay(Map<String, Object> map);
	public Result getOffLineData(Integer type);
	public Result doneOrderByOrderId(OrderInfo info);
	public Result cancelOrder(Integer orderId, Integer userId);
	public Result calculateOrderMoney(OrderInfo order, Integer useBalance, String coupons);
	public void payAlipay(Map<String, String> params);
	public void payWX(Map<String, String> map);
	public Result submitCustomOrder(OrderInfo orderInfo);
}
