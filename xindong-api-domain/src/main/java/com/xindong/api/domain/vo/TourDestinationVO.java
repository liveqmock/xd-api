package com.xindong.api.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.xindong.api.domain.TourDestination;
/**
 * @author lichaoxiong
 *
 */
public class TourDestinationVO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer destinationId;

    private String destinationName;

    private List<TourDestination> tourDestinationChilds;

    private boolean isChoose;//是否选中
    
	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}

	public Integer getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public List<TourDestination> getTourDestinationChilds() {
		return tourDestinationChilds;
	}

	public void setTourDestinationChilds(List<TourDestination> tourDestinationChilds) {
		this.tourDestinationChilds = tourDestinationChilds;
	}

	

   
}