package com.xindong.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 一个商品最多有6个sku，每个sku有开始时间与结束时间；
* 每个sku有成人价与儿童价(可选)
* 是否有库存: 值为1有库存，下单时需要扣除库存数量，订单进入待支付状态；
              0无库存，下单时不需要扣除库存数量，订单进入待确认状态，等待erp人员确认订单；
* 总人数即是该sku的库存； 如总库存为0，库存标记为没有库存
* 其中property_type为3 
 * @author lichaoxiong
 *
 */
public class Sku implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer skuId;

    private Integer itemId;

    private Date skuStartDate;//启程时间(yymmdd)

    private Date skuReturnDate;//回城时间(yymmdd

    private String barCode;//条形码

    private BigDecimal skuAdultPrice;//条形码

    private Integer skuChildrenFlag;//	0没有儿童价;1有儿童价

    private BigDecimal childrenPrice;//儿童价

    private Integer stockFlag;//0没有库存;1有库存

    private Integer stock;//库存数量

    private Integer yn;

    private Date created;

    private Date modified;

    private Integer adultLeastBuy;//成人最低人数

    private Integer childrenLeastBuy;//儿童最低人数

    private Integer adultMostBuy;//成人最高人数

    private Integer childrenMostBuy;//儿童最高人数

    private BigDecimal skuRoomsPrice;//单房差价格

    private Integer otherNumOne;//备用数量1

    private Integer otherNumTwo;//备用数量2

    private BigDecimal otherPriceOne;//备用价格1

    private BigDecimal otherPriceTwo;//备用价格2

    private BigDecimal otherPriceThree;//备用价格3
    private Integer originId;//出发城市

    private String originName;//出发城市名称
    
    private Date endTime;//行程报名截止日期

    private Date startTime;//行程报名开始日期(备用)

    private String bakFirst;//备用1

    private Integer bakSecond;//备用2
    
    private String remark;//备注
    
    private String promotionStr;//促销信息
    
    public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getBakFirst() {
		return bakFirst;
	}

	public void setBakFirst(String bakFirst) {
		this.bakFirst = bakFirst;
	}

	public Integer getBakSecond() {
		return bakSecond;
	}

	public void setBakSecond(Integer bakSecond) {
		this.bakSecond = bakSecond;
	}

	public Integer getOriginId() {
		return originId;
	}

	public void setOriginId(Integer originId) {
		this.originId = originId;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public Integer getAdultLeastBuy() {
        return adultLeastBuy;
    }

    public void setAdultLeastBuy(Integer adultLeastBuy) {
        this.adultLeastBuy = adultLeastBuy;
    }

    public Integer getChildrenLeastBuy() {
        return childrenLeastBuy;
    }

    public void setChildrenLeastBuy(Integer childrenLeastBuy) {
        this.childrenLeastBuy = childrenLeastBuy;
    }

    public Integer getAdultMostBuy() {
        return adultMostBuy;
    }

    public void setAdultMostBuy(Integer adultMostBuy) {
        this.adultMostBuy = adultMostBuy;
    }

    public Integer getChildrenMostBuy() {
        return childrenMostBuy;
    }

    public void setChildrenMostBuy(Integer childrenMostBuy) {
        this.childrenMostBuy = childrenMostBuy;
    }

    public BigDecimal getSkuRoomsPrice() {
        return skuRoomsPrice ==null ? BigDecimal.valueOf(0):skuRoomsPrice ;
    }

    public void setSkuRoomsPrice(BigDecimal skuRoomsPrice) {
        this.skuRoomsPrice = skuRoomsPrice;
    }

    public Integer getOtherNumOne() {
        return otherNumOne;
    }

    public void setOtherNumOne(Integer otherNumOne) {
        this.otherNumOne = otherNumOne;
    }

    public Integer getOtherNumTwo() {
        return otherNumTwo;
    }

    public void setOtherNumTwo(Integer otherNumTwo) {
        this.otherNumTwo = otherNumTwo;
    }

    public BigDecimal getOtherPriceOne() {
        return otherPriceOne ==null ? BigDecimal.valueOf(0):otherPriceOne ;
    }

    public void setOtherPriceOne(BigDecimal otherPriceOne) {
        this.otherPriceOne = otherPriceOne;
    }

    public BigDecimal getOtherPriceTwo() {
        return otherPriceTwo ==null ? BigDecimal.valueOf(0):otherPriceTwo ;
    }

    public void setOtherPriceTwo(BigDecimal otherPriceTwo) {
        this.otherPriceTwo = otherPriceTwo;
    }

    public BigDecimal getOtherPriceThree() {
        return otherPriceThree ==null ? BigDecimal.valueOf(0):otherPriceThree ;
    }

    public void setOtherPriceThree(BigDecimal otherPriceThree) {
        this.otherPriceThree = otherPriceThree;
    }
    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Date getSkuStartDate() {
        return skuStartDate;
    }

    public void setSkuStartDate(Date skuStartDate) {
        this.skuStartDate = skuStartDate;
    }

    public Date getSkuReturnDate() {
        return skuReturnDate;
    }

    public void setSkuReturnDate(Date skuReturnDate) {
        this.skuReturnDate = skuReturnDate;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getSkuAdultPrice() {
        return skuAdultPrice ==null ? BigDecimal.valueOf(0):skuAdultPrice;
    }

    public void setSkuAdultPrice(BigDecimal skuAdultPrice) {
        this.skuAdultPrice = skuAdultPrice;
    }

    public Integer getSkuChildrenFlag() {
        return skuChildrenFlag;
    }

    public void setSkuChildrenFlag(Integer skuChildrenFlag) {
        this.skuChildrenFlag = skuChildrenFlag;
    }

    public BigDecimal getChildrenPrice() {
        return childrenPrice ==null ? BigDecimal.valueOf(0):childrenPrice ;
    }

    public void setChildrenPrice(BigDecimal childrenPrice) {
        this.childrenPrice = childrenPrice;
    }

    public Integer getStockFlag() {
        return stockFlag;
    }

    public void setStockFlag(Integer stockFlag) {
        this.stockFlag = stockFlag;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPromotionStr() {
		return promotionStr;
	}

	public void setPromotionStr(String promotionStr) {
		this.promotionStr = promotionStr;
	}
}