package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 订单发票
 * @author lichaoxiong
 *
 */
public class OrderInvoice implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer invoiceId;

    private Integer orderId;

    private Integer invoiceType;//发票类型(1:普通发票;)

    private String invoiceTitle;//发票抬头

    private String invoiceContent;//发票内容

    private Integer invoiceStatus;//发票状态(1:未开;2已开;)

    private Date created;

    private Date modified;

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
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