package com.xindong.api.domain.query;

/**
 * 广告
 * @author lichaoxiong
 *
 */
public class AdInfoQuery {
    private Integer id;
    private Integer adType;//类型1-首页
    private Integer sortNum;//位置排序
    private Integer state;//状态（1待上线2已上线3已下线）
    private String adName;//广告名称
    private Integer yn;
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
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
    

}