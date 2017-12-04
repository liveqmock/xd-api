package com.xindong.api.dao;

import com.xindong.api.domain.SysNotice;

public interface SysNoticeDAO {
	Integer insert(SysNotice record);

    int updateByPrimaryKey(SysNotice record);

    SysNotice selectByPrimaryKey(Integer noticeId);

    int deleteByPrimaryKey(Integer noticeId);

}