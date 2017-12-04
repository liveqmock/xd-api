package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 活动回复
 * @author lichaoxiong
 *
 */
public class ActivityReplyVO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer activityReplyId;

    private Integer activityId;

    private String activityReplyIntro;//活动回复内容

    private Integer userId;//

    private String userPhone;//回复手机号

    private Date replayDate;//活动回复时间

    private String activityName;//活动标题

	public Integer getActivityReplyId() {
		return activityReplyId;
	}

	public void setActivityReplyId(Integer activityReplyId) {
		this.activityReplyId = activityReplyId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityReplyIntro() {
		return activityReplyIntro;
	}

	public void setActivityReplyIntro(String activityReplyIntro) {
		this.activityReplyIntro = activityReplyIntro;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Date getReplayDate() {
		return replayDate;
	}

	public void setReplayDate(Date replayDate) {
		this.replayDate = replayDate;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

}