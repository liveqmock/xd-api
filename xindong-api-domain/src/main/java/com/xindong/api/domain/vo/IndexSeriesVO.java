package com.xindong.api.domain.vo;

import com.xindong.api.domain.IndexSeries;
/**
 * 首页系列
 * @author lichaoxiong
 *
 */
public class IndexSeriesVO {
    private Integer id;

    private String imgUrl;//H5主图地址

    private String name;//名称
    
    private String pcImgUrl;//PC主图地址
    
    public IndexSeriesVO() {
	}
    public IndexSeriesVO(IndexSeries indexSeries) {
    	this.id =indexSeries.getId();
    	this.imgUrl =indexSeries.getImgUrl();
    	this.pcImgUrl = indexSeries.getPcImgUrl();
    	this.name =indexSeries.getName();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPcImgUrl() {
		return pcImgUrl;
	}
	public void setPcImgUrl(String pcImgUrl) {
		this.pcImgUrl = pcImgUrl;
	}
	
    
}