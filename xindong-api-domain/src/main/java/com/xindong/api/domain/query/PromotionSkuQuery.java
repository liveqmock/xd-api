package com.xindong.api.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PromotionSkuQuery  extends BaseSearchForMysqlVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 自增ID */
	private Integer id;
	
	/** 促销ID */
	private Integer promotionId;
	
	/** SKU_ID */
	private Integer skuId;
	/** itemId */
	private Integer itemId;
	
	/** 直降价格 */
	private BigDecimal deduction_price;
	
	private Date modified;
	
	private Date created;

	private Integer yn;
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}


	public BigDecimal getDeduction_price() {
		return deduction_price;
	}

	public void setDeduction_price(BigDecimal deduction_price) {
		this.deduction_price = deduction_price;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}
	
	
}
