package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author lichaoxiong
 *
 */
public class OrderInfoQuery extends BaseSearchForMysqlVo   implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer skuId;//
	private Integer itemId;
	private Integer orderId;
	private Integer orderStatus;//订单状态（0-待库存确认  1-待付款 2-已支付 3- 4-待出行 5-已取消 6-已删除 7已完成）
    private Integer orderType;//订单类型（1旅行订单;2咨询订单  ）
    private Integer venderUserId;//
    private Integer userId;//
    private Date orderTime;//下单时间
    private Date sendOutTime;//下单时间
    private Date finishTime;//订单完成时间
    private Date payTime;//支付时间
    private Integer payType;//支付方式支付方式（0alipay 1银联 2 财付通3线下）
    private Integer isComment;//0未评价1已评价2已追加评价
    private String contactName;//联系人名称
    private String contactMobile;//联系人手机
    private String contactEmail;//联系人email
    private String personalizationRemark;//个性化其他需求
    private String orderRemark;//订单备注
    private Integer yn;//
    private Integer fromWhere;//订单来源（0-h5  1-android 2-ios 3-pc）
    public void setFromWhere(Integer fromWhere) {
		this.fromWhere = fromWhere;
	}
    public Integer getFromWhere() {
		return fromWhere;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getSkuId() {
		return skuId;
	}
	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
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
    
}