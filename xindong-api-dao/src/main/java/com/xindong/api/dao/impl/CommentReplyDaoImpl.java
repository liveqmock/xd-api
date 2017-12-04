package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.CommentReplyDao;
import com.xindong.api.domain.CommentReply;
@Repository(value="commentReplyDao")
public class CommentReplyDaoImpl extends SqlMapClientTemplate implements CommentReplyDao {

    public CommentReplyDaoImpl() {
        super();
    }

    public int insert(CommentReply record) {
        return (Integer)insert("comment_reply.insert", record);
    }

    public int updateByPrimaryKey(CommentReply record) {
        int rows = update("comment_reply.updateByPrimaryKey", record);
        return rows;
    }

    public CommentReply selectByPrimaryKey(Integer id) {
        CommentReply key = new CommentReply();
        key.setId(id);
        CommentReply record = (CommentReply) queryForObject("comment_reply.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        CommentReply key = new CommentReply();
        key.setId(id);
        int rows = delete("comment_reply.deleteByPrimaryKey", key);
        return rows;
    }


    
}