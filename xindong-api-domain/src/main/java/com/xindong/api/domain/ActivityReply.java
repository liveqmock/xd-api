package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 活动回复
 * @author lichaoxiong
 *
 */
public class ActivityReply implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer activityReplyId;

    private Integer activityId;

    private String activityReplyIntro;//活动回复内容

    private Integer activityReplyStatu;//回复内容状态(0未审核;1已审核;2已删除)

    private Integer userId;//

    private String userPhone;//回复手机号

    private Integer sortNum;//

    private Date replayDate;//活动回复时间

    private Integer yn;

    private Date created;

    private Date modified;

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

    public Integer getActivityReplyStatu() {
        return activityReplyStatu;
    }

    public void setActivityReplyStatu(Integer activityReplyStatu) {
        this.activityReplyStatu = activityReplyStatu;
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

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Date getReplayDate() {
        return replayDate;
    }

    public void setReplayDate(Date replayDate) {
        this.replayDate = replayDate;
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
}