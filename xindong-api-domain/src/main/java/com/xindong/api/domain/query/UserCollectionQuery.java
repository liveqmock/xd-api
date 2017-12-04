package com.xindong.api.domain.query;

import java.io.Serializable;
/**
 * 用户收藏信息
 * @author lichaoxiong
 *
 */
public class UserCollectionQuery  extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer collectionId;

    private Integer userId;//

    private Integer type;//收藏类型1-商品

    private Integer businessId;//业务ID

    private Integer itemId;

    private Integer yn;

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

}