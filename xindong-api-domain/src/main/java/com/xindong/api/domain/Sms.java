package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;

public class Sms implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    private String mobile;

    private String content;

    private Integer status;
    private Date created;

    private Date modified;
    private Integer type;//模板（1,2,3）
    public void setType(Integer type) {
		this.type = type;
	}
    public Integer getType() {
		return type;
	}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}