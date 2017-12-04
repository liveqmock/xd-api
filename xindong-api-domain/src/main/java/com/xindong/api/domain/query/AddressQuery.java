package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;

public class AddressQuery extends BaseSearchForMysqlVo implements Serializable{
	
	/**
	 * 地址信息
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

}