package com.xindong.api.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.domain.ActivityReply;
import com.xindong.api.domain.query.ActivityDetailQuery;
import com.xindong.api.domain.query.ActivityQuery;
import com.xindong.api.domain.query.ActivityReplyQuery;
import com.xindong.api.service.ActivityService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.web.base.BaseController;

/** 首页信息 */

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 新品看板
	 * @return 
	 */
	@RequestMapping(value="getActivityByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getActivityByPage(ActivityQuery activityQuery ,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return activityService.getActivityByPage(activityQuery);
	}
	
	/**
	 * 活动专区
	 * @return 
	 */
	@RequestMapping(value="activityList", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result ActivityList(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return activityService.getActivityList();
	}
	
	/**
	 * 活动详情页
	 * @return
	 */
	@RequestMapping(value="getActivityDetail", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getActivityDetail(ActivityDetailQuery activityDetailQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		if(activityDetailQuery.getActivityId() ==null){
			result.setResultCode("1001");
			result.setResultMessage("活动编号不能为空");
			return result;
		}
		return activityService.getActivityDetail(activityDetailQuery);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="askActivity", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result askActivity(Integer activityId ,String activityReplyIntro,String userPhone,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		if(activityId ==null){
			result.setResultCode("1001");
			result.setResultMessage("活动编号不能为空");
			return result;
		}
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(StringUtils.isBlank(userPhone)){
			result.setResultCode("1001");
			result.setResultMessage("请您填写手机号码");
			return result;
		}
		if(StringUtils.isBlank(activityReplyIntro)){
			result.setResultCode("1001");
			result.setResultMessage("请您填写咨询内容");
			return result;
		}
		ActivityReply activityReply =new ActivityReply();
		activityReply.setUserId(userId);
		activityReply.setActivityReplyIntro(activityReplyIntro);
		activityReply.setUserPhone(userPhone);
		activityReply.setActivityId(activityId);
		activityReply.setReplayDate(new Date());
		return activityService.askActivity(activityReply);
	}
	/**
	 *我的回复列表
	 * @return
	 */
	@RequestMapping(value="getAskActivityByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getAskActivityByPage(ActivityReplyQuery activityReplyQuery ,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId =ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		activityReplyQuery.setUserId(userId);
		return activityService.getAskActivityByPage(activityReplyQuery);
	}
	/**
	 * 活动点赞转发
	 * type（1转发；2点赞）
	 * @return
	 */
	@RequestMapping(value="addActivityCount", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addActivityCount(Integer id, Integer activityId,Integer type,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(activityId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("活动编号不能为空");
			return result;
		}
		if(type == null){
			type =2;
		}
		return activityService.addActivityCount(id,activityId,type);
	}
}
