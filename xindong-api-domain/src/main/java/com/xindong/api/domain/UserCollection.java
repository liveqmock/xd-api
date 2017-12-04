package com.xindong.api.domain;

import java.util.Date;
/**
 * 用户收藏信息
 * @author lichaoxiong
 *
 */
public class UserCollection {
    private Integer collectionId;

    private Integer userId;

    private Integer type;

    private Integer businessId;

    private Integer itemId;

    private String bakFirst;

    private Integer bakSecond;

    private Integer yn;

    private Date created;

    private Date modified;

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

    public String getBakFirst() {
        return bakFirst;
    }

    public void setBakFirst(String bakFirst) {
        this.bakFirst = bakFirst;
    }

    public Integer getBakSecond() {
        return bakSecond;
    }

    public void setBakSecond(Integer bakSecond) {
        this.bakSecond = bakSecond;
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