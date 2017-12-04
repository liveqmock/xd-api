package com.xindong.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.service.IndexService;
import com.xindong.api.service.result.Result;
import com.xindong.api.web.base.BaseController;

/** 首页信息 */

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	@Autowired
	private IndexService indexService;
	
	/**
	 * 首页轮播图接口信息
	 * @return
	 */
	@RequestMapping(value="getIndexImages", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexImages(Integer type,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getIndexImages(type);
	}

	/**
	 * 首页信息
	 * @return
	 */
	@RequestMapping(value="getIndex", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndex(Integer type,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getIndex();
	}
	
	/**
	 * 首页广告接口
	 * @return
	 */
	@RequestMapping(value="getIndexAds", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexAds(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getIndexAds();
	}
	/**
	 *h5 首页接口信息
	 * @return
	 */
	@RequestMapping(value="getH5Index", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getH5Index(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getH5Index();
	}
	/**
	 *h5 首页推荐商品信息
	 * @return
	 */
	@RequestMapping(value="getIndexRecommendItems", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexRecommendItems(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getIndexRecommendItems();
	}
	/**
	 *h5 首页系列信息
	 * @return
	 */
	@RequestMapping(value="getIndexSeriesDetail", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexSeriesDetail(Integer seriesId,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getIndexSeriesDetail(seriesId);
	}
	
	/**
	 *PC 首页系列详情信息
	 * @return
	 */
	@RequestMapping(value="getPcIndexSeriesDetail", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getPcIndexSeriesDetail(Integer seriesId,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getPcIndexSeriesDetail(seriesId);
	}
	
	/**
	 *目的地列表
	 * @return
	 */
	@RequestMapping(value="getDestinations", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getDestinations(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getDestinations();
	}
	/**
	 *h5 首页发现接口信息
	 * @return
	 */
	@RequestMapping(value="getH5IndexFind", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getH5IndexFind(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getH5IndexFind();
	}
	
	/**
	 *PC首页系列接口信息
	 * @return
	 */
	@RequestMapping(value="getPcIndexSeries", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getPcIndexSeries(Integer type, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getPcIndexSeries(type);
	}
	
	/**
	 *PC搜索推荐商品
	 * @return
	 */
	@RequestMapping(value="getIndexRecommendPcItems", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexRecommendPcItems(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getIndexRecommendPcItems();
	}
	
	/**
	 *PC心动特选
	 * @return
	 */
	@RequestMapping(value="getIndexSelected", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getIndexSelected(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return indexService.getIndexSelected();
	}
}
