package com.xindong.api.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xindong.api.dao.UserBalanceDao;
import com.xindong.api.dao.UserBalanceDetailDao;
import com.xindong.api.domain.UserBalance;
import com.xindong.api.domain.UserBalanceDetail;
import com.xindong.api.domain.query.UserBalanceDetailQuery;
import com.xindong.api.service.UserBalanceService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.EcUtils;

@Service(value="userBalanceService")
public class UserBalanceServiceImpl implements UserBalanceService {
	private static final Logger log = LoggerFactory.getLogger(UserBalanceServiceImpl.class);
	private UserBalanceDetailDao userBalanceDetailDao;
	private UserBalanceDao userBalanceDao;
	public void setUserBalanceDao(UserBalanceDao userBalanceDao) {
		this.userBalanceDao = userBalanceDao;
	}
	public void setUserBalanceDetailDao(UserBalanceDetailDao userBalanceDetailDao) {
		this.userBalanceDetailDao = userBalanceDetailDao;
	}
	@Override
	public Result getUserBalanceByPage(UserBalanceDetailQuery detailQuery) {
		Result result = new Result();
		try{
			int total = userBalanceDetailDao.countByCondition(detailQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			int totalPage = total/detailQuery.getPageSize() + 1;
			if(detailQuery.getPageNo() > totalPage){
				detailQuery.setPageNo(totalPage);
			}
			List<UserBalanceDetail> list = userBalanceDetailDao.selectByConditionForPage(detailQuery);
           //该用户可用的积分总数
			UserBalance balance = userBalanceDao.selectByUserId(detailQuery.getUserId());
			BigDecimal balanceTotal = BigDecimal.valueOf(0);
			if(balance !=null){
				balanceTotal =balance.getBalanceSum().subtract(balance.getLockedAmount()).subtract
						(balance.getOverdueAmount()).subtract(balance.getUsedAmount());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", detailQuery.getPageNo());
			map.put("list", list);
			map.put("balanceTotal", balanceTotal);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	
}
