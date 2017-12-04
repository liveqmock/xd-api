package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.PartnerDAO;
import com.xindong.api.domain.Partner;
@Repository(value="partnerDao")
public class PartnerDAOImpl extends SqlMapClientTemplate implements PartnerDAO {

    public PartnerDAOImpl() {
        super();
    }

    public Integer insert(Partner record) {
    	return (Integer) insert("partner.insert", record);
    }

    public int updateByPrimaryKey(Partner record) {
        int rows = update("partner.updateByPrimaryKey", record);
        return rows;
    }

    public Partner selectByPrimaryKey(Integer partnerId) {
        Partner key = new Partner();
        key.setPartnerId(partnerId);
        Partner record = (Partner) queryForObject("partner.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer partnerId) {
        Partner key = new Partner();
        key.setPartnerId(partnerId);
        int rows = delete("partner.deleteByPrimaryKey", key);
        return rows;
    }

   
}