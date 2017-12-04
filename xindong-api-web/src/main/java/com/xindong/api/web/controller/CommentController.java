package com.xindong.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.domain.Comment;
import com.xindong.api.domain.query.CommentQuery;
import com.xindong.api.service.CommentService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;
import com.xindong.api.service.utils.RedisUtils;
import com.xindong.api.web.base.BaseController;

/** 评论，评价信息  */

@Controller
@RequestMapping("comment")
public class CommentController extends BaseController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);
	
	private CommentService commentService;
	
	@RequestMapping(value="index", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result index(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		//出行后 6天内（含）完成评论并通过审核，可获得  10元奖励，出行后  30天以上完成评论并通过审核，可获得30元奖励。
		StringBuffer sb =  new StringBuffer();
		
		String commentBeforDay = RedisUtils.get("COMMENT_BEFOR_DAY");//评论之前天数
		String commentBeforBal = RedisUtils.get("COMMENT_BEFOR_BAL");//评论天数之前奖励积分
		String commentAfterDay = RedisUtils.get("COMMENT_AFTER_DAY");//评论之后天数
		String commentAfterBal = RedisUtils.get("COMMENT_AFTER_BAL");//评论天数之后奖励积分
		if(StringUtils.isBlank(commentBeforDay)){
			commentBeforDay = "7";
			RedisUtils.set("COMMENT_BEFOR_DAY", commentBeforDay);
		}
		if(StringUtils.isBlank(commentBeforBal)){
			commentBeforBal = "20";
			RedisUtils.set("COMMENT_BEFOR_BAL", commentBeforBal);
		}
		if(StringUtils.isBlank(commentAfterDay)){
			commentAfterDay = "7";
			RedisUtils.set("COMMENT_AFTER_DAY", commentAfterDay);
		}
		if(StringUtils.isBlank(commentAfterBal)){
			commentAfterBal = "10";
			RedisUtils.set("COMMENT_AFTER_BAL", commentAfterBal);
		}
		sb.append("出行后");
		sb.append(commentBeforDay);
		sb.append("天内（含）完成评论并通过审核，可获得");
		sb.append(commentBeforBal);
		sb.append("元奖励，");
		sb.append("出行后");
		sb.append(commentAfterDay);
		sb.append("天以上完成评论并通过审核，可获得");
		sb.append(commentAfterBal);
		sb.append("元奖励。");
		
		result.setResult(sb.toString());
		EcUtils.setSuccessResult(result);
		return result;
	}
	
	/**
	 * 获取商品评论
	 * @param commentQuery 评论申请
	 * @return
	 */
	@RequestMapping(value="addComment", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addComment(Comment comment ,String token, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isNotEmpty(token)){
			Integer userId = ComFunction.getUserId(token);
			comment.setUserId(userId);
		}else{
			result.setResultCode("1001");
			result.setResultMessage("请先登录再评价");
			return result;
		}
		return commentService.addComment(comment);
	}
	
	
	/**
	 * 获取评论信息总数
	 * @param commentQuery 评论申请
	 * @return
	 */
	@RequestMapping(value="getCommentCount", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCommentCount(CommentQuery commentQuery, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(commentQuery.getItemId() == null){
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		
		return commentService.getCommentCount(commentQuery);
	}
	
	/**
	 * 获取评论信息(分页)
	 * @param commentQuery 评价请求
	 * @return
	 */
	@RequestMapping(value="getCommentByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCommentByPage(CommentQuery commentQuery,String token, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(commentQuery.getItemId() == null){
			result.setResultCode("1001");
			result.setResultMessage("itemId不能为空");
			return result;
		}
		commentQuery.setUserId(ComFunction.getUserId(token));
		return commentService.getCommentByPage(commentQuery);
	}
	
	/**
	 * 修改评价的有用总数
	 * @param commentId 评价id
	 * @return
	 */
	@RequestMapping(value="updateUsefulCountById", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result updateUsefulCountById(Integer commentId, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(commentId == null){
			result.setResultCode("1001");
			result.setResultMessage("commentId不能为空");
			return result;
		}
		
		return commentService.updateUsefulCountById(commentId);
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
}
