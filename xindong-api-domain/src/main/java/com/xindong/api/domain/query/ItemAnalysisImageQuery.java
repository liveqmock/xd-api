package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;


public class ItemAnalysisImageQuery extends BaseSearchForMysqlVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**  */
	private Integer id;
	/** 商品id */
	private Integer itemId;
	/** 图片地址 */
	private String imageUrl;
	/** 序号 */
	private Integer sortNumber;
	/** 有效性 */
	private Integer yn;
	/** 创建时间 */
	private Date created;
	/** 修改时间 */
	private Date modified;
	/** 图片类型(1、商品分析图;2、商品图书) */
	private Integer type;
	/** H5图片地址 */
	private String imageUrlApp;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id=id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId=itemId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl=imageUrl;
	}
	public Integer getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber=sortNumber;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn=yn;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created=created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified=modified;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type=type;
	}
	public String getImageUrlApp() {
		return imageUrlApp;
	}
	public void setImageUrlApp(String imageUrlApp) {
		this.imageUrlApp = imageUrlApp;
	}
	
}
