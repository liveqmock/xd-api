package com.xindong.api.service;

import com.xindong.api.domain.Comment;
import com.xindong.api.domain.query.CommentQuery;
import com.xindong.api.service.result.Result;

public interface CommentService {

	/**
	 * 获取评论信息(分页)
	 * @param CommentQuery
	 * @return
	 */
	public Result getCommentByPage(CommentQuery commentQuery);
	
	/**
	 * 获取评论总数、好评数、中评数、差评数
	 * @param CommentQuery
	 * @return
	 */
	public Result getCommentCount(CommentQuery commentQuery);
	
	/**
	 * 修改评价的有用总数
	 * @param Comment
	 * @return
	 */
	public Result updateUsefulCountById(Integer commentId);
	
	
	/**
	 * 添加商品评论
	 * @param Comment
	 * @return
	 */
	public Result addComment(Comment comment);
}




