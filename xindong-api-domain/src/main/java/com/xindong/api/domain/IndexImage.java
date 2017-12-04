package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 首页广告图
 * @author lichaoxiong
 *
 */
public class IndexImage implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    private String imageUrl;//图片地址

    private String imageName;//图片名称

    private String skipUrl;//跳转地址

    private Integer sortNumber;//排序

    private Date created;

    private Date modified;

    private Integer yn;

    private Integer type;//0-首次打开动画图;1-首页导航图

    private Integer urlType;//跳转类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
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

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }
}