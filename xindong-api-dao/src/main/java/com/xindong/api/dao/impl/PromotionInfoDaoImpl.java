package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.PromotionInfoDao;
import com.xindong.api.domain.PromotionInfo;
import com.xindong.api.domain.query.PromotionInfoQuery;
@Repository(value="promotionInfoDao")
public class PromotionInfoDaoImpl extends SqlMapClientTemplate implements PromotionInfoDao {

    public PromotionInfoDaoImpl() {
        super();
    }

    public int insert(PromotionInfo record) {
       return (Integer) insert("promotion_info.insert", record);
    }

    public int updateByPrimaryKey(PromotionInfo record) {
        int rows = update("promotion_info.updateByPrimaryKey", record);
        return rows;
    }

    public PromotionInfo selectByPrimaryKey(Integer promotionId) {
        PromotionInfo key = new PromotionInfo();
        key.setPromotionId(promotionId);
        PromotionInfo record = (PromotionInfo) queryForObject("promotion_info.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer promotionId) {
        PromotionInfo key = new PromotionInfo();
        key.setPromotionId(promotionId);
        int rows = delete("promotion_info.deleteByPrimaryKey", key);
        return rows;
    }
    
	@Override
	public List<PromotionInfo> selectByCondition(PromotionInfoQuery promotionInfoQuery) {
		return queryForList("promotion_info.selectByCondition", promotionInfoQuery);
	}

}