package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.SmsDAO;
import com.xindong.api.domain.Sms;
@Repository(value="smsDao")
public class SmsDAOImpl extends SqlMapClientTemplate implements SmsDAO {

    public SmsDAOImpl() {
        super();
    }

    public Integer insert(Sms record) {
    	return  (Integer) insert("sms.insert", record);
    }

    public int updateByPrimaryKey(Sms record) {
        int rows = update("sms.updateByPrimaryKey", record);
        return rows;
    }

    public Sms selectByPrimaryKey(Integer id) {
        Sms key = new Sms();
        key.setId(id);
        Sms record = (Sms) queryForObject("sms.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        Sms key = new Sms();
        key.setId(id);
        int rows = delete("sms.deleteByPrimaryKey", key);
        return rows;
    }

    
}