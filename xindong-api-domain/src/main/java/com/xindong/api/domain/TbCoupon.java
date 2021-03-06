package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;

public class TbCoupon implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 优惠券_ID */
	private Integer couponId;
	/** 优惠券促销ID */
	private Integer couponPromId;
	/** 优惠券_类型(0 满减券 1直减券 2 免运费券 ) */
	private Integer couponType;
	/** 订单_限额 */
	private Integer orderQuota;
	/** 优惠券_金额 */
	private Integer couponAmount;
	/** 优惠券_状态(0未使用1已使用2已过期3已锁定) */
	private Integer couponState;
	/** 有效状态(1有效;0无效) */
	private Integer yn;
	/** 用户ID */
	private Integer userId;
	/** 优惠券_有效_开始时间 */
	private Date couponValidStarttime;
	/** 优惠券_有效_结束时间 */
	private Date couponValidEndtime;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 优惠券名称 */
	private String couponName;
	/** 订单ID */
	private Integer orderId;
	private Integer couponRuleType;//优惠券类型（1全品类2具体产品3具体SKU）
    private Integer skuId;//优惠券sku编号
    private Integer itemId;//优惠券item编号
    private String bakFirst;//多sku1001-1003
    private Integer bakSecond;//备用1
    private String itemDesc;//前端展示使用  可购买商品描述
    private String couponRuleContent;//促销规则
    
    public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
    public String getItemDesc() {
		return itemDesc;
	}
	public Integer getCouponRuleType() {
		return couponRuleType;
	}
	public void setCouponRuleType(Integer couponRuleType) {
		this.couponRuleType = couponRuleType;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getBakFirst() {
		return bakFirst;
	}
	public void setBakFirst(String bakFirst) {
		this.bakFirst = bakFirst;
	}
	public Integer getBakSecond() {
		return bakSecond;
	}
	public void setBakSecond(Integer bakSecond) {
		this.bakSecond = bakSecond;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId=couponId;
	}
	public Integer getCouponPromId() {
		return couponPromId;
	}
	public void setCouponPromId(Integer couponPromId) {
		this.couponPromId=couponPromId;
	}
	public Integer getCouponType() {
		return couponType;
	}
	public void setCouponType(Integer couponType) {
		this.couponType=couponType;
	}
	public Integer getOrderQuota() {
		return orderQuota;
	}
	public void setOrderQuota(Integer orderQuota) {
		this.orderQuota=orderQuota;
	}
	public Integer getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Integer couponAmount) {
		this.couponAmount=couponAmount;
	}
	public Integer getCouponState() {
		return couponState;
	}
	public void setCouponState(Integer couponState) {
		this.couponState=couponState;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn=yn;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId=userId;
	}
	public Date getCouponValidStarttime() {
		return couponValidStarttime;
	}
	public void setCouponValidStarttime(Date couponValidStarttime) {
		this.couponValidStarttime=couponValidStarttime;
	}
	public Date getCouponValidEndtime() {
		return couponValidEndtime;
	}
	public void setCouponValidEndtime(Date couponValidEndtime) {
		this.couponValidEndtime=couponValidEndtime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName=couponName;
	}
	public String getCouponRuleContent() {
		return couponRuleContent;
	}
	public void setCouponRuleContent(String couponRuleContent) {
		this.couponRuleContent = couponRuleContent;
	}
}
