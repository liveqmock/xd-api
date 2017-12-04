package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;
/**
 * 订单支付表
 * @author lichaoxiong
 *
 */
public class OrderPaymentQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer paymentId;

    private Integer orderId;

    private String orderPaymentNo;//支付号=订单编号-主键

    private String thirdPaymentNo;//第三方支付编号

    private String openid;//第三方支付编号

    private Integer fromWhere;//来源(0-h5  1-android 2-ios 3-pc)

    private Integer payType;//支付方式（0-alipay 1-银联 2-微信公众号 3-先下支付 4-微信wap 5-微信扫码）

    private Date created;

    private Date modify;

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

    public String getOrderPaymentNo() {
        return orderPaymentNo;
    }

    public void setOrderPaymentNo(String orderPaymentNo) {
        this.orderPaymentNo = orderPaymentNo;
    }

    public String getThirdPaymentNo() {
        return thirdPaymentNo;
    }

    public void setThirdPaymentNo(String thirdPaymentNo) {
        this.thirdPaymentNo = thirdPaymentNo;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModify() {
        return modify;
    }

    public void setModify(Date modify) {
        this.modify = modify;
    }
}