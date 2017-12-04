package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer userId;//用户id

    private String userName;//

    private String mobile;//

    private String password;//

    private Integer userType;//用户类型(1普通用户 2 erp用户)

    private String userFlag;//用户旅行偏好(#1#人文艺术、#2#亲子女性、#3#户外运动、
    //#4#商旅交流、#5#教育公益、#6#历史军事、#7#美食养生、#8#行修国学、#9#自驾游艇)

    private Date registerTime;//

    private String registerIp;//

    private Date lastLoginTime;//

    private String lastLoginIp;//

    private Integer level;//用户级别(0:普通用户,1:一级用户,1:二级用户,1:三级用户)

    private String headUrl;//头像地址

    private String qrcode;//

    private String qrcodeUrl;//二维码图片地址

    private Integer sex;//性别1男2女

    private Date birthDate;//

    private String idNumber;//身份证号

    private Integer venderUserId;//商户编号

    private String unionid;//身份证号

    private Date createTime;//

    private Date modifiedTime;//

    private Integer yn;//有效性（0-无效  1-有效）
    private String province;//手机号码归属地

    private String city;//手机号码归属地

    private String supplier;//手机号码归属地供应商
    private String invitationCode;//自己的邀请码

    private String registerInvitationCode;//注册使用的邀请码
    
    public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	public String getRegisterInvitationCode() {
		return registerInvitationCode;
	}
	public void setRegisterInvitationCode(String registerInvitationCode) {
		this.registerInvitationCode = registerInvitationCode;
	}
    public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getVenderUserId() {
        return venderUserId;
    }

    public void setVenderUserId(Integer venderUserId) {
        this.venderUserId = venderUserId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}