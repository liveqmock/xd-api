package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.TbCoupon;
import com.xindong.api.domain.query.TbCouponQuery;


public interface TbCouponDao {
	
	/**
	 * 添加优惠券
	 * @param tbCoupon
	 * @return
	 */
	public Integer insert(TbCoupon tbCoupon);

	/**
	 * 依据优惠券ID修改优惠券
	 * @param tbCoupon
	 */
	public void modify(TbCoupon tbCoupon);

	/**
	 * 依据优惠券ID查询优惠券
	 * @param couponId
	 * @return
	 */
	public TbCoupon selectByTbCouponId(int couponId);
	
	/**
	 * 根据相应的条件查询满足条件的优惠券的总数
	 * @param tbCouponQuery
	 * @return
	 */
	public int countByCondition(TbCouponQuery tbCouponQuery);
	
	/**
	 * 根据相应的条件查询优惠券
	 * @param tbCouponQuery
	 * @return
	 */
	public List<TbCoupon> selectByCondition(TbCouponQuery tbCouponQuery); 
	
	/**
	 * 根据相应的条件分页查询优惠券
	 * @param tbCouponQuery
	 * @return
	 */
	public List<TbCoupon> selectByConditionForPage(TbCouponQuery tbCouponQuery);
	
}
