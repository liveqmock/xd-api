package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 订单多个备注信息
 * @author lichaoxiong
 *
 */
public class OrderRemark implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer orderRemarkId;

    private Integer orderId;

    private Integer remarkType;//备注类型(1:普通发票;)

    private String otherTypeName;//其他名称

    private String remarkContent;//备注内容

    private Integer operaUserId;//操作用户id

    private Date created;

    private Date modified;

    public Integer getOrderRemarkId() {
        return orderRemarkId;
    }

    public void setOrderRemarkId(Integer orderRemarkId) {
        this.orderRemarkId = orderRemarkId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(Integer remarkType) {
        this.remarkType = remarkType;
    }

    public String getOtherTypeName() {
        return otherTypeName;
    }

    public void setOtherTypeName(String otherTypeName) {
        this.otherTypeName = otherTypeName;
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }

    public Integer getOperaUserId() {
        return operaUserId;
    }

    public void setOperaUserId(Integer operaUserId) {
        this.operaUserId = operaUserId;
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