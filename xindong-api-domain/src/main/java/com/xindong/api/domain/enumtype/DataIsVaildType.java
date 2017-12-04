package com.xindong.api.domain.enumtype;

/**
 * 数据有效状态(1：有效，0：无效)
 * @author FBSK742A
 *
 */
public enum DataIsVaildType {
	EFFECTIVE(1),
	INVALID(0);
	
	private int fn;
	
	DataIsVaildType(int fn){
		this.fn = fn;
	}
	
	public int getValue(){
		return fn;
	}
}
