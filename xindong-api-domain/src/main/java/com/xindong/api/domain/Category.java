package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 分类
 *
 */
public class Category  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Integer categoryId;

    /** 分类名称 */
    private String categoryName;

    /** 分类等级 */
    private Integer categoryLevel;

    /** 父分类ID */
    private Integer parentCategoryId;

    /** 排序号 */
    private Integer sortNumber;

    /** 是否有销售属性 */
    private Integer ifHaveSaleProperty;

    /** 有效性 */
    private Integer yn;

    /** 创建时间 */
    private Date created;

    /** 修改时间 */
    private Date modified;

    private String imgUrl;//图片地址
    
    public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public Integer getIfHaveSaleProperty() {
        return ifHaveSaleProperty;
    }

    public void setIfHaveSaleProperty(Integer ifHaveSaleProperty) {
        this.ifHaveSaleProperty = ifHaveSaleProperty;
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