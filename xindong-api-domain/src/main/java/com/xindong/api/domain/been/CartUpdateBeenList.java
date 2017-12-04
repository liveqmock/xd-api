package com.xindong.api.domain.been;

import java.util.List;

/**
 *	修改购物车-接收参数
 */
public class CartUpdateBeenList {
	private List<CartUpdateBeen> list;

	public List<CartUpdateBeen> getList() {
		return list;
	}

	public void setList(List<CartUpdateBeen> list) {
		this.list = list;
	}
}
