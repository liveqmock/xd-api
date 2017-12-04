package com.xindong.api.dao;

import com.xindong.api.domain.OperaLog;

public interface OperaLogDAO {
	Integer insert(OperaLog record);

    int updateByPrimaryKey(OperaLog record);

    OperaLog selectByPrimaryKey(Integer operaLogId);

    int deleteByPrimaryKey(Integer operaLogId);

}