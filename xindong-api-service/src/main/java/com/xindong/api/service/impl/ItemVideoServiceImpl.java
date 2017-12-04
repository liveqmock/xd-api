package com.xindong.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.dao.ItemDAO;
import com.xindong.api.dao.ItemImageDAO;
import com.xindong.api.dao.ItemVideoDao;
import com.xindong.api.dao.PromotionInfoDao;
import com.xindong.api.dao.PromotionSkuDao;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.ItemVideo;
import com.xindong.api.domain.PromotionInfo;
import com.xindong.api.domain.PromotionSku;
import com.xindong.api.domain.query.ItemVideoQuery;
import com.xindong.api.domain.query.PromotionSkuQuery;
import com.xindong.api.service.ItemVideoService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.EcUtils;

@Service(value="itemVideoService")
public class ItemVideoServiceImpl implements ItemVideoService{

	private static final Logger log = LoggerFactory.getLogger(ItemVideoServiceImpl.class);
	@Autowired
	private ItemVideoDao itemVideoDao;
	@Autowired
	private ItemDAO itemDao;
	@Autowired
	private ItemImageDAO imageDao;
	@Autowired
	private PromotionSkuDao promotionSkuDao;
	@Autowired
	private PromotionInfoDao promotionInfoDao;
	
	@Override
	public Result getItemVideoByPage(ItemVideoQuery itemVideoQuery) {
		Result result = new Result();
		Map<String, Object> map=new HashMap<String, Object>();
		try{
			itemVideoQuery.setYn(Constants.YES);
			int total = itemVideoDao.countByCondition(itemVideoQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			int totalPage = total/itemVideoQuery.getPageSize() + 1;
			if(itemVideoQuery.getPageNo() > totalPage){
				itemVideoQuery.setPageNo(totalPage);
			}
			List<ItemVideo> list = itemVideoDao.selectByConditionForPage(itemVideoQuery);
			if(list == null || list.isEmpty()){
				map.put("datas", null);
			}else{
				for (ItemVideo itemVideo : list) {
					Item item = itemDao.selectByPrimaryKey(itemVideo.getItemId());
					if(item == null){
					}else{
						itemVideo.setItemName(item.getItemName());
						itemVideo.setItemImage(item.getItemImageMin());
						itemVideo.setPromotionStr(getPromotionStr(item.getItemId()));
					}
				}
			}
			
			map.put("datas", list);
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", itemVideoQuery.getPageNo());
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	private String getPromotionStr(Integer itemId){
		String promotionStr="";
	 	PromotionSkuQuery promotionSkuQuery = new PromotionSkuQuery();
		promotionSkuQuery.setItemId(itemId);
        promotionSkuQuery.setYn(Constants.YES);
        List<PromotionSku> psList = promotionSkuDao.selectByCondition(promotionSkuQuery);
        for(PromotionSku promotionSku:psList){
	        PromotionInfo promotionInfo = promotionInfoDao.selectByPrimaryKey(promotionSku.getPromotionId());
			Date now =new Date();
			//判断是否有促销活动
			 if(promotionInfo != null
	                    && promotionInfo.getPromotionStatus()!=null && promotionInfo.getStartTime()!=null && promotionInfo.getEndTime() !=null
	                    && promotionInfo.getYn() == 1 && promotionInfo.getPromotionStatus() == 1
	                    && now.after(promotionInfo.getStartTime()) && now.before(promotionInfo.getEndTime())){
				 String promotionRule = promotionInfo.getPromotionRule();
				 if(!promotionStr.contains(promotionRule)){
					 promotionStr += promotionRule;
				 }
			 }
		 }
       return promotionStr;
	}
}
