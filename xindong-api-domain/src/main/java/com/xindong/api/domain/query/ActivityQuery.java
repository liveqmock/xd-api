package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;
/**
 * 活动
 * @author lichaoxiong
 *
 */
public class ActivityQuery extends BaseSearchForMysqlVo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer activityId;
	
    private String activityName;//活动标题

    private String activityIntro;//活动内容

    private Integer activityStatu;//活动状态(0待上线;1已上线;2已下线)

    private String activityAuthor;//活动作者

    private Integer sortNum;//优先级排序

    private Date activityPublishDate;//活动发布时间
    
    private Integer yn;//
    
    private Integer activityType;//活动类型（1看板新出发2活动专区）
    
    private Integer whetherCoupon;//是否有优惠券
    
    private String ruleCoupon;//优惠券规则
    
    private Integer couponPromoId;//优惠券活动编号
    
    public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
    public Integer getActivityType() {
		return activityType;
	}
	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getYn() {
		return yn;
	}

	public void setYn(Integer yn) {
		this.yn = yn;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityIntro() {
		return activityIntro;
	}

	public void setActivityIntro(String activityIntro) {
		this.activityIntro = activityIntro;
	}

	public Integer getActivityStatu() {
		return activityStatu;
	}

	public void setActivityStatu(Integer activityStatu) {
		this.activityStatu = activityStatu;
	}

	public String getActivityAuthor() {
		return activityAuthor;
	}

	public void setActivityAuthor(String activityAuthor) {
		this.activityAuthor = activityAuthor;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Date getActivityPublishDate() {
		return activityPublishDate;
	}

	public void setActivityPublishDate(Date activityPublishDate) {
		this.activityPublishDate = activityPublishDate;
	}
	public Integer getWhetherCoupon() {
		return whetherCoupon;
	}
	public void setWhetherCoupon(Integer whetherCoupon) {
		this.whetherCoupon = whetherCoupon;
	}
	public String getRuleCoupon() {
		return ruleCoupon;
	}
	public void setRuleCoupon(String ruleCoupon) {
		this.ruleCoupon = ruleCoupon;
	}
	public Integer getCouponPromoId() {
		return couponPromoId;
	}
	public void setCouponPromoId(Integer couponPromoId) {
		this.couponPromoId = couponPromoId;
	}

   
}