package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;
/**
 * 旅客信息表-仓库
 * @author lichaoxiong
 *
 */
public class PassengerGroupQuery  extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer passengerId;

    private Integer userId;

    private Integer passengerIdentityType;//旅客认证类型(1身份证2护照3军官证4回乡证5台胞证6港澳通行证7台湾通行证8士兵证)

    private String passengerIdentityNumber;//旅客认证号码

    private String passengerMobile;//旅客手机
    private String passengerName;//旅客姓名
    private Integer defaultPassengerFlag;//是否默认旅客人(1是0否)

    private Integer yn;

    private Date created;

    private Date modified;

    
    public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPassengerIdentityType() {
        return passengerIdentityType;
    }

    public void setPassengerIdentityType(Integer passengerIdentityType) {
        this.passengerIdentityType = passengerIdentityType;
    }

    public String getPassengerIdentityNumber() {
        return passengerIdentityNumber;
    }

    public void setPassengerIdentityNumber(String passengerIdentityNumber) {
        this.passengerIdentityNumber = passengerIdentityNumber;
    }

    public String getPassengerMobile() {
        return passengerMobile;
    }

    public void setPassengerMobile(String passengerMobile) {
        this.passengerMobile = passengerMobile;
    }

    public Integer getDefaultPassengerFlag() {
        return defaultPassengerFlag;
    }

    public void setDefaultPassengerFlag(Integer defaultPassengerFlag) {
        this.defaultPassengerFlag = defaultPassengerFlag;
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