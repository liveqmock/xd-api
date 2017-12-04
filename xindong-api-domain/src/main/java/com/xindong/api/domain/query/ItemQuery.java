package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;

public class ItemQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer itemId;//

    private String itemName;//

    private Integer itemType;//商品类型(1、自营  2、pop)

    private Integer destinationId1;//商品一级目的地

    private Integer destinationId2;//商品二级目的地

    private Integer subjectId1;//商品一级主题

    private Integer subjectId2;//商品二级主题

    private Integer dateId1;//商品二级时间

    private Integer dateId2;//商品二级时间

    private Integer originId1;//商品二级出发地

    private Integer originId2;//商品二级出发地

    private String destinationName1;//目的地名称1(后台商品列表用-冗余)

    private String destinationName2;//目的地名称2(后台商品列表用-冗余)

    private String subjectName1;//主题名称1(后台商品列表用-冗余)

    private String subjectName;//主题名称2(后台商品列表用-冗余)

    private String itemOriginName1;//出发地名称(前台产品详情用-冗余)

    private Integer heartScore;//心动指数

    private Integer qualityScore;//品质指数

    private Integer challengeScore;//挑战指数

    private Integer venderUserId;//商家id

    private Integer flagId;//旅游商品标签id

    private String supplierFlagId;//供应商编号

    private Integer supplierId;//外部产品编号

    private Integer itemStatus;//商品状态（0-新创建 1-上架、2-下架）

    private String itemImage;//商品主图

    private String itemImageMin;//商品主图小(列表页产品图)

    private Integer ifDeposit;//支付方式

    private Integer itemFlag;//商品标签(0:普通)

    private Integer usefulCount;//有赞数量

    private Integer forwordCount;//转发数量

    private String shareWebUrl;//分享url

    private String shareSummary;//分享简介

    private Integer flag;//首页推荐标识（0否1是已推荐）

    private String qrcodeUrl;//商品二维码地址

    private String qrcodeImgUrl;//商品二维码图片地址

    private Date onlineDate;//上线日期

    private Date offlineDate;//下线日期
    private Integer customstatus;//是否能定制（0不能1能）

    private String customdesc;//定制描述

    private Integer yn;//

    private Date created;//

    private Date modified;//
    
    private String destinationJson1;//目的地

    private String musicWebUrl;//音频地址
    
    private String musicWebAuthor;//音频简介
    
    private String h5ShareWebUrl;//H5视频地址
    
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

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getDestinationId1() {
        return destinationId1;
    }

    public void setDestinationId1(Integer destinationId1) {
        this.destinationId1 = destinationId1;
    }

    public Integer getDestinationId2() {
        return destinationId2;
    }

    public void setDestinationId2(Integer destinationId2) {
        this.destinationId2 = destinationId2;
    }

    public Integer getSubjectId1() {
        return subjectId1;
    }

    public void setSubjectId1(Integer subjectId1) {
        this.subjectId1 = subjectId1;
    }

    public Integer getSubjectId2() {
        return subjectId2;
    }

    public void setSubjectId2(Integer subjectId2) {
        this.subjectId2 = subjectId2;
    }

    public Integer getDateId1() {
        return dateId1;
    }

    public void setDateId1(Integer dateId1) {
        this.dateId1 = dateId1;
    }

    public Integer getDateId2() {
        return dateId2;
    }

    public void setDateId2(Integer dateId2) {
        this.dateId2 = dateId2;
    }

    public Integer getOriginId1() {
        return originId1;
    }

    public void setOriginId1(Integer originId1) {
        this.originId1 = originId1;
    }

    public Integer getOriginId2() {
        return originId2;
    }

    public void setOriginId2(Integer originId2) {
        this.originId2 = originId2;
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

    public Integer getHeartScore() {
        return heartScore;
    }

    public void setHeartScore(Integer heartScore) {
        this.heartScore = heartScore;
    }

    public Integer getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Integer qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Integer getChallengeScore() {
        return challengeScore;
    }

    public void setChallengeScore(Integer challengeScore) {
        this.challengeScore = challengeScore;
    }

    public Integer getVenderUserId() {
        return venderUserId;
    }

    public void setVenderUserId(Integer venderUserId) {
        this.venderUserId = venderUserId;
    }

    public Integer getFlagId() {
        return flagId;
    }

    public void setFlagId(Integer flagId) {
        this.flagId = flagId;
    }

    public String getSupplierFlagId() {
        return supplierFlagId;
    }

    public void setSupplierFlagId(String supplierFlagId) {
        this.supplierFlagId = supplierFlagId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
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

    public Integer getIfDeposit() {
        return ifDeposit;
    }

    public void setIfDeposit(Integer ifDeposit) {
        this.ifDeposit = ifDeposit;
    }

    public Integer getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(Integer itemFlag) {
        this.itemFlag = itemFlag;
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

    public String getShareWebUrl() {
        return shareWebUrl;
    }

    public void setShareWebUrl(String shareWebUrl) {
        this.shareWebUrl = shareWebUrl;
    }

    public String getShareSummary() {
        return shareSummary;
    }

    public void setShareSummary(String shareSummary) {
        this.shareSummary = shareSummary;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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

    public Date getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    public Date getOfflineDate() {
        return offlineDate;
    }

    public void setOfflineDate(Date offlineDate) {
        this.offlineDate = offlineDate;
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

	public String getDestinationJson1() {
		return destinationJson1;
	}

	public void setDestinationJson1(String destinationJson1) {
		this.destinationJson1 = destinationJson1;
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
    
}