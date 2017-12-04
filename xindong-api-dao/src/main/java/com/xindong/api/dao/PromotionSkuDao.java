package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.PromotionSku;
import com.xindong.api.domain.query.PromotionSkuQuery;

public interface PromotionSkuDao {
    int insert(PromotionSku record);

    int updateByPrimaryKey(PromotionSku record);

    PromotionSku selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

	List<PromotionSku> selectByCondition(PromotionSkuQuery promotionSkuQuery);

}