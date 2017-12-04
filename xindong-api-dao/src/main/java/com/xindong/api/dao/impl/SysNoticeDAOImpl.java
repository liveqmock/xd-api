package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.SysNoticeDAO;
import com.xindong.api.domain.SysNotice;
@Repository(value="sysNoticeDao")
public class SysNoticeDAOImpl extends SqlMapClientTemplate implements SysNoticeDAO {

    public SysNoticeDAOImpl() {
        super();
    }

    public Integer insert(SysNotice record) {
    	return (Integer) insert("sys_notice.insert", record);
    }

    public int updateByPrimaryKey(SysNotice record) {
        int rows = update("sys_notice.updateByPrimaryKey", record);
        return rows;
    }

    public SysNotice selectByPrimaryKey(Integer noticeId) {
        SysNotice key = new SysNotice();
        key.setNoticeId(noticeId);
        SysNotice record = (SysNotice) queryForObject("sys_notice.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer noticeId) {
        SysNotice key = new SysNotice();
        key.setNoticeId(noticeId);
        int rows = delete("sys_notice.deleteByPrimaryKey", key);
        return rows;
    }

    
}