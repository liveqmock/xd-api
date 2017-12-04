package com.xindong.api.domain.vo;

import java.util.Date;

/**
 * @author lichaoxiong
 *用户收藏
 */
public class UserCollectionVO {
	 private Integer itemId;//商品编号
	 private String intro;//介绍
	 private String itemName;//商品名称
	 private String imgUrl;//主图地址
	 private String itemIntro1;//商品简介1(产品特色第一行)
	 private String itemIntro2;//商品简介1(产品特色第二行)
	 private String itemIntro3;//商品简介1(产品特色第三行)
	 private Date created;//收藏时间
	 public void setCreated(Date created) {
		this.created = created;
	}
	 public Date getCreated() {
		return created;
	}
	 public String getItemIntro1() {
		return itemIntro1;
	}
	public void setItemIntro1(String itemIntro1) {
		this.itemIntro1 = itemIntro1;
	}
	public String getItemIntro2() {
		return itemIntro2;
	}
	public void setItemIntro2(String itemIntro2) {
		this.itemIntro2 = itemIntro2;
	}
	public String getItemIntro3() {
		return itemIntro3;
	}
	public void setItemIntro3(String itemIntro3) {
		this.itemIntro3 = itemIntro3;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	 public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
    
}