package com.xindong.api.domain.query;


import java.io.Serializable;


public class BaseSearchForMysqlVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 默认每页条数 */
	public static Integer DEFAULT_PAGE_SIZE = 10;
	
	/** 页码 */
	public Integer pageNo=1;
	
	/** 每页条数 */
	public Integer pageSize;
	
	/** 初始位置-依据页码及每页条数计算获得 */
	public Integer start;
	
	
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize ==null ? DEFAULT_PAGE_SIZE : pageSize;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getStart(){
		int start =  (getPageNo() - 1) * getPageSize();
		return start < 0 ? 0 : start;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
