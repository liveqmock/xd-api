package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.AdImgDao;
import com.xindong.api.domain.AdImg;
@Repository(value="adImgDaoDao")
@SuppressWarnings("unchecked")
public class AdImgDaoImpl extends SqlMapClientTemplate implements AdImgDao {

    public AdImgDaoImpl() {
        super();
    }

    public void insert(AdImg record) {
        insert("ad_img.insert", record);
    }

    public int updateByPrimaryKey(AdImg record) {
        int rows = update("ad_img.updateByPrimaryKey", record);
        return rows;
    }


    public AdImg selectByPrimaryKey(Integer id) {
        AdImg key = new AdImg();
        key.setId(id);
        AdImg record = (AdImg) queryForObject("ad_img.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer id) {
        AdImg key = new AdImg();
        key.setId(id);
        int rows = delete("ad_img.deleteByPrimaryKey", key);
        return rows;
    }

	
	@Override
	public List<AdImg> selectByCondition(AdImg img) {
		return queryForList("ad_img.selectByCondition", img);
	}



   
}