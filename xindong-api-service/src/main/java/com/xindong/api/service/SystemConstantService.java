package com.xindong.api.service;

import java.util.List;

import com.xindong.api.domain.SystemConstantType;
import com.xindong.api.domain.SystemConstantValue;

public interface SystemConstantService {
	public List<SystemConstantType> selectSystemConstantType(SystemConstantType systemConstantType);
	public List<SystemConstantValue> selectSystemConstantValue(SystemConstantValue systemConstantValue);
	public void setSystemConstantValues();
	public void setSystemConstantTypesValues();
}
