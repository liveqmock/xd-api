package com.xindong.api.domain.vo;

import java.io.Serializable;

public class RecommendItemVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private String imageUrl;//图片地址
    private Integer itemId;//编号 
    private String itemName;//商品名称

    public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}