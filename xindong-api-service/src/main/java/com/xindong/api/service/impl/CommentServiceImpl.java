package com.xindong.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.dao.CommentDao;
import com.xindong.api.dao.ItemDAO;
import com.xindong.api.dao.UserInfoDAO;
import com.xindong.api.dao.UserInfoIncrDAO;
import com.xindong.api.domain.Comment;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.OperationsStaff;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.UserInfoIncr;
import com.xindong.api.domain.query.CommentQuery;
import com.xindong.api.domain.query.OperationsStaffQuery;
import com.xindong.api.domain.vo.CommentVO;
import com.xindong.api.service.CommentService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;
import com.xindong.api.service.utils.RedisUtils;

@Service(value="commentService")
public class CommentServiceImpl implements CommentService {
	
	private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserInfoDAO userInfoDao;
	@Autowired
	private UserInfoIncrDAO userInfoIncrDao;
	@Autowired
	private ItemDAO itemDao;
	
	/**
	 * 获取评论信息(分页)
	 */
	@Override
	public Result getCommentByPage(CommentQuery commentQuery) {
		Result result = new Result();
		commentQuery.setStatus(Constants.SubjectCommentStatus.AUDIT_PASS);
		try{
			int total = commentDao.countByCondition(commentQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			
			int totalPage = total/commentQuery.getPageSize() + 1;
			if(commentQuery.getPageNo() > totalPage){
				commentQuery.setPageNo(totalPage);
			}
			
			List<Comment> list = commentDao.selectByConditionForPage(commentQuery);
			List<CommentVO> resultList = new ArrayList<CommentVO>();
			CommentVO vo =null;
			for (Comment comment : list) {
				vo =new CommentVO(comment);
				resultList.add(vo);
				/*commentReplyQuery.setCommentId(comment.getId());
				Integer replyCount = commentReplyDao.countByCondition(commentReplyQuery);
				comment.setReplyCount(replyCount);*/ 
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", commentQuery.getPageNo());
			map.put("list", resultList);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 获取评论总数、好评数、中评数、差评数
	 */
	@Override
	public Result getCommentCount(CommentQuery commentQuery) {
		Result result = new Result();
		
		try{/*
			// 总评价数
			int totalCount = commentDao.countByCondition(commentQuery);
			
			// 好评评价数
			commentQuery.setMaxScore(5);
			commentQuery.setMinScore(5);
			int positiveCount = commentDao.countByCondition(commentQuery);
			
			// 中评评价数
			commentQuery.setMaxScore(4);
			commentQuery.setMinScore(3);
			int neutralCount = commentDao.countByCondition(commentQuery);
			
			// 差评评价数
			commentQuery.setMaxScore(2);
			commentQuery.setMinScore(1);
			int negativeCount = commentDao.countByCondition(commentQuery);
			
			CommentCountVO commentCountVO = new CommentCountVO();
			commentCountVO.setTotalCount(totalCount);
			commentCountVO.setPositiveCount(positiveCount);
			commentCountVO.setNegativeCount(negativeCount);
			commentCountVO.setNeutralCount(neutralCount);
			
			result.setResult(commentCountVO);
			EcUtils.setSuccessResult(result);
		*/}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
		
	}
	
	/**
	 * 修改评价的有用总数
	 */
	@Override
	public Result updateUsefulCountById(Integer commentId){
		Result result = new Result();
		
		try{
//			commentDao.updateUsefulCountById(commentId);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	
	/**
	 * 添加商品评论
	 */
	@Override
	public Result addComment(Comment comment) {
		Result result = new Result();
		try{
			//1,组装评论内容
			comment.setCommentTime(new Date());
			comment.setTopped(0);//1置顶0未置顶-1沉低
			comment.setDeleted(0);//0：不显示，1：显示
			comment.setStatus(Constants.SubjectCommentStatus.NOT_AUDIT);
			comment.setIntegral(0);//积分
			comment.setBalanceAmount(BigDecimal.valueOf(0));//评论获得数量
			if(comment.getScore() == null){
				comment.setScore(5);
			}
			String userName = null;
			Integer userId = comment.getUserId();
			UserInfoIncr incr = new UserInfoIncr();
			incr.setUserId(userId);
			incr.setYn(Constants.YES);
			List<UserInfoIncr> incrList = userInfoIncrDao.selectByCondition(incr);
			if(incrList == null || incrList.isEmpty()){
				UserInfo user = userInfoDao.selectByPrimaryKey(userId);
				String mobile = user.getMobile();
				if(user == null || mobile == null){
					userName = "匿名用户";
				}else{
					userName = mobile.substring(0,mobile.length()-(mobile.substring(3)).length())+"****"+mobile.substring(7); 
				}
			}else{
				userName = incrList.get(0).getName();
			}
			comment.setUserName(userName);//设置用户昵称
			Item item = itemDao.selectByPrimaryKey(comment.getItemId());
			String itemName = item.getItemName();
			comment.setItemName(itemName);//设置商品名称
			commentDao.insert(comment);
			
			//用户评论发邮件
			String commentAuditor =  RedisUtils.get("COMMENT_AUDITOR");
			String subject = "用户发表评论";
			String message = "用户（" + userId + "）针对商品：" + comment.getItemId() + "（"+ itemName +"）发表了一条评论，请尽快审核。";
			if(StringUtils.isBlank(commentAuditor)){
				commentAuditor = "dong_tiejun@xindong8.com";
				ComFunction.sendMail(commentAuditor, subject, message);
			}else{
				String[] auditors = commentAuditor.split("#");
				for (int i = 0; i < auditors.length; i++) {
					ComFunction.sendMail(auditors[i], subject, message);
				}
			}
			 
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
}
