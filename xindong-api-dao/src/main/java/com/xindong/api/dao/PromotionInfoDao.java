package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.PromotionInfo;
import com.xindong.api.domain.query.PromotionInfoQuery;

public interface PromotionInfoDao {
    int insert(PromotionInfo record);

    int updateByPrimaryKey(PromotionInfo record);

    PromotionInfo selectByPrimaryKey(Integer promotionId);

    int deleteByPrimaryKey(Integer promotionId);

    List<PromotionInfo> selectByCondition(PromotionInfoQuery promotionInfoQuery);

}