
package com.xindong.api.service.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindong.api.common.mail.Mail;
import com.xindong.api.common.mail.MailUtil;
import com.xindong.api.common.qrcode.ZXingCodeUtil;
import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.DESUtil;
import com.xindong.api.domain.Category;
import com.xindong.api.domain.SystemConstantType;
import com.xindong.api.domain.SystemConstantValue;
import com.xindong.api.service.CategoryService;
import com.xindong.api.service.SystemConstantService;


public class ComFunction 
{
	  // 1.SPRING 全局配置
	  private static final Logger log = LoggerFactory.getLogger(ComFunction.class);
	  private static SystemConstantService systemConstantService ;
	  private static CategoryService categoryService ;
	  public  void setCategoryService(CategoryService categoryService) {
		ComFunction.categoryService = categoryService;
	 }
	  public   void setSystemConstantService(SystemConstantService systemConstantService) {
		ComFunction.systemConstantService = systemConstantService;
	 }
	  
  /**
   * 根据分类编号获取分类名称
   * @param type
   * @return
   */
  public static String getCategoryName (Integer categoryId)
  {
	  String  categoryName= "";
	  if(categoryId ==null){
		  return categoryName;
	  }
	  categoryName = RedisUtils.get(RedisValue.CategoryName+categoryId);
	  if(StringUtils.isBlank(categoryName)){
		  Category category = categoryService.selectByCategoryId(categoryId);
		  if(category !=null && StringUtils.isNotBlank(category.getCategoryName())){
			  categoryName = category.getCategoryName();
			  RedisUtils.set(RedisValue.CategoryName+categoryId, RedisValue.CategoryNameTime, categoryName);
		  }
	  }
	  return categoryName;
  }
  
  /**
   * 根据常量类型 查询所有
   * @param type
   * @return
   */
  public static String getSystemConstantValue (String type,Integer value)
  {
	  String name ="";
	  if(StringUtils.isBlank(type) || value == null){
		  return name;
	  }
	  name =  RedisUtils.get(RedisValue.SystemStatusName+type+value.toString());
	  if(StringUtils.isBlank(name)){
		  SystemConstantValue systemConstantValue =new SystemConstantValue();
		  systemConstantValue.setYn(Constants.YES);
		  systemConstantValue.setTypeValue(type);
		  systemConstantValue.setValue(value);
		  List<SystemConstantValue> types = systemConstantService.selectSystemConstantValue(systemConstantValue );
		  if(null != types && types.size()>0){
			  name= types.get(0).getName();
			  RedisUtils.set(RedisValue.SystemStatusName+type+value.toString(), RedisValue.SystemStatusNameTime, name);
		  }
	  }
    return name;
  }
  /**
   * 根据常量类型 查询所有
   * @param type
   * @return
   */
  public static List<SystemConstantValue> getSystemConstantType (String type)
  {
	  List<SystemConstantValue> types = new ArrayList<SystemConstantValue>();
	  if(StringUtils.isBlank(type)){
		  return types;
	  }
	 SystemConstantValue systemConstantValue =new SystemConstantValue();
	 systemConstantValue.setYn(Constants.YES);
	 systemConstantValue.setTypeValue(type);
	 types = systemConstantService.selectSystemConstantValue(systemConstantValue );
    return types;
  }
  /**
   * 查询系统 常量值
   * @param name
   * @return
   */
  public static String getSystemConstantTypeValue (String name)
  {
	  String value="";
	  value =  RedisUtils.get(name);
	  if(StringUtils.isBlank(value)){
		  SystemConstantType constantType =new SystemConstantType();
		  constantType.setYn(Constants.YES);
		  constantType.setName(name);
		  List<SystemConstantType> types = systemConstantService.selectSystemConstantType(constantType );
		  if(types !=null && types.size() >0){
			  SystemConstantType type =types.get(0);
			  value =type.getValue();
			  RedisUtils.set(type.getName(), RedisValue.SystemStatusNameTime, type.getValue());
		  }
	  }
    return value;
  }
//过滤特殊字符
	public   static  String StringFilter(String   str)   throws   PatternSyntaxException   {   
               // 只允许字母和数字		
               // String   regEx  =  "[^a-zA-Z0-9]";                   
                  // 清除掉所有特殊字符
		  String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		  Pattern   p   =   Pattern.compile(regEx);   
		  Matcher   m   =   p.matcher(str);   
		  return   m.replaceAll("").trim();   
		  }   
    /**
	 * 去除特殊图形字符
	 * @param name
	 * @return
	 */
	public static String setValue(String name){
		StringBuffer buffer =new StringBuffer();
		char[] chars = name.toCharArray();
		for(int i = 0; i < chars.length; i ++) {
			//汉字判断
			if((chars[i] >= 19968 && chars[i] <= 40869) || (chars[i] >= 97 && chars[i] <= 122) || (chars[i] >= 65 && chars[i] <= 90)) {
				buffer.append(chars[i]);
			}else{
				if(isLetter(chars[i])){
					buffer.append(chars[i]);
				}
			}
		}
		if(buffer.length() >0){
			return buffer.toString();
		}else{
			return buffer.toString();
		}
	}
	/*iso8859-1 gbk gbk utf-8 unicode*/
	/**
	 * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	 * @param c char  需要判断的字符
	 * @return boolean 返回true,Ascill字符
	 */
	public static boolean isLetter(char c) { 
        int k = 0x80; 
        return c / k == 0 ? true : false; 
    }
	
	/**
	  *  生成微信支付二维码图片
	  * @param content   跳转地址
	  * @return
	  * @throws Exception
	  */
	public static String createQrcodeLogo(String content){
		String uploadPath ="/"+ ComFunction
				.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.upload_img_url);
		String logoPath =uploadPath+ "/img/icon1.png";//logo 
		String codePath =getPath(uploadPath+ "/img/qrcode");
		String qrcodeLogoPath =getPath(uploadPath+ "/img/qrcode");
		ZXingCodeUtil.createQrcodeLogo(content, logoPath, codePath, qrcodeLogoPath);
       //将多张图片合在一起
       return qrcodeLogoPath;
	}
	/**
	 * 得到文件路径 
	 * 2016/01/10/
	 * @return
	 */
	private static String getPath(String uploadPath) {
		int choice = 97;
		Random random = new Random();
		char var = (char) (choice + random.nextInt(26));
		String prd = "p" + var + (int) (Math.random() * 1000000);
		String fileName = prd + ".png";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		String path =uploadPath+"/"+ year + "/" + month + "/" + day ;
		File file2 = new File(path);
		if(!file2.exists()){
			file2.mkdirs();
		}
		path =path+ "/" + fileName;
		return path;
	}
	 /**
	  *  生成礼品卡二维码图片
	  * @param shareUrl   跳转地址
	  * @param backgroundImg 背景图片
	  * @param content 规则
	  * @return
	  * @throws Exception
	  */
	/*public static String createQrcodeImg(String shareUrl,String content, String backgroundImg)
			throws Exception {
		String uploadPath = ComFunction
				.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.upload_img_url);
		String endPath = "";
		//生成二维码
		String codeUrl = createQrcodeLogo(shareUrl);// 生成二维码的路径
		MergeImageUtils tt = new MergeImageUtils();
       BufferedImage sbw = tt.loadImageLocal(codeUrl);//code 
//       backgroundImg = "d:\\test.i.51wot.com\\img\\qrcode\\12345678.jpg";
       BufferedImage d = tt.loadImageLocal(backgroundImg);//背景图片 
       if(d == null){
       	throw new IOException("BufferedImage 背景图片  is null");
       }
//       String contenFile = uploadFile
       String codePath = "/"+uploadPath+"/img/qrcode/"+getPathFile();//中间图片的地址
       tt.writeImageLocal(codePath,tt.modifyImage(d,content,80,80));
       //往图片上写文件
       BufferedImage bgNew = tt.loadImageLocal(codePath);
       if(bgNew == null){
       	throw new IOException("BufferedImage 图片文字 is null");
       }
       
       endPath = "/"+uploadPath+"/img/qrcode/"+getPathFile();//生成分享图片地址
       File file2 = new File(endPath);
		if(!file2.exists()){
			file2.mkdirs();
		}
       tt.writeImageLocal(endPath,tt.modifyImagetogeter(sbw, bgNew,30,580));
       //将多张图片合在一起
       return "http:/"+endPath;
	}*/
	/**
	 * 计算字符长度 中文算2个字符
	 * @param value
	 * @return
	 */
	public static int length(String value) {
	        int valueLength = 0;
	        String chinese = "[\u0391-\uFFE5]";
	        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
	        for (int i = 0; i < value.length(); i++) {
	            /* 获取一个字符 */
	            String temp = value.substring(i, i + 1);
	            /* 判断是否为中文字符 */
	            if (temp.matches(chinese)) {
	                /* 中文字符长度为2 */
	                valueLength += 2;
	            } else {
	                /* 其他字符长度为1 */
	                valueLength += 1;
	            }
	        }
	        return valueLength;
	 }
	/**
	 * 得到不重复的
	 * 2016/01/10/1231.png
	 * @return
	 */
	private static String getPathFile() {
		int choice = 97;
		Random random = new Random();
		char var = (char) (choice + random.nextInt(26));
		String prd = "p" + var + (int) (Math.random() * 1000000);
		String fileName = prd + ".png";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		String path = year + "/" + month + "/" + day + "/" + fileName;

		return path;
	}
	/**
	 * 根据token获取userId
	 * @param token
	 * @return
	 */
	public static Integer getUserId(String token){
		Integer userId =null;
		if(StringUtils.isNotBlank(token)){
			try{
				String value = DESUtil.decrypt(token, Constants.TOKEN_DES);
				if(StringUtils.isNotBlank(value)){
					String [] tokenArr = value.split("-");
					if(tokenArr != null && tokenArr.length == 3){
						userId = Integer.parseInt(tokenArr[0]);
					}
				}
			}catch (Exception e) {
				log.error("", e);
			}
		}
		return userId;
	}
	
	/**
	   * 查询系统 用户名称
	   * @param name
	   * @return
	   *//*
	  public static String getUserName(Integer userId)
	  {
		  UserInfoService userInfoService = (UserInfoService) ctx.getBean("userInfoService");
		  String value="";
		  value =  RedisUtils.get(RedisValue.UserName+userId);
		  if(StringUtils.isBlank(value)){
			  UserInfo info = userInfoService.selectByUserId(userId);
			  if(info !=null ){
				  value =info.getUserName();
				  RedisUtils.set(RedisValue.UserName+userId, RedisValue.UserNameTime, value);
			  }
		  }
	    return value;
	  }*/
	
	/**
	 * 发送邮件
	 * @param receiver 接收人
	 * @param subject  邮件主题
	 * @param message   邮件内容，支持HTML
	 * @return true 表示成功
	 */
	public static boolean sendMail(String receiver,String subject,String message){
		Mail mail = new Mail();
		mail.setReceiver(receiver); // 接收人
		mail.setUsername(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.mail_username));
		mail.setSender(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.mail_username));
		mail.setPassword(ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.mail_password));
//		mail.setName("您好：");
		mail.setSubject(subject);//主题
		mail.setMessage(message);//内容
		return new MailUtil().send(mail);
	}
}
