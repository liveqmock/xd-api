package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ItemDescriptionDAO;
import com.xindong.api.domain.ItemDescription;
@Repository(value="itemDescriptionDao")
@SuppressWarnings("unchecked")
public class ItemDescriptionDAOImpl extends SqlMapClientTemplate implements ItemDescriptionDAO {

    public ItemDescriptionDAOImpl() {
        super();
    }

    public Integer insert(ItemDescription record) {
    	return (Integer) insert("item_description.insert", record);
    }

    public int updateByPrimaryKey(ItemDescription record) {
        int rows = update("item_description.updateByPrimaryKey", record);
        return rows;
    }


    public ItemDescription selectByPrimaryKey(Integer id) {
        ItemDescription key = new ItemDescription();
        key.setId(id);
        ItemDescription record = (ItemDescription) queryForObject("item_description.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer id) {
        ItemDescription key = new ItemDescription();
        key.setId(id);
        int rows = delete("item_description.deleteByPrimaryKey", key);
        return rows;
    }

	
	@Override
	public List<ItemDescription> selectByItemId(Integer itemId) {
		return queryForList("item_description.selectByItemId", itemId);
	}




}