package com.xindong.api.domain.been;

/**
 *	购物车-修改、删除操作(接收参数)
 */
public class CartUpdateBeen {
	/** 购物车ID */
	private Integer cartId;
	
	/** 数量 */
	private Integer skuNum;
	
	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}
}
