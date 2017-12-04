package com.xindong.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.dao.ActivityDAO;
import com.xindong.api.dao.ActivityDetailDao;
import com.xindong.api.dao.ActivityReplyDAO;
import com.xindong.api.dao.ItemDAO;
import com.xindong.api.dao.PromotionInfoDao;
import com.xindong.api.dao.PromotionSkuDao;
import com.xindong.api.dao.SkuDAO;
import com.xindong.api.dao.TbCouponPromoDao;
import com.xindong.api.domain.Activity;
import com.xindong.api.domain.ActivityDetail;
import com.xindong.api.domain.ActivityReply;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.PromotionInfo;
import com.xindong.api.domain.PromotionSku;
import com.xindong.api.domain.Sku;
import com.xindong.api.domain.TbCouponPromo;
import com.xindong.api.domain.query.ActivityDetailQuery;
import com.xindong.api.domain.query.ActivityQuery;
import com.xindong.api.domain.query.ActivityReplyQuery;
import com.xindong.api.domain.query.PromotionSkuQuery;
import com.xindong.api.domain.query.TbCouponPromoQuery;
import com.xindong.api.domain.vo.ActivityReplyVO;
import com.xindong.api.service.ActivityService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.EcUtils;

@Service(value="activityService")
public class ActivityServiceImpl implements ActivityService {
	private static final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);
	@Autowired
	private ActivityDAO activityDao;
	@Autowired
	private DataSourceTransactionManager transactionManager;
	@Autowired
	private ActivityReplyDAO activityReplyDao;
	@Autowired
	private ItemDAO itemDao;
	@Autowired
	private SkuDAO skuDao;
	@Autowired
	private ActivityDetailDao activityDetailDao;
	@Autowired
	private PromotionSkuDao promotionSkuDao;
	@Autowired
	private PromotionInfoDao promotionInfoDao;
	@Autowired
	private TbCouponPromoDao tbCouponPromoDao;
	
	@Override
	public Result getActivityByPage(ActivityQuery activityQuery) {
		Result result = new Result();
		try{
			activityQuery.setYn(Constants.YES);
			activityQuery.setActivityStatu(Constants.ActivityStatus.YSX);
			if(activityQuery.getActivityType() ==null){
				activityQuery.setActivityType(Constants.ActivityType.KB);
			}
			int total = activityDao.countByCondition(activityQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			int totalPage = total/activityQuery.getPageSize() + 1;
			if(activityQuery.getPageNo() > totalPage){
				activityQuery.setPageNo(totalPage);
			}
			List<Activity> list = activityDao.selectByConditionForPage(activityQuery);
			//查询商品点赞数
			if(list != null && list.size() > 0 && activityQuery.getActivityType() == 2){
				for (Activity activity : list) {
					String url = activity.getActivityUrl();
					if(!StringUtils.isBlank(url)){
						String itemId = url.substring(url.indexOf("itemId=")+"itemId=".length());
						if(isNumeric(itemId)){
							Item item = itemDao.selectByPrimaryKey(Integer.parseInt(itemId));
							if(item != null){
								activity.setUsefulCount(item.getUsefulCount());
							}
						}
					}
				}
			}
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("datas", list);
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", activityQuery.getPageNo());
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getActivityList() {
		Result result = new Result();
		Map<String, Object> map=new HashMap<String, Object>();
		ActivityQuery activityQuery = new ActivityQuery();
		
		try{
			activityQuery.setYn(Constants.YES);
			activityQuery.setActivityStatu(Constants.ActivityStatus.YSX);
			activityQuery.setActivityType(Constants.ActivityType.HD);
			List<Activity> activityList = activityDao.selectByCondition(activityQuery);
			if(activityList == null || activityList.isEmpty()){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}else{
				for (Activity activity : activityList) {
					//查询活动详情
					ActivityDetailQuery activityDetailQuery = new ActivityDetailQuery();
					activityDetailQuery.setYn(Constants.YES);
					activityDetailQuery.setActivityId(activity.getActivityId());
					List<ActivityDetail> activityDetails = activityDetailDao.selectByCondition(activityDetailQuery);
					List<ActivityDetail> list = new ArrayList<ActivityDetail>();
					//取前3条记录
					list = activityDetails.subList(0, 3);
					for (ActivityDetail activityDetail : list) {
						String url = activityDetail.getSkipUrl();
						//商品点赞数
						if(!StringUtils.isBlank(url)){
							String itemId = url.substring(url.indexOf("itemId=")+"itemId=".length());
							if(isNumeric(itemId)){
								Item item = itemDao.selectByPrimaryKey(Integer.parseInt(itemId));
								if(item != null){
									activityDetail.setUsefulCount(item.getUsefulCount());
									activityDetail.setPromotionStr(getPromotionStr(item));
									activityDetail.setCouponStr(getCouponStr(Integer.parseInt(itemId)));
								}
							}
						}else{
							/*result.setResult(new HashMap<String, Object>());
							EcUtils.setSuccessResult(result);
							return result;*/
						}
					}
					activity.setActivityDetailList(list);
				}
			}
			map.put("datas", activityList);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getActivityDetail(ActivityDetailQuery activityDetailQuery) {
		Result result = new Result();
		Map<String, Object> map=new HashMap<String, Object>();
		ActivityQuery activityQuery =new ActivityQuery();
		Activity activity = new Activity();
		try{
			//查询活动状态
			activityQuery.setActivityId(activityDetailQuery.getActivityId());
			activityQuery.setYn(Constants.YES);
			activityQuery.setActivityStatu(Constants.ActivityStatus.YSX);
			List<Activity> activites = activityDao.selectByCondition(activityQuery);
			if(activites == null || activites.isEmpty()){
				result.setResultMessage("活动已下线");
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}else{
				activity = activites.get(0);
				map.put("activity", activity);
			}
			
			activityDetailQuery.setYn(Constants.YES);
			int total = activityDetailDao.countByCondition(activityDetailQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			int totalPage = total/activityDetailQuery.getPageSize() + 1;
			if(activityDetailQuery.getPageNo() > totalPage){
				activityDetailQuery.setPageNo(totalPage);
			}
			List<ActivityDetail> activityDetails = activityDetailDao.selectByConditionForPage(activityDetailQuery);
		
			if(activity.getActivityType() != null && activity.getActivityType() ==1){//新品详情
				map.put("data", activites.get(0));
			}else if(activity.getActivityType() != null && activity.getActivityType() ==2){//活动详情
				if(activityDetails == null || activityDetails.isEmpty()){
					map.put("data", null);
				}else{
					for (ActivityDetail activityDetail : activityDetails) {
						String url = activityDetail.getSkipUrl();
						//商品点赞数
						if(!StringUtils.isBlank(url)){
							String itemId = url.substring(url.indexOf("itemId=")+"itemId=".length());
							if(isNumeric(itemId)){
								Item item = itemDao.selectByPrimaryKey(Integer.parseInt(itemId));
								if(item != null){
									activityDetail.setUsefulCount(item.getUsefulCount());
									activityDetail.setPromotionStr(getPromotionStr(item));
									activityDetail.setCouponStr(getCouponStr(Integer.parseInt(itemId)));
								}
							}
						}else{
							/*result.setResult(new HashMap<String, Object>());
							EcUtils.setSuccessResult(result);
							return result;*/
						}
					}
				}
			}
			map.put("data", activityDetails);
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", activityDetailQuery.getPageNo());
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result askActivity(final ActivityReply activityReply) {
		Result result = new Result();
		try{
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					activityReply.setActivityReplyStatu(Constants.ActivityReplyStatus.DSH);
					activityReply.setSortNum(0);
					Integer id = activityReplyDao.insert(activityReply);
					if(id == null || id == 0){
						throw new RuntimeException("添加活动咨询失败");
					}
					Activity activity =new Activity();
					activity.setActivityId(activityReply.getActivityId());
					activity.setReplyNum(1);
					activity.setModified(new Date());
					activityDao.updateByPrimaryKey(activity);
				}
			});
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getAskActivityByPage(ActivityReplyQuery activityReplyQuery) {

		Result result = new Result();
		try{
			activityReplyQuery.setYn(Constants.YES);
			int total = activityReplyDao.countByCondition(activityReplyQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			int totalPage = total/activityReplyQuery.getPageSize() + 1;
			if(activityReplyQuery.getPageNo() > totalPage){
				activityReplyQuery.setPageNo(totalPage);
			}
			List<ActivityReplyVO> list = activityReplyDao.selectByReplyForPage(activityReplyQuery);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("datas", list);
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", activityReplyQuery.getPageNo());
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	
	}
	@Override
	public Result addActivityCount(Integer id, Integer activityId,  Integer type) {
		Result result =new Result();
		try{
			if(null == id){//新品点赞
				Activity activity = new Activity();
				activity.setActivityId(activityId);
				if(type == 1){
					activity.setForwordCount(1);
				}else{
					activity.setUsefulCount(1);
				}
				activity.setModified(new Date());
				activityDao.updateByPrimaryKey(activity);
			}else{//活动点赞
				ActivityDetailQuery activityDetailQuery = new ActivityDetailQuery();
				activityDetailQuery.setYn(Constants.YES);
				activityDetailQuery.setId(id);
				activityDetailQuery.setActivityId(activityId);
				List<ActivityDetail> activityDetails = activityDetailDao.selectByCondition(activityDetailQuery);
				if(activityDetails == null || activityDetails.isEmpty()){
					result.setResultCode("1001");
					result.setResultMessage("活动详情信息不存在");
					return result;
				}else{
					ActivityDetail activityDetail = activityDetails.get(0);
					String url = activityDetail.getSkipUrl();
					String itemId = url.substring(url.indexOf("itemId=")+"itemId=".length());
					if(isNumeric(itemId)){
						Item item = itemDao.selectByPrimaryKey(Integer.parseInt(itemId));
						if(item ==null){
						}else{
							Item itemu = new Item();
							itemu.setItemId(Integer.parseInt(itemId));
							if(type == 1){
								itemu.setForwordCount(1);
							}else{
								itemu.setUsefulCount(1);
							}
							itemu.setModified(new Date());
							itemDao.updateByPrimaryKey(itemu);
						}
					}
				}
			}
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	private String getPromotionStr(Item item){
		String promotionStr="";
	 	PromotionSkuQuery promotionSkuQuery = new PromotionSkuQuery();
		promotionSkuQuery.setItemId(item.getItemId());
        promotionSkuQuery.setYn(1);
        List<PromotionSku> psList = promotionSkuDao.selectByCondition(promotionSkuQuery);
        for(PromotionSku promotionSku:psList){
	        PromotionInfo promotionInfo = promotionInfoDao.selectByPrimaryKey(promotionSku.getPromotionId());
			Date now =new Date();
			//判断是否有促销活动
			 if(promotionInfo != null
	                    && promotionInfo.getPromotionStatus()!=null && promotionInfo.getStartTime()!=null && promotionInfo.getEndTime() !=null
	                    && promotionInfo.getYn() == 1 && promotionInfo.getPromotionStatus() == 1
	                    && now.after(promotionInfo.getStartTime()) && now.before(promotionInfo.getEndTime())){
				 if(promotionInfo.getPromotionType().equals(1)){//促销类型(1-满人数直降)
					 Integer num =promotionInfo.getPurchaseNumberMax();//最大数量
					 promotionStr +="成人满"+num+"人,直降"+promotionSku.getDeductionPrice()+"元;";
				 }
			 }
		 }
       return promotionStr;
	}
	
	//得到优惠券集合
  	private String getCouponStr(Integer itemId){
  		String couponStr = "";
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
	  		//List<TbCouponPromo> couponPromoList = new ArrayList<TbCouponPromo>();
	  		//得到所以优惠活动
	  		TbCouponPromoQuery couponPromoQuery = new TbCouponPromoQuery();
	  		couponPromoQuery.setYn(Constants.YES);
	  		couponPromoQuery.setCouponPromoState(1);
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
	  					couponDesc = "满" + orderQuota + "，减" + couponAmount +"元；";
	  					if(amountRandom == 1){
	  						couponDesc = "满" + orderQuota + "，最多减" + couponAmount +"元；";
	  					}
	  				}else if(couponType == 1){//直减
	  					couponDesc = "订单直减" + couponAmount +"元；";
	  					if(amountRandom == 1){
	  						couponDesc = "订单最多减" + couponAmount +"元；";
	  					}
	  				}
	  				//tbCouponPromo.setCouponDesc(couponDesc);
	  				couponStr += couponDesc;
	  			}
	  			//couponPromoList.addAll(allCouponList);
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
	  					couponDesc = "满" + orderQuota + "，减" + couponAmount +"元；";
	  					if(amountRandom == 1){
	  						couponDesc = "满" + orderQuota + "，最多减" + couponAmount +"元；";
	  					}
	  				}else if(couponType == 1){//直减
	  					couponDesc = "订单直减" + couponAmount +"元；";
	  					if(amountRandom == 1){
	  						couponDesc = "订单最多减" + couponAmount +"元；";
	  					}
	  				}
	  				//tbCouponPromo.setCouponDesc(couponDesc);
	  				couponStr += couponDesc;
	  			}
	  			//couponPromoList.addAll(itemCouponList);
	  		}
		}
  		return couponStr;		
  	}
	
	
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	 }
	
}
