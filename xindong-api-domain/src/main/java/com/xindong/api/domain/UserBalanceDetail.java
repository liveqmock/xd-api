package com.xindong.api.domain;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户余额明细
 * @author lichaoxiong
 *
 */
public class UserBalanceDetail {
    private Integer balanceDetailId;

    private Integer balanceId;//余额ID

    private Integer userId;//用户ID

    private Integer balanceStatus;//0：未过期1：已过期

    private Integer orderId;//订单ID

    private BigDecimal balanceAmount;//余额数量 如果扣减则是负数

    private BigDecimal balanceIncreAmount;//余额总数+余额数量

    private Integer balanceType;//余额类型(0:增加余额;1减少余额)

    private Integer grantType;//余额发放类型(1:邀请会员送余额2下单使用余额3注册奖励4增加余额)

    private Date grantTime;//余额发放时间

    private Date expireTime;//余额过期时间

    private Date created;

    private Date modified;
    private String remark;//备注
    private String businessValue;//获得积分的业务主键（订单号，注册用户，操作用户）
    public void setBusinessValue(String businessValue) {
		this.businessValue = businessValue;
	}
    public String getBusinessValue() {
		return businessValue;
	}
    public void setRemark(String remark) {
		this.remark = remark;
	}
    public String getRemark() {
		return remark;
	}
    public Integer getBalanceDetailId() {
        return balanceDetailId;
    }

    public void setBalanceDetailId(Integer balanceDetailId) {
        this.balanceDetailId = balanceDetailId;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(Integer balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getBalanceAmount() {
		return balanceAmount == null ? BigDecimal.valueOf(0):balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getBalanceIncreAmount() {
		return balanceIncreAmount;
	}

	public void setBalanceIncreAmount(BigDecimal balanceIncreAmount) {
		this.balanceIncreAmount = balanceIncreAmount;
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}

	public Integer getGrantType() {
		return grantType;
	}

	public void setGrantType(Integer grantType) {
		this.grantType = grantType;
	}

	public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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