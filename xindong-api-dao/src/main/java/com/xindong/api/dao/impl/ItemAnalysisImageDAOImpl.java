package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ItemAnalysisImageDAO;
import com.xindong.api.domain.ItemAnalysisImage;
import com.xindong.api.domain.query.ItemAnalysisImageQuery;

@Repository(value="itemAnalysisImageDAO")
public class ItemAnalysisImageDAOImpl extends SqlMapClientTemplate implements ItemAnalysisImageDAO{

	@Override
	public Integer insert(ItemAnalysisImage itemAnalysisImage) {
		return (Integer) insert("itemAnalysisImage.insert", itemAnalysisImage);
	}

	@Override
	public int updateByPrimaryKey(ItemAnalysisImage itemAnalysisImage) {
		return update("itemAnalysisImage.updateByPrimaryKey", itemAnalysisImage);
	}

	@Override
	public ItemAnalysisImage selectByPrimaryKey(Integer id) {
		return (ItemAnalysisImage) queryForObject("itemAnalysisImage.selectByPrimaryKey", id);
	}

	@Override
	public int countByCondition(ItemAnalysisImageQuery itemAnalysisImageQuery) {
		return (Integer) queryForObject("itemAnalysisImage.countByCondition", itemAnalysisImageQuery);
	}

	@Override
	public List<ItemAnalysisImage> selectByCondition(ItemAnalysisImageQuery itemAnalysisImageQuery) {
		return (List<ItemAnalysisImage>)queryForList("itemAnalysisImage.selectByCondition", itemAnalysisImageQuery);
	}

}
