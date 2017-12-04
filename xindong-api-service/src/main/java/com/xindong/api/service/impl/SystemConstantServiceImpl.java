package com.xindong.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.dao.SystemConstantTypeDao;
import com.xindong.api.dao.SystemConstantValueDao;
import com.xindong.api.domain.SystemConstantType;
import com.xindong.api.domain.SystemConstantValue;
import com.xindong.api.service.SystemConstantService;
import com.xindong.api.service.utils.RedisUtils;
import com.xindong.api.service.utils.RedisValue;


@Service(value = "systemConstantService")
public class SystemConstantServiceImpl implements SystemConstantService{
	@Autowired
	private SystemConstantTypeDao systemConstantTypeDao;
	@Autowired
	private SystemConstantValueDao systemConstantValueDao;

	@Override
	public List<SystemConstantType> selectSystemConstantType(SystemConstantType systemConstantType) {
		List<SystemConstantType> list  = systemConstantTypeDao.selectByCondition(systemConstantType);
		return list;
	}

	@Override
	public List<SystemConstantValue> selectSystemConstantValue(SystemConstantValue systemConstantValue) {
		List<SystemConstantValue> list  = systemConstantValueDao.selectByCondition(systemConstantValue);
		return list;
	}

	/**
	 * 设置系统常量值到缓存 状态
	 */
	@Override
	public void setSystemConstantValues() {
		  SystemConstantValue systemConstantValue =new SystemConstantValue();
		  systemConstantValue.setYn(Constants.YES);
		  List<SystemConstantValue> values = selectSystemConstantValue(systemConstantValue );
		  for(SystemConstantValue sys:values){
			  String name= sys.getName();
			  Integer value =sys.getValue();
			  if(value ==null){
				  continue;
			  }
			  RedisUtils.set(RedisValue.SystemStatusName+sys.getTypeValue()+value.toString(), RedisValue.SystemStatusNameTime, name);
		  }
		  setSystemConstantTypesValues();
	}
	/**
	 * 设置系统常量值到缓存
	 */
	@Override
	public void setSystemConstantTypesValues() {
		 SystemConstantType constantType =new SystemConstantType();
		 constantType.setYn(Constants.YES);
		 List<SystemConstantType> list  = systemConstantTypeDao.selectByCondition(constantType);
		 for(SystemConstantType type:list){
			 RedisUtils.set(type.getName(), RedisValue.SystemStatusNameTime, type.getValue());
		 }
	}

	
}
