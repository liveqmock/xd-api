package com.xindong.api.domain;


import java.io.Serializable;
import java.util.Date;

import com.xindong.api.domain.enumtype.BusinessUserType;

/**
 * 商家扩展信息
 *
 */
public class BusinessUserExt implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Integer id;

    /** 用户ID=商家用户ID */
    private Integer userId;

    /** 店铺名称 */
    private String shopName;
    
    /** 店铺图片 */
    private String shopImage;

    /** 店铺状态 */
    private Integer status;

    /** 是否有保证金 */
    private Integer ifHaveDeposit;
    
    /** 保证金 */
    private Integer deposit;
    
    /** 1 普通商家 2 认证商家 3 VIP商家 */
    private Integer businessType;
    
    /** 供货地省份 */
    private Integer supplyProvince;
    
    /** 供货地市区 */
    private Integer supplyCity;
    
    /** 供货地县 */
    private Integer supplyCounty;
    
    /** 供货地省份名称 */
    private String supplyProvinceName;
    
    /** 供货地市区名称*/
    private String supplyCityName;
    
    /** 供货地县名称*/
    private String supplyCountyName;
    
     /** 手机号 */
    private String mobile;

    /** 创建时间 */
    private Date created;

    /** 修改时间 */
    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImage() {
		return shopImage;
	}

	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	public Integer getStatus() {
		return status;
	}
    
    public void setStatus(Integer status) {
		this.status = status;
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

    public Integer getIfHaveDeposit() {
        return ifHaveDeposit;
    }

    public void setIfHaveDeposit(Integer ifHaveDeposit) {
        this.ifHaveDeposit = ifHaveDeposit;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getBusinessType() {
        return businessType;
    }
     public String getBusinessTypeName() {
        if( null == businessType ){
            return null;
        }
        return BusinessUserType.getValue(businessType);
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getSupplyProvince() {
        return supplyProvince;
    }

    public void setSupplyProvince(Integer supplyProvince) {
        this.supplyProvince = supplyProvince;
    }

    public Integer getSupplyCity() {
        return supplyCity;
    }

    public void setSupplyCity(Integer supplyCity) {
        this.supplyCity = supplyCity;
    }

    public Integer getSupplyCounty() {
        return supplyCounty;
    }

    public void setSupplyCounty(Integer supplyCounty) {
        this.supplyCounty = supplyCounty;
    }

    public String getSupplyProvinceName() {
        return supplyProvinceName;
    }

    public void setSupplyProvinceName(String supplyProvinceName) {
        this.supplyProvinceName = supplyProvinceName;
    }

    public String getSupplyCityName() {
        return supplyCityName;
    }

    public void setSupplyCityName(String supplyCityName) {
        this.supplyCityName = supplyCityName;
    }

    public String getSupplyCountyName() {
        return supplyCountyName;
    }

    public void setSupplyCountyName(String supplyCountyName) {
        this.supplyCountyName = supplyCountyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "BusinessUserExt{" +
                "id=" + id +
                ", userId=" + userId +
                ", shopName='" + shopName + '\'' +
                ", status=" + status +
                ", ifHaveDeposit=" + ifHaveDeposit +
                ", deposit=" + deposit +
                ", businessType=" + businessType +
                ", businessTypeName=" +getBusinessTypeName()+
                ", supplyProvince=" + supplyProvince +
                ", supplyCity=" + supplyCity +
                ", supplyCounty=" + supplyCounty +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}