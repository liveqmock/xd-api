package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.xindong.api.domain.TourDate;
/**
 * 旅游时间
 * @author lichaoxiong
 *
 */
public class TourDateVO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer dateId;

    private String dateName;

    private List<TourDate> tourDateChilds;

    private boolean isChoose;//是否选中
    
	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}

	public List<TourDate> getTourDateChilds() {
		return tourDateChilds;
	}

	public void setTourDateChilds(List<TourDate> tourDateChilds) {
		this.tourDateChilds = tourDateChilds;
	}

	public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

   
}