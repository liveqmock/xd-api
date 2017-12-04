package com.xindong.api.domain;
/**
 * 系统常量类型表
 * @author lichaoxiong
 *
 */
public class SystemConstantType {
    private Integer id;

    private String name;//名称

    private String value;//常量值

    private Integer yn;//是否有效(1有效;0无效)

    private Integer sortNum;//优先级排序
    private String description;//描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}