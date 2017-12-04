package com.xindong.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xindong.api.dao.ItemDAO;
import com.xindong.api.dao.TbCouponDao;
import com.xindong.api.dao.TbCouponPromoDao;
import com.xindong.api.dao.UserInfoDAO;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.TbCoupon;
import com.xindong.api.domain.TbCouponPromo;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.query.TbCouponPromoQuery;
import com.xindong.api.domain.query.TbCouponQuery;
import com.xindong.api.service.CouponService;
import com.xindong.api.service.OrderInfoService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.EcUtils;

/**
 * 优惠券相关
 * @author BCSK742A
 *
 */
@Service("couponService")
public class CouponServiceImpl implements CouponService {
	private static final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);
	
	@Autowired
	private TbCouponDao tbCouponDao;
	@Autowired
	private TbCouponPromoDao tbCouponPromoDao;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private ItemDAO itemDao;
	@Autowired
	private DataSourceTransactionManager transactionManager;
	@Autowired
	private UserInfoDAO userInfoDAO;
	
	/** 查询我的优惠券 */
	@Override
	public Result selectByUserId(Integer userId) {
		Result result = new Result();
		try{
			//查询条件， 实例化TbCouponQuery
			TbCouponQuery tbCouponQuery = new TbCouponQuery();
			//Yn为有效状态，1为有效，0位无效
			tbCouponQuery.setYn(1);
			//查询条件：userId的值赋给TbCouponQuery中用户ID
			tbCouponQuery.setUserId(userId);
			// 查询条件：优惠券状态，0为未使用，1为已使用，2位已过期，3位已锁定
			tbCouponQuery.setCouponState(0);
			//创建wsyList集合接收从数据库tb_coupon表中查询到的未使用优惠券信息
			List<TbCoupon> wsyList = tbCouponDao.selectByCondition(tbCouponQuery);
			// 查询优惠券状态为3已锁定的优惠券信息
			tbCouponQuery.setCouponState(3);
			//创建ysdList集合接收从数据库tb_coupon表中查询到的已锁定优惠券信息
			List<TbCoupon> ysdList = tbCouponDao.selectByCondition(tbCouponQuery);
			// 查询优惠券状态为1已使用的优惠券信息
			tbCouponQuery.setCouponState(1);
			//创建ysyList集合接收从数据库tb_coupon表中查询到的已锁定优惠券信息
			List<TbCoupon> ysyList = tbCouponDao.selectByCondition(tbCouponQuery);
			// 查询优惠券状态为0已过期的优惠券信息
			tbCouponQuery.setCouponState(2);
			//创建ygqList集合接收从数据库tb_coupon表中查询到的已锁定优惠券信息
			List<TbCoupon> ygqList = tbCouponDao.selectByCondition(tbCouponQuery);
			
			//初始化一个新的list集合
			List<TbCoupon> list = new ArrayList<TbCoupon>();
			//把wsyList集合中值添加给list
			list.addAll(wsyList);
			//把ysdList集合中值添加给list
			list.addAll(ysdList);
			//把ysyList集合中值添加给list
			list.addAll(ysyList);
			//把ygqList集合中值添加给list
			list.addAll(ygqList);
			
			//把list集合中的值赋给result
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	/** 使用优惠券 */
	@Override
	public Result selectUseByUserId(Integer userId, BigDecimal price) {
		Result result = new Result();
		try{
			// 准备数据,ky集合为存储可用优惠券
			List<TbCoupon> ky = new ArrayList<TbCoupon>();
			//bky集合为存储不可用优惠券
			List<TbCoupon> bky = new ArrayList<TbCoupon>();
			//kymj集合为存储可用满减券
			List<TbCoupon> kymj = new ArrayList<TbCoupon>();
			//kyzj集合为存储可用直减券
			List<TbCoupon> kyzj = new ArrayList<TbCoupon>();
			//kymyf集合为存储可用免运费券
			List<TbCoupon> kymyf = new ArrayList<TbCoupon>();
			// 查询数据，实例化TbCouponQuery
			TbCouponQuery tbCouponQuery = new TbCouponQuery();
			//查询条件：Yn为有效状态，1为有效，0位无效
			tbCouponQuery.setYn(1);
			//查询条件：优惠券状态，0为未使用，1为已使用，2位已过期，3位已锁定
			tbCouponQuery.setCouponState(0);
			//查询条件：userId的值赋给TbCouponQuery中用户ID
			tbCouponQuery.setUserId(userId);
			//查询条件：根据优惠券类型查询满减券
			tbCouponQuery.setCouponType(0);
			//创建不可用满减券bkymj集合接收从数据库tb_coupon表中查询的优惠券信息
			List<TbCoupon> bkymj = tbCouponDao.selectByCondition(tbCouponQuery);
			
			// 根据优惠券类型查询直减券
			tbCouponQuery.setCouponType(1);
			//创建不可用直减券bkyzj集合接收从数据库tb_coupon表中查询的优惠券信息
			List<TbCoupon> bkyzj = tbCouponDao.selectByCondition(tbCouponQuery);
			
			// 根据优惠券类型查询免运费券
			tbCouponQuery.setCouponType(2);
			//创建不可用免运费券bkymyf集合接收从数据库tb_coupon表中查询的优惠券信息
			List<TbCoupon> bkymyf = tbCouponDao.selectByCondition(tbCouponQuery);
			
			//判断bkymj是否有赋值，如果有返回true进入
			if(!bkymj.isEmpty()){
				//使用foreach循环把bkymj中的值赋给tbCoupon
				for (TbCoupon tbCoupon : bkymj) {
					//判断
					//得到订单限额getOrderQuota()
					//使用price的值与根据BigDecimal重载OrderQuota订单限额的值用compareTo比较，返回三种结果，1,0，-1;
					if(price.compareTo(new BigDecimal(tbCoupon.getOrderQuota())) >= 0){
						//可以满减添加优惠券信息
						kymj.add(tbCoupon);
					}
				}
				//把可用满减kymj集合赋给可用满减集合ky接收
				ky.addAll(kymj);
				//removeAll掉kymj集合中信息存储在bkymj集合
				bkymj.removeAll(kymj);
			}
			//不可用满减优惠券添加到不可用集合中
			bky.addAll(bkymj);
			//判断不可用直减集合中的值不为空进入
			if(!bkyzj.isEmpty()){
				//使用for循环把不可用直减集合中的值赋给tbCoupon优惠券信息类
				for (TbCoupon tbCoupon : bkyzj) {
					//tbCoupon优惠券信息类中的值赋给可用直减集合
					kyzj.add(tbCoupon);
				}
				//可用直减集合添加到可用集合中
				ky.addAll(kyzj);
				//不可用直减中的值把包含可用直减部分删除掉
				bkyzj.removeAll(kyzj);
			}
			//不可用直减集合添加到不可用优惠券集合
			bky.addAll(bkyzj);
			//判断不可用 免运费券不为空进入
			if(!bkymyf.isEmpty()){
				
				for (TbCoupon tbCoupon : bkymyf) {
					if(price.compareTo(new BigDecimal(39)) <= 0){
						kymyf.add(tbCoupon);
					}
				}
				ky.addAll(kymyf);
				bkymyf.removeAll(kymyf);
			}
			bky.addAll(bkymyf);
			//创建map集合接收list集合中信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usable", ky);
			map.put("nousable", bky);
			result.setResult(map);
			
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result drawCoupon(Integer userId, Integer couponPromoId) {
		Result result = new Result();
		try{
			TbCouponPromoQuery tbCouponPromoQuery = new TbCouponPromoQuery();
			tbCouponPromoQuery.setCouponPromoId(couponPromoId);
			tbCouponPromoQuery.setCouponPromoState(1);
			List<TbCouponPromo> tbCouponPromoList = tbCouponPromoDao.selectByCondition(tbCouponPromoQuery);
			if(tbCouponPromoList == null || tbCouponPromoList.isEmpty()){
				log.info("活动已结束");
				result.setResult(4);
			}else{
				TbCouponPromo tbCouponPromo = tbCouponPromoList.get(0);
				if(tbCouponPromo.getCouponValidEndtime().compareTo(new Date()) > 0){
					TbCouponQuery tbCouponQuery = new TbCouponQuery();
					tbCouponQuery.setCouponPromId(couponPromoId);
					List<TbCoupon> couponList = tbCouponDao.selectByCondition(tbCouponQuery);
					tbCouponQuery.setUserId(userId);
					List<TbCoupon> list = tbCouponDao.selectByCondition(tbCouponQuery);
					if(list == null || list.isEmpty()){
						int count = 0;
						if(couponList != null && !couponList.isEmpty()){
							count = couponList.size();
						}
						if(tbCouponPromo.getCouponApplyAmount() > count){
							TbCoupon tbCoupon = new TbCoupon();
							tbCoupon.setUserId(userId);
							tbCoupon.setCouponPromId(couponPromoId);
							tbCoupon.setCouponName(tbCouponPromo.getCouponPromoName());
							tbCoupon.setCouponType(tbCouponPromo.getCouponType());
							tbCoupon.setOrderQuota(tbCouponPromo.getOrderQuota());
							tbCoupon.setCouponAmount(tbCouponPromo.getCouponAmount());
							tbCoupon.setCouponState(0);
							tbCoupon.setYn(1);
							tbCoupon.setCouponValidStarttime(tbCouponPromo.getCouponValidStarttime());
							tbCoupon.setCouponValidEndtime(tbCouponPromo.getCouponValidEndtime());
							tbCouponDao.insert(tbCoupon);
							
							tbCouponPromo.setCouponGrantAmount(tbCouponPromo.getCouponGrantAmount() + 1);
							tbCouponPromoDao.modify(tbCouponPromo);
							
							log.info("用户成功领取");
							result.setResult(0);
						}else{
							log.info("活动数量已经领取完");
							result.setResult(2);
						}
					}else{
						log.info("用户已领取");
						result.setResult(1);
					}
				}else{
					log.info("活动已过期");
					result.setResult(3);
				}
			}
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result selectByCouponQuery(TbCouponQuery couponQuery) {
		Result result = new Result();
		try{
			couponQuery.setYn(Constants.YES);
			Integer state = couponQuery.getCouponState() ==null ? Constants.CouponStatus.UNUSED :couponQuery.getCouponState();
			couponQuery.setCouponState(state);
			int total = tbCouponDao.countByCondition(couponQuery);
			int noUse = 0;//未使用
			int used = 0;//已使用
			int overUse = 0;//已过期
			if(state.equals(Constants.CouponStatus.UNUSED)){
				noUse = total ;
			}else{
				couponQuery.setCouponState(Constants.CouponStatus.USED);
				used= tbCouponDao.countByCondition(couponQuery);
				
				couponQuery.setCouponState(Constants.CouponStatus.EXPIRED);
				overUse= tbCouponDao.countByCondition(couponQuery);
			}
			
			if(state.equals(Constants.CouponStatus.EXPIRED)){
				overUse=total;
			}else{
				couponQuery.setCouponState(Constants.CouponStatus.UNUSED);
				noUse= tbCouponDao.countByCondition(couponQuery);
				
				couponQuery.setCouponState(Constants.CouponStatus.USED);
				used= tbCouponDao.countByCondition(couponQuery);
			}
			
			if(state.equals(Constants.CouponStatus.USED)){
				used = total ;
			}else{
				couponQuery.setCouponState(Constants.CouponStatus.UNUSED);
				noUse= tbCouponDao.countByCondition(couponQuery);
				
				couponQuery.setCouponState(Constants.CouponStatus.EXPIRED);
				overUse= tbCouponDao.countByCondition(couponQuery);
			}
			
			Map<String, Object> map=new HashMap<String, Object>();
			if(total == 0){
				map.put("noUse", noUse);
				map.put("used", used);
				map.put("overUse", overUse);
				result.setResult(map);
				EcUtils.setSuccessResult(result);
			}
			
			couponQuery.setCouponState(state);
			int totalPage = total/couponQuery.getPageSize() + 1;
			if(couponQuery.getPageNo() > totalPage){
				couponQuery.setPageNo(totalPage);
			}
			List<TbCoupon> list = tbCouponDao.selectByConditionForPage(couponQuery);
			for(TbCoupon coupon:list){
				Integer couponPromoId = coupon.getCouponPromId();
				TbCouponPromo couponPromo = tbCouponPromoDao.selectByCouponPromoId(couponPromoId);
				if(couponPromo != null){
					coupon.setItemDesc(couponPromo.getCouponPromoName());
					coupon.setCouponRuleContent(couponPromo.getCouponRuleContent());
				}
			}
			map.put("noUse", noUse);
			map.put("used", used);
			map.put("overUse", overUse);
			map.put("list", list);
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", couponQuery.getPageNo());
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getCouponCanUse(OrderInfo order) {
    	Result result =new Result();
    	try{
        //计算此笔定的 金额
        Result  re	= orderInfoService.calculateOrderMoney(order,0,null);
        Map<String, Object> map = (Map<String, Object>) re.getResult();
    	BigDecimal cartPromPrice =(BigDecimal) map.get("balanceMoney");
		
		//查询该用户可用的优惠券
    	TbCouponQuery tbCouponQuery =new TbCouponQuery();
    	tbCouponQuery.setYn(Constants.YES);
    	tbCouponQuery.setCouponState(Constants.CouponStatus.UNUSED);
    	//tbCouponQuery.setCouponValidStarttime(new Date());
    	//tbCouponQuery.setCouponValidEndtime(new Date());
    	tbCouponQuery.setUserId(order.getUserId());
    	List<TbCoupon> coupons = new ArrayList<TbCoupon>();
    	List<TbCoupon> unCoupons = new ArrayList<TbCoupon>();
		List<TbCoupon> wsyList = tbCouponDao.selectByCondition(tbCouponQuery);
		for(TbCoupon tbCoupon:wsyList){
			Integer type = tbCoupon.getCouponType();
			//类型判断
			Integer typeRule = tbCoupon.getCouponRuleType();
			if(Constants.CouponRuleType.ITEM.equals(typeRule)){
				Integer itemId =tbCoupon.getItemId();
				if(itemId !=null){
					Item item = itemDao.selectByPrimaryKey(itemId);
					if(item ==null){
						continue;
					}
					tbCoupon.setItemDesc(item.getItemName());
					//如果先品类 但是不等于当前商品，则不可用
					if(!itemId.equals(order.getItemId())){
						 unCoupons.add(tbCoupon);
						 continue;
					}
				}
			}else if(Constants.CouponRuleType.SKU.equals(typeRule)){
				String skus =tbCoupon.getBakFirst();
				if(StringUtils.isBlank(skus)){
					continue;
				}
				//skus =1001-1230-120
				String skuId =order.getSkuId().toString();
				String skuSp[] =skus.split("[#]");
				Integer length =skuSp.length;
				boolean istrue =false;//是否有该sku的优惠券
				for(int i=0;i<length;i++){
					if(StringUtils.isBlank(skuSp[i])){
						continue;
					}
					//如果有 
					if(skuId.equals(skuSp[i])){
						istrue =true;
						break;
					}
				}
				//如果没有，则加入不可用优惠券，继续循环下一个。如果有，继续判断时间。
				if(!istrue){
					 unCoupons.add(tbCoupon);
					 continue;
				}
				if(length >1){
					tbCoupon.setItemDesc("仅可购买部分商品");//
				}else{
					if(StringUtils.isBlank(skuSp[0])){
							continue;
					}
					Integer skuIdn =Integer.valueOf(skuSp[0]);
					String name =itemDao.selectByskuId(skuIdn);
					tbCoupon.setItemDesc(name);
				}
				
			}else{
				tbCoupon.setItemDesc("全品类");
			}
			//时间判断
			Date now =new Date();
			Date couponValidEndtime =tbCoupon.getCouponValidEndtime();
			Date couponValidStarttime =tbCoupon.getCouponValidStarttime();
			
			boolean yes =true;//判断满减
			Integer amount = tbCoupon.getOrderQuota();
			if(Constants.CouponType.FULL.equals(type)){
				if(cartPromPrice.subtract(new BigDecimal(amount)).compareTo(BigDecimal.valueOf(0)) <0){
					//说明不可用使用该优惠券
					yes =false;
				}
			}
			//结束时间为空，判断开始时间
			if(couponValidEndtime ==null ){
				if(couponValidStarttime ==null){
					if(yes){
					    //说明可用使用该优惠券
					   coupons.add(tbCoupon);
					}else{
					   unCoupons.add(tbCoupon);
					}
				}else if(couponValidStarttime!=null  && couponValidStarttime.before(now)){
					if(yes){
					    //说明可用使用该优惠券
					   coupons.add(tbCoupon);
					}else{
					   unCoupons.add(tbCoupon);
					}
				}else{
					unCoupons.add(tbCoupon);
				}
				continue;
			}
			//开始时间为空，判断结束时间
			if(couponValidStarttime ==null ){
				 if(couponValidEndtime.after(now)){
					if(yes){
					    //说明可用使用该优惠券
					   coupons.add(tbCoupon);
					}else{
					   unCoupons.add(tbCoupon);
					}
				}else{
					unCoupons.add(tbCoupon);
				}
				continue;
			}
			//都不为空时，判断
			if( couponValidEndtime.after(now) && couponValidStarttime.before(now)){
				if(yes){
				    //说明可用使用该优惠券
				   coupons.add(tbCoupon);
				}else{
				   unCoupons.add(tbCoupon);
				}
			}else{
				  unCoupons.add(tbCoupon);
			}
			
		}
		Map<String, Object> mapr =new HashMap<String, Object>();
		mapr.put("usable", coupons);
		mapr.put("nousable", unCoupons);//不可用
		result.setResult(mapr);
		EcUtils.setSuccessResult(result);
	    }catch (Exception e) {
			log.error("计算优惠券出现错误",e);
			EcUtils.setExceptionResult(result);
			return result;
		}
    	return result;
    }
	
	@Override
	public Result receiveCoupon(Integer userId, Integer couponPromoId) {
		Result result = new Result();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//判断用户手机号是否注册
			UserInfo userInfo = userInfoDAO.selectByPrimaryKey(userId);
			String mobile = userInfo.getMobile();
			if(StringUtils.isBlank(mobile)){
				result.setResultCode("1001");
				result.setResultMessage("请您先登陆");
				return result;
			}
			
			//判断用户是否已经领取过该优惠券
			TbCouponQuery tbCouponQuery =new TbCouponQuery();
			tbCouponQuery.setYn(Constants.YES);
			tbCouponQuery.setCouponPromId(couponPromoId);
			tbCouponQuery.setUserId(userId);
			List<TbCoupon>  tbcoupons =tbCouponDao.selectByCondition(tbCouponQuery);
			if(tbcoupons !=null && tbcoupons.size() > 0){
				result.setResultCode("200");
				result.setSuccess(true);
				result.setResultMessage("您已经领取过该优惠券");
				return result;
			}
			//判断优惠 是否可以领取
			TbCouponPromoQuery tbCouponPromoQuery =new TbCouponPromoQuery();
			tbCouponPromoQuery.setCouponBusinessType(Constants.CouponBusinessType.link);
			tbCouponPromoQuery.setYn(Constants.YES);
			tbCouponPromoQuery.setCouponPromoId(couponPromoId);
			tbCouponPromoQuery.setCouponPromoState(1);//进行中的
			List<TbCouponPromo> tbCouponPromoList =tbCouponPromoDao.selectByCondition(tbCouponPromoQuery );
			if(tbCouponPromoList ==null || tbCouponPromoList.size() ==0){
				result.setResultCode("200");
				result.setSuccess(true);
				result.setResultMessage("优惠券不存在");
				return result;
			}
			final TbCouponPromo promo =tbCouponPromoList.get(0);
			if(promo.getCouponValidEndtime().compareTo(new Date()) < 0){
				result.setResultCode("200");
				result.setSuccess(true);
				result.setResultMessage("优惠券活动已过期");
				return result;
			}
			//优惠券数量  couponApplyAmount不减少   couponGrantAmount发放数量增加
			if(promo.getCouponApplyAmount() <= promo.getCouponGrantAmount()){
				result.setResultCode("200");
				result.setSuccess(true);
				result.setResultMessage("优惠券已经被抢完啦");
				return result;
			}
			Integer couponAmount = promo.getCouponAmount();
			//判读优惠券是否随机发放金额
			if(promo.getAmountRandom() == 1){
				Integer min = promo.getCouponAmountMin();
				Integer max = promo.getCouponAmount();
				couponAmount =  (int)((max - min)* Math.random() + min);
			}
			
			//用户获得优惠券
			final TbCoupon tbCoupon = new TbCoupon();
			tbCoupon.setCouponPromId(promo.getCouponPromoId());
			tbCoupon.setCouponType(promo.getCouponType());
			tbCoupon.setOrderQuota(promo.getOrderQuota());
			tbCoupon.setCouponAmount(couponAmount);
			tbCoupon.setCouponState(0);
			tbCoupon.setYn(1);
			tbCoupon.setCouponValidStarttime(promo.getCouponValidStarttime());
			tbCoupon.setCouponValidEndtime(promo.getCouponValidEndtime());
			tbCoupon.setCouponName(promo.getCouponPromoName());
			tbCoupon.setUserId(userId);
			tbCoupon.setItemId(promo.getItemId());
			tbCoupon.setSkuId(promo.getSkuId());
			tbCoupon.setBakFirst(promo.getBakFirst());
			tbCoupon.setBakSecond(promo.getBakSecond());
			tbCoupon.setCouponRuleType(promo.getCouponRuleType());
			// 添加用户优惠券
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					tbCouponDao.insert(tbCoupon);
					promo.setCouponGrantAmount(1);
					tbCouponPromoDao.modify(promo);
				}
			});
			resultMap.put("tbCoupon", tbCoupon);
			result.setResult(resultMap);
			EcUtils.setSuccessResult(result);
			result.setResultMessage("领取成功");
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getAllCouponPromo(TbCouponPromoQuery couponPromoQuery) {
		Result result = new Result();
		try{
			TbCouponPromoQuery tbCouponPromoQuery = new TbCouponPromoQuery();
			tbCouponPromoQuery.setCouponPromoState(1);//进行中
			tbCouponPromoQuery.setYn(1);
			tbCouponPromoQuery.setPromoStarttime(new Date());
			tbCouponPromoQuery.setPromoEndtime(new Date());
			int total = tbCouponPromoDao.countByCondition(tbCouponPromoQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			int totalPage = total/couponPromoQuery.getPageSize() + 1;
			if(couponPromoQuery.getPageNo() > totalPage){
				couponPromoQuery.setPageNo(totalPage);
			}
			List<TbCouponPromo> tbCouponPromoList = tbCouponPromoDao.selectByConditionForPage(tbCouponPromoQuery);
			for(TbCouponPromo promo:tbCouponPromoList){
				if(Constants.CouponRuleType.ITEM.equals(promo.getCouponRuleType())){
					Integer itemId =promo.getItemId();
					if(itemId !=null){
						Item item = itemDao.selectByPrimaryKey(itemId);
						if(item ==null){
							continue;
						}
						promo.setItemDesc(item.getItemName());
					}
					
				}else if(Constants.CouponRuleType.SKU.equals(promo.getCouponRuleType())){
					String skus =promo.getBakFirst();
					if(StringUtils.isBlank(skus)){
						continue;
					}
					//skus =1001-1230-120
					String skuSp[] =skus.split("[#]");
					int  length =skuSp.length;
					if(length >1){
						promo.setItemDesc("仅可购买部分商品");//
					}else{
						if(StringUtils.isBlank(skuSp[0])){
							continue;
						}
						Integer skuId =Integer.valueOf(skuSp[0]);
						String name =itemDao.selectByskuId(skuId);
						promo.setItemDesc(name);
					}
					
				}else{
					promo.setItemDesc("全品类");
				}
			}
		
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("list", tbCouponPromoList);
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", couponPromoQuery.getPageNo());
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
}