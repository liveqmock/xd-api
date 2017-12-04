package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.ItemAnalysisImage;
import com.xindong.api.domain.query.ItemAnalysisImageQuery;

public interface ItemAnalysisImageDAO {
	/**
	 * 添加
	 * @param customized
	 * @return
	 */
	Integer insert(ItemAnalysisImage itemAnalysisImage);

	/**
	 * 修改
	 * @param customized
	 * @return
	 */
    int updateByPrimaryKey(ItemAnalysisImage itemAnalysisImage);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ItemAnalysisImage selectByPrimaryKey(Integer id);

    /**
     *  查询总条数
     * @param customizedQuery
     * @return
     */
	int countByCondition(ItemAnalysisImageQuery itemAnalysisImageQuery);

	/**
	 * 根据条件查询
	 * @param customizedQuery
	 * @return
	 */
	List<ItemAnalysisImage> selectByCondition(ItemAnalysisImageQuery itemAnalysisImageQuery);

}