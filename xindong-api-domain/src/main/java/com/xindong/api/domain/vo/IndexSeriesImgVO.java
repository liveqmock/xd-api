package com.xindong.api.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.xindong.api.domain.IndexSeriesImg;
import com.xindong.api.domain.Item;
/**
 * 首页系列详情
 * @author lichaoxiong
 *
 */
public class IndexSeriesImgVO {
	 private Integer type;//类型1-商品2-链接
	 private String imgUrl;//主图地址
	 private String linkUrl;//跳转地址
	 private Integer itemId;//商品编号
	 private String intro;//介绍
	 private String itemName;//商品名称
	 private String itemIntro1;//商品简介1(产品特色第一行)
	 private String itemIntro2;//商品简介1(产品特色第二行)
	 private String itemIntro3;//商品简介1(产品特色第三行)
	 private Integer usefulCount;//有赞数量
	 private String destinationS;//目的地。（多个#分割）
	 private Date skuStartDate;//最早出发时间
	 private BigDecimal  skuAdultPrice;//价格(最近出发时间的成人价格)
	 private String shareSummary; //商品表的小图
	 private Integer flagId;//是否为定制
	 private String promotionStr;//促销信息
	 private String couponStr;//优惠信息
	 
    public IndexSeriesImgVO() {
	}
    public IndexSeriesImgVO(IndexSeriesImg indexSeries,Item item) {
    	this.type =indexSeries.getType();
    	this.imgUrl =indexSeries.getImgUrl();
    	this.itemId =indexSeries.getItemId();
    	this.linkUrl =indexSeries.getLinkUrl();
    	this.intro =indexSeries.getIntro();
    	if(item !=null){
    		this.itemIntro1 =item.getItemIntro1();
        	this.itemIntro2 =item.getItemIntro2();
        	this.itemIntro3 =item.getItemIntro3();
        	this.usefulCount=item.getUsefulCount();
        	this.itemName =item.getItemName();
    	}
	}
    
    public IndexSeriesImgVO(IndexSeriesImg indexSeries, Item item, String promotionStr, String couponStr) {
    	this.type =indexSeries.getType();
    	this.imgUrl =indexSeries.getImgUrl();
    	this.itemId =indexSeries.getItemId();
    	this.linkUrl =indexSeries.getLinkUrl();
    	this.intro =indexSeries.getIntro();
    	if(item !=null){
    		this.itemIntro1 =item.getItemIntro1();
        	this.itemIntro2 =item.getItemIntro2();
        	this.itemIntro3 =item.getItemIntro3();
        	this.usefulCount=item.getUsefulCount();
        	this.itemName =item.getItemName();
    	}
     	this.promotionStr = promotionStr;
    	this.couponStr = couponStr;
	}
    
    public IndexSeriesImgVO(IndexSeriesImg indexSeries,Item item,String destinationS,Date skuStartDate,BigDecimal  skuAdultPrice,String shareSummary,Integer flagId) {
    	this.itemId =indexSeries.getItemId();
    	if(item !=null){
        	this.itemName =item.getItemName();
    	}
    	this.destinationS = destinationS;
    	this.skuStartDate = skuStartDate;
    	this.skuAdultPrice = skuAdultPrice;
    	this.shareSummary = shareSummary;
    	this.flagId = flagId;
	}
    
    public IndexSeriesImgVO(IndexSeriesImg indexSeries,Item item,String destinationS,Date skuStartDate,BigDecimal  skuAdultPrice,String shareSummary,Integer flagId,String promotionStr, String couponStr) {
    	this.itemId =indexSeries.getItemId();
    	if(item !=null){
        	this.itemName =item.getItemName();
        	this.itemIntro1 = item.getItemIntro1();
        	this.itemIntro2 = item.getItemIntro2();
        	this.itemIntro3 = item.getItemIntro3();
    	}
    	this.destinationS = destinationS;
    	this.skuStartDate = skuStartDate;
    	this.skuAdultPrice = skuAdultPrice;
    	this.shareSummary = shareSummary;
    	this.flagId = flagId;
    	this.promotionStr = promotionStr;
    	this.couponStr = couponStr;
	}
    
	public String getItemIntro1() {
		return itemIntro1;
	}
	public void setItemIntro1(String itemIntro1) {
		this.itemIntro1 = itemIntro1;
	}
	public String getItemIntro2() {
		return itemIntro2;
	}
	public void setItemIntro2(String itemIntro2) {
		this.itemIntro2 = itemIntro2;
	}
	public String getItemIntro3() {
		return itemIntro3;
	}
	public void setItemIntro3(String itemIntro3) {
		this.itemIntro3 = itemIntro3;
	}
	public Integer getUsefulCount() {
		return usefulCount;
	}
	public void setUsefulCount(Integer usefulCount) {
		this.usefulCount = usefulCount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
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
	public Date getSkuStartDate() {
		return skuStartDate;
	}
	public void setSkuStartDate(Date skuStartDate) {
		this.skuStartDate = skuStartDate;
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
	public Integer getFlagId() {
		return flagId;
	}
	public void setFlagId(Integer flagId) {
		this.flagId = flagId;
	}
	public String getPromotionStr() {
		return promotionStr;
	}
	public void setPromotionStr(String promotionStr) {
		this.promotionStr = promotionStr;
	}
	public String getCouponStr() {
		return couponStr;
	}
	public void setCouponStr(String couponStr) {
		this.couponStr = couponStr;
	}
}