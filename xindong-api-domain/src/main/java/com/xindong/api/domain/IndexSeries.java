package com.xindong.api.domain;

import java.util.Date;
/**
 * 首页系列
 * @author lichaoxiong
 *
 */
public class IndexSeries {
    private Integer id;

    private Integer type;//类型1-首页

    private Integer sortNum;//位置排序

    private String imgUrl;//H5主图地址

    private String linkUrl;//跳转地址

    private Integer state;//状态（1待上线2已上线3已下线）

    private String name;//名称

    private String intro;//介绍

    private String bakFirst;

    private Integer bakSecond;

    private Integer yn;

    private Date created;

    private Date modified;
    
    private String pcImgUrl;//PC主图地址

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

	public String getPcImgUrl() {
		return pcImgUrl;
	}

	public void setPcImgUrl(String pcImgUrl) {
		this.pcImgUrl = pcImgUrl;
	}
}