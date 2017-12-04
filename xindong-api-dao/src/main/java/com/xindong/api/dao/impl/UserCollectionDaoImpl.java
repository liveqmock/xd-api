package com.xindong.api.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.UserCollectionDao;
import com.xindong.api.domain.UserCollection;
import com.xindong.api.domain.query.UserCollectionQuery;
@Repository(value="userCollectionDao")
@SuppressWarnings("unchecked")
public class UserCollectionDaoImpl extends SqlMapClientTemplate implements UserCollectionDao {

    public UserCollectionDaoImpl() {
        super();
    }

    public int insert(UserCollection record) {
      return (Integer)  insert("user_collection.insert", record);
    }

    public int updateByPrimaryKey(UserCollection record) {
        int rows = update("user_collection.updateByPrimaryKey", record);
        return rows;
    }

    public UserCollection selectByPrimaryKey(Integer collectionId) {
        UserCollection key = new UserCollection();
        key.setCollectionId(collectionId);
        UserCollection record = (UserCollection) queryForObject("user_collection.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer collectionId) {
        UserCollection key = new UserCollection();
        key.setCollectionId(collectionId);
        int rows = delete("user_collection.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public int countByCondition(UserCollectionQuery userCollectionQuery) {
		return (Integer) queryForObject("user_collection.countByCondition", userCollectionQuery);
	}

	
	@Override
	public List<UserCollection> selectByConditionForPage(
			UserCollectionQuery userCollectionQuery) {
		return queryForList("user_collection.selectByConditionForPage", userCollectionQuery);
	}

	@Override
	public void deleteByPrimary(UserCollection userCollection) {
		 delete("user_collection.deleteByPrimary", userCollection);
		
	}
	@Override
	public List<UserCollection> selectByCondition(UserCollectionQuery userCollectionQuery) {
		return queryForList("user_collection.selectByCondition", userCollectionQuery);
	}

   
}