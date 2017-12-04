package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.xindong.api.domain.TourSubject;
/**
 * @author lichaoxiong
 *
 */
public class TourSubjectVO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer subjectId;

    private String subjectName;

    private List<TourSubject> tourSubjectChilds;

    private boolean isChoose;//是否选中
    
	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<TourSubject> getTourSubjectChilds() {
		return tourSubjectChilds;
	}

	public void setTourSubjectChilds(List<TourSubject> tourSubjectChilds) {
		this.tourSubjectChilds = tourSubjectChilds;
	}

	

   
}