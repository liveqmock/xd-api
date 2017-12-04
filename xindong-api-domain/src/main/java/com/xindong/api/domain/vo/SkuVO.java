package com.xindong.api.domain.vo;

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
public class SkuVO implements Serializable{
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
        return skuAdultPrice;
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
        return childrenPrice;
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
}