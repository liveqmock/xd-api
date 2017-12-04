package com.xindong.api.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ItemTourFlagDAO;
import com.xindong.api.domain.ItemTourFlag;
@Repository(value="itemTourFlagDao")
@SuppressWarnings("unchecked")
public class ItemTourFlagDAOImpl extends SqlMapClientTemplate implements ItemTourFlagDAO {

    public ItemTourFlagDAOImpl() {
        super();
    }

    public Integer insert(ItemTourFlag record) {
        return (Integer) insert("item_tour_flag.insert", record);
    }

    public int updateByPrimaryKey(ItemTourFlag record) {
        int rows = update("item_tour_flag.updateByPrimaryKey", record);
        return rows;
    }

    public ItemTourFlag selectByPrimaryKey(Integer id) {
        ItemTourFlag key = new ItemTourFlag();
        key.setId(id);
        ItemTourFlag record = (ItemTourFlag) queryForObject("item_tour_flag.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        ItemTourFlag key = new ItemTourFlag();
        key.setId(id);
        int rows = delete("item_tour_flag.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<ItemTourFlag> selectByCondition(ItemTourFlag flag) {
		return queryForList("item_tour_flag.selectByCondition", flag);
	}

   
}