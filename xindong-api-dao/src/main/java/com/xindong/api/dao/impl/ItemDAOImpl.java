package com.xindong.api.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ItemDAO;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.query.ItemQuery;
@Repository(value="itemDao")
@SuppressWarnings("unchecked")
public class ItemDAOImpl extends SqlMapClientTemplate implements ItemDAO {

    public ItemDAOImpl() {
        super();
    }

    public Integer insert(Item record) {
    	return  (Integer) insert("item.insert", record);
    }

    public int updateByPrimaryKey(Item record) {
        int rows =update("item.updateByPrimaryKey", record);
        return rows;
    }

    public Item selectByPrimaryKey(Integer itemId) {
        Item key = new Item();
        key.setItemId(itemId);
        Item record = (Item)queryForObject("item.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer itemId) {
        Item key = new Item();
        key.setItemId(itemId);
        int rows =delete("item.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<Item> selectByCondition(ItemQuery itemQuery) {
		return queryForList("item.selectByCondition", itemQuery);
	}

	@Override
	public List<Item> selectByConditionForPage(ItemQuery itemQuery) {
		return queryForList("item.selectByConditionForPage", itemQuery);
	}

	@Override
	public List<Item> selectOthersByConditionForPage(ItemQuery itemQuery) {
		return queryForList("item.selectOthersByConditionForPage", itemQuery);
	}

	@Override
	public String selectByskuId(Integer skuId) {
		return (String) queryForObject("item.selectByskuId", skuId);
	}



}