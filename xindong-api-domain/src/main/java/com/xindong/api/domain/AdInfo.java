package com.xindong.api.domain;

import java.util.Date;
/**
 * 广告
 * @author lichaoxiong
 *
 */
public class AdInfo {
    private Integer id;

    private Integer adType;//类型1-首页

    private Integer sortNum;//位置排序

    private String adMainImgUrl;//主图地址

    private String adUrl;//跳转地址

    private Integer state;//状态（1待上线2已上线3已下线）

    private String adName;//广告名称

    private String adIntro;//广告介绍

    private String adIntro1;//广告语1

    private String adIntro2;//广告语2

    private String adIntro3;//广告语3

    private String adIntro4;//广告语4

    private String adIntro5;//广告语5

    private String adDesc;//广告描述

    private String bakFirst;//

    private Integer bakSecond;//0跳转地址1视频地址

    private Integer yn;

    private Date created;

    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getAdMainImgUrl() {
        return adMainImgUrl;
    }

    public void setAdMainImgUrl(String adMainImgUrl) {
        this.adMainImgUrl = adMainImgUrl;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdIntro() {
        return adIntro;
    }

    public void setAdIntro(String adIntro) {
        this.adIntro = adIntro;
    }

    public String getAdIntro1() {
        return adIntro1;
    }

    public void setAdIntro1(String adIntro1) {
        this.adIntro1 = adIntro1;
    }

    public String getAdIntro2() {
        return adIntro2;
    }

    public void setAdIntro2(String adIntro2) {
        this.adIntro2 = adIntro2;
    }

    public String getAdIntro3() {
        return adIntro3;
    }

    public void setAdIntro3(String adIntro3) {
        this.adIntro3 = adIntro3;
    }

    public String getAdIntro4() {
        return adIntro4;
    }

    public void setAdIntro4(String adIntro4) {
        this.adIntro4 = adIntro4;
    }

    public String getAdIntro5() {
        return adIntro5;
    }

    public void setAdIntro5(String adIntro5) {
        this.adIntro5 = adIntro5;
    }

    public String getAdDesc() {
        return adDesc;
    }

    public void setAdDesc(String adDesc) {
        this.adDesc = adDesc;
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