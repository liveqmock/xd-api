package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.xindong.api.domain.ItemDescription;
import com.xindong.api.domain.ItemImage;
import com.xindong.api.domain.Sku;
/**
 * 产品详情展示
 * @author lichaoxiong
 *
 */
public class ItemDetailVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private ItemVO item;
	private List<Sku> skus;
	private List<ItemDescription> itemDescriptions;
	private List<ItemImage> itemImages;
	private List<RecommendItemVO> recommendImages;
	public ItemVO getItem() {
		return item;
	}
	public void setItem(ItemVO item) {
		this.item = item;
	}
	public List<Sku> getSkus() {
		return skus;
	}
	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}
	public List<ItemDescription> getItemDescriptions() {
		return itemDescriptions;
	}
	public void setItemDescriptions(List<ItemDescription> itemDescriptions) {
		this.itemDescriptions = itemDescriptions;
	}
	public List<ItemImage> getItemImages() {
		return itemImages;
	}
	public void setItemImages(List<ItemImage> itemImages) {
		this.itemImages = itemImages;
	}
	public List<RecommendItemVO> getRecommendImages() {
		return recommendImages;
	}
	public void setRecommendImages(List<RecommendItemVO> recommendImages) {
		this.recommendImages = recommendImages;
	}
      
}