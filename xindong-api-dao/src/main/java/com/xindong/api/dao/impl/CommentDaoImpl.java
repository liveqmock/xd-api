package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.CommentDao;
import com.xindong.api.domain.Comment;
import com.xindong.api.domain.query.CommentQuery;
@Repository(value="commentDao")
public class CommentDaoImpl extends SqlMapClientTemplate implements CommentDao {

    public CommentDaoImpl() {
        super();
    }

    public int  insert(Comment record) {
       return   (Integer)insert("comment.insert", record);
    }

    public int updateByPrimaryKey(Comment record) {
        int rows = update("comment.updateByPrimaryKey", record);
        return rows;
    }


    public Comment selectByPrimaryKey(Integer id) {
        Comment key = new Comment();
        key.setId(id);
        Comment record = (Comment) queryForObject("comment.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer id) {
        Comment key = new Comment();
        key.setId(id);
        int rows = delete("comment.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public int countByCondition(CommentQuery commentQuery) {
		return (Integer) queryForObject("comment.countByCondition", commentQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> selectByConditionForPage(CommentQuery commentQuery) {
		return queryForList("comment.selectByConditionForPage", commentQuery);
	}




}