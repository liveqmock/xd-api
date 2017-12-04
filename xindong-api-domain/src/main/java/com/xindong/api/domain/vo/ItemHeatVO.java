package com.xindong.api.domain.vo;

import java.math.BigDecimal;

public class ItemHeatVO {
	
	private Integer itemId;//商品编号
	
	private String itemName;//商品名称
	
	private String destinationS;//目的地。（多个#分割）
	
	private BigDecimal  skuAdultPrice;//价格(最近出发时间的成人价格)
	
	private String shareSummary; //商品表的小图
	
	private Integer browseCount; //商品浏览数量

	public ItemHeatVO(){}
	
	public ItemHeatVO(Integer itemId, String itemName, String destinationS,BigDecimal skuAdultPrice, String shareSummary, Integer browseCount) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.destinationS = destinationS;
		this.skuAdultPrice = skuAdultPrice;
		this.shareSummary = shareSummary;
		this.browseCount = browseCount;
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

	public String getDestinationS() {
		return destinationS;
	}

	public void setDestinationS(String destinationS) {
		this.destinationS = destinationS;
	}

	public BigDecimal getSkuAdultPrice() {
		return skuAdultPrice;
	}

	public void setSkuAdultPrice(BigDecimal skuAdultPrice) {
		this.skuAdultPrice = skuAdultPrice;
	}

	public String getShareSummary() {
		return shareSummary;
	}

	public void setShareSummary(String shareSummary) {
		this.shareSummary = shareSummary;
	}

	public Integer getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}
	
	
}
