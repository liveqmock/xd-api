package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 操作日志
 * @author lichaoxiong
 *
 */
public class OperaLog implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer operaLogId;//

    private Integer operaUserId;//

    private String operaUserName;//操作用户

    private Integer businessId;//业务id

    private Integer operaType;//操作类型（0-订单1商品）

    private String operaBeforeTxt;//操作前描述

    private String poeraAfterTxt;//操作后描述

    private String operaResultTx;//操作结果

    private Date created;

    private Date modify;
    private Integer userType;//用户类型（1会员2后台3系统）
    
    public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

    public Integer getOperaLogId() {
        return operaLogId;
    }

    public void setOperaLogId(Integer operaLogId) {
        this.operaLogId = operaLogId;
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

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getOperaType() {
        return operaType;
    }

    public void setOperaType(Integer operaType) {
        this.operaType = operaType;
    }

    public String getOperaBeforeTxt() {
        return operaBeforeTxt;
    }

    public void setOperaBeforeTxt(String operaBeforeTxt) {
        this.operaBeforeTxt = operaBeforeTxt;
    }

    public String getPoeraAfterTxt() {
        return poeraAfterTxt;
    }

    public void setPoeraAfterTxt(String poeraAfterTxt) {
        this.poeraAfterTxt = poeraAfterTxt;
    }

    public String getOperaResultTx() {
        return operaResultTx;
    }

    public void setOperaResultTx(String operaResultTx) {
        this.operaResultTx = operaResultTx;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModify() {
        return modify;
    }

    public void setModify(Date modify) {
        this.modify = modify;
    }
}