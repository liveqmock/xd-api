package com.xindong.api.common.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Node;
import org.w3c.dom.NameList;
import org.w3c.dom.Notation;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class HttpXmlSend {

 private static Logger logger = Logger.getLogger(HttpXmlSend.class);
 //密钥
 private static String  key= "61F1544985191F32";

 public HttpXmlSend() {
  
 }

 public static void main(String[] args)  {
  Map<String, String> requestMap = new HashMap<String, String>();
  requestMap.put("cardnum1", "111111");
  requestMap.put("cardnum2","111111");
  Map<String, String> responseMap = new HashMap<String, String>();
//  String respInfo = NoBankRecharge(requestMap,responseMap);
//  System.out.println("respInfo= " + respInfo);
 }




 //向服务器发送报文并得到响应结果
 private static String sendXmlReturnRespCode(String sendXml) {
  String url = "http://192.168.17.115:9084/bestv/trans/";
  String returnXml = "";
  try {
   logger.debug("sendXml is ==[" + sendXml + "]");

   // 发送报文
   URL sendUrl = new URL(url.trim());
   URLConnection connection = sendUrl.openConnection();
   
   connection.setConnectTimeout(30000);
   connection.setReadTimeout(30000);
   connection.setDoOutput(true);
   
   OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
   out.write(sendXml);

   out.flush();
   out.close();
   
   // 一旦发送成功，用以下方法就可以得到服务器的回应：
   String sCurrentLine;
   String sTotalString;
   sCurrentLine = "";
   sTotalString = "";
   InputStream l_urlStream = connection.getInputStream();

   InputStreamReader isr = new InputStreamReader(l_urlStream,"UTF-8");
   BufferedReader l_reader = new BufferedReader(isr);
   while ((sCurrentLine = l_reader.readLine()) != null) {
    sTotalString += sCurrentLine + "\r\n";
   }
   System.out.println("sTotalString="+sTotalString);
   if (sTotalString.length() > 0) {
 //   returnXml = new String(Base64Util.decodeString(sTotalString.substring(4)));
   }
  } catch (Exception ex) {
   logger.error("ErrorInfo is ==[" + ex.getMessage() + "]");
   ex.printStackTrace();
  }
  logger.debug("response =====================================================");
  logger.debug("");
  logger.debug("responseXml is ==[" + returnXml + "]");
 // String respCode = ParserUtils.fetchValueByNodename(returnXml, "Resp.respCode");
  if ("00".equals("") == false) {
   logger.error("发送报文错误");
  }
  System.out.println("returnXml =" + returnXml);
  System.out.println("respCode =" + "");
  return returnXml;
 }

 //比对响应报文以及响应map中对应字段的值
/* private static String compareReciveXml(String responXml,
   Map<String, String> respMap) {
  String result = "报文格式错误";
  if (!"00".equals(XmlParserUtils.fetchValueByNodename(responXml,
    "Resp.respCode")))
   return result;
  StringBuilder sb = new StringBuilder();
  for (String str : respMap.keySet()) {
   String keyValue = respMap.get(str);
   String reciveValue = XNode.fetchValueByNodename(responXml,
     str);
   if (keyValue.equals(reciveValue))
    continue;
   sb.append(str + ":(" + keyValue + "," + reciveValue + ");<br>");
  }
  if (StringUtils.isEmpty(sb.toString())) {
   result = "OK";
  } else {
   result = "Error:<br>" + sb.toString();
  }
  return result;
 }*/

/* private static String transTemplateStrSendMsg(String templateStr,
   Map<String, String> requestMap) {
  Set<String> keySet = requestMap.keySet();
  for (String key : keySet) {
   String keyValue = requestMap.get(key);
   String templateKeyValue = XmlParserUtils.fetchValueByNodename(
     templateStr, key);
   if (key.contains(".")) {
    templateStr = changeNodeText(key, templateStr, keyValue);
   } else {
    if (templateStr.contains(key)) {
     templateStr = templateStr.replace("<" + key + ">"
       + templateKeyValue + "</" + key + ">", "<" + key
       + ">" + keyValue + "</" + key + ">");
    }
   }
  }
  return templateStr;
 }*/

 private static String changeNodeText(String nodeTexts, String templateStr,
   String secondValue) {
  String[] nodeStr = nodeTexts.split("\\.");

  String firstNode = StringUtils.substringBetween(templateStr, "<"
    + nodeStr[0] + ">", "</" + nodeStr[0] + ">");
  String firstOldStr = "<" + nodeStr[0] + ">" + firstNode + "</"
    + nodeStr[0] + ">";
  String secondNode = StringUtils.substringBetween(firstNode, "<"
    + nodeStr[1] + ">", "</" + nodeStr[1] + ">");
  String secondOldStr = "<" + nodeStr[1] + ">" + secondNode + "</"
    + nodeStr[1] + ">";
  String secondNodeValue = "<" + nodeStr[1] + ">" + secondValue + "</"
    + nodeStr[1] + ">";
  String newFirstStr = firstOldStr.replace(secondOldStr, secondNodeValue);
  return templateStr.replace(firstOldStr, newFirstStr);
 }

 private static String loadTemplateToMemoryStr(String templateName) {
  StringBuilder sb = new StringBuilder();
  try {
   URL resourceURL = Thread.currentThread().getContextClassLoader()
     .getResource(templateName);
   String filePath = resourceURL.getFile();
   FileInputStream fr = new FileInputStream(filePath);
   InputStreamReader is = new InputStreamReader(fr, "UTF-8");
   BufferedReader bfr = new BufferedReader(is);
   while (bfr.ready()) {
    sb.append(bfr.readLine().trim());
   }
   bfr.close();
   fr.close();
  } catch (Exception e) {
   logger.error("获取模板错误：" + e.getMessage());
   e.printStackTrace();
  }
  return sb.toString();
 }

 
 
 public static  byte[] encrypt(byte[] keyBytes,byte[] src) {
  try {
   //生成密钥
   SecretKey deskey = new SecretKeySpec(keyBytes, "DES");
   //加密
   Cipher c1 = Cipher.getInstance("DES/ECB/PKCS5Padding");
   c1.init(Cipher.ENCRYPT_MODE, deskey);
   return c1.doFinal(src);
  } catch (java.security.NoSuchAlgorithmException e1) {
   e1.printStackTrace();
  } catch (javax.crypto.NoSuchPaddingException e2) {
   e2.printStackTrace();
  } catch (java.lang.Exception e3) {
   e3.printStackTrace();
  }
  return null;
 }
}
