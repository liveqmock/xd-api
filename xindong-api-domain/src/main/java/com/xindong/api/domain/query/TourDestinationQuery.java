package com.xindong.api.domain.query;

import java.io.Serializable;
import java.util.Date;
/**
 * 旅游目的地
 * @author lichaoxiong
 *
 */
public class TourDestinationQuery extends BaseSearchForMysqlVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
    
	private Integer destinationId;//

    private String destinationName;//

    private Integer destinationLevel;//级别（1-一级分类  2-二级分类）

    private Integer parentDestinationId;//父id

    private Integer yn;

    private Date created;

    private Date modified;

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

    public Integer getDestinationLevel() {
        return destinationLevel;
    }

    public void setDestinationLevel(Integer destinationLevel) {
        this.destinationLevel = destinationLevel;
    }

    public Integer getParentDestinationId() {
        return parentDestinationId;
    }

    public void setParentDestinationId(Integer parentDestinationId) {
        this.parentDestinationId = parentDestinationId;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}