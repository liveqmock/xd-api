package com.xindong.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 支付明细信息
 * @author lichaoxiong
 *
 */
public class PaymentInfo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer paymentId;

    private Integer orderId;

    private Integer orderPayType;//订单款项类型（1-全额）

    private Integer payType;//支付方式（0alipay 1银联 2 财付通3线下）

    private Integer paymentInfoType;//支付信息类型(1、支付信息   2、支付成功确认信息)

    private BigDecimal paymentMoney;//支付金额

    private String paymentNumber;//第三方支付单号

    private String busiPartner;//业务类型 (备用0旅行) 银联交易类型

    private String orderTime;//支付订单时间

    private Integer validOrder;//订单有效时间

    private String settleDate;//清算日期

    private String bankName;//银行名称

    private String bankCode;//银行编号

    private Date created;//

    private Date modified;

    private String openid;

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(Integer orderPayType) {
        this.orderPayType = orderPayType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPaymentInfoType() {
        return paymentInfoType;
    }

    public void setPaymentInfoType(Integer paymentInfoType) {
        this.paymentInfoType = paymentInfoType;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getBusiPartner() {
        return busiPartner;
    }

    public void setBusiPartner(String busiPartner) {
        this.busiPartner = busiPartner;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getValidOrder() {
        return validOrder;
    }

    public void setValidOrder(Integer validOrder) {
        this.validOrder = validOrder;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}