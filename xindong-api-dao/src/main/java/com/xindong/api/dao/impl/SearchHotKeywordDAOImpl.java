package com.xindong.api.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.xindong.api.dao.SearchHotKeywordDAO;
import com.xindong.api.domain.SearchHotKeyword;
@Repository(value="searchHotKeywordDao")
public class SearchHotKeywordDAOImpl extends SqlMapClientTemplate implements SearchHotKeywordDAO {

    public SearchHotKeywordDAOImpl() {
        super();
    }

    public Integer insert(SearchHotKeyword record) {
    	return (Integer) insert("search_hot_keyword.insert", record);
    }

    public int updateByPrimaryKey(SearchHotKeyword record) {
        int rows = update("search_hot_keyword.updateByPrimaryKey", record);
        return rows;
    }

    public SearchHotKeyword selectByPrimaryKey(Integer id) {
        SearchHotKeyword key = new SearchHotKeyword();
        key.setId(id);
        SearchHotKeyword record = (SearchHotKeyword) queryForObject("search_hot_keyword.selectByPrimaryKey", key);
        return record;
    }

    public int deleteByPrimaryKey(Integer id) {
        SearchHotKeyword key = new SearchHotKeyword();
        key.setId(id);
        int rows = delete("search_hot_keyword.deleteByPrimaryKey", key);
        return rows;
    }

    
}