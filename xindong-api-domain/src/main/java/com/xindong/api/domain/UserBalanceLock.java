package com.xindong.api.domain;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户余额锁定
 * @author lichaoxiong
 *
 */
public class UserBalanceLock {
    private Integer balanceLockId;

    private Integer balanceId;//余额ID

    private Integer userId;//用户ID

    private BigDecimal balanceAmount;//余额数量

    private Integer orderId;//订单ID

    private Integer balanceStatus;//锁定状态(0:申请下单锁定;1下单锁定中;2下单锁定完成)

    private Integer workStatus;//处理状态(0:未处理;1扣减余额中;2余额扣减完成;3已取消)

    private Date created;

    private Date modified;
    private String remark;//备注
    private String businessValue;//获得积分的业务主键（订单号，注册用户，操作用户）
    private Integer grantType;//余额发放类型(1:邀请会员送余额2下单使用余额3注册奖励4增加余额)
    public Integer getGrantType() {
		return grantType;
	}
    public void setGrantType(Integer grantType) {
		this.grantType = grantType;
	}
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
    public Integer getBalanceLockId() {
        return balanceLockId;
    }

    public void setBalanceLockId(Integer balanceLockId) {
        this.balanceLockId = balanceLockId;
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

    public BigDecimal getBalanceAmount() {
		return balanceAmount== null ? BigDecimal.valueOf(0):balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(Integer balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
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