package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 快捷支付绑卡信息表
 * @author lichaoxiong
 *
 */
public class PaymentBindbankcard implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    private String merCustId;//	用户			

    private String venderUserId;//商家

    private String usrBusiAgreementId;//用户业务协议号

    private String usrPayAgreementId;//支付协议号

    private String mobile;//银行预留手机号

    private String bank;//开户行

    private String bankAc;//开户行账号

    private String branch;//开户行支行

    private String branchAc;//支行联行号

    private String bankAcLast4;//

    private String branchAcLast4;//

    private String identitytype;//证件类型

    private String identitycode;//证件号

    private String cardholder;//持卡人姓名

    private Date createDate;//

    private Date updateDate;//

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerCustId() {
        return merCustId;
    }

    public void setMerCustId(String merCustId) {
        this.merCustId = merCustId;
    }

    public String getVenderUserId() {
        return venderUserId;
    }

    public void setVenderUserId(String venderUserId) {
        this.venderUserId = venderUserId;
    }

    public String getUsrBusiAgreementId() {
        return usrBusiAgreementId;
    }

    public void setUsrBusiAgreementId(String usrBusiAgreementId) {
        this.usrBusiAgreementId = usrBusiAgreementId;
    }

    public String getUsrPayAgreementId() {
        return usrPayAgreementId;
    }

    public void setUsrPayAgreementId(String usrPayAgreementId) {
        this.usrPayAgreementId = usrPayAgreementId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAc() {
        return bankAc;
    }

    public void setBankAc(String bankAc) {
        this.bankAc = bankAc;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchAc() {
        return branchAc;
    }

    public void setBranchAc(String branchAc) {
        this.branchAc = branchAc;
    }

    public String getBankAcLast4() {
        return bankAcLast4;
    }

    public void setBankAcLast4(String bankAcLast4) {
        this.bankAcLast4 = bankAcLast4;
    }

    public String getBranchAcLast4() {
        return branchAcLast4;
    }

    public void setBranchAcLast4(String branchAcLast4) {
        this.branchAcLast4 = branchAcLast4;
    }

    public String getIdentitytype() {
        return identitytype;
    }

    public void setIdentitytype(String identitytype) {
        this.identitytype = identitytype;
    }

    public String getIdentitycode() {
        return identitycode;
    }

    public void setIdentitycode(String identitycode) {
        this.identitycode = identitycode;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}