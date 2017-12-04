package com.xindong.api.dao;

import java.util.List;

import com.xindong.api.domain.UserCollection;
import com.xindong.api.domain.query.UserCollectionQuery;

public interface UserCollectionDao {
    int insert(UserCollection record);

    int updateByPrimaryKey(UserCollection record);

    UserCollection selectByPrimaryKey(Integer collectionId);

    int deleteByPrimaryKey(Integer collectionId);

	int countByCondition(UserCollectionQuery userCollectionQuery);

	List<UserCollection> selectByConditionForPage(
			UserCollectionQuery userCollectionQuery);

	void deleteByPrimary(UserCollection userCollection);

	List<UserCollection> selectByCondition(UserCollectionQuery userCollectionQuery);

}