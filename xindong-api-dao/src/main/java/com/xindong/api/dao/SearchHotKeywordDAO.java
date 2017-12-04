package com.xindong.api.dao;

import com.xindong.api.domain.SearchHotKeyword;

public interface SearchHotKeywordDAO {
	Integer insert(SearchHotKeyword record);

    int updateByPrimaryKey(SearchHotKeyword record);

    SearchHotKeyword selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

}