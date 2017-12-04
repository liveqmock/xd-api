package com.xindong.api.domain;
/**
 * 系统常量 值 对象
 * @author lichaoxiong
 */
public class SystemConstantValue {
    private Integer id;

    private String typeValue;//对应类型值

    private String name;//名称

    private Integer value;//常量值

    private Integer yn;//是否有效(1有效;0无效)

    private Integer sortNum;//优先级排序
    private String description;//描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
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

}