package com.xindong.api.web.wx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindong.api.common.utils.Constants;
import com.xindong.api.common.utils.MD5Util;

public class WXPayUtils {
	private static final Logger log = LoggerFactory.getLogger(WXPayUtils.class);
	public static String createSign(String charSet,SortedMap<String,String> parameters, Integer type){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
            	sb.append(k + "=" + v + "&");
            }
        }
        String key = WXValues.AppPaySecret;
        sb.append("key=" + key);
        log.info("sign未加密的===="+sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), charSet).toUpperCase();
        return sign;
    }
    
    public static Map<String, String> doXMLParse(String xml) {
        Document document = null;
		try {
			document = (Document) DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 获取根元素
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());

        // 获取所有子元素
        List<Element> childList = root.elements();
        Map<String, String> map = new HashMap<String, String>();
        for (Element element : childList) {
        	map.put(element.getName(), element.getData().toString());
        	System.out.println(element.getData().toString());
		}
        return map;
    }
}
