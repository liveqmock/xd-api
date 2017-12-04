package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 活动
 * @author lichaoxiong
 *
 */
public class Activity implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer activityId;

    private String activityName;//活动标题

    private String activityIntro;//活动内容

    private String activityImgDet;//活动图片（小）

    private String activityImg;//活动图片（主）

    private Integer activityStatu;//活动状态(0待上线;1已上线;2已下线)

    private String activityAuthor;//活动作者

    private Integer sortNum;//优先级排序

    private Date activityPublishDate;//活动发布时间

    private Integer replyNum;//回复人数

    private Integer yn;//

    private Date created;

    private Date modified;
    private String activityDesc;//简介

    private Integer usefulCount;//有赞数量

    private Integer forwordCount;//转发数量

    private String remark;//备注

    private Integer activityType;//活动类型（1看板新出发2活动专区）

    private String activityUrl;//活动跳转地址

    private String bakFirst;//备用1

    private Integer bakSecond;//备用1
    
    private Integer whetherCoupon;//是否有优惠券
    
    private String ruleCoupon;//优惠券规则
    
    private Integer couponPromoId;//优惠券活动编号
    
    private List<ActivityDetail> activityDetailList;//商品详情
    
    public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getActivityUrl() {
		return activityUrl;
	}

	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}

	public String getBakFirst() {
		return bakFirst;
	}

	public void setBakFirst(String bakFirst) {
		this.bakFirst = bakFirst;
	}

	public Integer getBakSecond() {
		return bakSecond;
	}

	public void setBakSecond(Integer bakSecond) {
		this.bakSecond = bakSecond;
	}

	public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public Integer getUsefulCount() {
        return usefulCount;
    }

    public void setUsefulCount(Integer usefulCount) {
        this.usefulCount = usefulCount;
    }

    public Integer getForwordCount() {
        return forwordCount;
    }

    public void setForwordCount(Integer forwordCount) {
        this.forwordCount = forwordCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
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

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
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

	public String getActivityImgDet() {
		return activityImgDet;
	}

	public void setActivityImgDet(String activityImgDet) {
		this.activityImgDet = activityImgDet;
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

	public List<ActivityDetail> getActivityDetailList() {
		return activityDetailList;
	}

	public void setActivityDetailList(List<ActivityDetail> activityDetailList) {
		this.activityDetailList = activityDetailList;
	}
}