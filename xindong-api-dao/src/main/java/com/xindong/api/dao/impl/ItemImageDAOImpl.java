package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.ItemImageDAO;
import com.xindong.api.domain.ItemImage;
@Repository(value="itemImageDao")
@SuppressWarnings("unchecked")
public class ItemImageDAOImpl extends SqlMapClientTemplate implements ItemImageDAO {

    public ItemImageDAOImpl() {
        super();
    }

    public Integer insert(ItemImage record) {
    	return (Integer) insert("item_image.insert", record);
    }

    public int updateByPrimaryKey(ItemImage record) {
        int rows = update("item_image.updateByPrimaryKey", record);
        return rows;
    }

    public ItemImage selectByPrimaryKey(Integer id) {
        ItemImage key = new ItemImage();
        key.setId(id);
        ItemImage record = (ItemImage) queryForObject("item_image.selectByPrimaryKey", key);
        return record;
    }


    public int deleteByPrimaryKey(Integer id) {
        ItemImage key = new ItemImage();
        key.setId(id);
        int rows = delete("item_image.deleteByPrimaryKey", key);
        return rows;
    }

	/*@Override
	public List<ItemImage> selectByItemId(Integer itemId) {
		return queryForList("item_image.selectByItemId", itemId);
	}*/

	@Override
	public List<ItemImage> selectByCondition(ItemImage itemq) {
		return  queryForList("item_image.selectByCondition", itemq);
	}

	@Override
	public List<ItemImage> selectByConditionForH5(ItemImage itemq) {
		return  queryForList("item_image.selectByConditionForH5", itemq);
	}

	

}