package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 热门搜索关键词
 * @author lichaoxiong
 *
 */
public class SearchHotKeyword implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    private String keyWord;

    private Integer yn;

    private Short sort;

    private Date created;

    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
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