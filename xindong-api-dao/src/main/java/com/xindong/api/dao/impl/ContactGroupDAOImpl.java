package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ContactGroupDAO;
import com.xindong.api.domain.ContactGroup;
@Repository(value="contactGroupDao")
public class ContactGroupDAOImpl extends SqlMapClientTemplate implements ContactGroupDAO {

    public ContactGroupDAOImpl() {
        super();
    }

    public Integer insert(ContactGroup record) {
    	return (Integer) insert("contact_group.insert", record);
    }

    public int updateByPrimaryKey(ContactGroup record) {
        int rows = update("contact_group.updateByPrimaryKey", record);
        return rows;
    }

    public ContactGroup selectByPrimaryKey(Integer contactId) {
        ContactGroup key = new ContactGroup();
        key.setContactId(contactId);
        ContactGroup record = (ContactGroup) queryForObject("contact_group.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer contactId) {
        ContactGroup key = new ContactGroup();
        key.setContactId(contactId);
        int rows = delete("contact_group.deleteByPrimaryKey", key);
        return rows;
    }



}