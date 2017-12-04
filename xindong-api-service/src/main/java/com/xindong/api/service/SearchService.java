package com.xindong.api.service;

import com.xindong.api.domain.query.SearchQuery;
import com.xindong.api.service.result.Result;

public interface SearchService {

	Result getSearchItemsByPage(SearchQuery searchQuery);

	Result getSearchItems(SearchQuery searchQuery);
	
	Result autoComplete(String keyword);
}
