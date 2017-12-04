package com.xindong.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @author lichaoxiong
 *
 */
public class OrderInfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer skuId;//
	
	private Integer orderId;

	private Integer orderStatus;//订单状态（0-待库存确认  1-待付款 2-已支付 3- 4-待出行 5-已取消 6-已删除 7已完成）

    private Date startDate;//出发时间

    private Date returnDate;//返程时间
    
    private String startDateStr;//出发时间

    private String returnDateStr;//返程时间
    
    private Integer itemId;//

    private String itemName;//

    private String itemImage;//商品主图

    private String originName;//出发城市

    private Integer orderType;//订单类型（1旅行订单;2咨询订单  ）

    private Integer venderUserId;//

    private Integer userId;//

    private BigDecimal skuAdultPrice;//sku成人价

    private BigDecimal skuChildrenPrice;//sku儿童价

    private Integer adultNum;//出行成人数量

    private Integer childrenNum;//出行儿童数量

    private Date orderTime;//下单时间

    private Date sendOutTime;//下单时间

    private Date finishTime;//订单完成时间

    private Date payTime;//支付时间

    private String ip;//

    private Integer lockStatus;////前序状态 修改订单状态前的订单状态

    private String lockReason;//

    private Integer fromWhere;//来源(0-h5  1-android 2-ios 3-pc)

    private Integer payType;//支付方式（0alipay 1银联 2 财付通3线下）

    private Integer isComment;//0未评价1已评价2已追加评价

    private BigDecimal orderMoney;//订单金额

    private BigDecimal discountMoney;//优惠金额

    private BigDecimal oughtPayMoney;//应付金额=总价=订单金额-优惠金额

    private BigDecimal payMoney;//实付金额

    private BigDecimal discountCouponMoney;//实付金额

    private BigDecimal integralMoney;//积分优惠金额

    private Integer balanceId;//旅行人群（1家庭2商务3朋友）

    private Date balanceDate;//废弃

    private String contactName;//联系人名称

    private String contactMobile;//联系人手机

    private String contactEmail;//联系人email

    private String personalizationRemark;//个性化其他需求

    private String orderRemark;//订单备注

    private Integer yn;//

    private Date created;//

    private Date modified;//
    
    private BigDecimal skuRoomsPrice;//单房差价格

    private Integer skuRoomsNum;//单房差人数
    
    private BigDecimal balanceMoney;//使用金额

    private String promotionStr; //促销信息
    
    public void setBalanceMoney(BigDecimal balanceMoney) {
		this.balanceMoney = balanceMoney;
	}
    public BigDecimal getBalanceMoney() {
		return balanceMoney;
	}
    public BigDecimal getSkuRoomsPrice() {
		return skuRoomsPrice;
	}
	public void setSkuRoomsPrice(BigDecimal skuRoomsPrice) {
		this.skuRoomsPrice = skuRoomsPrice;
	}
	public Integer getSkuRoomsNum() {
		return skuRoomsNum;
	}
	public void setSkuRoomsNum(Integer skuRoomsNum) {
		this.skuRoomsNum = skuRoomsNum;
	}
	public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
    public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getReturnDateStr() {
		return returnDateStr;
	}

	public void setReturnDateStr(String returnDateStr) {
		this.returnDateStr = returnDateStr;
	}

	public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
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

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getVenderUserId() {
        return venderUserId;
    }

    public void setVenderUserId(Integer venderUserId) {
        this.venderUserId = venderUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getSkuAdultPrice() {
        return skuAdultPrice;
    }

    public void setSkuAdultPrice(BigDecimal skuAdultPrice) {
        this.skuAdultPrice = skuAdultPrice;
    }

    public BigDecimal getSkuChildrenPrice() {
        return skuChildrenPrice;
    }

    public void setSkuChildrenPrice(BigDecimal skuChildrenPrice) {
        this.skuChildrenPrice = skuChildrenPrice;
    }

    public Integer getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(Integer adultNum) {
        this.adultNum = adultNum;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getSendOutTime() {
        return sendOutTime;
    }

    public void setSendOutTime(Date sendOutTime) {
        this.sendOutTime = sendOutTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public Integer getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(Integer fromWhere) {
        this.fromWhere = fromWhere;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getOughtPayMoney() {
        return oughtPayMoney;
    }

    public void setOughtPayMoney(BigDecimal oughtPayMoney) {
        this.oughtPayMoney = oughtPayMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getDiscountCouponMoney() {
        return discountCouponMoney;
    }

    public void setDiscountCouponMoney(BigDecimal discountCouponMoney) {
        this.discountCouponMoney = discountCouponMoney;
    }

    public BigDecimal getIntegralMoney() {
        return integralMoney;
    }

    public void setIntegralMoney(BigDecimal integralMoney) {
        this.integralMoney = integralMoney;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPersonalizationRemark() {
        return personalizationRemark;
    }

    public void setPersonalizationRemark(String personalizationRemark) {
        this.personalizationRemark = personalizationRemark;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPromotionStr() {
		return promotionStr;
	}
	public void setPromotionStr(String promotionStr) {
		this.promotionStr = promotionStr;
	}
    
    
}