package com.xindong.api.domain;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户余额
 * @author lichaoxiong
 *
 */
public class UserBalance {
    private Integer balanceId;//主键

    private Integer userId;//用户ID

    private BigDecimal balanceSum;//余额总数(只增不减)可用余额=余额总数-已锁定数量-已使用数量-过期数量

    private BigDecimal lockedAmount;//已锁定数量

    private BigDecimal usedAmount;//已使用数量

    private BigDecimal overdueAmount;//过期数量

    private Date created;

    private Date modified;

    private String remark;//备注
    private Integer balanceStatus;//1正常2冻结
    public void setBalanceStatus(Integer balanceStatus) {
		this.balanceStatus = balanceStatus;
	}
    public Integer getBalanceStatus() {
		return balanceStatus;
	}
    public void setRemark(String remark) {
		this.remark = remark;
	}
    public String getRemark() {
		return remark;
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

	public BigDecimal getBalanceSum() {
		return balanceSum == null ? BigDecimal.valueOf(0):balanceSum;
	}

	public void setBalanceSum(BigDecimal balanceSum) {
		this.balanceSum = balanceSum;
	}

	public BigDecimal getLockedAmount() {
		return lockedAmount == null ? BigDecimal.valueOf(0):lockedAmount;
	}

	public void setLockedAmount(BigDecimal lockedAmount) {
		this.lockedAmount = lockedAmount;
	}

	public BigDecimal getUsedAmount() {
		return usedAmount == null ? BigDecimal.valueOf(0):usedAmount;
	}

	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount ;
	}

	public BigDecimal getOverdueAmount() {
		return overdueAmount == null ? BigDecimal.valueOf(0):overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
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