package com.xindong.api.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.misc.BASE64Decoder;
import cn.com.infosec.util.Base64;
import com.xindong.api.common.utils.Constants;
import com.xindong.api.service.CategoryService;
import com.xindong.api.service.result.Result;
import com.xindong.api.service.utils.ComFunction;
import com.xindong.api.service.utils.EcUtils;
import com.xindong.api.web.base.BaseController;


/** 商品分类信息  */

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 获取所有一级分类信息
	 * @return
	 */
	@RequestMapping(value="getAllParentCategory", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Result getAllParentCategory(HttpServletRequest request,HttpServletResponse response, ModelMap context){
		return categoryService.getAllParentCategory();
	}
	
	/**
	 * 上传图片
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/uploadCommentImg", method={ RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> uploadCommentImg(HttpServletResponse response, HttpServletRequest request, ModelMap map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String path = null;
		String savefilePath=null;
        //创建一个通用的多部分解析器.     
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext()); 
        //判断 request 是否有文件上传,即多部分请求...    
        if(multipartResolver.isMultipart(request)){  
        	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)(request);
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                MultipartFile file = multiRequest.getFile(iter.next());
                if(StringUtils.isBlank(file.getOriginalFilename())){//文件为空
        			resultMap.put("msg","null");
        			return resultMap;
                }
                if(file.getSize() > 1024*1024*5){//判断文件的大小
                	resultMap.put("msg","图片大小不能超过5M");
        			return resultMap;
                }
                String fileName = file.getOriginalFilename();//得到上传时的文件名
                String fileType=fileName.substring(fileName.lastIndexOf("."));//得到文件的后缀名
                
                //生成文件的名称
                Random random=new Random();
                int choice=97;
                char var = (char) (choice + random.nextInt(26)); 
                fileName= "p"+ var +(int)(Math.random() * 1000000)+fileType;
                fileName=fileName.toLowerCase();
                
                //生成文件的路径
				Calendar calendar = Calendar.getInstance();
				int year=calendar.get(Calendar.YEAR);//得到年
				int month=calendar.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
				int day=calendar.get(Calendar.DAY_OF_MONTH);//得到天
				path = "/img" + "/" + year +"/" + month+"/"+day+"/";
				savefilePath = "/"+ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.upload_img_url) + path;
				
				File foler = new File(savefilePath);
				if(!foler.exists()){
					foler.mkdirs();
				}
				
				File savedFile = new File(savefilePath, fileName);
				path +=  fileName;
				savefilePath ="http:/" + savefilePath + fileName;
				try {
					//压缩图片
					file.transferTo(savedFile);
					//ImageUtils.imageZip(savedFile, savedFile, 500, 500);//不缩放图片大小。以后优化
				} catch (IOException e) {
					e.printStackTrace();
				}
            }  
        }  
		resultMap.put("msg","success");
		resultMap.put("imageUrl", savefilePath);
		
		String damain = "xindong8.com"; 
		String setDomain = "<script>document.domain = \""+damain+"\";</script>";
		String jsonStr = setDomain + "{\"msg\":\"success\",\"imageUrl\":\""+savefilePath+"\"}";
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.print(jsonStr);
		
		//resultMap.put("url", setDomain);
		return null;
	}
	
	
	/**
	 * 评论上传图片
	 * @param response
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/uploadImg", method={ RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Result uploadImg(String imgStr, String fileType, HttpServletResponse response, HttpServletRequest request, ModelMap map) {
		Result result =new Result();
		String path = null;
		String savefilePath=null;
        String fileName = null; 
        String suffix = null;
        
        try {
        	if("image/jpeg".equalsIgnoreCase(fileType)){//image/jpeg;base64,base64编码的jpeg图片数据
            	suffix = ".jpg";
            } else if("image/x-icon".equalsIgnoreCase(fileType)){//image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            } else if("image/gif".equalsIgnoreCase(fileType)){//image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            } else if("image/png".equalsIgnoreCase(fileType)){//image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else{
            	result.setResultCode("1001");
            	result.setResultMessage("上传图片格式不合法");
    			return result;
            }
        	
        	 //生成文件的名称
            Random random=new Random();
            int choice=97;
            char var = (char) (choice + random.nextInt(26)); 
            fileName= "p"+ var +(int)(Math.random() * 1000000)+suffix;
            fileName=fileName.toLowerCase();
        	
            //生成文件的路径
    		Calendar calendar = Calendar.getInstance();
    		int year=calendar.get(Calendar.YEAR);//得到年
    		int month=calendar.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
    		int day=calendar.get(Calendar.DAY_OF_MONTH);//得到天
    		path = "/img" + "/" + year +"/" + month+"/"+day+"/";
    		savefilePath = "/"+ComFunction.getSystemConstantTypeValue(Constants.SystemConstantTypeValue.upload_img_url) + path;
    		
    		boolean flag = generateImage(imgStr, fileName, savefilePath);
    		path +=  fileName;
    		savefilePath ="http:/" + savefilePath + fileName;
    		
    		result.setResult(savefilePath);
    		if(flag){
    			EcUtils.setSuccessResult(result);
    		}else{
    			EcUtils.setExceptionResult(result);
    		}
		} catch (Exception e) {
			log.error("", e);
			EcUtils.setExceptionResult(result);
		}
		return result;
	}
	
	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author: 
	 * @CreateTime: 
	 * @param imgStr base64编码字符串
	 * @param path 图片路径-具体到文件
	 * @return
	*/
	public static boolean generateImage(String imgStr, String fileName, String filePath) {
		if (imgStr == null){
			return false;
		}
		try {
			// 解密
			byte[] b = new BASE64Decoder().decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i){
				if (b[i] < 0){
					b[i] += 256;
				}
			}
			File foler = new File(filePath);
			if(!foler.exists()){
				foler.mkdirs();
			}
			
			File file = new File(filePath, fileName);
			OutputStream out = new FileOutputStream(file);
			out.write(b);
			out.flush();
			out.close();
			return true;
		}catch (Exception e) {
			log.error("", e);
			return false;
		}
	}
}
