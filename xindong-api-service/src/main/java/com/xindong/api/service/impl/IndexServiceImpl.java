package com.xindong.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xindong.api.common.utils.Constants;
import com.xindong.api.dao.AdImgDao;
import com.xindong.api.dao.AdInfoDao;
import com.xindong.api.dao.IndexImageDAO;
import com.xindong.api.dao.IndexRecommendDao;
import com.xindong.api.dao.IndexSeriesDao;
import com.xindong.api.dao.IndexSeriesImgDao;
import com.xindong.api.dao.ItemDAO;
import com.xindong.api.dao.ItemImageDAO;
import com.xindong.api.dao.PromotionInfoDao;
import com.xindong.api.dao.PromotionSkuDao;
import com.xindong.api.dao.SkuDAO;
import com.xindong.api.dao.TbCouponPromoDao;
import com.xindong.api.dao.TourDateDAO;
import com.xindong.api.dao.TourDestinationDAO;
import com.xindong.api.dao.TourSubjectDAO;
import com.xindong.api.domain.AdInfo;
import com.xindong.api.domain.IndexImage;
import com.xindong.api.domain.IndexRecommend;
import com.xindong.api.domain.IndexSeries;
import com.xindong.api.domain.IndexSeriesImg;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.ItemImage;
import com.xindong.api.domain.PromotionInfo;
import com.xindong.api.domain.PromotionSku;
import com.xindong.api.domain.Sku;
import com.xindong.api.domain.TbCouponPromo;
import com.xindong.api.domain.TourDate;
import com.xindong.api.domain.TourDestination;
import com.xindong.api.domain.TourSubject;
import com.xindong.api.domain.query.AdInfoQuery;
import com.xindong.api.domain.query.ItemQuery;
import com.xindong.api.domain.query.PromotionSkuQuery;
import com.xindong.api.domain.query.TbCouponPromoQuery;
import com.xindong.api.domain.vo.AdInfoVO;
import com.xindong.api.domain.vo.DestinationVo;
import com.xindong.api.domain.vo.DestinationsVo;
import com.xindong.api.domain.vo.IndexRecommendVO;
import com.xindong.api.domain.vo.IndexSeriesImgVO;
import com.xindong.api.domain.vo.IndexSeriesVO;
import com.xindong.api.domain.vo.TourDateVO;
import com.xindong.api.domain.vo.TourDestinationVO;
import com.xindong.api.service.IndexService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.EcUtils;

@Service(value="indexService")
public class IndexServiceImpl implements IndexService {
	private static final Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);
	@Autowired
	private IndexImageDAO indexImageDao;
	@Autowired
	private TourSubjectDAO tourSubjectDao;
	@Autowired
	private  TourDateDAO tourDateDao;
	@Autowired
	private TourDestinationDAO tourDestinationDao;
	@Autowired
	private AdInfoDao adInfoDao;
	@Autowired
	private AdImgDao adImgDao;
	@Autowired
	private IndexSeriesDao indexSeriesDao;
	@Autowired
	private IndexRecommendDao indexRecommendDao;
	@Autowired
	private ItemDAO itemDao;
	@Autowired
	private IndexSeriesImgDao indexSeriesImgDao;
	@Autowired
	private ItemImageDAO itemImageDao;
	@Autowired
	private SkuDAO skuDao;
	@Autowired
	private PromotionSkuDao promotionSkuDao;
	@Autowired
	private PromotionInfoDao promotionInfoDao;
	@Autowired
	private TbCouponPromoDao tbCouponPromoDao;
	
	@Override
	public Result getIndexImages(Integer type) {
		Result result = new Result();
		try{
			IndexImage indexImageQuery = new IndexImage();
			indexImageQuery.setYn(1);
			indexImageQuery.setType(type);  
			
			List<IndexImage> list = indexImageDao.selectByCondition(indexImageQuery);
			if(list == null || list.size() == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getIndex() {
		Result result = new Result();
		try{
			TourDate TourDateQuery = new TourDate();
			TourDateQuery.setYn(1);
			TourDateQuery.setDateLevel(1);  //查询一级
			//TourDateQuery.setModified(new Date());
			List<TourDate> tourDates = tourDateDao.selectByCondition(TourDateQuery);
			List<TourDateVO> tourDateVOs =new ArrayList<TourDateVO>();
			for(TourDate date:tourDates){
				TourDateVO dateVO =new TourDateVO();
				dateVO.setDateId(date.getDateId());
				dateVO.setDateName(date.getDateName());
				TourDateQuery.setDateLevel(2);
				TourDateQuery.setParentDateId(date.getDateId());
				dateVO.setTourDateChilds(tourDateDao.selectByCondition(TourDateQuery));
				tourDateVOs.add(dateVO);
			}
			TourDestination TourDestinationQuery = new TourDestination();
			TourDestinationQuery.setYn(1);
			TourDestinationQuery.setDestinationLevel(1);  //查询一级
			List<TourDestination> tourDestinations = tourDestinationDao.selectByCondition(TourDestinationQuery);
			TourSubject TourSubjectQuery = new TourSubject();
			TourSubjectQuery.setYn(1);
			TourSubjectQuery.setSubjectLevel(1);  //查询一级
			List<TourSubject> tourSubjects = tourSubjectDao.selectByCondition(TourSubjectQuery);
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("tourDates", tourDateVOs);
			map.put("tourDestinations", tourDestinations);
			map.put("tourSubjects", tourSubjects);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getIndexAds() {
		Result result = new Result();
		try{
			AdInfoQuery adInfoQuery =new AdInfoQuery();
			adInfoQuery.setAdType(Constants.AdType.first);
			adInfoQuery.setYn(Constants.YES);
			adInfoQuery.setState(Constants.AdState.ysx);
			List<AdInfo> list = adInfoDao.selectByCondition(adInfoQuery);
			if(list == null || list.size() == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			Map<String, Object> map =new HashMap<String, Object>();
 			List<AdInfoVO> vos  =new ArrayList<AdInfoVO>();
			for(AdInfo ad:list){
				AdInfoVO vo  =new AdInfoVO(ad);
				/*AdImg img =new AdImg();
				img.setYn(Constants.YES);
				img.setAdInfoId(ad.getId());
				List<AdImg> adImgList = adImgDao.selectByCondition(img);
				vo.setAdImgList(adImgList);*/
				vos.add(vo); 
			}
			map.put("datas", vos);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getH5Index() {
		Result result = new Result();
		try{
			//查询首页系列
			IndexSeries indexSeries =new IndexSeries();
			indexSeries.setYn(Constants.YES);
			indexSeries.setState(Constants.LineState.ysx);
			indexSeries.setType(Constants.IndexSeriesType.series);//首页
			List<IndexSeries> list = indexSeriesDao.selectByCondition(indexSeries);
			//查询首页推荐商品系列
			Result res = this.getIndexRecommendItems();
			List<IndexRecommendVO> recommends  = (List<IndexRecommendVO>) res.getResult();
			Map<String, Object> map =new HashMap<String, Object>();
 			List<IndexSeriesVO> vos  =new ArrayList<IndexSeriesVO>();
 			IndexSeriesVO vo =null;
			for(IndexSeries ser:list){
				vo =new IndexSeriesVO(ser);
				vos.add(vo);
			}
			TourSubject TourSubjectQuery = new TourSubject();
			TourSubjectQuery.setYn(1);
			TourSubjectQuery.setSubjectLevel(1);  //查询一级
			List<TourSubject> tourSubjects = tourSubjectDao.selectByCondition(TourSubjectQuery);
			map.put("tourSubjects", tourSubjects);
			map.put("seriesList", vos);
			map.put("recommendList", recommends);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getIndexRecommendItems() {
		Result result = new Result();
		try{
			//查询首页推荐商品系列
			IndexRecommend indexRecommend =new IndexRecommend();
			indexRecommend.setYn(Constants.YES);
			indexRecommend.setType(1);//首页
			List<IndexRecommend> recommends = indexRecommendDao.selectByCondition(indexRecommend);
			if(recommends == null || recommends.size() == 0){
				result.setResult(new ArrayList<IndexRecommendVO>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			List<IndexRecommendVO> vos =new ArrayList<IndexRecommendVO>();
			IndexRecommendVO vo =null;
			for(IndexRecommend ren:recommends){
				Item item =itemDao.selectByPrimaryKey(ren.getItemId());
				if(item ==null){
					continue;
				}
				String promotionStr = getPromotionStr(item.getItemId());
				vo =new IndexRecommendVO(ren,item,promotionStr,null);
				ItemImage itemq =new ItemImage();
				itemq.setItemId(item.getItemId());
				itemq.setType(1);//h5图片
				List<ItemImage> itemImages = itemImageDao.selectByCondition(itemq);
				if(itemImages !=null && itemImages.size() >0){
					vo.setImgUrl(itemImages.get(0).getImageUrl());
				}
				//前端商品图片取 itemImge的
				vos.add(vo);
			}
			result.setResult(vos);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getIndexSeriesDetail(Integer seriesId) {
		Result result = new Result();
		try{
			//查询首页系列
			IndexSeriesImg indexSeriesImg =new IndexSeriesImg();
			indexSeriesImg.setYn(Constants.YES);
			indexSeriesImg.setSeriesId(seriesId);
			List<IndexSeriesImg> list = indexSeriesImgDao.selectByCondition(indexSeriesImg);
			if(list == null || list.size() == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}else {
				//判断商品是否在线
				for (int i = 0; i < list.size(); i++) {
					Integer itemId = list.get(i).getItemId();
					ItemQuery itemQuery = new ItemQuery();
					itemQuery.setYn(Constants.YES);
					itemQuery.setItemId(itemId);
					itemQuery.setItemStatus(Constants.ItemStatus.ONSALE);
					List<Item> itemList = itemDao.selectByCondition(itemQuery);
					if(itemList == null || itemList.isEmpty()){
						list.remove(i);
					}
				}
			}
			//查询首页推荐商品系列
			Map<String, Object> map =new HashMap<String, Object>();
			List<IndexSeriesImgVO> vos  =new ArrayList<IndexSeriesImgVO>();
			IndexSeriesImgVO vo =null;
			for(IndexSeriesImg ser:list){
				
				if(ser.getType() ==1 && ser.getItemId() !=null){//系列
					Item item =itemDao.selectByPrimaryKey(ser.getItemId());
					if(item ==null){
						continue;
					}
					String promotionStr = getPromotionStr(item.getItemId());
					vo =new IndexSeriesImgVO(ser,item,promotionStr,null);
					ItemImage itemq =new ItemImage();
					itemq.setItemId(item.getItemId());
					itemq.setType(1);//h5图片
					List<ItemImage> itemImages = itemImageDao.selectByCondition(itemq);
					if(itemImages !=null && itemImages.size() >0){
						vo.setImgUrl(itemImages.get(0).getImageUrl());
					}
				}else{
					vo =new IndexSeriesImgVO(ser,null);
				}
				vos.add(vo);
			}
			map.put("seriesList", vos);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result getDestinations() {
		Result result = new Result();
		Map<String, Object> map =new HashMap<String, Object>();
		try{
			TourDestination TourDestinationQuery = new TourDestination();
			TourDestinationQuery.setYn(1);
			TourDestinationQuery.setDestinationLevel(1);  //查询一级
			List<TourDestination> tourDestinations = tourDestinationDao.selectByCondition(TourDestinationQuery);
			/*if(tourDestinations == null || tourDestinations.size() == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}*/
			List<TourDestinationVO> destinationVOs =new ArrayList<TourDestinationVO>();
			for(TourDestination dest:tourDestinations){
				TourDestinationVO destinationVO =new TourDestinationVO();
				destinationVO.setDestinationId(dest.getDestinationId());
				destinationVO.setDestinationName(dest.getDestinationName());
				
				TourDestinationQuery.setDestinationLevel(2);
				TourDestinationQuery.setParentDestinationId(dest.getDestinationId());
				destinationVO.setTourDestinationChilds(tourDestinationDao.selectByCondition(TourDestinationQuery));
				destinationVOs.add(destinationVO);
			}
			TourDate TourDateQuery = new TourDate();
			TourDateQuery.setYn(1);
			TourDateQuery.setDateLevel(1);  //查询一级
			//TourDateQuery.setModified(new Date());
			List<TourDate> tourDates = tourDateDao.selectByCondition(TourDateQuery);
			List<TourDateVO> tourDateVOs =new ArrayList<TourDateVO>();
			for(TourDate date:tourDates){
				TourDateVO dateVO =new TourDateVO();
				dateVO.setDateId(date.getDateId());
				dateVO.setDateName(date.getDateName());
				TourDateQuery.setDateLevel(2);
				TourDateQuery.setParentDateId(date.getDateId());
				dateVO.setTourDateChilds(tourDateDao.selectByCondition(TourDateQuery));
				tourDateVOs.add(dateVO);
			}
			map.put("destinationVOs", destinationVOs);
			map.put("tourDates", tourDateVOs);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result getH5IndexFind() {
		Result result = new Result();
		try{
			//查询首页发现
			IndexSeries indexSeries =new IndexSeries();
			indexSeries.setYn(Constants.YES);
			indexSeries.setState(Constants.LineState.ysx);
			indexSeries.setType(Constants.IndexSeriesType.find);//首页发现
			List<IndexSeries> list = indexSeriesDao.selectByCondition(indexSeries);
			Map<String, Object> map =new HashMap<String, Object>();
 			List<IndexSeriesVO> vos  =new ArrayList<IndexSeriesVO>();
 			IndexSeriesVO vo =null;
			for(IndexSeries ser:list){
				vo =new IndexSeriesVO(ser);
				vos.add(vo);
			}
			map.put("seriesList", vos);
			result.setResult(map); 
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getIndexRecommendPcItems() {
		Result result = new Result();
		try{
			//查询首页推荐商品系列
			IndexRecommend indexRecommend =new IndexRecommend();
			indexRecommend.setYn(Constants.YES);
			indexRecommend.setType(1);//推荐商品
			List<IndexRecommend> recommends = indexRecommendDao.selectByCondition(indexRecommend);
			if(recommends == null || recommends.size() == 0){
				result.setResult(new ArrayList<IndexRecommendVO>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			List<IndexRecommendVO> vos =new ArrayList<IndexRecommendVO>();
			IndexRecommendVO vo =null;
			for(IndexRecommend ren:recommends){
				Item item =itemDao.selectByPrimaryKey(ren.getItemId());
				if(item ==null){
					continue;
				}
				
				//获取目的地
				List<DestinationsVo> destinationJson2List  = JSON.parseObject(item.getDestinationJson2(),new TypeReference<List<DestinationsVo>>(){});
				String destinationS = transDestinations(destinationJson2List);
				
				//获取最近行程开始件和成人价格
				BigDecimal skuAdultPrice =  new BigDecimal(0);
				Date currentDate = new Date();
				Date skuStartDate = null;
				Sku sku = new Sku();
				sku.setItemId(item.getItemId());
				sku.setYn(Constants.YES);
				sku.setSkuStartDate(currentDate);
				sku.setEndTime(currentDate);
				List<Sku> skuList = skuDao.selectByCondition(sku);
				Integer flagId = 0;
				if(skuList == null || skuList.isEmpty()){ //如果sku 全部过期，显示个性化
					flagId = item.getCustomstatus();//能否定制
				}else{
					skuStartDate = skuList.get(0).getSkuStartDate();
					skuAdultPrice = skuList.get(0).getSkuAdultPrice();
				}
				
				String promotionStr = getPromotionStr(item.getItemId());
				String couponStr = getCouponStr(item.getItemId());
				//商品表的小图
				String shareSummary = null;
				shareSummary = item.getItemImageMin();
				vo =new IndexRecommendVO(ren,item,destinationS,skuStartDate,skuAdultPrice,shareSummary,flagId,promotionStr,couponStr);
				vos.add(vo);
			}
			result.setResult(vos);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getIndexSelected() {
		Result result = new Result();
		try{
			//查询首页推荐商品系列
			IndexRecommend indexRecommend =new IndexRecommend();
			indexRecommend.setYn(Constants.YES);
			indexRecommend.setType(2);//心动特选
			List<IndexRecommend> recommends = indexRecommendDao.selectByCondition(indexRecommend);
			if(recommends == null || recommends.size() == 0){
				result.setResult(new ArrayList<IndexRecommendVO>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			List<IndexRecommendVO> vos =new ArrayList<IndexRecommendVO>();
			IndexRecommendVO vo =null;
			for(IndexRecommend ren:recommends){
				Item item =itemDao.selectByPrimaryKey(ren.getItemId());
				if(item ==null){
					continue;
				}
				
				//获取目的地
				List<DestinationsVo> destinationJson2List  = JSON.parseObject(item.getDestinationJson2(),new TypeReference<List<DestinationsVo>>(){});
				String destinationS = transDestinations(destinationJson2List);
				
				//获取最近行程开始件和成人价格
				BigDecimal skuAdultPrice =  new BigDecimal(0);
				Date currentDate = new Date();
				Date skuStartDate = null;
				Sku sku = new Sku();
				sku.setItemId(item.getItemId());
				sku.setYn(Constants.YES);
				sku.setSkuStartDate(currentDate);
				sku.setEndTime(currentDate);
				List<Sku> skuList = skuDao.selectByCondition(sku);
				Integer flagId = 0;
				if(skuList == null || skuList.isEmpty()){ //如果sku 全部过期，显示个性化
					flagId = item.getCustomstatus();//能否定制
				}else{
					skuStartDate = skuList.get(0).getSkuStartDate();
					skuAdultPrice = skuList.get(0).getSkuAdultPrice();
				}
				
				String promotionStr = getPromotionStr(item.getItemId());
				String couponStr = getCouponStr(item.getItemId());
				//商品表的小图
				String shareSummary = null;
				shareSummary = item.getItemImageMin();
				vo =new IndexRecommendVO(ren,item,destinationS,skuStartDate,skuAdultPrice,shareSummary,flagId,promotionStr,couponStr);
				vos.add(vo);
			}
			result.setResult(vos);
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

	@Override
	public Result getPcIndexSeries(Integer type) {
		Result result = new Result();
		try{
			//查询首页系列
			IndexSeries indexSeries =new IndexSeries();
			indexSeries.setYn(Constants.YES);
			indexSeries.setState(Constants.LineState.ysx);
			indexSeries.setType(type);//
			List<IndexSeries> list = indexSeriesDao.selectByCondition(indexSeries);
			Map<String, Object> map =new HashMap<String, Object>();
 			List<IndexSeriesVO> vos  =new ArrayList<IndexSeriesVO>();
 			IndexSeriesVO vo =null;
			for(IndexSeries ser:list){
				vo =new IndexSeriesVO(ser);
				vos.add(vo);
			}
			map.put("seriesList", vos);
			result.setResult(map); 
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getPcIndexSeriesDetail(Integer seriesId) {
		Result result = new Result();
		try{
			//查询首页系列详细列表
			IndexSeriesImg indexSeriesImg =new IndexSeriesImg();
			indexSeriesImg.setYn(Constants.YES);
			indexSeriesImg.setSeriesId(seriesId);
			List<IndexSeriesImg> list = indexSeriesImgDao.selectByCondition(indexSeriesImg);
			if(list == null || list.size() == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}else{
				//判断商品是否在线
				for (int i = 0; i < list.size(); i++) {
					Integer itemId = list.get(i).getItemId();
					ItemQuery itemQuery = new ItemQuery();
					itemQuery.setYn(Constants.YES);
					itemQuery.setItemId(itemId);
					itemQuery.setItemStatus(Constants.ItemStatus.ONSALE);
					List<Item> itemList = itemDao.selectByCondition(itemQuery);
					if(itemList == null || itemList.isEmpty()){
						list.remove(i);
					}
				}
			}
			
			IndexSeries indexSeries = indexSeriesDao.selectByPrimaryKey(seriesId);
			
			//查询首页推荐商品系列
			Map<String, Object> map =new HashMap<String, Object>();
			List<IndexSeriesImgVO> vos  =new ArrayList<IndexSeriesImgVO>();
			IndexSeriesImgVO vo =null;
			
			for(IndexSeriesImg ser:list){
				if(ser.getType() ==1 && ser.getItemId() !=null){
					Item item =itemDao.selectByPrimaryKey(ser.getItemId());
					if(item ==null){
						continue;
					}
					
					//获取目的地
					List<DestinationsVo> destinationJson2List  = JSON.parseObject(item.getDestinationJson2(),new TypeReference<List<DestinationsVo>>(){});
					String destinationS = transDestinations(destinationJson2List);
					
					//获取最近行程开始件和成人价格
					BigDecimal skuAdultPrice =  new BigDecimal(0);
					Date currentDate = new Date();
					Date skuStartDate = null;
					Sku sku = new Sku();
					sku.setItemId(item.getItemId());
					sku.setYn(Constants.YES);
					sku.setSkuStartDate(currentDate);
					sku.setEndTime(currentDate);
					List<Sku> skuList = skuDao.selectByCondition(sku);
					Integer flagId = 0;
					if(skuList == null || skuList.isEmpty()){ //如果sku 全部过期，显示个性化
						flagId = item.getCustomstatus();//能否定制
					}else{
						skuStartDate = skuList.get(0).getSkuStartDate();
						skuAdultPrice = skuList.get(0).getSkuAdultPrice();
					}
					//商品表的小图
					String shareSummary = null;
					shareSummary = item.getItemImageMin();
					
					//促销信息
					Integer itemId = item.getItemId();
					String promotionStr = getPromotionStr(itemId);
					String couponStr = getCouponStr(itemId);
					vo =new IndexSeriesImgVO(ser,item,destinationS,skuStartDate,skuAdultPrice,shareSummary,flagId,promotionStr,couponStr);
				}else{
					vo =new IndexSeriesImgVO(ser,null);
				}
				vos.add(vo);
			}
			map.put("seriesList", vos);
			map.put("indexSeries", indexSeries);
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
}
