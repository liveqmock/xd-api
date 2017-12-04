package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品介绍
 * @author lichaoxiong
 *
 */
public class ItemDescription implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer itemId;

    private Integer descriptionType;//类型（1-产品特色;2-每日行程;3-出行说明;4-费用说明;5-预定须知）

    private String descriptionInfo;//商品介绍电脑版

    private String descriptionInfoApp;//商品介绍app版

    private Integer yn;

    private Date created;

    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(Integer descriptionType) {
        this.descriptionType = descriptionType;
    }

    public String getDescriptionInfo() {
        return descriptionInfo;
    }

    public void setDescriptionInfo(String descriptionInfo) {
        this.descriptionInfo = descriptionInfo;
    }

    public String getDescriptionInfoApp() {
        return descriptionInfoApp;
    }

    public void setDescriptionInfoApp(String descriptionInfoApp) {
        this.descriptionInfoApp = descriptionInfoApp;
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