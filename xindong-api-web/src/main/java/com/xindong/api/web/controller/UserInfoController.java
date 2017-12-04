package com.xindong.api.web.controller;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.RegisterValidateRules;
import com.xindong.api.domain.PassengerGroup;
import com.xindong.api.domain.UserCollection;
import com.xindong.api.domain.UserInfo;
import com.xindong.api.domain.query.PassengerGroupQuery;
import com.xindong.api.domain.query.UserBalanceDetailQuery;
import com.xindong.api.domain.query.UserCollectionQuery;
import com.xindong.api.service.LoginService;
import com.xindong.api.service.UserInfoService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.web.base.BaseController;
import com.xindong.api.web.wx.WXUtils;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);
	
	private UserInfoService userInfoService;
	private LoginService loginService;
	
	/**
	 * 重置手机号
	 * @param k 旧手机校验码
	 * @param code 新手机验证码
	 * @param mobile 新手机号
	 * @return
	 */
	@RequestMapping(value="resetMobile", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result resetMobile(String k, String code, String mobile, HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(k)){
			result.setResultCode("1001");
			result.setResultMessage("验证参数不能为空");
			return result;
		}
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1001");
			result.setResultMessage("验证码不能为空");
			return result;
		}
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("手机号不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1002");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		
		return loginService.resetMobile(k, code, mobile, getUserId(reuqest));
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	@RequestMapping(value="getUserInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getUserInfo(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return userInfoService.getUserInfoByUserId(getUserId(request));
	}
	
	/**
	 * 获取用户绑定银行卡信息
	 * @return
	 */
	@RequestMapping(value="getPaymentBindbankcard", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getPaymentBindbankcard(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return userInfoService.getPaymentBindbankcard(getUserId(request));
	}
	
	/**
	 * 根据userId获取用户信息修改登陆密码
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return
	 */
	@RequestMapping(value="updatePwd", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result updatePwd(String oldPwd, String newPwd, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		if(StringUtils.isBlank(oldPwd)){
			result.setResultCode("1001");
			result.setResultMessage("旧密码不能为空");
			return result;
		}
		if(StringUtils.isBlank(newPwd)){
			result.setResultCode("1001");
			result.setResultMessage("新密码不能为空");
			return result;
		}
		return userInfoService.updatePwd(getUserId(request), oldPwd, newPwd);
	}

	
	/**
	 * 上传用户头像信息
	 * @return
	 */
	@RequestMapping(value="uploadUserHeadImg", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result uploadUserHeadImg( HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return userInfoService.updateuploadUserHeadImgUserHeadImg(request);
	}
	/**
	 * 用户信息完善
	 * mediaId h5头像编号
	 */
	@RequestMapping(value="completeUserInfo", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result completeUserInfo(UserInfo userInfo,String mediaId,String birthDateStr, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		if(StringUtils.isNotBlank(birthDateStr)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				userInfo.setBirthDate(sdf.parse(birthDateStr));
			} catch (ParseException e) {
				log.error("转换日期出现异常");
				e.printStackTrace();
			}
		}
		log.debug("==mediaId=="+mediaId);
		if(StringUtils.isNotBlank(mediaId)){
			userInfo.setHeadUrl(getHeadUrl(mediaId));
		}
		userInfo.setUserId(getUserId(request));
		return userInfoService.completeUserInfo(userInfo);
	}
	
	/**
	 * 得到头像地址
	 * @param mediaId
	 * @return
	 */
	private String getHeadUrl(String mediaId){
		String url ="";
		String access_token = WXUtils.getToken();
		int choice=97;
        Random random=new Random();
        char var = (char) (choice + random.nextInt(26)); 
        String prd = "p"+ var +(int)(Math.random() * 1000000);
        String fileName = prd + ".png";
        Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);//得到年
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		String path = year +"/" + month+"/"+day+"/";
		String savefilePath =  "/"+ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.upload_img_url) +
				"/img/" + path;
		boolean flag = downloadFromUrl("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+access_token+"&media_id="+mediaId,fileName,savefilePath);
		if(flag){
			 url ="http:/"+savefilePath +fileName;
		}
		return url;
	}
	public static boolean downloadFromUrl(String url,String fileName,String savefilePath) {  
        try {  
        	log.debug("url=="+url);
            URL httpurl = new URL(url);  
    		File file = new File(savefilePath);
			if(!file.exists()){
				file.mkdirs();
			}
            File f = new File(savefilePath, fileName);  
            FileUtils.copyURLToFile(httpurl, f);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }   
        return true;  
    }  
	/**
	 * 第三方登录 用户绑定 手机号
	 * @param code 手机验证码
	 * @param mobile 手机号
	 * @return
	 */
	@RequestMapping(value="setPhone", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result setPhone(String code, String mobile,String password,String registerInvitationCode, String userFlag,HttpServletRequest reuqest,HttpServletResponse response, ModelMap context){
		Result result = new Result();
		
		if(StringUtils.isEmpty(code)){
			result.setResultCode("1001");
			result.setResultMessage("验证码不能为空");
			return result;
		}
		if(StringUtils.isEmpty(mobile)){
			result.setResultCode("1001");
			result.setResultMessage("手机号不能为空");
			return result;
		}
		if(!RegisterValidateRules.isMobile(mobile)){
			result.setResultCode("1002");
			result.setResultMessage("手机格式不正确");
			return result;
		}
		return loginService.setPhone(code, mobile, password,getUserId(reuqest),registerInvitationCode,userFlag);
	}
	
	/**
	 * 发送公司信息到手机
	 */
	@RequestMapping(value="sendAccountData", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result sendAccountData(String token, HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		UserInfo user = userInfoService.selectByUserId(userId);
		if(user ==null || StringUtils.isBlank(user.getMobile())){
			result.setResultCode("1002");
			result.setResultMessage("用户错误，请您重新登陆");
			return result;
		}
		return userInfoService.sendAccountData(user.getMobile());
	}
	/**
	 * 获取用户余额信息
	 * @return
	 */
	@RequestMapping(value="getUserInfoBalancePage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getUserInfoBalancePage(UserBalanceDetailQuery detailQuery,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		detailQuery.setUserId(userId);
		return userInfoService.getUserInfoBalancePage(detailQuery);
	}
	/**
	 * 获取用户收藏信息
	 * @return
	 */
	@RequestMapping(value="getCollectionByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCollectionByUserId(UserCollectionQuery
			userCollectionQuery,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		userCollectionQuery.setUserId(userId);
		return userInfoService.getCollectionByUserId(userCollectionQuery);
	}
	/**
	 * 添加用户收藏信息
	 * @return
	 */
	@RequestMapping(value="addCollectionByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result addCollectionByUserId(Integer itemId,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(itemId ==null){
			result.setResultCode("1001");
			result.setResultMessage("收藏商品编号不能为空");
			return result;
		}
		UserCollection userCollection =new UserCollection();
		userCollection.setUserId(userId);
		userCollection.setItemId(itemId);
		userCollection.setBusinessId(itemId);
		userCollection.setType(1);//1-商品
		return userInfoService.addCollectionByUserId(userCollection);
	}
	
	/**
	 * 获取用户旅客信息
	 * @return
	 */
	@RequestMapping(value="getPassengerByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getPassengerByUserId(PassengerGroupQuery passengerGroupQuery,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		passengerGroupQuery.setUserId(userId);
		return userInfoService.getPassengerByUserId(passengerGroupQuery);
	}
	/**
	 * 修改用户旅客信息
	 * @return
	 */
	@RequestMapping(value="updatePassengerByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result updatePassengerByUserId(PassengerGroup passengerGroup,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(passengerGroup.getPassengerName() ==null){
			result.setResultCode("1001");
			result.setResultMessage("旅客姓名不能为空");
			return result;
		}
		if(passengerGroup.getPassengerMobile() ==null){
			result.setResultCode("1001");
			result.setResultMessage("旅客手机号不能为空");
			return result;
		}
		if(passengerGroup.getPassengerIdentityType() ==null){
			result.setResultCode("1001");
			result.setResultMessage("旅客证件类型不能为空");
			return result;
		}
		if(passengerGroup.getPassengerIdentityNumber() ==null){
			result.setResultCode("1001");
			result.setResultMessage("旅客证件号码不能为空");
			return result;
		}
		passengerGroup.setUserId(userId);
		return userInfoService.updatePassengerByUserId(passengerGroup);
	}
	/**
	 * 获取用户旅客信息
	 * @return
	 */
	@RequestMapping(value="delPassengerByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result delPassengerByUserId(Integer passengerId,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(passengerId ==null){
			result.setResultCode("1002");
			result.setResultMessage("请求参数错误");
			return result;
		}
		PassengerGroup passengerGroup =new PassengerGroup();
		passengerGroup .setUserId(userId);
		passengerGroup.setPassengerId(passengerId);
		return userInfoService.delPassengerByUserId(passengerGroup);
	}
	/**
	 * 取消用户收藏信息
	 * @return
	 */
	@RequestMapping(value="cancleCollectionByUserId", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result cancleCollectionByUserId(Integer collectionId,String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Result result =new Result();
		Integer userId = ComFunction.getUserId(token);
		if(userId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请您先登陆");
			return result;
		}
		if(collectionId ==null){
			result.setResultCode("1001");
			result.setResultMessage("请求参数错误");
			return result;
		}
		
		UserCollection userCollection =new UserCollection();
		userCollection.setUserId(userId);
		userCollection.setCollectionId(collectionId);
		return userInfoService.addCollectionByUserId(userCollection);
	}
	/**
	 * 得到所有商品收藏信息
	 * @return
	 */
	@RequestMapping(value="getCollectionAll", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getCollectionAll(String token,HttpServletRequest request,HttpServletResponse response, ModelMap context){
		Integer userId = ComFunction.getUserId(token);
		return userInfoService.getCollectionAll(userId);
	}
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

}
