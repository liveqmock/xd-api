package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.SkuDAO;
import com.xindong.api.domain.Sku;
@Repository(value="skuDao")
@SuppressWarnings("unchecked")
public class SkuDAOImpl extends SqlMapClientTemplate implements SkuDAO {

    public SkuDAOImpl() {
        super();
    }

    public Integer insert(Sku record) {
    	return (Integer) insert("sku.insert", record);
    }

    public int updateByPrimaryKey(Sku record) {
        int rows = update("sku.updateByPrimaryKey", record);
        return rows;
    }


    public Sku selectByPrimaryKey(Integer skuId) {
        Sku key = new Sku();
        key.setSkuId(skuId);
        Sku record = (Sku) queryForObject("sku.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer skuId) {
        Sku key = new Sku();
        key.setSkuId(skuId);
        int rows = delete("sku.deleteByPrimaryKey", key);
        return rows;
    }

	
	@Override
	public List<Sku> selectByCondition(Sku sku) {
		return queryForList("sku.selectByCondition", sku);
	}

   
}