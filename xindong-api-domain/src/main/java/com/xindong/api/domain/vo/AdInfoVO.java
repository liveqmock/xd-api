package com.xindong.api.domain.vo;

import java.util.List;

import com.xindong.api.domain.AdImg;
import com.xindong.api.domain.AdInfo;

/**
 * 广告
 * @author lichaoxiong
 *
 */
public class AdInfoVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer sortNum;//位置排序

    private String adMainImgUrl;//主图地址

    private String adUrl;//跳转地址

    private String adName;//广告名称

    private String adIntro;//广告介绍

    private String adIntro1;//广告语1

    private String adIntro2;//广告语2

    private String adIntro3;//广告语3

    private String adIntro4;//广告语4

    private String adIntro5;//广告语5

    private String adDesc;//广告描述
    private Integer bakSecond;//0跳转地址1视频地址
    private List<AdImg> adImgList;//配图

    public AdInfoVO() {
	}
    public AdInfoVO(AdInfo adInfo) {
    	this.id =adInfo.getId();
    	this.adDesc=adInfo.getAdDesc();
    	this.adIntro=adInfo.getAdIntro();
    	this.adIntro1=adInfo.getAdIntro1();
    	this.adIntro2=adInfo.getAdIntro2();
    	this.adIntro3=adInfo.getAdIntro3();
    	this.adIntro4=adInfo.getAdIntro4();
    	this.adIntro5=adInfo.getAdIntro5();
    	this.adMainImgUrl=adInfo.getAdMainImgUrl();
    	this.adName=adInfo.getAdName();
    	this.adUrl=adInfo.getAdUrl();
    	this.sortNum =adInfo.getSortNum();
    	this.bakSecond =adInfo.getBakSecond();
	}
    
	public Integer getBakSecond() {
		return bakSecond;
	}
	public void setBakSecond(Integer bakSecond) {
		this.bakSecond = bakSecond;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getAdMainImgUrl() {
		return adMainImgUrl;
	}

	public void setAdMainImgUrl(String adMainImgUrl) {
		this.adMainImgUrl = adMainImgUrl;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdIntro() {
		return adIntro;
	}

	public void setAdIntro(String adIntro) {
		this.adIntro = adIntro;
	}

	public String getAdIntro1() {
		return adIntro1;
	}

	public void setAdIntro1(String adIntro1) {
		this.adIntro1 = adIntro1;
	}

	public String getAdIntro2() {
		return adIntro2;
	}

	public void setAdIntro2(String adIntro2) {
		this.adIntro2 = adIntro2;
	}

	public String getAdIntro3() {
		return adIntro3;
	}

	public void setAdIntro3(String adIntro3) {
		this.adIntro3 = adIntro3;
	}

	public String getAdIntro4() {
		return adIntro4;
	}

	public void setAdIntro4(String adIntro4) {
		this.adIntro4 = adIntro4;
	}

	public String getAdIntro5() {
		return adIntro5;
	}

	public void setAdIntro5(String adIntro5) {
		this.adIntro5 = adIntro5;
	}

	public String getAdDesc() {
		return adDesc;
	}

	public void setAdDesc(String adDesc) {
		this.adDesc = adDesc;
	}

	public List<AdImg> getAdImgList() {
		return adImgList;
	}

	public void setAdImgList(List<AdImg> adImgList) {
		this.adImgList = adImgList;
	}
    
}