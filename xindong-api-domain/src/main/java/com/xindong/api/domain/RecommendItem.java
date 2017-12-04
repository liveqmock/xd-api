package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 推荐商品
 * @author lichaoxiong
 *
 */
public class RecommendItem implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer recommendItemId;

    private Integer recommendType;//1列表页更多精彩推荐;2搜索无结果推荐

    private Integer itemId;//

    private Integer yn;//

    private Integer sortNum;//

    private Date created;

    private Date modified;

    public Integer getRecommendItemId() {
        return recommendItemId;
    }

    public void setRecommendItemId(Integer recommendItemId) {
        this.recommendItemId = recommendItemId;
    }

    public Integer getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(Integer recommendType) {
        this.recommendType = recommendType;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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