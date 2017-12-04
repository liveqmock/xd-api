package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品介绍
 * @author lichaoxiong
 *
 */
public class ItemDescriptionVO implements Serializable{
	private static final long serialVersionUID = 1L;

    private Integer descriptionType;//类型（1-产品特色;2-每日行程;3-出行说明;4-费用说明;5-预定须知）

    private String descriptionInfo;//商品介绍电脑版

    public Integer getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(Integer descriptionType) {
        this.descriptionType = descriptionType;
    }

    public String getDescriptionInfo() {
        return descriptionInfo;
    }

    public void setDescriptionInfo(String descriptionInfo) {
        this.descriptionInfo = descriptionInfo;
    }

}