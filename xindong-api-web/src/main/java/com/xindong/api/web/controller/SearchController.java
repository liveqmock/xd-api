package com.xindong.api.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.domain.query.SearchQuery;
import com.xindong.api.service.SearchService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.EcUtils;
import com.xindong.api.service.utils.RedisUtils;
import com.xindong.api.web.base.BaseController;

/** 筛选列表页面 */

@Controller
@RequestMapping("/search")
public class SearchController extends BaseController {
	@Autowired
	private SearchService searchService;
	private static final Logger log = LoggerFactory.getLogger(SearchController.class);
	/**
	 * 筛选列表页面
	 * @return
	 */
	@RequestMapping(value="getSearchItems", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getSearchItems(SearchQuery searchQuery,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		log.error("开始0关键词为" + searchQuery.getN());
		return searchService.getSearchItems(searchQuery);
	}

	/**
	 * 筛选列表页面-查看更多
	 * @return
	 */
	@RequestMapping(value="getSearchItemsByPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getSearchItemsByPage (SearchQuery searchQuery,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		log.error("开始0关键词为" + searchQuery.getN());
		return searchService.getSearchItemsByPage(searchQuery);
	}
	
	/**
	 *搜索关键词
	 * @return
	 */
	@RequestMapping(value="getKeyword", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result keyword(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		String keyword = RedisUtils.get("SEARCH_KEYWORD");
		String[] kws = keyword.split("#");
		int len = kws.length;
		if(len == 1){
		}else{
			int ran = (int)(Math.random()*(len)) ;
			keyword = kws[ran];
		}
		result.setResult(keyword);
		EcUtils.setSuccessResult(result);
		return result;
	}
	
	/**
	 *搜索自动补全
	 * @return
	 */
	@RequestMapping(value="autoComplete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result autoComplete(String  keyword, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(keyword)){
			return result;
		}
		List<String> strs = new ArrayList<String>();
		Set<String> content = RedisUtils.zrevrange(keyword,0,9);
        for(Iterator iterator = content.iterator(); iterator.hasNext();) {
        	String string = (String) iterator.next();
        	strs.add(string);
        }
		//result = searchService.autoComplete(keyword);
        result.setResult(strs);
        EcUtils.setSuccessResult(result);
		return result;
	}
}
