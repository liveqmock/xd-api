package com.xindong.api.domain.query;

import java.io.Serializable;

public class BusinessUserExtQuery extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;

    /** 用户ID=商家用户ID */
    private Integer userId;

    /** 店铺名称 */
    private String shopName;

    /** 店铺状态 */
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}