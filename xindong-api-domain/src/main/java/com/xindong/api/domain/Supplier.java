package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 供应商
 * @author lichaoxiong
 *
 */
public class Supplier implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer supplierId;

    private String supplierName;

    private Integer supplierType;//供应商类型（1 默认）

    private Integer yn;

    private Date created;

    private Date modified;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Integer supplierType) {
        this.supplierType = supplierType;
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