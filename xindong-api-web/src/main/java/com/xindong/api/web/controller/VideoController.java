package com.xindong.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.domain.query.ItemVideoQuery;
import com.xindong.api.service.ItemVideoService;
import com.xindong.api.service.result.Result;
import com.xindong.api.web.base.BaseController;

/** 视频信息 */
@Controller
@RequestMapping("/video")
public class VideoController extends BaseController{
	
	@Autowired
	private ItemVideoService itemVideoService;
	
	/**
	 * 视频列表
	 * @return
	 */
	@RequestMapping(value="videoList", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result videoList(ItemVideoQuery itemVideoQuery, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		itemVideoQuery.setYn(Constants.YES);
		return itemVideoService.getItemVideoByPage(itemVideoQuery);
	}
	
}
