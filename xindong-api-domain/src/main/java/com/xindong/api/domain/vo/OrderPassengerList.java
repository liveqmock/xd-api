package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.xindong.api.domain.OrderPassenger;


public class OrderPassengerList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<OrderPassenger> list;
	public List<OrderPassenger> getList() {
		return list;
	}
	public void setList(List<OrderPassenger> list) {
		this.list = list;
	}
    
	
}
