package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.OperaLogDAO;
import com.xindong.api.domain.OperaLog;
@Repository(value="operaLogDao")
public class OperaLogDAOImpl extends SqlMapClientTemplate implements OperaLogDAO {

    public OperaLogDAOImpl() {
        super();
    }

    public Integer insert(OperaLog record) {
        return (Integer) insert("opera_log.insert", record);
    }

    public int updateByPrimaryKey(OperaLog record) {
        int rows = update("opera_log.updateByPrimaryKey", record);
        return rows;
    }

    public OperaLog selectByPrimaryKey(Integer operaLogId) {
        OperaLog key = new OperaLog();
        key.setOperaLogId(operaLogId);
        OperaLog record = (OperaLog) queryForObject("opera_log.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer operaLogId) {
        OperaLog key = new OperaLog();
        key.setOperaLogId(operaLogId);
        int rows = delete("opera_log.deleteByPrimaryKey", key);
        return rows;
    }


  
}