package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 联系人信息库表-仓库
 * @author lichaoxiong
 *
 */
public class ContactGroup implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer contactId;

    private Integer userId;

    private String contactName;//联系人名称

    private String contactMobile;//联系人手机

    private String contactEmail;//联系人email

    private Integer defaultContactFlag;//是否默认联系人(1是0否)

    private Integer yn;

    private Date created;

    private Date modified;

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Integer getDefaultContactFlag() {
        return defaultContactFlag;
    }

    public void setDefaultContactFlag(Integer defaultContactFlag) {
        this.defaultContactFlag = defaultContactFlag;
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