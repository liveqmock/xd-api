package com.xindong.api.dao;

import com.xindong.api.domain.Partner;

public interface PartnerDAO {
	Integer insert(Partner record);

    int updateByPrimaryKey(Partner record);

    Partner selectByPrimaryKey(Integer partnerId);

    int deleteByPrimaryKey(Integer partnerId);

}