package com.xindong.api.dao;

import com.xindong.api.domain.CommentReply;

public interface CommentReplyDao {
    int insert(CommentReply record);

    int updateByPrimaryKey(CommentReply record);

    CommentReply selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

}