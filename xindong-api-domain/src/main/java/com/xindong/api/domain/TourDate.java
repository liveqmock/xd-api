package com.xindong.api.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 旅游时间
 * @author lichaoxiong
 *
 */
public class TourDate implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer dateId;

    private String dateName;

    private Integer dateLevel;

    private Integer parentDateId;

    private Integer yn;

    private Date created;

    private Date modified;

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

    public Integer getDateLevel() {
        return dateLevel;
    }

    public void setDateLevel(Integer dateLevel) {
        this.dateLevel = dateLevel;
    }

    public Integer getParentDateId() {
        return parentDateId;
    }

    public void setParentDateId(Integer parentDateId) {
        this.parentDateId = parentDateId;
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