package com.xindong.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.service.ItemService;
import com.xindong.api.service.result.Result;
import com.xindong.api.web.base.BaseController;

/** 商品信息 */

@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 查询商品详情
	 * type 0PC 1H5 默认0
	 * @return
	 */
	@RequestMapping(value="getItemByItemId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getItemByItemId(Integer itemId,Integer type,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(itemId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("商品编号不能为空");
			return result;
		}
		if(type ==null){
			type=0;//
		}
		return itemService.getItemByItemId(itemId,type);
	}
	/**
	 * 商品点赞转发
	 * type（1转发；2点赞）
	 * @return
	 */
	@RequestMapping(value="addItemCount", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addItemCount(Integer itemId,Integer type,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(itemId == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("商品编号不能为空");
			return result;
		}
		if(type == null){
			type =2;
		}
		return itemService.addItemCount(itemId,type);
	}

	/**
	 * 更多推荐
	 * @return
	 */
	@RequestMapping(value="getRecommendItems", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getRecommendItems(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return itemService.getRecommendItems();
	}
	
	/**
	 * 商品热搜
	 * @return
	 */
	@RequestMapping(value="itemHeat", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result itemHeat(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return itemService.getItemHeat();
	}
}
