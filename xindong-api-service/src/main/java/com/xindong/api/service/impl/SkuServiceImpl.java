package com.xindong.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xindong.api.dao.SkuDAO;
import com.xindong.api.domain.Sku;
import com.xindong.api.service.SkuService;

@Service(value="skuService")
public class SkuServiceImpl implements SkuService {
	@Autowired
	private SkuDAO skuDao;
	@Override
	public Sku selectByPrimaryKey(Integer skuId) {
		return skuDao.selectByPrimaryKey(skuId);
	}

}
