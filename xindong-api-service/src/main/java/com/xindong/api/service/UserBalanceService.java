package com.xindong.api.service;

import com.xindong.api.domain.query.UserBalanceDetailQuery;
import com.xindong.api.service.result.Result;

public interface UserBalanceService {
	
	public Result getUserBalanceByPage(UserBalanceDetailQuery indexPromItemQuery);
}
