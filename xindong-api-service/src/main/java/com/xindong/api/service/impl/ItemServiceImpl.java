package com.xindong.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xindong.api.common.utils.Constants;
import com.xindong.api.dao.ItemAnalysisImageDAO;
import com.xindong.api.dao.ItemDAO;
import com.xindong.api.dao.ItemDescriptionDAO;
import com.xindong.api.dao.ItemImageDAO;
import com.xindong.api.dao.PromotionInfoDao;
import com.xindong.api.dao.PromotionSkuDao;
import com.xindong.api.dao.SkuDAO;
import com.xindong.api.dao.TbCouponPromoDao;
import com.xindong.api.dao.TourFlagDAO;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.ItemAnalysisImage;
import com.xindong.api.domain.ItemDescription;
import com.xindong.api.domain.ItemImage;
import com.xindong.api.domain.PromotionInfo;
import com.xindong.api.domain.PromotionSku;
import com.xindong.api.domain.Sku;
import com.xindong.api.domain.TbCouponPromo;
import com.xindong.api.domain.TourFlag;
import com.xindong.api.domain.query.ItemAnalysisImageQuery;
import com.xindong.api.domain.query.ItemQuery;
import com.xindong.api.domain.query.PromotionSkuQuery;
import com.xindong.api.domain.query.TbCouponPromoQuery;
import com.xindong.api.domain.vo.DestinationVo;
import com.xindong.api.domain.vo.DestinationsVo;
import com.xindong.api.domain.vo.IndexRecommendVO;
import com.xindong.api.domain.vo.ItemHeatVO;
import com.xindong.api.domain.vo.ItemOriginVO;
import com.xindong.api.domain.vo.ItemVO;
import com.xindong.api.domain.vo.RecommendItemVO;
import com.xindong.api.service.ItemService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.EcUtils;
import com.xindong.api.service.utils.RedisUtils;

@Service(value="itemService")
public class ItemServiceImpl implements ItemService {
	private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
	@Autowired
	private ItemDAO itemDao;
	@Autowired
	private SkuDAO skuDao;
	@Autowired
	private ItemDescriptionDAO itemDescriptionDao;
	@Autowired
	private ItemImageDAO itemImageDao;
	@Autowired
	private TourFlagDAO tourFlagDao;
	@Autowired
	private PromotionSkuDao promotionSkuDao;
	@Autowired
	private PromotionInfoDao promotionInfoDao;
	@Autowired
	private ItemAnalysisImageDAO itemAnalysisImageDao;
	@Autowired
	private TbCouponPromoDao tbCouponPromoDao;
	
	@Override
	public Result getItemByItemId(Integer itemId, Integer type) {
		Result result =new Result();
		try{
//			ItemDetailVO detailVO=new ItemDetailVO();
			Item item =itemDao.selectByPrimaryKey(itemId);
			if(item ==null){
				result.setResultCode("1001");
				result.setResultMessage("商品信息不存在");
				return result;
			}
			if(item.getItemStatus() != 1){
				result.setResultCode("1001");
				result.setResultMessage("商品已下架");
				return result;
			}
			Sku sku =new Sku();
			sku.setYn(Constants.YES);
			sku.setItemId(itemId);
			sku.setSkuStartDate(new Date());
			sku.setEndTime(new Date());//查询报名截止日期大于当前的sku
			List<Sku> skus =skuDao.selectByCondition(sku); 
			/*if(skus ==null ||skus.size() ==0){
				result.setResultCode("1001");
				result.setResultMessage("商品信息不存在");
				return result;
			}*/
			
			boolean isFirst =true;
			Date skuStartDate =null;
			//进行 按出发城市 分组。。TODO
			ItemOriginVO itemOriginVOs =null;
			
			Map<String,ItemOriginVO> map =new HashMap<String, ItemOriginVO>();
//			List<Sku> skuList =null;
//			Sku su =new Sku();
			for(Sku sk:skus){
				Date startDate =sk.getSkuStartDate();
				if(isFirst){
					skuStartDate =startDate;
					isFirst =false;
				}
				sk.setPromotionStr(getPromotionForSku(sk.getSkuId()));
				
//				Integer orginId =sk.getOriginId();
				String orginName =sk.getOriginName();
				if(StringUtils.isEmpty(orginName)){
					continue;
				}
				//先获取 是否有。如果有，则新加入。如果没有，则加入
				itemOriginVOs = map.get(orginName);
				if(itemOriginVOs==null){
					itemOriginVOs =new ItemOriginVO();
					itemOriginVOs.setId(sk.getOriginId());
					itemOriginVOs.setName(sk.getOriginName());
					List<Sku> skuList = new ArrayList<Sku>();
					skuList.add(sk); 
					itemOriginVOs.setSkus(skuList);
				}else{
					List<Sku> skuList = itemOriginVOs.getSkus();
					skuList.add(sk); 
					itemOriginVOs.setSkus(skuList);
//					itemOriginVOs.add(vo);
				}
				map.put(orginName, itemOriginVOs);
				
			}
			//然后把MAP 里面的list取出
			List<ItemOriginVO> originVOList =new ArrayList<ItemOriginVO>();
			for (String key : map.keySet()) {
				originVOList.add(map.get(key)); 
			}
				 
			List<ItemDescription> des =itemDescriptionDao.selectByItemId(itemId);
			List<ItemImage> itemImages= new ArrayList<ItemImage>();
			ItemAnalysisImage itemAnalysisImage= new ItemAnalysisImage();
			ItemAnalysisImage itemBookImage= new ItemAnalysisImage();
			List<RecommendItemVO> recommendImages =new ArrayList<RecommendItemVO>();
			//如果是H5
			if(type == 1){
				ItemImage itemq =new ItemImage();
				itemq.setItemId(item.getItemId());
				itemq.setType(1);//h5图片
				itemImages = itemImageDao.selectByConditionForH5(itemq);
				if(itemImages !=null && itemImages.size() >0){
					item.setItemImage(itemImages.get(itemImages.size()-1).getImageUrl());
				}
			}else{
				//查询商品图片
				ItemImage itemq =new ItemImage();
				itemq.setItemId(item.getItemId());
				itemq.setType(0);//PC图片
				itemImages = itemImageDao.selectByCondition(itemq);
				
				//PC才去查询推荐商品
				recommendImages =getRecommendItemList(item.getItemId());
			}
			
			//查询商品分析图
			ItemAnalysisImageQuery query = new ItemAnalysisImageQuery();
			query.setYn(Constants.YES);
			query.setItemId(item.getItemId());
			query.setType(Constants.ItemImgType.IMG_ANAALYSIS);
			List<ItemAnalysisImage> itemAnalysisImages = itemAnalysisImageDao.selectByCondition(query);
			if(itemAnalysisImages == null || itemAnalysisImages.isEmpty()){
			}else{
				itemAnalysisImage = itemAnalysisImages.get(0);
			}
			
			//查询商品图书图片
			query.setYn(Constants.YES);
			query.setItemId(item.getItemId());
			query.setType(Constants.ItemImgType.IMG_BOOK);
			List<ItemAnalysisImage> itemBookImages = itemAnalysisImageDao.selectByCondition(query);
			if(itemBookImages == null || itemBookImages.isEmpty()){
			}else{
				itemBookImage = itemBookImages.get(0);
			}
			
			String itemTours ="";
			TourFlag flag = tourFlagDao.selectByPrimaryKey(item.getFlagId());
			if(flag !=null){
				itemTours=flag.getFlagName();
			}
			ItemVO  itemVO =new ItemVO();
			itemVO.setChallengeScore(item.getChallengeScore());
			itemVO.setCustomdesc(item.getCustomdesc());
			itemVO.setCustomstatus(item.getCustomstatus());
			itemVO.setDestinationName1(item.getDestinationName1());
			itemVO.setDestinationName2(item.getDestinationName2());
			itemVO.setForwordCount(item.getForwordCount());
			itemVO.setHeartScore(item.getHeartScore());
			itemVO.setItemId(item.getItemId());
			itemVO.setItemImage(item.getItemImage());
			itemVO.setItemImageMin(item.getItemImageMin());
			itemVO.setItemIntro1(item.getItemIntro1());
			itemVO.setItemIntro2(item.getItemIntro2());
			itemVO.setItemIntro3(item.getItemIntro3());
			itemVO.setItemName(item.getItemName());
			itemVO.setItemOriginName1(item.getItemOriginName1());
			itemVO.setItemType(item.getItemType());
			itemVO.setQualityScore(item.getQualityScore());
			itemVO.setSubjectName(item.getSubjectName());
			itemVO.setSubjectName1(item.getSubjectName1());
			itemVO.setUsefulCount(item.getUsefulCount());
			itemVO.setItemOriginName1(item.getItemOriginName1());
			itemVO.setSupplierdesc(item.getSupplierdesc());
			itemVO.setSkuStartDate(skuStartDate);
			itemVO.setItemTours(itemTours);
			itemVO.setQrcodeImgUrl(item.getQrcodeImgUrl());
			itemVO.setMostNum(item.getMostNum());
			itemVO.setLeastNum(item.getLeastNum());
			itemVO.setShareWebUrl(item.getShareWebUrl());
			itemVO.setH5ShareWebUrl(item.getH5ShareWebUrl());
			itemVO.setMusicWebUrl(item.getMusicWebUrl());
			itemVO.setMusicWebAuthor(item.getMusicWebAuthor());
			
			itemVO.setPromotionStr(getPromotionStr(item.getItemId()));
			itemVO.setCouponList(getCouponList(itemId));
			
			Map<String, Object> mapE=new HashMap<String, Object>();
			mapE.put("skus", skus); 
			mapE.put("item", itemVO);
			mapE.put("itemDescriptions", des);
			mapE.put("itemImages", itemImages);
			mapE.put("itemAnalysisImage", itemAnalysisImage);
			mapE.put("itemBookImage", itemBookImage);
			mapE.put("recommendImages", recommendImages);
			mapE.put("originList", originVOList); 
			result.setResult(mapE);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
	   return result;
	}
	
	/**
	 * 得到促销描述
	 * @param item
	 * @return
	 */
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
						 if(promotionInfo.getPromotionForm() == 2){
							 promotionRule = promotionRule+ "(部分行期)";
						 }
						 promotionStr += promotionRule + "；";
					 }
				 }
	        }
	        //去取符号“；”
	        if(StringUtils.isNotBlank(promotionStr)){
	        	promotionStr = promotionStr.substring(0,promotionStr.length()-1);
	        }
	        return promotionStr;
	}
	
	/**
	 * 根据sku获取促销信息
	 * @param skuId
	 * @return
	 */
	private String getPromotionForSku(Integer skuId){
		//拼接sku促销信息
		String promotionStr = "";
		PromotionSkuQuery promotionSkuQuery = new PromotionSkuQuery();
		promotionSkuQuery.setSkuId(skuId);
		promotionSkuQuery.setYn(Constants.YES);
		List<PromotionSku> promotionSkuList = promotionSkuDao.selectByCondition(promotionSkuQuery);
		if(promotionSkuList == null || promotionSkuList.isEmpty()){
		}else{
			for (PromotionSku promotionSku : promotionSkuList) {
				PromotionInfo promotionInfo = promotionInfoDao.selectByPrimaryKey(promotionSku.getPromotionId());
				Date now =new Date();
				//判断是否有促销活动
				 if(promotionInfo != null
		                    && promotionInfo.getPromotionStatus()!=null && promotionInfo.getStartTime()!=null && promotionInfo.getEndTime() !=null
		                    && promotionInfo.getYn() == 1 && promotionInfo.getPromotionStatus() == 1
		                    && now.after(promotionInfo.getStartTime()) && now.before(promotionInfo.getEndTime())){
					 String promotionRule = promotionInfo.getPromotionRule();
					 if(!promotionStr.contains(promotionRule)){
						 promotionStr += promotionRule+"；";
					 }
				 }
			}
		}
		if(StringUtils.isNotBlank(promotionStr)){
        	promotionStr = promotionStr.substring(0,promotionStr.length()-1);
		}
		return promotionStr;
	}
	
	//得到优惠券集合
	private List<TbCouponPromo> getCouponList(Integer itemId){
		List<TbCouponPromo> couponPromoList = new ArrayList<TbCouponPromo>();
		
		//判断商品是否有行程
  		Date currentDate = new Date();
		Sku sku = new Sku();
		sku.setItemId(itemId);
		sku.setYn(Constants.YES);
		sku.setSkuStartDate(currentDate);
		sku.setEndTime(currentDate);
		List<Sku> skuList = skuDao.selectByCondition(sku);
		if(skuList == null || skuList.isEmpty()){
		}else{
			//得到所有优惠活动
			TbCouponPromoQuery couponPromoQuery = new TbCouponPromoQuery();
			couponPromoQuery.setYn(Constants.YES);
			couponPromoQuery.setCouponPromoState(Constants.CouponPromoStatus.YSX);
			couponPromoQuery.setCouponBusinessType(Constants.CouponBusinessType.link);
			couponPromoQuery.setPromoStarttime(currentDate);
			couponPromoQuery.setPromoEndtime(currentDate);
			//组装有效的优惠活动
			couponPromoQuery.setCouponRuleType(1);//全部商品
			List<TbCouponPromo> allCouponList = tbCouponPromoDao.selectByCondition(couponPromoQuery);
			if(allCouponList == null || allCouponList.isEmpty()){
			}else{
				for (TbCouponPromo tbCouponPromo : allCouponList) {
					String couponDesc = "";
					Integer couponType = tbCouponPromo.getCouponType();
					Integer couponAmount = tbCouponPromo.getCouponAmount();
					Integer orderQuota = tbCouponPromo.getOrderQuota();
					Integer amountRandom = tbCouponPromo.getAmountRandom();
					if(couponType == 0){//满减
						couponDesc = "满" + orderQuota + "，减" + couponAmount +"元";
						if(amountRandom == 1){
							couponDesc = "满" + orderQuota + "，最多减" + couponAmount +"元";
						}
					}else if(couponType == 1){//直减
						couponDesc = "订单直减" + couponAmount +"元";
						if(amountRandom == 1){
							couponDesc = "订单最多减" + couponAmount +"元";
						}
					}
					tbCouponPromo.setCouponDesc(couponDesc);
				}
				couponPromoList.addAll(allCouponList);
			}
			couponPromoQuery.setCouponRuleType(2);//具体商品
			couponPromoQuery.setBakFirst(itemId.toString());
			List<TbCouponPromo> itemCouponList = tbCouponPromoDao.selectByCondition(couponPromoQuery);
			if(itemCouponList == null || itemCouponList.isEmpty()){
			}else{
				for (TbCouponPromo tbCouponPromo : itemCouponList) {
					String couponDesc = "";
					Integer couponType = tbCouponPromo.getCouponType();
					Integer couponAmount = tbCouponPromo.getCouponAmount();
					Integer orderQuota = tbCouponPromo.getOrderQuota();
					Integer amountRandom = tbCouponPromo.getAmountRandom();
					if(couponType == 0){//满减
						couponDesc = "满" + orderQuota + "，减" + couponAmount +"元";
						if(amountRandom == 1){
							couponDesc = "满" + orderQuota + "，最多减" + couponAmount +"元";
						}
					}else if(couponType == 1){//直减
						couponDesc = "订单直减" + couponAmount +"元";
						if(amountRandom == 1){
							couponDesc = "订单最多减" + couponAmount +"元";
						}
					}
					tbCouponPromo.setCouponDesc(couponDesc);
				}
				couponPromoList.addAll(itemCouponList);
			}
		}
		return couponPromoList;		
	}
	
	/**
	 * 商品点赞转发
	 * type（1转发；2点赞）
	 * @return
	 */
	@Override
	public Result addItemCount(Integer itemId, Integer type) {
		Result result =new Result();
		try{
			Item item =itemDao.selectByPrimaryKey(itemId);
			if(item ==null){
				result.setResultCode("1001");
				result.setResultMessage("商品信息不存在");
				return result;
			}
			Item itemu = new Item();
			itemu.setItemId(itemId);
			if(type == 1){
				itemu.setForwordCount(1);
			}else{
				itemu.setUsefulCount(1);
			}
			itemu.setModified(new Date());
			itemDao.updateByPrimaryKey(itemu);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * 获取更多推荐商品
	 * @return
	 */
	private List<RecommendItemVO> getRecommendItemList(Integer itemId ){
		ItemQuery itemQuery =new ItemQuery();
		itemQuery.setYn(Constants.YES);
		itemQuery.setItemStatus(Constants.ItemStatus.ONSALE);
		itemQuery.setStart(1);
		itemQuery.setPageSize(3);
		if(itemId!=0){
			itemQuery.setItemId(itemId);
			Item itemVo = itemDao.selectByPrimaryKey(itemId);
			itemQuery.setDestinationJson1(itemVo.getDestinationJson1());
		}
//		List<Item> recommends =itemDao.selectByConditionForPage(itemQuery);
		List<Item> recommends =new ArrayList<Item> ();
		recommends =itemDao.selectOthersByConditionForPage(itemQuery);
		List<RecommendItemVO> recommendImages =new ArrayList<RecommendItemVO>();
		if(recommends.size()>0){
			for(Item it:recommends){
				RecommendItemVO vo =new RecommendItemVO();
				vo.setItemName(it.getItemName());
				vo.setImageUrl(it.getItemImageMin());
				vo.setItemId(it.getItemId());
				recommendImages.add(vo);
			}
		}
		return recommendImages;
	}
	
	/**
	 * 获取更多推荐商品
	 * @return
	 */
	@Override
	public Result getRecommendItems(){
		Result result =new Result();
		try{
			List<RecommendItemVO> recommendImages =getRecommendItemList(0);
			result.setResult(recommendImages);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getItemHeat() {
		Result result =new Result();
		try{
			String key = "item_pv_all";//统计所有商品ID的key
			List<ItemHeatVO> itemList = new ArrayList<ItemHeatVO>();
			
			//遍历redis获取数据前10的数据  实际需要3条记录
    		Set<Tuple> tuples = RedisUtils.zrevrangeWithScores(key, 0, 9);
            for (Tuple tuple : tuples) {
            	String itemId = tuple.getElement();
            	Double count = tuple.getScore();
            	if(StringUtils.isNotBlank(itemId)){
            		ItemQuery itemQuery = new ItemQuery();
            		itemQuery.setItemId(Integer.parseInt(itemId));
            		itemQuery.setYn(Constants.YES);
            		itemQuery.setItemStatus(Constants.ItemStatus.ONSALE);
            		
            		//Item item = itemDao.selectByPrimaryKey(Integer.parseInt(itemId));
            		List<Item> items = itemDao.selectByCondition(itemQuery);
            		if(items == null || items.isEmpty()){
            			continue;
            		}else{
            			int len = itemList.size();
            			if(len >= 3){//当itemList满足3条记录结束循环
            				break;
            			}else{
            				Item item = items.get(0);
            				//获取目的地
            				List<DestinationsVo> destinationJson2List  = JSON.parseObject(item.getDestinationJson2(),new TypeReference<List<DestinationsVo>>(){});
            				String destinationS = transDestinations(destinationJson2List);
            				
            				//获取最近行程开始件和成人价格
            				BigDecimal skuAdultPrice =  new BigDecimal(0);
            				Date currentDate = new Date();
            				Sku sku = new Sku();
            				sku.setItemId(item.getItemId());
            				sku.setYn(Constants.YES);
            				sku.setSkuStartDate(currentDate);
            				sku.setEndTime(currentDate);
            				List<Sku> skuList = skuDao.selectByCondition(sku);
            				if(skuList == null || skuList.isEmpty()){ //如果sku 全部过期，显示个性化
            				}else{
            					skuAdultPrice = skuList.get(0).getSkuAdultPrice();
            				}
            				//商品表的小图
            				String shareSummary = item.getItemImageMin();
            				ItemHeatVO itemHeat = new ItemHeatVO(Integer.parseInt(itemId), item.getItemName(), destinationS, skuAdultPrice, shareSummary, count.intValue());
            				itemList.add(itemHeat);
            			}
            		}
            	}
            }
            result.setResult(itemList);
            EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	private static String transDestinations( List<DestinationsVo> DestinationJson2List){
		StringBuilder sb = new StringBuilder();
		int i = 1;
		for (DestinationsVo ds : DestinationJson2List) {
			for (DestinationVo d : ds.getChildren()) {
				if (i++ > 1) {
					sb.append("#");
				}
				sb.append(d.getName());
			}
		}
		return sb.toString();
	}
	
}
