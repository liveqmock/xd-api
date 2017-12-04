package com.xindong.api.service;

import com.xindong.api.service.result.Result;

public interface ItemService {

	Result getItemByItemId(Integer itemId, Integer type);

	Result addItemCount(Integer itemId, Integer type);

	Result getRecommendItems();
	
	Result getItemHeat();
}
