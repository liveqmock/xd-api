package com.xindong.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.HttpUtils;
import com.xindong.api.common.utils.JsonUtils;
import com.xindong.api.dao.TourDateDAO;
import com.xindong.api.dao.TourDestinationDAO;
import com.xindong.api.dao.TourSubjectDAO;
import com.xindong.api.domain.ActivityDetail;
import com.xindong.api.domain.TourDate;
import com.xindong.api.domain.TourDestination;
import com.xindong.api.domain.TourSubject;
import com.xindong.api.domain.query.SearchQuery;
import com.xindong.api.domain.query.TourDestinationQuery;
import com.xindong.api.domain.vo.TourDateVO;
import com.xindong.api.domain.vo.TourDestinationVO;
import com.xindong.api.domain.vo.TourSubjectVO;
import com.xindong.api.service.SearchService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;
import com.xindong.api.service.utils.RedisUtils;

@Service(value="searchService")
public class SearchServiceImpl implements SearchService {
	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
	@Autowired
	private TourSubjectDAO tourSubjectDao;
	@Autowired
	private  TourDateDAO tourDateDao;
	@Autowired
	private TourDestinationDAO tourDestinationDao;

	@Override
	public Result getSearchItems(SearchQuery searchQuery){
		Result result = new Result();
		try{
			//如果不为空，先查询出二级，在查询一级
			List<TourDateVO> tourDateVOs =new ArrayList<TourDateVO>();
			if(searchQuery.getDateId2() !=null && searchQuery.getDateId2() >0){
				TourDate TourDateQuery = new TourDate();
				TourDateQuery.setYn(1);
				TourDateQuery.setDateLevel(2);  //查询er级
				TourDateQuery.setDateId(searchQuery.getDateId2());
				List<TourDate> tourDates = tourDateDao.selectByCondition(TourDateQuery);
				if(tourDates !=null && tourDates.size() >0){
					TourDate dt =tourDates.get(0);
					TourDateQuery =new TourDate();
					TourDateQuery.setYn(1);
					TourDateQuery.setDateLevel(1);  //查询1级
					//TourDateQuery.setModified(new Date());
					TourDateQuery.setDateId(dt.getParentDateId());
					List<TourDate> tourDate = tourDateDao.selectByCondition(TourDateQuery);
					for(TourDate date:tourDate){
						TourDateVO dateVO =new TourDateVO();
						dateVO.setDateId(date.getDateId());
						dateVO.setDateName(date.getDateName());
						dateVO.setChoose(true);
						TourDateQuery =new TourDate();
						TourDateQuery.setYn(1);
						TourDateQuery.setDateLevel(2);
						TourDateQuery.setParentDateId(date.getDateId());
						List<TourDate> tours = tourDateDao.selectByCondition(TourDateQuery);
						for(TourDate d:tours){
							if(searchQuery.getDateId2().equals(d.getDateId())){
								d.setYn(2);//选中二级
							}
						}
						dateVO.setTourDateChilds(tours);
						tourDateVOs.add(dateVO);
						
					}
				}
				
			}else{
				TourDate TourDateQuery = new TourDate();
				TourDateQuery.setYn(1);
				TourDateQuery.setDateLevel(1);  //查询1级
				//TourDateQuery.setModified(new Date());
				List<TourDate> tourDates = tourDateDao.selectByCondition(TourDateQuery);
//				boolean isChoose =true;
				for(TourDate date:tourDates){
					TourDateVO dateVO =new TourDateVO();
					dateVO.setDateId(date.getDateId());
					dateVO.setDateName(date.getDateName());
					TourDateQuery.setDateLevel(2);
					TourDateQuery.setParentDateId(date.getDateId());
					dateVO.setTourDateChilds(tourDateDao.selectByCondition(TourDateQuery));
					tourDateVOs.add(dateVO);
					
				}
			}
			
			
			TourDestination TourDestinationQuery = new TourDestination();
			TourDestinationQuery.setYn(1);
			TourDestinationQuery.setDestinationLevel(1);  //查询一级
			List<TourDestination> tourDestinations = tourDestinationDao.selectByCondition(TourDestinationQuery);
			List<TourDestinationVO> destinationVOs =new ArrayList<TourDestinationVO>();
			for(TourDestination dest:tourDestinations){
				TourDestinationVO destinationVO =new TourDestinationVO();
				destinationVO.setDestinationId(dest.getDestinationId());
				destinationVO.setDestinationName(dest.getDestinationName());
				if(searchQuery.getDestinationId1() !=null && 
						searchQuery.getDestinationId1().equals(dest.getDestinationId())){
						destinationVO.setChoose(true);
				}//判断选中
				TourDestinationQuery.setDestinationLevel(2);
				TourDestinationQuery.setParentDestinationId(dest.getDestinationId());
				destinationVO.setTourDestinationChilds(tourDestinationDao.selectByCondition(TourDestinationQuery));
				destinationVOs.add(destinationVO);
			}
			
			TourSubject TourSubjectQuery = new TourSubject();
			TourSubjectQuery.setYn(1);
			TourSubjectQuery.setSubjectLevel(1);  //查询一级
			List<TourSubject> tourSubjects = tourSubjectDao.selectByCondition(TourSubjectQuery);
			List<TourSubjectVO> subjectVOs =new ArrayList<TourSubjectVO>();
			for(TourSubject sub:tourSubjects){
				TourSubjectVO subjectVO =new TourSubjectVO();
				subjectVO.setSubjectId(sub.getSubjectId());
				subjectVO.setSubjectName(sub.getSubjectName());
				if(searchQuery.getSubjectId1() !=null && 
						searchQuery.getSubjectId1().equals(sub.getSubjectId())){
						subjectVO.setChoose(true);
				}//判断选中
				TourSubjectQuery.setSubjectLevel(2);
				TourSubjectQuery.setParentSubjectId(sub.getSubjectId());
				subjectVO.setTourSubjectChilds(tourSubjectDao.selectByCondition(TourSubjectQuery));
				subjectVOs.add(subjectVO);
			}
			log.error("开始1关键词为" + searchQuery.getN());
			Map<String, Object> map= getSolrItems(searchQuery);//获取商品
			Map<String, Object> returnMap=new HashMap<String, Object>();
			returnMap.put("tourDates", tourDateVOs);
			returnMap.put("tourDestinations", destinationVOs);
			returnMap.put("tourSubjects", subjectVOs);
			returnMap.put("items", map.get("list"));
			returnMap.put("total",  map.get("total"));
			returnMap.put("totalPage",  map.get("totalPage"));
			returnMap.put("curPage",  map.get("curPage"));
			result.setResult(returnMap);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	/**
     * 获取商品
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map<String, Object> getSolrItems(SearchQuery searchQuery){
    	Map<String, Object> map =new HashMap<String, Object>();
    	Result result = getSorlItemList(searchQuery);
 		if(result !=null && result.getSuccess()){
 			map = (Map<String, Object>) result.getResult();
 		}
		return map;
    }
    /**
     * 调用sorl接口
     * @param data
     * @return
     */
	private Result getSorlItemList(SearchQuery searchQuery){
		log.error("开始2关键为" + searchQuery.getN());
    	Result result =new Result();
    	
    	String itemSort = RedisUtils.get("ITEM_SORT");
    	if(StringUtils.isBlank(itemSort)){
			itemSort = "8";//默认商品编号降序排列
		}
    	searchQuery.setSort(itemSort);
    	
    	String n =searchQuery.getN();
    	if(StringUtils.isNotBlank(n)){
    		//增加目的地权重值 搜索排序
        	TourDestination tourDestination = new TourDestination();
        	tourDestination.setYn(Constants.YES);
        	tourDestination.setDestinationLevel(2);
        	tourDestination.setDestinationName(n);
        	List<TourDestination> tourDestinationList = tourDestinationDao.selectByCondition(tourDestination);
        	if(tourDestinationList == null || tourDestinationList.isEmpty()){
        	}else{
        		for(int i=0;i<n.length();i++){
        			String subStr = n.substring(i, i+1);
        			RedisUtils.zincrby(subStr,1,n);
        		}
        	}
        	
        	//关键词增加
        	StringBuffer keyword = new StringBuffer();
        	Date date = new Date();
        	keyword.append("keyword");
        	keyword.append(new SimpleDateFormat("yyyyMMdd").format(date));
        	log.error("开始3关键为" + searchQuery.getN());
        	RedisUtils.zincrby(keyword.toString(),1,n);
    	}
    	
    	if(StringUtils.isNotBlank(n)){
    		n =n.replaceAll(" ", "\b");//把空格转换
    	}
    	String data ="indexPage="+searchQuery.getPageNo()+"&pageSize="+searchQuery.getPageSize()+"&destinationId1="+searchQuery.getDestinationId1()+"&destinationId2="+searchQuery.getDestinationId2()+"&heartScore="+searchQuery.getHeartScore()+
    			"&qualityScore="+searchQuery.getQualityScore()+"&challengeScore="+searchQuery.getChallengeScore()+"&sort="+searchQuery.getSort()
    			+"&subjectId1="+searchQuery.getSubjectId1()+"&subjectId2="+searchQuery.getSubjectId2()
    			+"&originId1="+searchQuery.getOriginId1()+"&originId2="+searchQuery.getOriginId2()
    	        +"&dateId1="+searchQuery.getDateId1()+"&dateId2="+searchQuery.getDateId2()
    	        +"&n="+n;
    	String resultString  = HttpUtils.httpGetData(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.sorl_url)
    			, data , "UTF-8");
		result = JsonUtils.readValue(resultString, Result.class);
		return result;
    }

	@Override
	public Result getSearchItemsByPage(SearchQuery searchQuery) {
		Result result = new Result();
		result = getSorlItemList(searchQuery);
		return result;
	}

	@Override
	public Result autoComplete(String keyword) {
		Result result = new Result();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			TourDestinationQuery tourDestinationQuery = new TourDestinationQuery();
			tourDestinationQuery.setDestinationName(keyword);
			tourDestinationQuery.setYn(Constants.YES);
			int total = tourDestinationDao.countByCondition(tourDestinationQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			int totalPage = total/tourDestinationQuery.getPageSize() + 1;
			if(tourDestinationQuery.getPageNo() > totalPage){
				tourDestinationQuery.setPageNo(totalPage);
			}
			List<TourDestination> tourDestinationList = tourDestinationDao.selectByConditionForPage(tourDestinationQuery);
			resultMap.put("data", tourDestinationList);
			result.setResult(resultMap);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
}
