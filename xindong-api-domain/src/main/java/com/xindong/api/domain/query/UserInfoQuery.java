package com.xindong.api.domain.query;

import java.io.Serializable;

public class UserInfoQuery extends BaseSearchForMysqlVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Integer userId;

    /** 手机号 */
    private String mobile;

    /** 登陆密码 */
    private String password;

    /** 用户类型 */
    private Integer userType;

    /** 有效性 */
    private Integer yn;
    
    private String openId;
    private String unionid;//微信唯一主键   openid 无法确定用户
    private String giftName;//签名
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
    public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
    public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}