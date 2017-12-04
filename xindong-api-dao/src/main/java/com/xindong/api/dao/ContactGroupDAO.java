package com.xindong.api.dao;

import com.xindong.api.domain.ContactGroup;

public interface ContactGroupDAO {
	Integer insert(ContactGroup record);

    int updateByPrimaryKey(ContactGroup record);

    ContactGroup selectByPrimaryKey(Integer contactId);

    int deleteByPrimaryKey(Integer contactId);

}