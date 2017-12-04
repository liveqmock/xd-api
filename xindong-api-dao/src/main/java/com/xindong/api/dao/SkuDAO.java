package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.Sku;

public interface SkuDAO {
	Integer insert(Sku record);

    int updateByPrimaryKey(Sku record);

    Sku selectByPrimaryKey(Integer skuId);

    int deleteByPrimaryKey(Integer skuId);

	List<Sku> selectByCondition(Sku sku);

}