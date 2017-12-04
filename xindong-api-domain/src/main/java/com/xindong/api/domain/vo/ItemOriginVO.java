package com.xindong.api.domain.vo;

import java.util.List;

import com.xindong.api.domain.Sku;

/**
 * 出发城市
 * @author lichaoxiong
 *
 */
public class ItemOriginVO {
	private Integer id;
	private String name;
	private List<Sku> skus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}
	public List<Sku> getSkus() {
		return skus;
	}
}
