package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;

public class TbCouponPromo implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 优惠券_活动_ID */
	private Integer couponPromoId;
	/** 优惠券_活动_编码 */
	private String couponPromoCode;
	/** 优惠券_类型(0 满减券 1直减券 2 免运费券 ) */
	private Integer couponType;
	/** 优惠券_批次 */
	private String couponBatch;
	/** 优惠券_活动_名称 */
	private String couponPromoName;
	/** 优惠券_业务_类型(0 订单相关 1直接发放到账号 2用户行为相关 ) */
	private Integer couponBusinessType;
	/** 优惠券_申请_数量 */
	private Integer couponApplyAmount;
	/** 优惠券_发放_数量 */
	private Integer couponGrantAmount;
	/** 优惠券_过期_数量 */
	private Integer couponExpiredAmount;
	/** 优惠券有效方式(0 相对时间;1时间区间) */
	private Integer couponValidType;
	/** 优惠券_有效_开始时间 */
	private Date couponValidStarttime;
	/** 优惠券_有效_结束时间 */
	private Date couponValidEndtime;
	/** 优惠券_有效_天数 */
	private Integer couponValidDays;
	/** 活动开始时间 */
	private Date promoStarttime;
	/** 活动结束时间 */
	private Date promoEndtime;
	/** 优惠券_活动_状态(0待上线;1正在进行;2已结束;3已过期) */
	private Integer couponPromoState;
	/** 操作者 */
	private String opera;
	/** 操作时间 */
	private Date operaTime;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 申请人_名称 */
	private String applyUserName;
	/** 申请_部门_名称 */
	private String applyDepartmentName;
	/** 申请时间 */
	private Date applyTime;
	/** 优惠券_已使用_数量 */
	private Integer couponUsedAmount;
	/** 订单限额 */
	private Integer orderQuota;
	/** 优惠券_金额 */
	private Integer couponAmount;
	/** 发放_延迟_天数 */
	private Integer grantDelayDays;
	/** 限制条件(0没有 1 必须是首单才发放) */
	private Integer limitCondition;
	/** 有效状态(1有效0无效) */
	private Integer yn;
	/** 是否发放优惠券到账户（0，没有；1，已发放） */
	private Integer isSend;
	private Integer couponRuleType;//优惠券类型（1全品类2具体产品3具体SKU）
    private Integer skuId;//优惠券sku编号
    private Integer itemId;//优惠券item编号
    private String bakFirst;//多sku1001-1003
    private Integer bakSecond;//备用1
    private String itemDesc;//前端展示使用  可购买商品描述
    private Integer amountRandom;//优惠券金额随机(0、否；1、是)
    private Integer couponAmountMin;//优惠券最小金额
    private String couponRuleContent;//促销规则
    private String couponDesc;
    
	public Integer getCouponPromoId() {
		return couponPromoId;
	}
	public void setCouponPromoId(Integer couponPromoId) {
		this.couponPromoId = couponPromoId;
	}
	public String getCouponPromoCode() {
		return couponPromoCode;
	}
	public void setCouponPromoCode(String couponPromoCode) {
		this.couponPromoCode = couponPromoCode;
	}
	public Integer getCouponType() {
		return couponType;
	}
	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	public String getCouponBatch() {
		return couponBatch;
	}
	public void setCouponBatch(String couponBatch) {
		this.couponBatch = couponBatch;
	}
	public String getCouponPromoName() {
		return couponPromoName;
	}
	public void setCouponPromoName(String couponPromoName) {
		this.couponPromoName = couponPromoName;
	}
	public Integer getCouponBusinessType() {
		return couponBusinessType;
	}
	public void setCouponBusinessType(Integer couponBusinessType) {
		this.couponBusinessType = couponBusinessType;
	}
	public Integer getCouponApplyAmount() {
		return couponApplyAmount;
	}
	public void setCouponApplyAmount(Integer couponApplyAmount) {
		this.couponApplyAmount = couponApplyAmount;
	}
	public Integer getCouponGrantAmount() {
		return couponGrantAmount;
	}
	public void setCouponGrantAmount(Integer couponGrantAmount) {
		this.couponGrantAmount = couponGrantAmount;
	}
	public Integer getCouponExpiredAmount() {
		return couponExpiredAmount;
	}
	public void setCouponExpiredAmount(Integer couponExpiredAmount) {
		this.couponExpiredAmount = couponExpiredAmount;
	}
	public Integer getCouponValidType() {
		return couponValidType;
	}
	public void setCouponValidType(Integer couponValidType) {
		this.couponValidType = couponValidType;
	}
	public Date getCouponValidStarttime() {
		return couponValidStarttime;
	}
	public void setCouponValidStarttime(Date couponValidStarttime) {
		this.couponValidStarttime = couponValidStarttime;
	}
	public Date getCouponValidEndtime() {
		return couponValidEndtime;
	}
	public void setCouponValidEndtime(Date couponValidEndtime) {
		this.couponValidEndtime = couponValidEndtime;
	}
	public Integer getCouponValidDays() {
		return couponValidDays;
	}
	public void setCouponValidDays(Integer couponValidDays) {
		this.couponValidDays = couponValidDays;
	}
	public Date getPromoStarttime() {
		return promoStarttime;
	}
	public void setPromoStarttime(Date promoStarttime) {
		this.promoStarttime = promoStarttime;
	}
	public Date getPromoEndtime() {
		return promoEndtime;
	}
	public void setPromoEndtime(Date promoEndtime) {
		this.promoEndtime = promoEndtime;
	}
	public Integer getCouponPromoState() {
		return couponPromoState;
	}
	public void setCouponPromoState(Integer couponPromoState) {
		this.couponPromoState = couponPromoState;
	}
	public String getOpera() {
		return opera;
	}
	public void setOpera(String opera) {
		this.opera = opera;
	}
	public Date getOperaTime() {
		return operaTime;
	}
	public void setOperaTime(Date operaTime) {
		this.operaTime = operaTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getApplyDepartmentName() {
		return applyDepartmentName;
	}
	public void setApplyDepartmentName(String applyDepartmentName) {
		this.applyDepartmentName = applyDepartmentName;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Integer getCouponUsedAmount() {
		return couponUsedAmount;
	}
	public void setCouponUsedAmount(Integer couponUsedAmount) {
		this.couponUsedAmount = couponUsedAmount;
	}
	public Integer getOrderQuota() {
		return orderQuota;
	}
	public void setOrderQuota(Integer orderQuota) {
		this.orderQuota = orderQuota;
	}
	public Integer getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Integer couponAmount) {
		this.couponAmount = couponAmount;
	}
	public Integer getGrantDelayDays() {
		return grantDelayDays;
	}
	public void setGrantDelayDays(Integer grantDelayDays) {
		this.grantDelayDays = grantDelayDays;
	}
	public Integer getLimitCondition() {
		return limitCondition;
	}
	public void setLimitCondition(Integer limitCondition) {
		this.limitCondition = limitCondition;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	public Integer getIsSend() {
		return isSend;
	}
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
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
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public Integer getAmountRandom() {
		return amountRandom;
	}
	public void setAmountRandom(Integer amountRandom) {
		this.amountRandom = amountRandom;
	}
	public Integer getCouponAmountMin() {
		return couponAmountMin;
	}
	public void setCouponAmountMin(Integer couponAmountMin) {
		this.couponAmountMin = couponAmountMin;
	}
	public String getCouponRuleContent() {
		return couponRuleContent;
	}
	public void setCouponRuleContent(String couponRuleContent) {
		this.couponRuleContent = couponRuleContent;
	}
	public String getCouponDesc() {
		return couponDesc;
	}
	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}
}
