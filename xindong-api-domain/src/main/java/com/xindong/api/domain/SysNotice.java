package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统公告
 * @author lichaoxiong
 *
 */
public class SysNotice implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer noticeId;

    private String noticeName;

    private String noticeContent;//公告内容

    private Date startTime;//公告显示开始时间

    private Date endTime;//公告显示结束时间

    private Integer status;//状态（0待审核1审核通过2审核拒绝）

    private Integer yn;

    private Integer operaUserId;

    private String operaUserName;

    private Date created;

    private Date modified;

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Integer getOperaUserId() {
        return operaUserId;
    }

    public void setOperaUserId(Integer operaUserId) {
        this.operaUserId = operaUserId;
    }

    public String getOperaUserName() {
        return operaUserName;
    }

    public void setOperaUserName(String operaUserName) {
        this.operaUserName = operaUserName;
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