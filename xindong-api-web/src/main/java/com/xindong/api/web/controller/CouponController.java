package com.xindong.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.query.TbCouponPromoQuery;
import com.xindong.api.domain.query.TbCouponQuery;
import com.xindong.api.service.CouponService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.web.base.BaseController;

/** 优惠券 */

@Controller
@RequestMapping("coupon")
public class CouponController extends BaseController {
private static final Logger log = LoggerFactory.getLogger(CouponController.class);
	
	private CouponService couponService;
	
	/**
	 * 查询我的优惠券
	 * @return
	 */
	@RequestMapping(value="/getMyCoupon", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getMyCoupon(TbCouponQuery couponQuery, HttpServletRequest request,HttpServletResponse response){
		log.info("我的优惠券");
		couponQuery.setUserId(getUserId(request));
		return couponService.selectByCouponQuery(couponQuery);
	}
	
	/**
	 * 查询订单可用优惠券
	 * @param orderPrice 订单价格
	 * @return
	 */
	@RequestMapping(value="/getCouponCanUse", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCouponCanUse(OrderInfo order, HttpServletRequest request){
		log.info("使用优惠券");
		order.setUserId(getUserId(request));
		return couponService.getCouponCanUse(order);
	}
	/**
	 * 领取优惠券
	 * couponPromoId 优惠券编号
	 * @return
	 */
	@RequestMapping(value="/receiveCoupon", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result receiveCoupon(Integer couponPromoId,String token, HttpServletRequest request,HttpServletResponse response){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(couponPromoId ==null){
			result.setResultCode("1001");
			result.setResultMessage("优惠券参数错误");
			return result;
		}
		return couponService.receiveCoupon(userId,couponPromoId);
	}
	/**
	 * 查询可以直接领取的优惠券
	 * @return
	 */
	@RequestMapping(value="/getAllCoupon", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getAllCoupon(TbCouponPromoQuery couponPromoQuery, HttpServletRequest request,HttpServletResponse response){
//		couponQuery.setUserId(getUserId(request));
		return couponService.getAllCouponPromo(couponPromoQuery);
	}
	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}
}
