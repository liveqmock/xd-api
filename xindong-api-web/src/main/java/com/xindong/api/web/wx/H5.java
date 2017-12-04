package com.xindong.api.web.wx;

import com.xindong.api.common.utils.JsonUtils;

public class H5 {
	
	private String type;
	private String wap_url;
	private String wap_name;
	
	public H5(){}
	
	public H5(String type, String wap_url, String wap_name) {
		super();
		this.type = type;
		this.wap_url = wap_url;
		this.wap_name = wap_name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWap_url() {
		return wap_url;
	}
	public void setWap_url(String wap_url) {
		this.wap_url = wap_url;
	}
	public String getWap_name() {
		return wap_name;
	}
	public void setWap_name(String wap_name) {
		this.wap_name = wap_name;
	}
	
}
