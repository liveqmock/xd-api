package com.xindong.api.dao;

import com.xindong.api.domain.Sms;

public interface SmsDAO {
	Integer insert(Sms record);

    int updateByPrimaryKey(Sms record);

    Sms selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

}