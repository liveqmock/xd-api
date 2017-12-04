package com.xindong.api.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * 地址库
 *
 */
public class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 地址ID */
    private Integer addressId;
    
    /** 地址名称 */
    private String addressName;

    /** 地址等级 */
    private Integer addressLevel;

    /** 地址父ID */
    private Integer addressFid;

    /** 创建时间 */
    private Date created;

    /** 修改时间 */
    private Date modified;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Integer getAddressLevel() {
        return addressLevel;
    }

    public void setAddressLevel(Integer addressLevel) {
        this.addressLevel = addressLevel;
    }

    public Integer getAddressFid() {
        return addressFid;
    }

    public void setAddressFid(Integer addressFid) {
        this.addressFid = addressFid;
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