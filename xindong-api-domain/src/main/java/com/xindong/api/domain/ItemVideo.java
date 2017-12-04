package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;

public class ItemVideo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**  */
	private Integer id;
	/** 商品编号 */
	private Integer itemId;
	/** PC视频地址 */
	private String shareWebUrl;
	/** H5视频地址 */
	private String h5ShareWebUrl;
	/** 视频描述 */
	private String videoDesc;
	/** 排序 */
	private Integer sortNum;
	/** 有效性 */
	private Integer yn;
	/** 创建时间 */
	private Date created;
	/** 修改时间 */
	private Date modified;
	
	private String itemName;//商品名称
	private String itemImage;//商品图片
	private String promotionStr;//促销信息
	
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
	public String getShareWebUrl() {
		return shareWebUrl;
	}
	public void setShareWebUrl(String shareWebUrl) {
		this.shareWebUrl=shareWebUrl;
	}
	public String getH5ShareWebUrl() {
		return h5ShareWebUrl;
	}
	public void setH5ShareWebUrl(String h5ShareWebUrl) {
		this.h5ShareWebUrl=h5ShareWebUrl;
	}
	public String getVideoDesc() {
		return videoDesc;
	}
	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum=sortNum;
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
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public String getPromotionStr() {
		return promotionStr;
	}
	public void setPromotionStr(String promotionStr) {
		this.promotionStr = promotionStr;
	}
}
