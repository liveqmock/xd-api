package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.xindong.api.domain.OrderInfo;

public class OrderInfoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Date startDate;//出发日期
	private Date returnDate;//返程时间
	private String originName;//出发城市
	private Integer orderType;//订单类型（1旅行订单;2咨询订单  ）
	private Integer orderStatus;//订单状态（0-待库存确认  1-待付款 2-已支付 3- 4-待出行 5-已取消 6-已删除 7已完成）
	private String orderStatusStr;//订单状态描述
	private Integer orderId;//
	private BigDecimal orderMoney;//订单金额
	private Date residualTime;//订单取消时间
	private Date orderTime;//下单时间
	private Integer isComment;//0未评价1已评价2已追加评价
	private String itemImage;//商品主图
	private Integer itemId;//
	private String itemName;//
	private String payTypeStr;//支付方式描述
	private Integer payType;//支付方式
	private BigDecimal skuAdultPrice;//成人价格
	private BigDecimal skuChildrenPrice;//儿童价格
	private Integer adultNum;//成人数量
	private Integer childrenNum;//儿童数量
	private Date payTime;//支付时间
	private String travelCrowd;//旅行人群
	private String contactName;//联系人名称
    private String contactMobile;//联系人手机
    private String contactEmail;//联系人email
    private String personalizationRemark;//个性化其他需求
    private BigDecimal skuRoomsPrice;//单房差价格

    private Integer skuRoomsNum;//单房差人数
    private BigDecimal balanceMoney;//使用余额
    private BigDecimal oughtPayMoney;//应付金额=总价=订单金额-优惠金额
    private BigDecimal payMoney;//实付金额
    private BigDecimal discountCouponMoney;//优惠券金额
    private BigDecimal discountMoney;//促销金额
    private Date currentDate;//当前时间
    
	public OrderInfoVO() {}
	public OrderInfoVO(OrderInfo info) {
		this.isComment =info.getIsComment();
		this.itemId =info.getItemId();
		this.itemImage= info.getItemImage();
		this.itemName =info.getItemName();
		this.orderId =info.getOrderId();
		this.orderMoney=info.getOrderMoney();
		this.orderStatus=info.getOrderStatus();
		this.orderTime =info.getOrderTime();
		this.orderType =info.getOrderType();
		this.originName =info.getOriginName();
		this.payType =info.getPayType();
//		this.returnDate =info.getReturnDate();
//		this.startDate =info.getStartDate();
		this.skuAdultPrice =info.getSkuAdultPrice();
		this.skuChildrenPrice =info.getSkuChildrenPrice();
		this.adultNum =info.getAdultNum();
		this.childrenNum =info.getChildrenNum();
		this.payTime =info.getPayTime();
		this.contactEmail=info.getContactEmail();
		this.contactMobile=info.getContactMobile();
		this.contactName =info.getContactName();
		this.personalizationRemark =info.getPersonalizationRemark();
		this.skuRoomsPrice=info.getSkuRoomsPrice();
		this.skuRoomsNum =info.getSkuRoomsNum();
		this.balanceMoney =info.getBalanceMoney();
		this.payMoney =info.getPayMoney();
		this.oughtPayMoney =info.getOughtPayMoney();
		this.discountCouponMoney =info.getDiscountCouponMoney();
		this.discountMoney=info.getDiscountMoney();
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}
	public BigDecimal getDiscountMoney() {
		return discountMoney;
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
	public void setPersonalizationRemark(String personalizationRemark) {
		this.personalizationRemark = personalizationRemark;
	}
	public String getPersonalizationRemark() {
		return personalizationRemark;
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
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatusStr() {
		return orderStatusStr;
	}
	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	public Date getResidualTime() {
		return residualTime;
	}
	public void setResidualTime(Date residualTime) {
		this.residualTime = residualTime;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Integer getIsComment() {
		return isComment;
	}
	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
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
	public String getPayTypeStr() {
		return payTypeStr;
	}
	public void setPayTypeStr(String payTypeStr) {
		this.payTypeStr = payTypeStr;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
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
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getTravelCrowd() {
		return travelCrowd;
	}
	public void setTravelCrowd(String travelCrowd) {
		this.travelCrowd = travelCrowd;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
    
}