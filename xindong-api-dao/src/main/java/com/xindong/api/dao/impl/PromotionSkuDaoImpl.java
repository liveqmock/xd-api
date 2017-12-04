package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.PromotionSkuDao;
import com.xindong.api.domain.PromotionSku;
import com.xindong.api.domain.query.PromotionSkuQuery;
@Repository(value="promotionSkuDao")
public class PromotionSkuDaoImpl extends SqlMapClientTemplate implements PromotionSkuDao {

    public PromotionSkuDaoImpl() {
        super();
    }

    public int insert(PromotionSku record) {
       return (Integer) insert("promotion_sku.insert", record);
    }

    public int updateByPrimaryKey(PromotionSku record) {
        int rows = update("promotion_sku.updateByPrimaryKey", record);
        return rows;
    }

    public PromotionSku selectByPrimaryKey(Integer id) {
        PromotionSku key = new PromotionSku();
        key.setId(id);
        PromotionSku record = (PromotionSku) queryForObject("promotion_sku.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        PromotionSku key = new PromotionSku();
        key.setId(id);
        int rows = delete("promotion_sku.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<PromotionSku> selectByCondition(PromotionSkuQuery promotionSkuQuery) {
		return queryForList("promotion_sku.selectByCondition", promotionSkuQuery);
	}

   
}