package com.xindong.api.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户余额明细
 * @author lichaoxiong
 *
 */
public class UserBalanceDetailQuery  extends BaseSearchForMysqlVo implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer balanceId;//余额ID

    private Integer userId;//用户ID

    private Integer balanceStatus;//0：未过期1：已过期

    private Integer orderId;//订单ID

    private BigDecimal balanceAmount;//余额数量 如果扣减则是负数

    private BigDecimal balanceIncreAmount;//余额总数+余额数量

    private Integer balanceType;//余额类型(0:增加余额;1减少余额)

    private Integer grantType;//余额发放类型(0:评价商品送余额;1:评价专题送余额;2购物送余额)

    private Date grantTime;//余额发放时间

    private Date expireTime;//余额过期时间

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
		return balanceAmount;
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

}