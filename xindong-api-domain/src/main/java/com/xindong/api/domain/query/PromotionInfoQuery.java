package com.xindong.api.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PromotionInfoQuery extends BaseSearchForMysqlVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 促销ID */
    private Integer promotionId;

    /** 促销名称 */
    private String promotionName;
    
    /** 商品ID */
    private Integer itemId;

    /** 促销类型 */
    private Integer promotionType;

    /** 促销起始时间 */
    private Date startTime;

    /** 促销截至时间 */
    private Date endTime;

    /** 最小购买数量 */
    private Integer purchaseNumberMin;

    /** 最大购买数量 */
    private Integer purchaseNumberMax;

    /** 促销库存数量 */
    private Integer promotionStock;

    /** 促销规则 */
    private String promotionRule;

    /** 促销状态 */
    private Integer promotionStatus;

    /** 有效性 */
    private Integer yn;

    /** 创建时间 */
    private Date created;

    /** 修改时间 */
    private Date modified;
    
    private String bakFirst;

    private Integer bakSecond;
    
    private Integer promotionObj;//促销对象(0-全部;1-成人;2-儿童;)
    private Integer promotionForm;//促销形式(1、商品促销；sku促销)
    private BigDecimal promotionValue;//促销值
    
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getPromotionType() {
		return promotionType;
	}
	public void setPromotionType(Integer promotionType) {
		this.promotionType = promotionType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getPurchaseNumberMin() {
		return purchaseNumberMin;
	}
	public void setPurchaseNumberMin(Integer purchaseNumberMin) {
		this.purchaseNumberMin = purchaseNumberMin;
	}
	public Integer getPurchaseNumberMax() {
		return purchaseNumberMax;
	}
	public void setPurchaseNumberMax(Integer purchaseNumberMax) {
		this.purchaseNumberMax = purchaseNumberMax;
	}
	public Integer getPromotionStock() {
		return promotionStock;
	}
	public void setPromotionStock(Integer promotionStock) {
		this.promotionStock = promotionStock;
	}
	public String getPromotionRule() {
		return promotionRule;
	}
	public void setPromotionRule(String promotionRule) {
		this.promotionRule = promotionRule;
	}
	public Integer getPromotionStatus() {
		return promotionStatus;
	}
	public void setPromotionStatus(Integer promotionStatus) {
		this.promotionStatus = promotionStatus;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
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
	public Integer getPromotionObj() {
		return promotionObj;
	}
	public void setPromotionObj(Integer promotionObj) {
		this.promotionObj = promotionObj;
	}
	public Integer getPromotionForm() {
		return promotionForm;
	}
	public void setPromotionForm(Integer promotionForm) {
		this.promotionForm = promotionForm;
	}
	public BigDecimal getPromotionValue() {
		return promotionValue;
	}
	public void setPromotionValue(BigDecimal promotionValue) {
		this.promotionValue = promotionValue;
	}
}