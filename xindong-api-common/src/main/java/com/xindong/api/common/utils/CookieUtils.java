package com.xindong.api.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public final class CookieUtils {
	private static Integer maxAge = 86400*3;
	
	/**
	 * 设置无生命周期的cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 */
	public static void addCookie(HttpServletResponse response, String name, String value){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    response.addCookie(cookie);
	}
	
	/**
	 * 设置有生命周期的cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, Integer maxA){
	    Cookie cookie = new Cookie(name, value);
	    cookie.setPath("/");
	    maxA = maxA != null && maxA > 0 ? maxA : maxAge;
	    cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
	/**
	 * 根据名字获取value
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static String getValueByName(HttpServletRequest request,String name){
	    Map<String,String> cookieMap = ReadVlaueMap(request);
	    if(cookieMap.containsKey(name)){
	        return cookieMap.get(name);
	    }else{
	        return null;
	    }   
	}
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	/**
	 * 根据name删除cookie
	 * @param request
	 * @return
	 */
	public static void delCookieByName(HttpServletRequest request, HttpServletResponse response, String name){
		Cookie cookie = getCookieByName(request, name);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	 
	/**
	 * 将value封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,String> ReadVlaueMap(HttpServletRequest request){  
	    Map<String,String> cookieMap = new HashMap<String,String>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie.getValue());
	        }
	    }
	    return cookieMap;
	}
	 
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}

}
