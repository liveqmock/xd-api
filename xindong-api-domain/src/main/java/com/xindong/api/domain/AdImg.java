package com.xindong.api.domain;

import java.util.Date;
/**
 * 广告图片
 * @author lichaoxiong
 *
 */
public class AdImg {
    private Integer id;

    private Integer adInfoId;//广告编号

    private Integer adType;//类型1-首页

    private Integer sortNum;//位置排序

    private String adImgUrl;//配图地址

    private String adUrl;//跳转地址

    private String adImgIntro1;//配图广告语1

    private String adImgIntro2;//配图广告语2

    private String adImgIntro3;//配图广告语3

    private String adImgIntro4;//配图广告语4

    private String adImgIntro5;//配图广告语5

    private String adImgDesc;//	广告描述

    private String bakFirst;//

    private Integer bakSecond;//0跳转地址1视频地址

    private Integer yn;//

    private Date created;

    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdInfoId() {
        return adInfoId;
    }

    public void setAdInfoId(Integer adInfoId) {
        this.adInfoId = adInfoId;
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

    public String getAdImgUrl() {
        return adImgUrl;
    }

    public void setAdImgUrl(String adImgUrl) {
        this.adImgUrl = adImgUrl;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdImgIntro1() {
        return adImgIntro1;
    }

    public void setAdImgIntro1(String adImgIntro1) {
        this.adImgIntro1 = adImgIntro1;
    }

    public String getAdImgIntro2() {
        return adImgIntro2;
    }

    public void setAdImgIntro2(String adImgIntro2) {
        this.adImgIntro2 = adImgIntro2;
    }

    public String getAdImgIntro3() {
        return adImgIntro3;
    }

    public void setAdImgIntro3(String adImgIntro3) {
        this.adImgIntro3 = adImgIntro3;
    }

    public String getAdImgIntro4() {
        return adImgIntro4;
    }

    public void setAdImgIntro4(String adImgIntro4) {
        this.adImgIntro4 = adImgIntro4;
    }

    public String getAdImgIntro5() {
        return adImgIntro5;
    }

    public void setAdImgIntro5(String adImgIntro5) {
        this.adImgIntro5 = adImgIntro5;
    }

    public String getAdImgDesc() {
        return adImgDesc;
    }

    public void setAdImgDesc(String adImgDesc) {
        this.adImgDesc = adImgDesc;
    }

    public String getBakFirst() {
        return bakFirst;
    }

    public void setBakFirst(String bakFirst) {
        this.bakFirst = bakFirst;
    }

    public Integer getBakSecond() {
        return bakSecond ==null ? 0:bakSecond;
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