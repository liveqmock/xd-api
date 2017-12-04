package com.xindong.api.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.service.AddressService;
import com.xindong.api.service.result.Result;
import com.xindong.api.web.base.BaseController;

/** 地址列表  */

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(AddressController.class);
	
	private AddressService addressService;
	
	/**
	 * 查询所有地址列表
	 * 按层级关系
	 * @return
	 */
	@RequestMapping(value="getAllAddress", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getAllAddress(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return addressService.getAllAddress();
	}
	/**
	 * 查询一级地址列表
	 * @return
	 */
	@RequestMapping(value="getProvinces", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getProvinces(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return addressService.getProvinces();
	}
	
	/**
	 * 查询二级地址列表
	 * @param province 地址信息的父ID
	 * @return
	 */
	@RequestMapping(value="getCities", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCities(Integer province, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		//验证省份的地址等级是否为空
		if(province == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("province不能为空");
			return result;
		}
		return addressService.getCities(province);
	}
	
	/**
	 * 查询三级地址列表
	 * @param city 地址信息的父ID
	 * @return
	 */
	@RequestMapping(value="getCounties", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCounties(Integer city, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		//验证城市的父ID是否为空
		if(city == null){
			Result result = new Result();
			result.setResultCode("1001");
			result.setResultMessage("city不能为空");
			return result;
		}
		return addressService.getCounties(city);
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

}
