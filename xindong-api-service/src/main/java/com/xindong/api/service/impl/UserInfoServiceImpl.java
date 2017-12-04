package com.xindong.api.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.MD5Util;
import com.xindong.api.common.utils.RegisterValidateRules;
import com.xindong.api.dao.ItemDAO;
import com.xindong.api.dao.ItemImageDAO;
import com.xindong.api.dao.OrderInfoDAO;
import com.xindong.api.dao.PassengerGroupDAO;
import com.xindong.api.dao.SmsDAO;
import com.xindong.api.dao.UserBalanceDao;
import com.xindong.api.dao.UserBalanceDetailDao;
import com.xindong.api.dao.UserCollectionDao;
import com.xindong.api.dao.UserInfoDAO;
import com.xindong.api.domain.Item;
import com.xindong.api.domain.ItemImage;
import com.xindong.api.domain.PassengerGroup;
import com.xindong.api.domain.Sms;
import com.xindong.api.domain.SystemConstantValue;
import com.xindong.api.domain.UserBalance;
import com.xindong.api.domain.UserBalanceDetail;
import com.xindong.api.domain.UserCollection;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.query.ItemQuery;
import com.xindong.api.domain.query.OrderInfoQuery;
import com.xindong.api.domain.query.PassengerGroupQuery;
import com.xindong.api.domain.query.UserBalanceDetailQuery;
import com.xindong.api.domain.query.UserCollectionQuery;
import com.xindong.api.domain.vo.UserCollectionVO;
import com.xindong.api.service.UserInfoService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;

@Service(value="userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	@Autowired
	private UserInfoDAO userInfoDao;
//	@Autowired
//	private BusinessUserExtDao businessUserExtDao;
	@Autowired
	private SmsDAO smsDao;
	@Autowired
	private UserBalanceDetailDao userBalanceDetailDao;
	@Autowired
	private UserBalanceDao userBalanceDao;
	@Autowired
	private UserCollectionDao userCollectionDao;
	@Autowired
	private ItemDAO itemDao;
	@Autowired
	private PassengerGroupDAO passengerGroupDao;
	@Autowired
	private ItemImageDAO itemImageDao;
	@Autowired
	private OrderInfoDAO orderInfoDao;
	@Override
	public Result updatePwd(Integer userId, String oldPwd, String newPwd) {
		Result result = new Result();
		
		try{
			//根据userId获取用户信息
			UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);
			
			if(userInfo == null){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
			
			if(!userInfo.getPassword().equals(MD5Util.md5Hex(oldPwd))){
				result.setResultCode("4005");
				result.setResultMessage("旧密码不匹配");
				return result;
			}
			
			if(RegisterValidateRules.isInvalidPassword(newPwd)){
				result.setResultCode("1001");
				result.setResultMessage("新密码格式不正确");
				return result;
			}
			
			//修改登陆密码
			userInfo.setPassword(MD5Util.md5Hex(newPwd));
			userInfoDao.updateByPrimaryKey(userInfo);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getUserInfoByUserId(Integer userId) {
		Result result = new Result();
		
		try{
			//获取用户信息
			UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);
			if(userInfo == null){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
			//客服电话
			String servicePhone =ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.xd_service_phone);
			//订单数量
			OrderInfoQuery orderInfoQuery =new OrderInfoQuery();
			orderInfoQuery.setYn(1);
			orderInfoQuery.setUserId(userId);
			
			Integer orderTotal =orderInfoDao.countByCondition(orderInfoQuery);//总共订单数量
			
			orderInfoQuery.setOrderStatus(Constants.OrderStatus.DFK);
			Integer waitPayToatl =orderInfoDao.countByCondition(orderInfoQuery);//待付款
			
			orderInfoQuery.setOrderStatus(88);//查询待确认和已支付的订单
			Integer payToatl =orderInfoDao.countByCondition(orderInfoQuery);//已付款
			
			orderInfoQuery.setOrderStatus(Constants.OrderStatus.DCX);
			Integer travelToatl =orderInfoDao.countByCondition(orderInfoQuery);//待出行
			
			orderInfoQuery.setOrderStatus(Constants.OrderStatus.YWC);
			Integer doneTotal =orderInfoDao.countByCondition(orderInfoQuery);//已完成
			
			//已邀请的用户数量
			Integer invitationNum = userInfoDao.countByRegisterInvitationCode(userInfo.getInvitationCode());
			userInfo.setPassword(null);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userInfo", userInfo);
			map.put("orderTotal", orderTotal);
			map.put("waitPayToatl", waitPayToatl);
			map.put("payToatl", payToatl);
			map.put("travelToatl", travelToatl);
			map.put("doneTotal", doneTotal);
			map.put("servicePhone", servicePhone);
			map.put("invitationNum", invitationNum);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
			
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getPaymentBindbankcard(Integer userId) {
		Result result = new Result();
		/*
		try{
			//获取用户绑定银行卡信息
			PaymentBindbankcard paymentBindbankcard = new PaymentBindbankcard();
			paymentBindbankcard.setMer_cust_id(userId);
			List<PaymentBindbankcard> list = paymentBindbankcardDao.selectByCondition(paymentBindbankcard);
			if(list == null){
				result.setResultCode("9001");
				result.setResultMessage("未绑定银行卡");
				return result;
			}
			List<String> strList = new ArrayList<String>(); 
			for (PaymentBindbankcard pb : list) {
				String bankName = BankUtils.getBankName(pb.getBank());
				String str = bankName + " 银行卡号：**** **** **** "+pb.getBank_ac_last4();
				strList.add(str);
			}
			
			result.setResult(strList);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}*/
		return result;
	}

	@Override
	public Result updateUserHeadImg(Integer userId, String headUrl) {
		Result result = new Result();
		try{
			//获取用户信息
			UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);
			if(userInfo == null){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
			userInfo.setHeadUrl(headUrl);
			userInfoDao.updateByPrimaryKey(userInfo);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result completeUserInfo(UserInfo userInfo) {
		Result result = new Result();
		try{
			//获取用户信息
			UserInfo user = userInfoDao.selectByPrimaryKey(userInfo.getUserId());
			if(user == null){
				result.setResultCode("4001");
				result.setResultMessage("用户不存在");
				return result;
			}
			user.setBirthDate(userInfo.getBirthDate());
			user.setHeadUrl(userInfo.getHeadUrl());
			user.setIdNumber(userInfo.getIdNumber());
			user.setQrcode(userInfo.getQrcode());
			user.setQrcodeUrl(userInfo.getQrcodeUrl());
			user.setSex(userInfo.getSex());
			
			userInfoDao.updateByPrimaryKey(user);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result updateuploadUserHeadImgUserHeadImg(HttpServletRequest request) {
		Result result = new Result();
		try{
			List<String> list = imageUpload(request);
			result.setResult(list.get(0));
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	public List<String> imageUpload(HttpServletRequest request) {
		 String uploadFile = "/"+ ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.upload_img_url) +
				 "/img/";
		 String tempPath =uploadFile+ "tmp/";
		String path = null;
		String savefilePath=null;
		List<String> list = new ArrayList<String>();
		try {
			DiskFileUpload fu = new DiskFileUpload();   
	        // 设置最大文件尺寸，这里是4MB   
	        fu.setSizeMax(4194304);
	        // 设置缓冲区大小，这里是4kb   
	        fu.setSizeThreshold(4096);   
	        // 设置临时目录：   
	        fu.setRepositoryPath(tempPath);  
	  
	        // 得到所有的文件：   
	        List fileItems = fu.parseRequest(request);   
	        Iterator i = fileItems.iterator();
	        
	        while(i.hasNext()) {
				FileItem fi = (FileItem)i.next();
				if(!fi.isFormField()){
					String fileName50 = "";
					String fileName100 = "";
					String fileName200 = "";
					String fileName = fi.getName();
					fileName = fileName.toLowerCase();
//					String fileType=fileName.substring(fileName.lastIndexOf("."));
					int choice=97;
			        Random random=new Random();
			        char var = (char) (choice + random.nextInt(26)); 
			        String prd = "p"+ var +(int)(Math.random() * 1000000);
			        fileName = prd + ".png";
			       /* fileName50 = prd + "_50_50"+ fileType;
					fileName100 = prd + "_100_100"+ fileType;
					fileName200 = prd + "_200_200"+ fileType;*/
			        
			        Calendar cal = Calendar.getInstance();
					int year=cal.get(Calendar.YEAR);//得到年
					int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
					int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
					
					path = year +"/" + month+"/"+day+"/";
					savefilePath = uploadFile + path;

					File file = new File(savefilePath);
					if(!file.exists()){
						file.mkdirs();
					}
					
					File savedFile = new File(savefilePath, fileName);
					fi.write(savedFile);
					
					/*ImageUtils.resize(savefilePath + fileName, savefilePath + fileName50, 50, 50);
					ImageUtils.resize(savefilePath + fileName, savefilePath + fileName100, 100, 100);
					ImageUtils.resize(savefilePath + fileName, savefilePath + fileName200, 200, 200);*/
					
					list.add("http:/" + savefilePath + fileName);
				}
			}
	        return list;
		} catch (Exception e) {
			log.error("", e);
		}
		return list;
	}
	
	@Override
	public UserInfo selectByUserId(Integer userId) {
			//获取用户信息
		return  userInfoDao.selectByPrimaryKey(userId);
	}

	@Override
	public Result sendAccountData(String phone) {
		Result result = new Result();
		try{
			StringBuffer sb =new StringBuffer();
			sb.append(Constants.MESSAGE_SIGN);
			List<SystemConstantValue> types = ComFunction.getSystemConstantType(Constants.SYSTEM_COMPANY_TYPE);
			 for(SystemConstantValue type :types){
				   Integer value =type.getValue();
				   String name =type.getName();
				   if(Constants.SystemCompanyStatus.accountNumber.equals(value)){
					   sb.append(" 账户："+name);
				   }else if(Constants.SystemCompanyStatus.company.equals(value)){
					   sb.append("户名："+name);
				   }else if(Constants.SystemCompanyStatus.bank.equals(value)){
					   sb.append(" 开户行："+name);
				   }
			   }
			 Sms sms =new Sms();
			 sms.setStatus(0);
			 sms.setContent(sb.toString());
			 sms.setMobile(phone);
			 smsDao.insert(sms);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}

	@Override
	public Result getUserInfoBalancePage(UserBalanceDetailQuery detailQuery) {
		Result result = new Result();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			int total = userBalanceDetailDao.countByCondition(detailQuery);
			if(total == 0){
				result.setResult(new HashMap<String, Object>());
				EcUtils.setSuccessResult(result);
				return result;
			}
			List<UserBalanceDetail> list =new ArrayList<UserBalanceDetail>();
			int totalPage = total/detailQuery.getPageSize() + 1;
			if(detailQuery.getPageNo() > totalPage){
				detailQuery.setPageNo(totalPage);
			}
			list = userBalanceDetailDao.selectByConditionForPage(detailQuery);
           //该用户可用的积分总数
			UserBalance balance = userBalanceDao.selectByUserId(detailQuery.getUserId());
			BigDecimal balanceTotal = BigDecimal.valueOf(0);
			BigDecimal lockedAmount = BigDecimal.valueOf(0);
			int status =1;
			if(balance !=null){
				balanceTotal =balance.getBalanceSum().subtract(balance.getLockedAmount()).subtract
						(balance.getOverdueAmount()).subtract(balance.getUsedAmount());
				lockedAmount = balance.getLockedAmount();
				status =balance.getBalanceStatus();
			}
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", detailQuery.getPageNo());
			map.put("list", list);
			map.put("balanceTotal", balanceTotal);
			map.put("lockedAmount", lockedAmount);
			map.put("balanceStatus", status);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	@Override
	public Result getCollectionByUserId(UserCollectionQuery userCollectionQuery) {
		Result result = new Result();
		try{
			userCollectionQuery.setYn(Constants.YES);
			Map<String, Object> map = new HashMap<String, Object>();
			int total = userCollectionDao.countByCondition(userCollectionQuery);
			int totalPage =0;
			int pageNo =0;
			List<UserCollection> list =new ArrayList<UserCollection>();
			List<UserCollectionVO> vos =new ArrayList<UserCollectionVO>();
			if(total > 0){
				 totalPage = total/userCollectionQuery.getPageSize() + 1;
				if(userCollectionQuery.getPageNo() > totalPage){
					userCollectionQuery.setPageNo(totalPage);
					 pageNo =userCollectionQuery.getPageNo();
				}
				 list = userCollectionDao.selectByConditionForPage(userCollectionQuery);
				 UserCollectionVO vo=null;
				 for(UserCollection ul:list){
					 vo =new UserCollectionVO();
					 Integer itemId =ul.getItemId();
					 if(itemId ==null)continue;
					  Item item =itemDao.selectByPrimaryKey(itemId);
					  if(item ==null){
						 continue;
					  }
						vo.setItemId(itemId);
						vo.setItemName(item.getItemName());
						vo.setIntro(item.getItemIntro1());
						vo.setItemIntro1(item.getItemIntro1());
						vo.setItemIntro2(item.getItemIntro2());
						vo.setItemIntro3(item.getItemIntro3());
						vo.setCreated(ul.getCreated());
						ItemImage itemq =new ItemImage();
						itemq.setItemId(item.getItemId());
						itemq.setType(1);//h5图片
						List<ItemImage> itemImages = itemImageDao.selectByCondition(itemq);
						if(itemImages !=null && itemImages.size() >0){
							vo.setImgUrl(itemImages.get(0).getImageUrl());
						}
					    vos.add(vo);
				 }
			}
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", pageNo);
			map.put("list", vos);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("getCollectionByUserId出现异常", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result addCollectionByUserId(UserCollection userCollection) {
		Result result = new Result();
		try{
			
			ItemQuery itemQuery =new ItemQuery();
			itemQuery.setYn(Constants.YES);
			itemQuery.setItemId(userCollection.getItemId());
			itemQuery.setItemStatus(Constants.ItemStatus.ONSALE);
			List<Item> items =itemDao.selectByCondition(itemQuery );
			if(items==null || items.size() ==0){
				result.setSuccess(false);
				result.setResultCode("1005");
				result.setResultMessage("产品已经下架");
				return result;
			}
			
			UserCollectionQuery userCollectionQuery =new UserCollectionQuery();
			userCollectionQuery.setUserId(userCollection.getUserId());
			userCollectionQuery.setYn(Constants.YES);
			userCollectionQuery.setItemId(userCollection.getItemId());
			List<UserCollection>  usco = userCollectionDao.selectByConditionForPage(userCollectionQuery );
			//如果没有收藏，则去收藏
			if(usco==null || usco.size() ==0){
				userCollectionDao.insert(userCollection);
			}
			Item itemu = new Item();
			itemu.setItemId(userCollection.getItemId());
			itemu.setUsefulCount(1);
			itemu.setModified(new Date());
			itemDao.updateByPrimaryKey(itemu);
			
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("addCollectionByUserId出现异常", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result cancleCollectionByUserId(UserCollection userCollection) {
		Result result = new Result();
		try{
			userCollectionDao.deleteByPrimary(userCollection);
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("cancleCollectionByUserId出现异常", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result getCollectionAll(Integer userId) {
		Result result = new Result();
		try{
			List<Integer> list =new ArrayList<Integer>();
			if(userId !=null){
				UserCollectionQuery  userCollectionQuery =new UserCollectionQuery();
				userCollectionQuery.setType(1);//商品
				userCollectionQuery.setUserId(userId);
				List<UserCollection> lists = userCollectionDao.selectByCondition(userCollectionQuery);
				for(UserCollection u:lists){
					list.add(u.getItemId());
				}
			}
			result.setResult(list);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("cancleCollectionByUserId出现异常", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result getPassengerByUserId(PassengerGroupQuery passengerGroupQuery) {
		Result result = new Result();
		try{
			passengerGroupQuery.setYn(Constants.YES);
			Map<String, Object> map = new HashMap<String, Object>();
			int total = passengerGroupDao.countByCondition(passengerGroupQuery);
			int totalPage =0;
			int pageNo =0;
			List<PassengerGroup> list =new ArrayList<PassengerGroup>();
			if(total > 0){
				 totalPage = total/passengerGroupQuery.getPageSize() + 1;
				if(passengerGroupQuery.getPageNo() > totalPage){
					passengerGroupQuery.setPageNo(totalPage);
					 pageNo =passengerGroupQuery.getPageNo();
				}
				 list = passengerGroupDao.selectByConditionForPage(passengerGroupQuery);
			}
			map.put("total", total);
			map.put("totalPage", totalPage);
			map.put("curPage", pageNo);
			map.put("list", list);
			result.setResult(map);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("getPassengerByUserId出现异常", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result updatePassengerByUserId(PassengerGroup passengerGroup) {
		Result result = new Result();
		try{
			if(passengerGroup.getDefaultPassengerFlag() ==null){
				passengerGroup.setDefaultPassengerFlag(0);
			}
			passengerGroup.setYn(Constants.YES);
			Integer passengerId =passengerGroup.getPassengerId();
			//查询 该旅客是否已经添加。
			int count = passengerGroupDao.countByHavePassenger(passengerGroup);
			if(count > 0){
				result.setSuccess(false);
				result.setResultCode("1005");
				result.setResultMessage("旅客信息已经在列表中");
				return result;
			}
			if(passengerId !=null && passengerId >0){
				PassengerGroup group =passengerGroupDao.selectByPrimaryKey(passengerId);
				if(group ==null || !group.getUserId().equals(passengerGroup.getUserId())){
					result.setSuccess(false);
					result.setResultCode("1005");
					result.setResultMessage("未找到要修改旅客信息");
					return result;
				}else{
					passengerGroup.setModified(new Date());
					passengerGroupDao.updateByPrimaryKey(passengerGroup);
				}
			}else{
				passengerId =passengerGroupDao.insert(passengerGroup);
			}
			result.setResult(passengerId);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("updatePassengerByUserId出现异常", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	@Override
	public Result delPassengerByUserId(PassengerGroup passengerGroup) {
		Result result = new Result();
		try{
			Integer passengerId =passengerGroup.getPassengerId();
			PassengerGroup group =passengerGroupDao.selectByPrimaryKey(passengerId);
			if(group ==null || !group.getUserId().equals(passengerGroup.getUserId())){
					result.setSuccess(false);
					result.setResultCode("1005");
					result.setResultMessage("未找到要删除的旅客信息");
					return result;
			}else{
				passengerGroupDao.deleteByPrimaryKey(passengerId);
			}
			result.setResult(true);
			EcUtils.setSuccessResult(result);
		}catch (Exception e) {
			log.error("delPassengerByUserId出现异常", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
}
