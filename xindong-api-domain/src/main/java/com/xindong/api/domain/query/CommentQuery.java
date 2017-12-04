package com.xindong.api.domain.query;

import java.io.Serializable;

public class CommentQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	/** 评价ID */
	private Integer id;
	
	/** 属性ID */
	private Integer skuId;
	
	/** 商品ID */
	private Integer itemId;
	
	/** 用户ID */
	private Integer userId;
	
	/** 审核状态 */
	private Integer status;
	private Integer yn;
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
