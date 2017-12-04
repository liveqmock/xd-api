package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 旅游出发地
 * @author lichaoxiong
 *
 */
public class TourOrigin implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer originId;

    private String originName;

    private Integer originLevel;

    private Integer parentOriginId;

    private Integer yn;

    private Date created;

    private Date modified;

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public Integer getOriginLevel() {
        return originLevel;
    }

    public void setOriginLevel(Integer originLevel) {
        this.originLevel = originLevel;
    }

    public Integer getParentOriginId() {
        return parentOriginId;
    }

    public void setParentOriginId(Integer parentOriginId) {
        this.parentOriginId = parentOriginId;
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