package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品旅游标签
 * @author lichaoxiong
 *
 */
public class ItemTourFlag implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer itemId;

    private Integer flagId;

    private String flagName;//标签名称

    private Integer flagType;//标签类型（1 旅游商品标签 2供应商资质标签）

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

    public Integer getFlagId() {
        return flagId;
    }

    public void setFlagId(Integer flagId) {
        this.flagId = flagId;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    public Integer getFlagType() {
        return flagType;
    }

    public void setFlagType(Integer flagType) {
        this.flagType = flagType;
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