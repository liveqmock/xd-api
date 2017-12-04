package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.TbCouponPromo;
import com.xindong.api.domain.query.TbCouponPromoQuery;

public interface TbCouponPromoDao {
	
	/**
	 * 添加促销优惠券
	 * @param tbCouponPromo
	 * @return
	 */
	public Integer insert(TbCouponPromo tbCouponPromo);

	/**
	 * 依据促销优惠券ID修改促销优惠券
	 * @param tbCouponPromo
	 */
	public void modify(TbCouponPromo tbCouponPromo);

	/**
	 * 依据促销优惠券ID查询促销优惠券
	 * @param couponId
	 * @return
	 */
	public TbCouponPromo selectByCouponPromoId(int couponPromoId);
	
	/**
	 * 根据相应的条件查询满足条件的促销优惠券的总数
	 * @param tbCouponPromoQuery
	 * @return
	 */
	public int countByCondition(TbCouponPromoQuery tbCouponPromoQuery);
	
	/**
	 * 根据相应的条件查询促销优惠券
	 * @param tbCouponPromoQuery
	 * @return
	 */
	public List<TbCouponPromo> selectByCondition(TbCouponPromoQuery tbCouponPromoQuery); 
	
	/**
	 * 根据相应的条件分页查询促销优惠券
	 * @param tbCouponPromoQuery
	 * @return
	 */
	public List<TbCouponPromo> selectByConditionForPage(TbCouponPromoQuery tbCouponPromoQuery);
	
}
