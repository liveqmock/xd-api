package com.xindong.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.xindong.api.common.utils.DateUtil;
import com.xindong.api.dao.ItemDAO;
import com.xindong.api.dao.ItemImageDAO;
import com.xindong.api.dao.OperaLogDAO;
import com.xindong.api.dao.OperationsStaffDAO;
import com.xindong.api.dao.OrderInfoDAO;
import com.xindong.api.dao.OrderInvoiceDAO;
import com.xindong.api.dao.OrderPassengerDAO;
import com.xindong.api.dao.OrderPaymentDAO;
import com.xindong.api.dao.PaymentInfoDAO;
import com.xindong.api.dao.PromotionInfoDao;
import com.xindong.api.dao.PromotionSkuDao;
import com.xindong.api.dao.SkuDAO;
import com.xindong.api.dao.SmsDAO;
import com.xindong.api.dao.TbCouponDao;
import com.xindong.api.dao.UserBalanceDao;
import com.xindong.api.dao.UserBalanceLockDao;
import com.xindong.api.dao.UserInfoDAO;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.ItemImage;
import com.xindong.api.domain.OperaLog;
import com.xindong.api.domain.OperationsStaff;
import com.xindong.api.domain.OrderInfo;
import com.xindong.api.domain.OrderInvoice;
import com.xindong.api.domain.OrderPassenger;
import com.xindong.api.domain.OrderPayment;
import com.xindong.api.domain.PaymentInfo;
import com.xindong.api.domain.PromotionInfo;
import com.xindong.api.domain.PromotionSku;
import com.xindong.api.domain.Sku;
import com.xindong.api.domain.Sms;
import com.xindong.api.domain.SystemConstantValue;
import com.xindong.api.domain.TbCoupon;
import com.xindong.api.domain.UserBalance;
import com.xindong.api.domain.UserBalanceLock;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.query.OperationsStaffQuery;
import com.xindong.api.domain.query.OrderInfoQuery;
import com.xindong.api.domain.query.OrderPassengerQuery;
import com.xindong.api.domain.query.PromotionSkuQuery;
import com.xindong.api.domain.vo.OrderInfoVO;
import com.xindong.api.service.OrderInfoService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;

@Service(value = "orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
	private static final Logger log = LoggerFactory.getLogger(OrderInfoServiceImpl.class);
	@Autowired
	private OrderInfoDAO orderInfoDao;
	@Autowired
	private DataSourceTransactionManager transactionManager;
	@Autowired
	private OrderPassengerDAO orderPassengerDao;;
	@Autowired
	private UserInfoDAO userInfoDao;
	@Autowired
	private OrderInvoiceDAO orderInvoiceDao;
	// @Autowired
	// private OrderRemarkDAO orderRemarkDao;
	@Autowired
	private OrderPaymentDAO orderPaymentDao;
	@Autowired
	private PaymentInfoDAO paymentInfoDao;
	@Autowired
	private ItemDAO itemDao;
	@Autowired
	private SkuDAO skuDao;
	@Autowired
	private OperaLogDAO operaLogDao;
	@Autowired
	private SmsDAO smsDao;
	@Autowired
	private OperationsStaffDAO operationsStaffDao;
	@Autowired
	private UserBalanceDao userBalanceDao;
	@Autowired
	private UserBalanceLockDao userBalanceLockDao;
	@Autowired
	private TbCouponDao tbCouponDao;
	@Autowired
	private PromotionSkuDao promotionSkuDao;
	@Autowired
	private ItemImageDAO itemImageDao;
	@Autowired
	private PromotionInfoDao promotionInfoDao;

	/**
	 * 查询订单列表
	 */
	@Override
	public Result getOrderInfosByPage(OrderInfoQuery orderInfoQuery) {
		Result result = new Result();
		try {
			orderInfoQuery.setYn(Constants.YES);
			if (orderInfoQuery.getOrderStatus() != null && orderInfoQuery.getOrderStatus().equals(Constants.OrderStatus.YZF)) {
				orderInfoQuery.setOrderStatus(88);// 查询待确认和已支付的订单
			}
			Integer fromWhere = orderInfoQuery.getFromWhere();// 图片显示
			orderInfoQuery.setFromWhere(null);// 查询所有。
			int total = orderInfoDao.countByCondition(orderInfoQuery);
			if (total == 0) {
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			int totalPage = total / orderInfoQuery.getPageSize() + 1;
			if (orderInfoQuery.getPageNo() > totalPage) {
				orderInfoQuery.setPageNo(totalPage);
			}
			List<OrderInfo> list = orderInfoDao.selectByConditionForPage(orderInfoQuery);
			List<OrderInfoVO> vos = new ArrayList<OrderInfoVO>();
			for (OrderInfo info : list) {
				OrderInfoVO vo = new OrderInfoVO(info);
				// h5展示H5图片
				if (fromWhere != null && fromWhere.equals(Constants.FromWhere.H5)) {
					ItemImage itemq = new ItemImage();
					itemq.setItemId(info.getItemId());
					itemq.setType(1);// h5图片
					List<ItemImage> itemImages = itemImageDao.selectByCondition(itemq);
					if (itemImages != null && itemImages.size() > 0) {
						vo.setItemImage(itemImages.get(0).getImageUrl());
					}
				}
				Integer orderStatus = info.getOrderStatus();
				if (orderStatus.equals(Constants.OrderStatus.DQR)) {
					orderStatus = Constants.OrderStatus.YZF;// 如果是待确认状态前端还是显示已支付
				}
				vo.setOrderStatusStr(ComFunction.getSystemConstantValue(Constants.SYSTEM_ORDER_TYPE, orderStatus));
				vo.setPayTypeStr(ComFunction.getSystemConstantValue(Constants.SYSTEM_PAY_TYPE, info.getPayType()));
				if (Constants.OrderStatus.DFK.equals(orderStatus)) {
					vo.setResidualTime(info.getCreated());
				}
				Date returnDate = info.getReturnDate();
				Date startDate = info.getStartDate();
				vo.setReturnDate(returnDate);
				vo.setStartDate(startDate);
				/*
				 * if(Constants.OrderType.TRAVEL.equals(info.getOrderType())){
				 * Integer skuId =info.getSkuId(); Sku sku =
				 * skuDao.selectByPrimaryKey(skuId); if(sku ==null){ }else{
				 * vo.setReturnDate(sku.getSkuReturnDate());
				 * vo.setStartDate(sku.getSkuStartDate()); } }else{
				 * vo.setReturnDate(info.getReturnDate());
				 * vo.setStartDate(info.getStartDate()); }
				 */
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				Date currentDate = calendar.getTime();
				vo.setCurrentDate(currentDate);
				vos.add(vo);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("datas", vos);
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", orderInfoQuery.getPageNo());
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	/**
	 * 查询订单详情
	 */
	@Override
	public Result getOrderInfoByOrderIdAndUserId(Integer orderId, Integer userId, Integer fromWhere) {
		Result result = new Result();
		try {
			OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
			orderInfoQuery.setOrderId(orderId);
			orderInfoQuery.setUserId(userId);
			List<OrderInfo> infos = orderInfoDao.selectByCondition(orderInfoQuery);
			if (infos == null || infos.size() == 0) {
				result.setResultCode("1002");
				result.setResultMessage("订单不存在");
				return result;
			}
			OrderInfo info = infos.get(0);
			OrderInfoVO orderVo = new OrderInfoVO(info);
			Integer orderStatus = info.getOrderStatus();
			if (orderStatus.equals(Constants.OrderStatus.DQR)) {
				orderStatus = Constants.OrderStatus.YZF;// 如果是待确认状态前端还是显示已支付
			}
			// h5展示H5图片
			if (fromWhere == null) {
				ItemImage itemq = new ItemImage();
				itemq.setItemId(info.getItemId());
				itemq.setType(1);// h5图片
				List<ItemImage> itemImages = itemImageDao.selectByCondition(itemq);
				if (itemImages != null && itemImages.size() > 0) {
					orderVo.setItemImage(itemImages.get(0).getImageUrl());
				}
			}
			
			orderVo.setOrderStatusStr(ComFunction.getSystemConstantValue(Constants.SYSTEM_ORDER_TYPE, orderStatus));
			orderVo.setPayTypeStr(ComFunction.getSystemConstantValue(Constants.SYSTEM_PAY_TYPE, info.getPayType()));
			if (Constants.OrderStatus.DFK.equals(info.getOrderStatus())) {
				orderVo.setResidualTime(info.getCreated());
			}
			orderVo.setTravelCrowd(ComFunction.getSystemConstantValue(Constants.SYSTEM_TRAVEL_TYPE, info.getBalanceId()));
			orderVo.setReturnDate(info.getReturnDate());
			orderVo.setStartDate(info.getStartDate());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date currentDate = calendar.getTime();
			orderVo.setCurrentDate(currentDate);
			/*
			 * Integer skuId =info.getSkuId(); Sku sku =
			 * skuDao.selectByPrimaryKey(skuId); if(sku ==null){
			 * orderVo.setReturnDate(null); orderVo.setStartDate(null); }else{
			 * if(Constants.OrderType.TRAVEL.equals(info.getOrderType())){
			 * orderVo.setReturnDate(sku.getSkuReturnDate());
			 * orderVo.setStartDate(sku.getSkuStartDate()); }else{
			 * orderVo.setReturnDate(info.getReturnDate());
			 * orderVo.setStartDate(info.getStartDate()); }
			 * 
			 * }
			 */
			List<OrderPassenger> passenges = new ArrayList<OrderPassenger>();
			// 咨询订单没有旅客
			if (Constants.OrderType.TRAVEL.equals(info.getOrderType())) {
				OrderPassengerQuery passengerQuery = new OrderPassengerQuery();
				passengerQuery.setOrderId(orderId);
				passenges = orderPassengerDao.selectByCondition(passengerQuery);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderInfo", orderVo);
			map.put("passenges", passenges);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result submitSpecialOrder(final OrderInfo orderInfo) {
		final Result result = new Result();
		try {
			Integer itemId = orderInfo.getItemId();
			Sku sku = new Sku();
			sku.setYn(Constants.YES);
			sku.setItemId(itemId);
			sku.setEndTime(new Date());// 查询报名截止日期大于当前的sku
			List<Sku> skus = skuDao.selectByCondition(sku);
			if (skus == null || skus.size() == 0) {
				/*
				 * result.setResultCode("1002");
				 * result.setResultMessage("商品属性不存在"); return result;
				 */
			} else {
				sku = skus.get(0);
			}
			Item item = itemDao.selectByPrimaryKey(itemId);
			if (item == null) {
				result.setResultCode("1002");
				result.setResultMessage("商品信息不存在");
				return result;
			}
			UserInfo userInfo = userInfoDao.selectByPrimaryKey(orderInfo.getUserId());
			if (userInfo == null) {
				result.setResultCode("1002");
				result.setResultMessage("用户信息错误");
				return result;
			}
			Date orderTime = new Date();
			int cancleDate = Integer.valueOf(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.cancle_special_order_time));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(orderTime);
			calendar.add(Calendar.DATE, cancleDate);

			orderInfo.setItemImage(item.getItemImage());
			orderInfo.setItemName(item.getItemName());
			orderInfo.setSkuId(sku.getSkuId());
			orderInfo.setOrderType(Constants.OrderType.CONSULT);
			orderInfo.setOrderTime(orderTime);
			orderInfo.setCreated(calendar.getTime());// 过期时间
			orderInfo.setVenderUserId(item.getVenderUserId());
			orderInfo.setSkuAdultPrice(sku.getSkuAdultPrice());
			orderInfo.setSkuChildrenPrice(sku.getChildrenPrice());
			// 个性化订单应该支付的金额
			BigDecimal money = new BigDecimal(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.consult_order_money));
			BigDecimal zero = new BigDecimal(0);
			if (money.compareTo(zero) <= 0) {// 如果 小于0，则订单支付成功
				orderInfo.setPayTime(new Date());
				// 应付金额
				orderInfo.setPayMoney(zero);
				orderInfo.setOrderMoney(zero);
				orderInfo.setOughtPayMoney(zero);
				orderInfo.setOrderStatus(Constants.OrderStatus.YZF); // 修改订单状态为待付款
				orderInfo.setOrderRemark("咨询金额配置为0，不需要支付费用");
			} else {
				orderInfo.setOrderMoney(money);
				orderInfo.setOughtPayMoney(money);
				orderInfo.setOrderStatus(Constants.OrderStatus.DFK);
			}
			// 组装数据
			final OperaLog operaLog = new OperaLog();
			operaLog.setOperaBeforeTxt("准备创建个性化订单");
			operaLog.setOperaResultTx("个性化订单创建成功");
			operaLog.setOperaUserId(orderInfo.getUserId());
			operaLog.setOperaUserName(userInfo.getUserName());
			operaLog.setPoeraAfterTxt("个性化订单创建完成");
			operaLog.setOperaType(Constants.OperaLogType.ORDER);

			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					Integer id = orderInfoDao.insert(orderInfo);
					if (id == null || id == 0) {
						throw new RuntimeException("添加订单失败");
					}
					// 日志
					operaLog.setBusinessId(id);
					operaLogDao.insert(operaLog);

					result.setResult(id);
					result.setSuccess(true);
					result.setResultCode("200");
				}
			});
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;

	}

	@Override
	public Result submitOrder(final OrderInfo orderInfo, final OrderInvoice invoice, final List<OrderPassenger> passengers, final Integer useBalance,
			String coupons) {
		final Result result = new Result();
		// 1，插入订单表。库存 订单发票表，订单旅客表，日志表
		try {
			final UserInfo userInfo = userInfoDao.selectByPrimaryKey(orderInfo.getUserId());
			if (userInfo == null) {
				result.setResultCode("1002");
				result.setResultMessage("用户信息错误");
				return result;
			}
			Integer itemId = orderInfo.getItemId();
			final Item item = itemDao.selectByPrimaryKey(itemId);
			if (item == null) {
				result.setResultCode("1002");
				result.setResultMessage("商品信息不存在");
				return result;
			}
			Integer adultNum = orderInfo.getAdultNum();
			Integer childrenNum = orderInfo.getChildrenNum();
			Integer totalNum = adultNum + childrenNum;
			Integer mostNum = item.getMostNum() == null ? 0 : item.getMostNum();
			Integer leastNum = item.getLeastNum() == null ? 0 : item.getLeastNum();
			if (totalNum > mostNum && mostNum > 0) {
				result.setResultCode("1002");
				result.setResultMessage("产品最多只能" + mostNum + "人");
				return result;
			}
			if (totalNum < leastNum) {
				result.setResultCode("1002");
				result.setResultMessage("产品最少需要" + leastNum + "人");
				return result;
			}
			// 1，判断库存
			final Sku sku = skuDao.selectByPrimaryKey(orderInfo.getSkuId());
			if (sku == null) {
				result.setResultCode("1002");
				result.setResultMessage("产品属性不存在");
				return result;
			}
			Integer adultLeastBuy = sku.getAdultLeastBuy() == null ? 0 : sku.getAdultLeastBuy();
			Integer childrenLeastBuy = sku.getChildrenLeastBuy() == null ? 0 : sku.getChildrenLeastBuy();
			if (adultLeastBuy > adultNum) {
				result.setResultCode("1002");
				result.setResultMessage("成人数量小于最少预定数量");
				return result;
			}
			if (childrenLeastBuy > childrenNum) {
				result.setResultCode("1002");
				result.setResultMessage("儿童数量小于最少预定数量");
				return result;
			}
			if (new Date().after(sku.getSkuStartDate())) {
				result.setResultCode("1002");
				result.setResultMessage("行程已经开始");
				return result;
			}

			Date orderTime = new Date();
			//int cancleDate = Integer.valueOf(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.cancle_order_time));
			//Calendar calendar = Calendar.getInstance();
			//calendar.setTime(orderTime);
			//calendar.add(Calendar.DATE, cancleDate);
			// 此处出售的库存
			/*
			 * final Integer stock
			 * =orderInfo.getAdultNum()+orderInfo.getChildrenNum();
			 * if(sku.getStockFlag() ==1){//有库存 if(sku.getStock() <= 0 ||
			 * sku.getStock() <stock){ result.setResultCode("1002");
			 * result.setResultMessage("产品库存不足"); return result; }
			 * orderInfo.setOrderStatus(Constants.OrderStatus.DFK); }else{
			 * orderInfo.setOrderStatus(Constants.OrderStatus.DQR); }
			 */
			// 去掉库存，不管有没有设置库存，都可以支付
			orderInfo.setOrderStatus(Constants.OrderStatus.DFK);
			// 组装数据
			final OperaLog operaLog = new OperaLog();
			operaLog.setOperaBeforeTxt("准备创建订单");
			operaLog.setOperaResultTx("订单创建成功");
			operaLog.setOperaUserId(orderInfo.getUserId());
			operaLog.setOperaUserName(userInfo.getUserName());
			operaLog.setPoeraAfterTxt("订单创建完成");
			operaLog.setOperaType(Constants.OperaLogType.ORDER);

			BigDecimal adultPrice = sku.getSkuAdultPrice();
			BigDecimal childPrice = sku.getChildrenPrice();
			BigDecimal skuRoomsPrice = sku.getSkuRoomsPrice();
			orderInfo.setOriginName(sku.getOriginName());
			orderInfo.setItemImage(item.getItemImage());
			orderInfo.setItemName(item.getItemName());
			orderInfo.setOrderType(Constants.OrderType.TRAVEL);
			orderInfo.setOrderTime(orderTime);
			orderInfo.setCreated(sku.getEndTime());// 过期时间
			orderInfo.setVenderUserId(item.getVenderUserId());
			orderInfo.setSkuAdultPrice(adultPrice);
			orderInfo.setSkuChildrenPrice(childPrice);
			orderInfo.setReturnDate(sku.getSkuReturnDate());
			orderInfo.setStartDate(sku.getSkuStartDate());
			orderInfo.setSkuRoomsPrice(skuRoomsPrice);
			// 计算促销
			setPromPrice(sku, orderInfo);

			// BigDecimal money =orderInfo.getOrderMoney();
			// orderInfo.setOrderMoney(money);
			// orderInfo.setOughtPayMoney(money);
			// 设置优惠券
			// 计算优惠券
			final List<TbCoupon> couponList = new ArrayList<TbCoupon>();
			setCouponBigDeCimal(coupons, couponList, orderInfo);

			BigDecimal oughtPayMoney = orderInfo.getOughtPayMoney();// 结算金额
			BigDecimal balanceTotal = BigDecimal.valueOf(0);
			BigDecimal balancePayMoney = BigDecimal.valueOf(0);// 使用的余额

			// 添加锁定表数据，进行异步添加余额
			final UserBalanceLock lock = new UserBalanceLock();
			final UserBalance balanceLock = new UserBalance();
			if (useBalance.equals(1)) {
				// 查询用户余额
				UserBalance balance = userBalanceDao.selectByUserId(orderInfo.getUserId());
				if (balance != null) {
					balanceTotal = balance.getBalanceSum().subtract(balance.getLockedAmount()).subtract(balance.getOverdueAmount())
							.subtract(balance.getUsedAmount());
					// 判断余额是否够。如果余额大于订单金额，则可用余额等于订单金额，否则，可用余额等于全部余额
					BigDecimal balanceEnd = balanceTotal.subtract(oughtPayMoney);
					if (balanceEnd.compareTo(BigDecimal.valueOf(0)) >= 0) {
						balancePayMoney = oughtPayMoney;
						oughtPayMoney = BigDecimal.valueOf(0);
					} else {
						oughtPayMoney = balanceEnd.negate();
						balancePayMoney = balanceTotal;
					}
					// 如果没有，则说明是第一次获得余额，在定时任务时进行新增用户余额
					lock.setBalanceId(balance.getBalanceId());
					balanceLock.setBalanceId(balance.getBalanceId());
				}
			}

			orderInfo.setOughtPayMoney(oughtPayMoney);
			orderInfo.setBalanceMoney(balancePayMoney);
			if (oughtPayMoney.compareTo(new BigDecimal(0)) <= 0) {
				orderInfo.setOrderStatus(Constants.OrderStatus.YZF);
				orderInfo.setPayTime(new Date());
				orderInfo.setPayMoney(new BigDecimal(0));
				operaLog.setOperaResultTx("订单使用余额支付成功");
				operaLog.setPoeraAfterTxt("订单创建并支付完成");
			}
			lock.setUserId(orderInfo.getUserId());
			lock.setBalanceStatus(Constants.BalanceLockStatus.LOCK_DONE);
			lock.setWorkStatus(Constants.BalanceWorkStatus.UNTREATED);
			lock.setBalanceAmount(balancePayMoney.negate());// 消费余额
			lock.setGrantType(Constants.BalanceGrantType.xdsy);

			balanceLock.setModified(new Date());
			balanceLock.setLockedAmount(balancePayMoney);

			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 插入订单
					Integer orderId = orderInfoDao.insert(orderInfo);
					if (orderId == null || orderId == 0) {
						throw new RuntimeException("添加订单失败");
					}
					// 锁定余额
					if (lock.getBalanceId() != null) {
						lock.setRemark("下单(" + orderId + ")使用余额");
						lock.setOrderId(orderId);
						lock.setBusinessValue(String.valueOf(orderId));// 获得积分的业务主键（订单号，注册用户，操作用户）
						userBalanceLockDao.insert(lock);// 插入用户锁定积分
						// 同时锁定用户积分
						userBalanceDao.updateByBalance(balanceLock);
					}

					Integer status = 0;// 能否支付 0可以支付 1不可以支付.默认0
					// String message="产品库存告急，请您等运营人员确认后进行下单";//不能支付错误描述
					// 下单不扣减库存
					/*
					 * if(sku.getStockFlag() ==1){//有库存 Sku record=new Sku();
					 * record.setSkuId(orderInfo.getSkuId());
					 * record.setStock(-stock); record.setModified(new Date());
					 * int num= skuDao.updateByPrimaryKey(record); if( num ==
					 * 0){ EcUtils.setExceptionResult(result); throw new
					 * RuntimeException("扣减库存失败"); } // status =1; //
					 * message=""; }
					 */
					// 插入发票
					if (!Constants.OrderInvoiceStatus.NOTNEED.equals(invoice.getInvoiceStatus())) {
						invoice.setInvoiceType(1);// 普通
						invoice.setOrderId(orderId);
						orderInvoiceDao.insert(invoice);
					}
					// 添加订单使用优惠券绑定信息
					if (couponList != null && !couponList.isEmpty()) {
						for (TbCoupon tbCoupon : couponList) {
							tbCoupon.setCouponState(Constants.CouponStatus.USED);
							tbCoupon.setOrderId(orderId);
							tbCouponDao.modify(tbCoupon);
						}
					}
					// 订单旅客
					for (OrderPassenger passenger : passengers) {
						passenger.setOrderId(orderId);
					}
					orderPassengerDao.insertBatch(passengers);
					// 日志
					operaLog.setBusinessId(orderId);
					operaLogDao.insert(operaLog);

					map.put("status", status);
					map.put("message", "");
					map.put("orderId", orderId);
					// 订单待付款 发邮件
					String subject = "生成订单";
					String itemName = item.getItemName();
					String phone = userInfo.getMobile();
					String message = "用户（" + phone + "）提交订单（" + orderId + "）成功，状态为"
							+ ComFunction.getSystemConstantValue(Constants.SYSTEM_ORDER_TYPE, orderInfo.getOrderStatus()) + "，产品编号"
							+ orderInfo.getItemId() + "，产品名称（" + itemName + "）；订单应付金额" + orderInfo.getOughtPayMoney();
					OperationsStaffQuery query = new OperationsStaffQuery();
					query.setYn(Constants.YES);
					List<OperationsStaff> list = operationsStaffDao.selectByCondition(query);
					if (list.size() > 0) {
						for (OperationsStaff os : list) {
							String receiver = os.getMobile();
							ComFunction.sendMail(receiver, subject, message);
						}
					}
					if (!Constants.OrderStatus.DFK.equals(orderInfo.getOrderStatus())) {
						result.setResultMessage("done");
					}
					result.setSuccess(true);
					result.setResultCode("200");
					result.setResult(map);
				}
			});
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	/**
	 * 计算促销金额
	 * 
	 * @param sku
	 * @param orderInfo
	 */
	private void setPromPrice(Sku sku, OrderInfo orderInfo) {
		// 计算订单总金额
		BigDecimal adultPrice = sku.getSkuAdultPrice();// 成人价
		BigDecimal childPrice = sku.getChildrenPrice();// 儿童价
		BigDecimal skuRoomsPrice = sku.getSkuRoomsPrice();// 单房差
		Integer adultNum = orderInfo.getAdultNum();// 成人数
		Integer childrenNum = orderInfo.getChildrenNum();// 儿童数
		Integer roomsNum = orderInfo.getSkuRoomsNum();
		// 成人价总价
		BigDecimal adultTotal = adultPrice.multiply(new BigDecimal(adultNum));
		// 儿童总价
		BigDecimal childrenTotal = childPrice.multiply(new BigDecimal(childrenNum));
		// 成人价*数量+儿童价*数量+单房差人数*单房差价格
		BigDecimal totalMoney = (adultTotal).add(childrenTotal).add(skuRoomsPrice.multiply(new BigDecimal(roomsNum)));
		// 计算订单总金额，订单总金额为原价，不带促销
		orderInfo.setOrderMoney(totalMoney);

		// 计算促销金额
		PromotionSkuQuery promotionSkuQuery = new PromotionSkuQuery();
		promotionSkuQuery.setItemId(sku.getItemId());
		promotionSkuQuery.setSkuId(sku.getSkuId());
		promotionSkuQuery.setYn(1);
		List<PromotionSku> promoList = promotionSkuDao.selectByCondition(promotionSkuQuery);

		BigDecimal discountMoney = new BigDecimal(0);// 促销金额
		String promotionStr = "";// 促销信息

		if (promoList == null || promoList.isEmpty()) {
		} else {
			for (PromotionSku promotionSku : promoList) {
				PromotionInfo promotionInfo = promotionInfoDao.selectByPrimaryKey(promotionSku.getPromotionId());
				Date now = new Date();
				// 判断是否有促销活动
				if (promotionInfo != null && promotionInfo.getPromotionStatus() != null && promotionInfo.getStartTime() != null
						&& promotionInfo.getEndTime() != null && promotionInfo.getYn() == 1 && promotionInfo.getPromotionStatus() == 1
						&& now.after(promotionInfo.getStartTime()) && now.before(promotionInfo.getEndTime())) {

					Integer promotionType = promotionInfo.getPromotionType();// 促销类型(1-满人数直降;2-满人数折扣;3-满人数免人)
					Integer numMax = promotionInfo.getPurchaseNumberMax();// 最大人数
					Integer promotionObj = promotionInfo.getPromotionObj();// 促销对象(0-全部;1-成人;2-儿童;)
					BigDecimal promotionValue = promotionInfo.getPromotionValue();// 促销基础值
					BigDecimal discountMoneyCurrent = new BigDecimal(0);
					BigDecimal discountValue = new BigDecimal(0);// 折扣率
					if (promotionType == 2) {
						discountValue = (new BigDecimal(10).subtract(promotionValue)).multiply(new BigDecimal(0.1));
					}

					if (promotionObj == 0) { // 全部
						if ((adultNum + childrenNum) >= numMax) {
							if (promotionType == 1) {// 满减
								discountMoneyCurrent = promotionValue;
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销直降：" + discountMoney);
									promotionStr = promotionInfo.getPromotionRule();
								}
							} else if (promotionType == 2) {// 满折
								discountMoneyCurrent = totalMoney.multiply(discountValue).setScale(0, BigDecimal.ROUND_HALF_UP);
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销打：" + discountMoney + "折");
									promotionStr = promotionInfo.getPromotionRule();
								}
							} else if (promotionType == 3) {// 满免
								discountMoneyCurrent = adultPrice.multiply(promotionValue);
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销：满" + numMax + "人免" + promotionValue + "人");
									promotionStr = promotionInfo.getPromotionRule();
								}
							}
						}
					} else if (promotionObj == 1) {// 成人
						if (adultNum >= numMax) {
							if (promotionType == 1) {// 满减
								discountMoneyCurrent = promotionValue;
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销直降：" + discountMoney);
									promotionStr = promotionInfo.getPromotionRule();
								}
							} else if (promotionType == 2) {// 满折
								discountMoneyCurrent = adultTotal.multiply(discountValue).setScale(0, BigDecimal.ROUND_HALF_UP);
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销打：" + discountMoney + "折");
									promotionStr = promotionInfo.getPromotionRule();
								}
							} else if (promotionType == 3) {// 满免
								discountMoneyCurrent = adultPrice.multiply(promotionValue);
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销：成人满" + numMax + "人免" + promotionValue + "人");
									promotionStr = promotionInfo.getPromotionRule();
								}
							}
						}
					} else if (promotionObj == 2) {// 儿童
						if (childrenNum >= numMax) {
							if (promotionType == 1) {// 满减
								discountMoneyCurrent = promotionValue;
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销直降：" + discountMoney);
									promotionStr = promotionInfo.getPromotionRule();
								}
							} else if (promotionType == 2) {// 满折
								discountMoneyCurrent = childrenTotal.multiply(discountValue).setScale(0, BigDecimal.ROUND_HALF_UP);
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销打：" + discountMoney + "折");
									promotionStr = promotionInfo.getPromotionRule();
								}
							} else if (promotionType == 3) {// 满免
								discountMoneyCurrent = childPrice.multiply(promotionValue);
								if (discountMoneyCurrent.compareTo(discountMoney) > 0) {
									discountMoney = discountMoneyCurrent;
									orderInfo.setOrderRemark("产品促销：儿童满" + numMax + "人免" + promotionValue + "人");
									promotionStr = promotionInfo.getPromotionRule();
								}
							}
						}
					}
				}
			}
		}

		// 如果带促销，计算带促销订单价格-- 如果多个，已最后设置的为准
		// 计算订单总优惠金额
		orderInfo.setDiscountMoney(discountMoney);
		if (StringUtils.isNotBlank(promotionStr)) {
			orderInfo.setPromotionStr(promotionStr.substring(0, promotionStr.length() - 1));
		}
		// 订单应付金额
		BigDecimal oughtPayMoney = totalMoney.subtract(discountMoney);
		orderInfo.setOughtPayMoney(oughtPayMoney);
	}

	@Override
	public OrderInfo selectByOrderId(Integer orderId) {
		return orderInfoDao.selectByPrimaryKey(orderId);
	}

	@Override
	public void unionPay(Map<String, Object> map) {
		final Result result = new Result();
		// 1，修改订单表。日志表 ，订单支付信息表，订单结果表
		try {
			Integer orderId = null;
			String MerOrderNo = (String) map.get("MerOrderNo");
			orderId = Integer.parseInt(MerOrderNo);
			// String order_payment[] =
			// orderPaymentId.split(Constants.ORDER_PAYMENT_SPIT);
			// if(order_payment!=null && order_payment.length ==2){
			// orderId = Integer.parseInt(order_payment[0]);
			// paymentId = Integer.parseInt( order_payment[1]);//支付表主键
			// }
			OrderPayment orderPaymentr = new OrderPayment();
			orderPaymentr.setOrderId(orderId);
			OrderPayment payment = orderPaymentDao.selectOrderPamentByOne(orderPaymentr);
			if (payment == null) {
				log.error("==支付成功，payment不存在===orderId=" + orderId);
				return;
			}
			Integer paymentId = payment.getPaymentId();

			if (orderId == null) {
				log.error("==支付成功，订单编号不存在===orderId=" + orderId);
				return;
			}
			final OrderInfo orderInfo = orderInfoDao.selectByPrimaryKey(orderId);
			if (orderInfo == null) {
				log.error("==支付成功，订单不存在===orderId=" + orderId);
				return;
			}

			if (!(Constants.OrderStatus.DFK.equals(orderInfo.getOrderStatus()))) {
				log.error("==订单状态不能支付===orderId=" + orderId + "==OrderStatus=" + orderInfo.getOrderStatus());
				return;
			}
			BigDecimal payMoney = (new BigDecimal((String) map.get("OrderAmt"))).divide(new BigDecimal(100));
			final UserInfo userInfo = userInfoDao.selectByPrimaryKey(orderInfo.getUserId());
			if (userInfo == null) {
				return;
			}
			final OrderInfo order = new OrderInfo();
			order.setOrderId(orderId);
			order.setModified(new Date());
			order.setItemId(orderInfo.getItemId());
			order.setPayType(Constants.PayType.UNION_PAY);
			BigDecimal oughtPayMoney = orderInfo.getOughtPayMoney();
			String CompleteDate = (String) map.get("CompleteDate");// 交易完成日期
			String CompleteTime = (String) map.get("CompleteTime");// 交易完成日期
			Date payTime = null;
			try {
				payTime = DateUtil.parse(CompleteDate + CompleteTime, "yyyyMMddHHmmss");
			} catch (Exception e) {
				payTime = new Date();
			}
			if (oughtPayMoney.compareTo(payMoney) > 0) {// 如果 订单应该支付的金额
														// 大于实际支付的金额，则说明只支付了部分
				order.setPayTime(payTime);
				// 应付金额
				order.setPayMoney(payMoney);
				order.setOughtPayMoney(oughtPayMoney.subtract(payMoney));
				order.setOrderStatus(Constants.OrderStatus.DFK); // 修改订单状态为待付款
			} else {
				order.setPayTime(payTime);
				order.setPayMoney(payMoney);
				order.setOughtPayMoney(new BigDecimal(0));
				order.setOrderStatus(Constants.OrderStatus.YZF); // 修改订单状态为yi付款
			}
			// 如果没有设置库存，需要运营手工确认库存
			Sku sku = skuDao.selectByPrimaryKey(orderInfo.getSkuId());
			if (sku != null) {
				if (sku.getStockFlag() == 0 && Constants.OrderType.TRAVEL.equals(orderInfo.getOrderType())) {
					// 如果有库存，判断库存
					order.setOrderStatus(Constants.OrderStatus.DQR);
				}
			}
			String TranDate = (String) map.get("TranDate");// 交易日期
			// String TranTime = (String)map.get("TranTime");//交易日期
			String TranType = (String) map.get("TranType");// 交易类型
			// 组装数据
			final OrderPayment orderPayment = new OrderPayment();
			orderPayment.setPaymentId(paymentId);
			orderPayment.setOpenid(TranDate);// 存交易日期，退款时需要
			orderPayment.setOrderPaymentNo(MerOrderNo);
			orderPayment.setThirdPaymentNo((String) map.get("AcqSeqId"));// ChinaPay收单系统交易受理流水号
			orderPayment.setPayType(Constants.PayType.UNION_PAY);
			orderPayment.setFromWhere(Constants.FromWhere.PC);
			orderPayment.setModify(new Date());
			// 组装支付信息
			final PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setPaymentId(paymentId);
			paymentInfo.setOrderId(orderId);
			paymentInfo.setPayType(Constants.PayType.UNION_PAY);
			// paymentInfo.setOrderPayType(1);//（1-定金OR全款支付 2-尾款支付）
			// paymentInfo.setPaymentInfoType(2);//(1、支付信息 2、支付成功确认信息)
			paymentInfo.setPaymentNumber((String) map.get("AcqSeqId"));// ChinaPay收单系统交易受理流水号
			paymentInfo.setPaymentMoney(payMoney);
			paymentInfo.setBusiPartner(TranType);
			paymentInfo.setOrderTime(CompleteDate + CompleteTime);
			paymentInfo.setOpenid(TranDate);
			paymentInfo.setModified(new Date());

			final OperaLog operaLog = new OperaLog();
			operaLog.setBusinessId(orderId);
			operaLog.setOperaBeforeTxt("准备支付订单");
			operaLog.setOperaResultTx("订单支付成功");
			operaLog.setOperaUserId(orderInfo.getUserId());
			operaLog.setOperaUserName(userInfo.getUserName());
			operaLog.setPoeraAfterTxt("订单支付完成");
			operaLog.setOperaType(Constants.OperaLogType.ORDER);
			final Sms sms = new Sms();
			sms.setContent(orderInfo.getOrderId() + "");
			sms.setType(Constants.SmsType.fkcg);//
			sms.setMobile(userInfo.getMobile());
			sms.setStatus(0);
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					orderInfoDao.updateByPrimaryKey(order);
					orderPaymentDao.updateByPrimaryKey(orderPayment);
					try {
						OperationsStaffQuery query = new OperationsStaffQuery();
						query.setYn(Constants.YES);
						List<OperationsStaff> list = operationsStaffDao.selectByCondition(query);
						if (list.size() > 0) {
							// 订单支付成功 发邮件
							String subject = "订单支付成功";
							Item item = itemDao.selectByPrimaryKey(orderInfo.getItemId());
							String itemName = item.getItemName();
							String phone = userInfo.getMobile();
							String message = "用户（" + phone + "）支付（" + order.getOrderId() + "）订单成功，产品编号" + order.getItemId() + "，产品名称（" + itemName
									+ ")；订单实付金额" + order.getPayMoney();
							for (OperationsStaff os : list) {
								String receiver = os.getMobile();
								ComFunction.sendMail(receiver, subject, message);
							}
						}
						paymentInfoDao.insert(paymentInfo);
						// 日志
						operaLogDao.insert(operaLog);
						smsDao.insert(sms);
					} catch (Exception e) {
						log.error("订单支付信息插入失败orderInfo=" + order.getOrderId(), e);
					}

				}
			});
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
	}

	@Override
	public OrderInfo selectByOrderInfo(OrderInfo orderInfo) {
		return orderInfoDao.selectByOrderInfo(orderInfo);
	}

	@Override
	public Result getOffLineData(Integer orderType) {
		Result result = new Result();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<SystemConstantValue> types = ComFunction.getSystemConstantType(Constants.SYSTEM_COMPANY_TYPE);
			for (SystemConstantValue type : types) {
				Integer value = type.getValue();
				String name = type.getName();
				if (Constants.SystemCompanyStatus.accountNumber.equals(value)) {
					map.put("accountNumber", name);
				} else if (Constants.SystemCompanyStatus.company.equals(value)) {
					map.put("company", name);
				} else if (Constants.SystemCompanyStatus.bank.equals(value)) {
					map.put("bank", name);
				} else if (Constants.SystemCompanyStatus.contactNumber.equals(value)) {
					map.put("contactNumber", name);
				}
			}
			String desc = "";
			if (Constants.OrderType.TRAVEL.equals(orderType)) {
				String time = ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.cancle_order_time);
				desc = "确认后，请在" + time + "天内付清款项，超过" + time + "天系统会取消订单";
			} else if (Constants.OrderType.CONSULT.equals(orderType)) {
				String time = ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.cancle_special_order_time);
				desc = "确认后，请在" + time + "天内付清款项，超过" + time + "天系统会取消订单";
			}
			map.put("desc", desc);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	/**
	 * 订单完成出行
	 */
	@Override
	public Result doneOrderByOrderId(final OrderInfo info) {
		Result result = new Result();
		try {
			Integer orderId = info.getOrderId();
			OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
			orderInfoQuery.setOrderId(orderId);
			orderInfoQuery.setUserId(info.getUserId());
			List<OrderInfo> infos = orderInfoDao.selectByCondition(orderInfoQuery);
			if (infos == null || infos.size() == 0) {
				result.setResultCode("1002");
				result.setResultMessage("订单不存在");
				return result;
			}
			OrderInfo order = infos.get(0);
			if (!Constants.OrderStatus.DCX.equals(order.getOrderStatus())) {
				result.setResultCode("1002");
				result.setResultMessage("此订单状态下不能完成出行");
				return result;
			}
			UserInfo userInfo = userInfoDao.selectByPrimaryKey(info.getUserId());
			if (userInfo == null) {
				result.setResultCode("1002");
				result.setResultMessage("用户信息错误");
				return result;
			}
			info.setModified(new Date());
			info.setOrderRemark("客户完成出行订单操作");
			final OperaLog operaLog = new OperaLog();
			operaLog.setBusinessId(orderId);
			operaLog.setOperaBeforeTxt("客户完成出行订单操作开始");
			operaLog.setOperaResultTx("客户完成出行订单操作成功");
			operaLog.setOperaUserId(info.getUserId());
			operaLog.setOperaUserName(userInfo.getUserName());
			operaLog.setPoeraAfterTxt("客户完成出行订单操作完成");
			operaLog.setOperaType(Constants.OperaLogType.ORDER);

			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					int num = orderInfoDao.updateByPrimaryKey(info);
					if (num == 0) {
						throw new RuntimeException("订单完成出行操作修改失败");
					}
					try {
						// 日志
						operaLogDao.insert(operaLog);
					} catch (Exception e) {
						log.error("订单插入日志失败orderInfo=" + info.getOrderId(), e);
					}

				}
			});

			result.setResult(true);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result cancelOrder(final Integer orderId, final Integer userId) {
		Result result = new Result();
		try {
			OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
			orderInfoQuery.setOrderId(orderId);
			orderInfoQuery.setUserId(userId);
			List<OrderInfo> infos = orderInfoDao.selectByCondition(orderInfoQuery);
			if (infos == null || infos.size() == 0) {
				result.setResultCode("1002");
				result.setResultMessage("订单不存在");
				return result;
			}
			final OrderInfo order = infos.get(0);
			if (Constants.OrderStatus.DQR.equals(order.getOrderStatus()) || Constants.OrderStatus.DCX.equals(order.getOrderStatus())
					|| Constants.OrderStatus.YZF.equals(order.getOrderStatus()) || Constants.OrderStatus.DFK.equals(order.getOrderStatus())) {
				order.setOrderStatus(Constants.OrderStatus.QXSQ);
				order.setLockStatus(order.getOrderStatus());
			} else {
				result.setResultCode("1002");
				result.setResultMessage("此订单状态下不能取消");
				return result;
			}
			final UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);
			if (userInfo == null) {
				result.setResultCode("1002");
				result.setResultMessage("用户信息错误");
				return result;
			}
			order.setModified(new Date());
			order.setOrderRemark("客户取消订单操作");
			final OperaLog operaLog = new OperaLog();
			operaLog.setBusinessId(orderId);
			operaLog.setOperaBeforeTxt("客户取消订单操作开始");
			operaLog.setOperaResultTx("客户取消订单操作成功");
			operaLog.setOperaUserId(userId);
			operaLog.setOperaUserName(userInfo.getUserName());
			operaLog.setPoeraAfterTxt("客户取消订单操作完成");
			operaLog.setOperaType(Constants.OperaLogType.ORDER);

			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					int num = orderInfoDao.updateByPrimaryKey(order);
					if (num == 0) {
						throw new RuntimeException("客户取消订单操作修改失败");
					} else {
						// 取消申请 发邮件
						String subject = "取消申请的订单";
						Item item = itemDao.selectByPrimaryKey(order.getItemId());
						String phone = userInfo.getMobile();
						String itemName = item.getItemName();
						String message = "用户(" + phone + ")提交（" + orderId + "）订单，状态为取消申请，产品编号" + order.getItemId() + "，商品名称（" + itemName + ")；订单应付金额"
								+ order.getOughtPayMoney();
						OperationsStaffQuery query = new OperationsStaffQuery();
						query.setYn(Constants.YES);
						List<OperationsStaff> list = operationsStaffDao.selectByCondition(query);
						if (list.size() > 0) {
							for (OperationsStaff os : list) {
								String receiver = os.getMobile();
								ComFunction.sendMail(receiver, subject, message);
							}
						}
					}
					try {
						// 日志
						operaLogDao.insert(operaLog);
					} catch (Exception e) {
						log.error("订单插入日志失败orderInfo=" + order.getOrderId(), e);
					}

				}
			});

			result.setResult(true);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result calculateOrderMoney(OrderInfo orderInfo, Integer useBalance, String coupons) {
		Result result = new Result();
		try {
			UserInfo userInfo = userInfoDao.selectByPrimaryKey(orderInfo.getUserId());
			if (userInfo == null) {
				result.setResultCode("1002");
				result.setResultMessage("用户信息错误");
				return result;
			}
			Integer itemId = orderInfo.getItemId();
			Item item = itemDao.selectByPrimaryKey(itemId);
			if (item == null) {
				result.setResultCode("1002");
				result.setResultMessage("商品信息不存在");
				return result;
			}
			Sku sku = skuDao.selectByPrimaryKey(orderInfo.getSkuId());
			if (sku == null) {
				result.setResultCode("1002");
				result.setResultMessage("产品属性不存在");
				return result;
			}
			// 计算促销
			setPromPrice(sku, orderInfo);
			BigDecimal orderMoney = orderInfo.getOrderMoney();
			BigDecimal discountMoney = orderInfo.getDiscountMoney();// 促销优惠金额

			BigDecimal balanceTotal = BigDecimal.valueOf(0);
			int status = 2;
			// 设置优惠券
			List<TbCoupon> couponList = new ArrayList<TbCoupon>();
			setCouponBigDeCimal(coupons, couponList, orderInfo);
			BigDecimal oughtPayMoney = orderInfo.getOughtPayMoney();
			BigDecimal balanceMoney = oughtPayMoney;// 结算金额

			// 查询用户余额
			UserBalance balance = userBalanceDao.selectByUserId(orderInfo.getUserId());
			if (balance != null) {
				balanceTotal = balance.getBalanceSum().subtract(balance.getLockedAmount()).subtract(balance.getOverdueAmount())
						.subtract(balance.getUsedAmount());
				status = balance.getBalanceStatus();
				// 判断余额是否够。如果余额大于订单金额，则可用余额等于订单金额，否则，可用余额等于全部余额
				BigDecimal balanceEnd = balanceTotal.subtract(oughtPayMoney);
				if (balanceEnd.compareTo(BigDecimal.valueOf(0)) >= 0) {
					balanceTotal = oughtPayMoney;
					// 使用余额
					if (useBalance.equals(1)) {
						balanceMoney = BigDecimal.valueOf(0);
					}
				} else {
					// balanceTotal =balanceTotal;
					// 使用余额
					if (useBalance.equals(1)) {
						balanceMoney = balanceEnd.negate();
					}
				}
			}

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("orderMoney", orderMoney);
			map.put("balanceMoney", balanceMoney);
			map.put("balanceTotal", balanceTotal);
			map.put("balanceStatus", status);
			map.put("discountMoney", discountMoney);
			map.put("promotionStr", orderInfo.getPromotionStr());
			map.put("discountCouponMoney", orderInfo.getDiscountCouponMoney());
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	// 计算订单使用优惠券的金额
	private void setCouponBigDeCimal(String coupons, List<TbCoupon> couponList, OrderInfo orderInfo) {
		// 使用优惠券
		log.info("coupons==" + coupons);
		BigDecimal discountCouponMoney = new BigDecimal(0.0);
		if (StringUtils.isNotEmpty(coupons) && coupons.length() > 0) {
			String[] couponIds = coupons.split(",");
			for (String couponId : couponIds) {
				log.info("couponId==" + couponId);
				TbCoupon tbCoupon = tbCouponDao.selectByTbCouponId(Integer.parseInt(couponId));
				log.info("tbCoupon==" + tbCoupon.getCouponState());
				if (tbCoupon != null && orderInfo.getUserId().equals(tbCoupon.getUserId())
						&& Constants.CouponStatus.UNUSED.equals(tbCoupon.getCouponState())) {
					// 0 满减券 1直减券 2 免运费劵
					// 免运费劵||直减券处理
					BigDecimal oughtPayMoney = orderInfo.getOughtPayMoney();
					if (tbCoupon.getCouponType() == null)
						continue;
					if (tbCoupon.getCouponType().equals(Constants.CouponType.LAPSE)) {
						oughtPayMoney = oughtPayMoney.subtract(new BigDecimal(tbCoupon.getCouponAmount()));
						orderInfo.setOughtPayMoney(oughtPayMoney);
						discountCouponMoney = discountCouponMoney.add(new BigDecimal(tbCoupon.getCouponAmount()));
						couponList.add(tbCoupon);
					}
					// 满减券处理
					else if (tbCoupon.getCouponType().equals(Constants.CouponType.FULL)) {
						if (oughtPayMoney != null && oughtPayMoney.compareTo(new BigDecimal(tbCoupon.getOrderQuota())) >= 0) {
							log.info("tbCoupon=oughtPayMoney=" + oughtPayMoney);
							oughtPayMoney = oughtPayMoney.subtract(new BigDecimal(tbCoupon.getCouponAmount()));
							orderInfo.setOughtPayMoney(oughtPayMoney);
							log.info("tbCoupon=oughtPayMoney2=" + oughtPayMoney);
							discountCouponMoney = discountCouponMoney.add(new BigDecimal(tbCoupon.getCouponAmount()));
							couponList.add(tbCoupon);
						}
					}
				}
			}
		}
		BigDecimal oughtPayMoney = orderInfo.getOughtPayMoney();
		if (oughtPayMoney.compareTo(BigDecimal.valueOf(0)) < 0) {
			orderInfo.setOughtPayMoney(BigDecimal.valueOf(0));
		}
		orderInfo.setDiscountCouponMoney(discountCouponMoney);
	}
	
	/**
	 * 支付宝支付
	 */
	@Override
	public void payAlipay(Map<String, String> map) {
		String orderId = map.get("orderId");
		log.info("payAlipay开始处理===" + orderId);
		String tradeNo = map.get("trade_no");
		BigDecimal price = new BigDecimal(map.get("total_fee"));
		String timeEnd = map.get("gmt_payment");
		String openid = map.get("buyer_id");//
		log.info("transaction_id===" + tradeNo);
		log.info("price===" + price);
		log.info("timeEnd===" + timeEnd);
		log.info("openid===" + openid);
		dealPayTing(map, Constants.PayType.ALIPAY);
	}

	/**
	 * 微信支付
	 */
	@Override
	public void payWX(Map<String, String> map) {
		String orderId = map.get("orderId");
		log.info("payWX开始处理===" + orderId);
		String tradeNo = map.get("transaction_id");
		BigDecimal price = div(Double.parseDouble(map.get("total_fee")), 100.00, 2);
		String timeEnd = map.get("time_end");
		String openid = map.get("openid");
		log.info("transaction_id===" + tradeNo);
		log.info("price===" + price);
		log.info("openid===" + openid);
		log.info("timeEnd===" + timeEnd);
		dealPayTing(map, Constants.PayType.WEI_XIN);
	}
	
	public static BigDecimal div(double d1, double d2, int len) {// 进行除法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 支付成功，事物处理
	 * 
	 * @param orderInfo
	 * @param payType
	 *            支付方式
	 */
	private void dealPayTing(Map<String, String> mapS, Integer payType) {
		// 1，修改订单表。日志表 ，订单支付信息表，订单结果表
		try {
			Integer orderId = Integer.valueOf(mapS.get("orderId"));
			final OrderInfo orderInfo = orderInfoDao.selectByPrimaryKey(orderId);
			if (orderInfo == null) {
				log.error("==支付成功，订单不存在===orderId=" + orderId);
				return;
			}
			if (Constants.OrderStatus.DFK != orderInfo.getOrderStatus()) {
				log.error("==支付成功，订单的状态已经修改");
				return;
			}
			Integer paymentId = Integer.valueOf(mapS.get("paymentId"));
			String OrderPaymentNo = "";// 支付编号
			if (paymentId == null) {
				OrderPayment orderPaymentr = new OrderPayment();
				orderPaymentr.setOrderId(orderId);
				OrderPayment payment = orderPaymentDao.selectOrderPamentByOne(orderPaymentr);
				if (payment == null) {
					log.error("==支付成功，payment不存在===orderId=" + orderId);
					return;
				}
				paymentId = payment.getPaymentId();
				OrderPaymentNo = orderId + "";// 银联 不支持 中间有-。所以银联是根据订单号找支付编号
			} else {
				OrderPaymentNo = orderId + "-" + paymentId;// 微信和支付宝
			}
			if (paymentId == null) {
				log.error("==支付成功，paymentId不存在===orderId=" + orderId + "==payType==" + payType);
				return;
			}

			BigDecimal payMoney = null;
			Date payTime = new Date();
			String ThirdPaymentNo = "";// 第三方支付编号
			String orderTime = "";// 订单时间
			String openid = "";// 支付人
			String TranType = "";// 交易类型
			if (Constants.PayType.ALIPAY.equals(payType)) {
				payMoney = new BigDecimal(mapS.get("total_fee"));
				ThirdPaymentNo = mapS.get("trade_no");
				orderTime = mapS.get("gmt_payment");
				openid = mapS.get("buyer_id");//
			} else if (Constants.PayType.UNION_PAY.equals(payType)) {
				payMoney = (new BigDecimal((String) mapS.get("OrderAmt"))).divide(new BigDecimal(100));
				String CompleteDate = (String) mapS.get("CompleteDate");// 交易完成日期
				String CompleteTime = (String) mapS.get("CompleteTime");// 交易完成日期
				try {
					payTime = DateUtil.parse(CompleteDate + CompleteTime, "yyyyMMddHHmmss");
				} catch (Exception e) {
					payTime = new Date();
				}
				ThirdPaymentNo = mapS.get("AcqSeqId");
				orderTime = CompleteDate + CompleteTime;
				openid = (String) mapS.get("TranDate");// 交易日期
				TranType = (String) mapS.get("TranType");// 交易类型
			} else if (Constants.PayType.WEI_XIN.equals(payType)) {
				payMoney = div(Double.parseDouble(mapS.get("total_fee")), 100.00, 2);
				ThirdPaymentNo = mapS.get("transaction_id");
				orderTime = mapS.get("time_end");
				openid = mapS.get("openid");
			} 
			/*
			 * if(!(Constants.OrderStatus.DFK.equals(orderInfo.getOrderStatus()))
			 * ){
			 * log.error("==订单状态不能支付===orderId="+orderId+"==OrderStatus="+orderInfo
			 * .getOrderStatus()); return ; }
			 */
			final UserInfo userInfo = userInfoDao.selectByPrimaryKey(orderInfo.getUserId());
			if (userInfo == null) {
				log.error("==订单支付===userInfo为空=" + userInfo + "=orderId=" + orderId);
				return;
			}
			final OrderInfo order = new OrderInfo();
			order.setOrderId(orderId);
			order.setModified(new Date());
			order.setPayType(payType);
			order.setItemId(orderInfo.getItemId());
			order.setOughtPayMoney(orderInfo.getOughtPayMoney());
			BigDecimal oughtPayMoney = orderInfo.getOughtPayMoney();

			if (oughtPayMoney.compareTo(payMoney) > 0) {// 如果 订单应该支付的金额
														// 大于实际支付的金额，则说明只支付了部分
				order.setPayTime(payTime);
				// 应付金额
				order.setPayMoney(payMoney);
				order.setOughtPayMoney(oughtPayMoney.subtract(payMoney));
				order.setOrderStatus(Constants.OrderStatus.DFK); // 修改订单状态为待付款
			} else {
				order.setPayTime(payTime);
				order.setPayMoney(payMoney);
				order.setOughtPayMoney(new BigDecimal(0));
				order.setOrderStatus(Constants.OrderStatus.YZF); // 修改订单状态为yi付款
			}
			// 如果没有设置库存，需要运营手工确认库存
			Sku sku = skuDao.selectByPrimaryKey(orderInfo.getSkuId());
			if (sku != null) {
				if (sku.getStockFlag() == 0 && Constants.OrderType.TRAVEL.equals(orderInfo.getOrderType())) {
					// 如果有库存，判断库存
					order.setOrderStatus(Constants.OrderStatus.DQR);
				}
			}

			// 组装数据
			final OrderPayment orderPayment = new OrderPayment();
			orderPayment.setPaymentId(paymentId);
			orderPayment.setOpenid(openid);// 存交易日期，退款时需要
			orderPayment.setOrderPaymentNo(OrderPaymentNo);
			orderPayment.setThirdPaymentNo(ThirdPaymentNo);// ChinaPay收单系统交易受理流水号
			//orderPayment.setPayType(payType);
			// orderPayment.setFromWhere(Constants.FromWhere.PC);
			orderPayment.setModify(new Date());
			// 组装支付信息
			final PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setPaymentId(paymentId);
			paymentInfo.setOrderId(orderId);
			paymentInfo.setPayType(payType);
			// paymentInfo.setOrderPayType(1);//（1-定金OR全款支付 2-尾款支付）
			// paymentInfo.setPaymentInfoType(2);//(1、支付信息 2、支付成功确认信息)
			paymentInfo.setPaymentNumber(ThirdPaymentNo);// ChinaPay收单系统交易受理流水号
			paymentInfo.setPaymentMoney(payMoney);
			paymentInfo.setBusiPartner(TranType);
			paymentInfo.setOrderTime(orderTime);
			paymentInfo.setOpenid(openid);
			paymentInfo.setModified(new Date());

			final OperaLog operaLog = new OperaLog();
			operaLog.setBusinessId(orderId);
			operaLog.setOperaBeforeTxt("准备支付订单");
			operaLog.setOperaResultTx("订单支付成功");
			operaLog.setOperaUserId(orderInfo.getUserId());
			operaLog.setOperaUserName(userInfo.getUserName());
			operaLog.setPoeraAfterTxt("订单支付完成");
			operaLog.setOperaType(Constants.OperaLogType.ORDER);
			final Sms sms = new Sms();
			sms.setContent(orderInfo.getOrderId() + "");
			sms.setType(Constants.SmsType.fkcg);//
			sms.setMobile(userInfo.getMobile());
			sms.setStatus(0);
			new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					orderInfoDao.updateByPrimaryKey(order);
					orderPaymentDao.updateByPrimaryKey(orderPayment);
					OperationsStaffQuery query = new OperationsStaffQuery();
					query.setYn(Constants.YES);
					List<OperationsStaff> list = operationsStaffDao.selectByCondition(query);
					if (list.size() > 0) {
						// 订单支付成功 发邮件
						String subject = "订单支付成功";
						Item item = itemDao.selectByPrimaryKey(orderInfo.getItemId());
						String itemName = item.getItemName();
						String phone = userInfo.getMobile();
						String message = "用户(" + phone + ")支付（" + order.getOrderId() + "）订单成功，产品编号" + order.getItemId() + "，商品名称（" + itemName
								+ ")；订单实付金额" + order.getPayMoney();
						for (OperationsStaff os : list) {
							String receiver = os.getMobile();
							ComFunction.sendMail(receiver, subject, message);
						}
					}
					// 日志
					operaLogDao.insert(operaLog);
					smsDao.insert(sms);
					try {
						paymentInfoDao.insert(paymentInfo);
					} catch (Exception e) {
						log.error("订单支付信息插入失败orderInfo=" + order.getOrderId(), e);
					}
				}
			});
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public Result submitCustomOrder(OrderInfo orderInfo) {
		Result result = new Result(); 
		try {
			orderInfo.setOrderType(Constants.OrderType.CONSULT);
			orderInfo.setOrderStatus(Constants.OrderStatus.DFK);
			orderInfo.setOrderTime(new Date());
			orderInfo.setYn(Constants.YES);
			orderInfoDao.insert(orderInfo);
			
			OperationsStaffQuery query = new OperationsStaffQuery();
			query.setYn(Constants.YES);
			List<OperationsStaff> list = operationsStaffDao.selectByCondition(query);
			if (list.size() > 0) {
				// 个性定制订单
				String subject = "创建个性定制订单";
				String phone = orderInfo.getContactMobile();
				String name = orderInfo.getContactName();
				String message = "用户：" + name +"(" + phone + ")创建订单，目的地："+ orderInfo.getOriginName() +"，备注："+ orderInfo.getPersonalizationRemark() +"，请及时处理";
				for (OperationsStaff os : list) {
					String receiver = os.getMobile();
					ComFunction.sendMail(receiver, subject, message);
				}
			}
		} catch (Exception e) {
			log.error("",e);
			EcUtils.setExceptionResult(result);
		} 
		EcUtils.setSuccessResult(result);
		return result;
	}

}
