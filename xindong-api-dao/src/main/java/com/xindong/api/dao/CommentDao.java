package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.Comment;
import com.xindong.api.domain.query.CommentQuery;

public interface CommentDao {
    int insert(Comment record);

    int updateByPrimaryKey(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	int countByCondition(CommentQuery commentQuery);

	List<Comment> selectByConditionForPage(CommentQuery commentQuery);

}