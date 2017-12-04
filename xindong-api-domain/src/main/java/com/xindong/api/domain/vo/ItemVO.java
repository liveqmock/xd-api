package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.xindong.api.domain.TbCouponPromo;

public class ItemVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer itemId;//

    private String itemName;//

    private String itemIntro1;//商品简介1(产品特色第一行)

    private String itemIntro2;//商品简介1(产品特色第二行)

    private String itemIntro3;//商品简介1(产品特色第三行)

    private Integer itemType;//商品类型(1、自营  2、pop)

    private String destinationName1;//目的地名称1(后台商品列表用-冗余)

    private String destinationName2;//目的地名称2(后台商品列表用-冗余)

    private String subjectName1;//主题名称1(后台商品列表用-冗余)

    private String subjectName;//主题名称2(后台商品列表用-冗余)

    private String itemOriginName1;//出发地名称(前台产品详情用-冗余)

    private BigDecimal heartScore;//心动指数

    private BigDecimal qualityScore;//品质指数

    private BigDecimal challengeScore;//挑战指数

    private String itemImage;//商品主图

    private String itemImageMin;//商品主图小(列表页产品图)

    private Integer usefulCount;//有赞数量

    private Integer forwordCount;//转发数量

    private Integer customstatus;//是否能定制（0不能1能）

    private String customdesc;//定制描述
    private String supplierdesc;//供应商资质
    private Date skuStartDate;//最早出发时间
    private String itemTours;//旅游标签
    private String shareWebUrl;//视频地址
    private String musicWebUrl;//音频地址
    private String musicWebAuthor;//音频简历
    private String h5ShareWebUrl;//h5视频地址

    private String shareSummary;//分享简介
    private String qrcodeUrl;//商品二维码地址
    private String qrcodeImgUrl;//微信分享二维码地址
    private Integer mostNum;//订单最多购买人数

    private Integer leastNum;//订单最少购买人数
    private String promotionStr;//促销描述
    private List<TbCouponPromo> couponList;//优惠券集合
    
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
	public Integer getItemType() {
		return itemType;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	public String getDestinationName1() {
		return destinationName1;
	}
	public void setDestinationName1(String destinationName1) {
		this.destinationName1 = destinationName1;
	}
	public String getDestinationName2() {
		return destinationName2;
	}
	public void setDestinationName2(String destinationName2) {
		this.destinationName2 = destinationName2;
	}
	public String getSubjectName1() {
		return subjectName1;
	}
	public void setSubjectName1(String subjectName1) {
		this.subjectName1 = subjectName1;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getItemOriginName1() {
		return itemOriginName1;
	}
	public void setItemOriginName1(String itemOriginName1) {
		this.itemOriginName1 = itemOriginName1;
	}
	public BigDecimal getHeartScore() {
		return heartScore;
	}
	public void setHeartScore(BigDecimal heartScore) {
		this.heartScore = heartScore;
	}
	public BigDecimal getQualityScore() {
		return qualityScore;
	}
	public void setQualityScore(BigDecimal qualityScore) {
		this.qualityScore = qualityScore;
	}
	public BigDecimal getChallengeScore() {
		return challengeScore;
	}
	public void setChallengeScore(BigDecimal challengeScore) {
		this.challengeScore = challengeScore;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public String getItemImageMin() {
		return itemImageMin;
	}
	public void setItemImageMin(String itemImageMin) {
		this.itemImageMin = itemImageMin;
	}
	public Integer getUsefulCount() {
		return usefulCount;
	}
	public void setUsefulCount(Integer usefulCount) {
		this.usefulCount = usefulCount;
	}
	public Integer getForwordCount() {
		return forwordCount;
	}
	public void setForwordCount(Integer forwordCount) {
		this.forwordCount = forwordCount;
	}
	public Integer getCustomstatus() {
		return customstatus;
	}
	public void setCustomstatus(Integer customstatus) {
		this.customstatus = customstatus;
	}
	public String getCustomdesc() {
		return customdesc;
	}
	public void setCustomdesc(String customdesc) {
		this.customdesc = customdesc;
	}
	public String getSupplierdesc() {
		return supplierdesc;
	}
	public void setSupplierdesc(String supplierdesc) {
		this.supplierdesc = supplierdesc;
	}
	public Date getSkuStartDate() {
		return skuStartDate;
	}
	public void setSkuStartDate(Date skuStartDate) {
		this.skuStartDate = skuStartDate;
	}
	public String getItemTours() {
		return itemTours;
	}
	public void setItemTours(String itemTours) {
		this.itemTours = itemTours;
	}
	public String getShareWebUrl() {
		return shareWebUrl;
	}
	public void setShareWebUrl(String shareWebUrl) {
		this.shareWebUrl = shareWebUrl;
	}
	public String getMusicWebUrl() {
		return musicWebUrl;
	}
	public void setMusicWebUrl(String musicWebUrl) {
		this.musicWebUrl = musicWebUrl;
	}
	public String getMusicWebAuthor() {
		return musicWebAuthor;
	}
	public void setMusicWebAuthor(String musicWebAuthor) {
		this.musicWebAuthor = musicWebAuthor;
	}
	public String getH5ShareWebUrl() {
		return h5ShareWebUrl;
	}
	public void setH5ShareWebUrl(String h5ShareWebUrl) {
		this.h5ShareWebUrl = h5ShareWebUrl;
	}
	public String getShareSummary() {
		return shareSummary;
	}
	public void setShareSummary(String shareSummary) {
		this.shareSummary = shareSummary;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	public String getQrcodeImgUrl() {
		return qrcodeImgUrl;
	}
	public void setQrcodeImgUrl(String qrcodeImgUrl) {
		this.qrcodeImgUrl = qrcodeImgUrl;
	}
	public Integer getMostNum() {
		return mostNum;
	}
	public void setMostNum(Integer mostNum) {
		this.mostNum = mostNum;
	}
	public Integer getLeastNum() {
		return leastNum;
	}
	public void setLeastNum(Integer leastNum) {
		this.leastNum = leastNum;
	}
	public String getPromotionStr() {
		return promotionStr;
	}
	public void setPromotionStr(String promotionStr) {
		this.promotionStr = promotionStr;
	}
	public List<TbCouponPromo> getCouponList() {
		return couponList;
	}
	public void setCouponList(List<TbCouponPromo> couponList) {
		this.couponList = couponList;
	}
}