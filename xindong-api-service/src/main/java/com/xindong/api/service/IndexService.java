package com.xindong.api.service;

import com.xindong.api.service.result.Result;

public interface IndexService {
	/**
	 * 获取首页轮播图
	 * @param type 0-首次打开;1-首页
	 * @return
	 */
	public Result getIndexImages(Integer type);

	public Result getIndex();

	public Result getIndexAds();

	public Result getH5Index();

	public Result getIndexRecommendItems();

	public Result getIndexSeriesDetail(Integer seriesId);

	public Result getDestinations();

	public Result getH5IndexFind();

	public Result getPcIndexSeries(Integer type);
	
	public Result getIndexRecommendPcItems();
	
	public Result getPcIndexSeriesDetail(Integer seriesId);
	
	public Result getIndexSelected();
}
