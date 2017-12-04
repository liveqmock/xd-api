package com.xindong.api.service;

import com.xindong.api.domain.query.ItemVideoQuery;
import com.xindong.api.service.result.Result;

public interface ItemVideoService {
	
	public Result getItemVideoByPage(ItemVideoQuery itemVideoQuery);
	
}
