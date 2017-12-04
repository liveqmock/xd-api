package com.xindong.api.domain;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户评论
 * @author lichaoxiong
 *
 */
public class Comment {
    private Integer id;

    private Integer skuId;//

    private Integer itemId;//商品编号

    private String itemName;//商品名称（冗余）

    private Integer userId;//评论用户编号

    private String userName;//用户名称

    private String title;//评论标题

    private String content;//评论内容

    private Date commentTime;//评价时间

    private Integer score;//5分：好评、3-4分：中评、1-2分：差评、其他评论默认是5分

    private Integer type;//评价类型1-商品2-订单

    private Integer businessId;//业务编号（订单编号等）

    private Integer topped;//（1置顶0未置顶-1沉低）

    private Integer status;//0：待审核 ，1：审核通过， 2：审核不通过)

    private Integer deleted;//(0：不显示，1：显示)

    private Integer integral;//评论获得积分数量

    private BigDecimal balanceAmount;//评论获得余额数量

    private String ip;//

    private String bakFirst;

    private Integer bakSecond;

    private Integer yn;

    private Date created;

    private Date modified;

    private String imgOne;//评论图片1

    private String imgTwo;//评论图片2

    private String imgThree;//评论图片3

    private String imgFour;//评论图片4
    
    public String getImgOne() {
		return imgOne;
	}

	public void setImgOne(String imgOne) {
		this.imgOne = imgOne;
	}

	public String getImgTwo() {
		return imgTwo;
	}

	public void setImgTwo(String imgTwo) {
		this.imgTwo = imgTwo;
	}

	public String getImgThree() {
		return imgThree;
	}

	public void setImgThree(String imgThree) {
		this.imgThree = imgThree;
	}

	public String getImgFour() {
		return imgFour;
	}

	public void setImgFour(String imgFour) {
		this.imgFour = imgFour;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getTopped() {
        return topped;
    }

    public void setTopped(Integer topped) {
        this.topped = topped;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
}