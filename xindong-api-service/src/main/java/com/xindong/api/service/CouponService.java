package com.xindong.api.service;

import java.math.BigDecimal;

import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.query.TbCouponPromoQuery;
import com.xindong.api.domain.query.TbCouponQuery;
import com.xindong.api.service.result.Result;

public interface CouponService {
	
	/**
	 * 查询我的优惠券
	 * @param userId 用户ID
	 * @return
	 */
	public Result selectByUserId(Integer userId);
	
	/**
	 * 使用优惠券
	 * @param userId 用户ID
	 * @param price 价格
	 * @return
	 */
	public Result selectUseByUserId(Integer userId, BigDecimal price);
	
	/**
	 * 
	 * @param userId 用户ID
	 * @param couponsId 优惠券ID
	 * @return
	 */
	public Result drawCoupon(Integer userId, Integer couponsId);

	public Result selectByCouponQuery(TbCouponQuery couponQuery);

	public Result getCouponCanUse(OrderInfo order);

	public Result receiveCoupon(Integer userId, Integer couponId);

	public Result getAllCouponPromo(TbCouponPromoQuery couponPromoQuery);
}
