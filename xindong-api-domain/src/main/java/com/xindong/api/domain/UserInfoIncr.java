package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 第三方用户信息扩展表
 * @author lichaoxiong
 *
 */
public class UserInfoIncr implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;//

    private Integer userId;//用户编号

    private String name;//第三方平台用户名称

    private String headImageurl;//用户头像

    private Integer sex;//性别1男2女

    private Integer fromWhere;//来源(0-h5  1-android 2-ios 3-pc)'  null default '3'

    private Integer yn;//

    private Date created;//

    private Date modified;//

    private Integer type;//第三方平台类型(1微信;2qq 3新浪微博)

    private String openid;//第三方平台主键

    private String unionid;//微信用户唯一主键

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(Integer fromWhere) {
        this.fromWhere = fromWhere;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}