package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;

public class ActivityDetailQuery extends BaseSearchForMysqlVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**  */
	private Integer id;
	/** 活动编号 */
	private Integer activityId;
	/** 图片地址 */
	private String imgUrl;
	/** 跳转地址 */
	private String skipUrl;
	/** 出行时间 */
	private Date travelTime;
	/** 商品名称 */
	private String itemName;
	/** 商品简介 */
	private String itemDesc;
	/** 是否有优惠（0、 无;1、有） */
	private Integer whetherCoupon;
	/** 优惠券ID */
	private Integer couponPromoId;
	/** 优惠规则 */
	private String ruleCoupon;
	/** 排序 */
	private Integer sortNum;
	/** 是否有效(0无效;1有效) */
	private Integer yn;
	/** 创建时间 */
	private Date created;
	/** 修改时间 */
	private Date modified;
	/** 备用1 */
	private String bakFirst;
	/** 备用2 */
	private Integer bakSecond;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id=id;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId=activityId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl=imgUrl;
	}
	public String getSkipUrl() {
		return skipUrl;
	}
	public void setSkipUrl(String skipUrl) {
		this.skipUrl=skipUrl;
	}
	public Date getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(Date travelTime) {
		this.travelTime=travelTime;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc=itemDesc;
	}
	public Integer getWhetherCoupon() {
		return whetherCoupon;
	}
	public void setWhetherCoupon(Integer whetherCoupon) {
		this.whetherCoupon=whetherCoupon;
	}
	public Integer getCouponPromoId() {
		return couponPromoId;
	}
	public void setCouponPromoId(Integer couponPromoId) {
		this.couponPromoId=couponPromoId;
	}
	public String getRuleCoupon() {
		return ruleCoupon;
	}
	public void setRuleCoupon(String ruleCoupon) {
		this.ruleCoupon=ruleCoupon;
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
	public String getBakFirst() {
		return bakFirst;
	}
	public void setBakFirst(String bakFirst) {
		this.bakFirst=bakFirst;
	}
	public Integer getBakSecond() {
		return bakSecond;
	}
	public void setBakSecond(Integer bakSecond) {
		this.bakSecond=bakSecond;
	}
}
