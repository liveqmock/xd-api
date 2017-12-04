package com.xindong.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xindong.api.dao.AddressDao;
import com.xindong.api.domain.Address;
import com.xindong.api.domain.query.AddressQuery;
import com.xindong.api.domain.vo.AddressCity;
import com.xindong.api.domain.vo.AddressCounty;
import com.xindong.api.domain.vo.AddressVO;
import com.xindong.api.service.AddressService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.EcUtils;

@Service(value="addressService")
public class AddressServiceImpl implements AddressService {
	private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
	
	//声明AddressDao接口
	private AddressDao addressDao;
	
	//查询一级地址列表
	@Override
	public Result getProvinces() {
		
		//实例化Result类
		Result result = new Result();
		try{
			//实例化地址信息
			AddressQuery addressQuery = new AddressQuery();
			//调用地址信息类中地址等级为1
			addressQuery.setAddressLevel(1);
			//创建一个地址列表集合，调用addressDao接口中selectByCondition抽象方法查询条件是addressQuery
			List<Address> list = addressDao.selectByCondition(addressQuery);
			//判断list集合是否为空，判断list中值的个数是否为零
			if(list == null || list.size() ==0){
//				//为Result类中resultCode属性赋值
//				result.setResultCode("6001");
//				//为Result类中resultMessage属性赋值
//				result.setResultMessage("地址列表不存在");
//				//返回result对象为null
//				return result;
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			//把list的值赋给Result类的对象result
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	//查询二级地址列表
	@Override
	public Result getCities(Integer province) {
		Result result = new Result();
		try{
			AddressQuery addressQuery = new AddressQuery();
			addressQuery.setAddressLevel(2);
			addressQuery.setAddressFid(province);
			List<Address> list = addressDao.selectByCondition(addressQuery);
			if(list == null || list.size() ==0){
//				result.setResultCode("6001");
//				result.setResultMessage("地址列表不存在");
//				return result;
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	//查询三级地址列表
	@Override
	public Result getCounties(Integer city) {
		Result result = new Result();
		try{
			AddressQuery addressQuery = new AddressQuery();
			addressQuery.setAddressLevel(3);
			addressQuery.setAddressFid(city);
			List<Address> list = addressDao.selectByCondition(addressQuery);
			
			if(list == null || list.size() ==0){
//				result.setResultCode("6001");
//				result.setResultMessage("地址列表不存在");
//				return result;
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getAllAddress() {
		//实例化Result类
		Result result = new Result();
		try{
			//实例化地址信息
			AddressQuery addressQuery = new AddressQuery();
			addressQuery.setAddressLevel(1);
			addressQuery.setAddressId(1);
			//创建一个地址列表集合，调用addressDao接口中selectByCondition抽象方法查询条件是addressQuery
			List<Address> list = addressDao.selectByCondition(addressQuery);
			//判断list集合是否为空，判断list中值的个数是否为零
			if(list == null || list.size() ==0){
//				//为Result类中resultCode属性赋值
//				result.setResultCode("6001");
//				//为Result类中resultMessage属性赋值
//				result.setResultMessage("地址列表不存在");
//				//返回result对象为null
//				return result;
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			List<AddressVO> addressList = new ArrayList<AddressVO>();
			List<AddressCity> addressCityList =null;
			AddressVO addressVO =null;
			for(Address address:list){
				addressCityList = new ArrayList<AddressCity>();
				addressVO = new AddressVO();
				addressVO.setAddressId(address.getAddressId());
				addressVO.setAddressFid(address.getAddressFid());
				addressVO.setAddressLevel(address.getAddressLevel());
				addressVO.setAddressName(address.getAddressName());
				//市地址
				addressQuery.setAddressId(null);
				addressQuery.setAddressLevel(2);
				addressQuery.setAddressFid(address.getAddressId());
				List<Address> addressCities = addressDao.selectByCondition(addressQuery);
				AddressCity addressCity =null;
				for(Address city :addressCities ){
					addressCity = new AddressCity();
					addressCity.setAddressId(city.getAddressId());
					addressCity.setAddressFid(city.getAddressFid());
					addressCity.setAddressLevel(city.getAddressLevel());
					addressCity.setAddressName(city.getAddressName());
					
					addressQuery.setAddressLevel(3);
					addressQuery.setAddressFid(city.getAddressId());
					List<AddressCounty> addressCounties = addressDao.selectByAddressCounty(addressQuery);
					//设置县
					addressCity.setAddressCounties(addressCounties);
					addressCityList.add(addressCity);
				}
				addressVO.setAddressCities(addressCityList);
				addressList.add(addressVO);
			}
			//把list的值赋给Result类的对象result
			result.setResult(addressList);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	//发送addressDao传给接口AddressDao
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	
	
}
