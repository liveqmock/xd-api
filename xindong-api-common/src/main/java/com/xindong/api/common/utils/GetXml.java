/***************************************************************************
 * Copyright (C) 2011 BIS, INC. ALL RIGHTS RESERVED.
 *
 * 项目名称		中国移动-位置路况
 * 业务名称		XXXXXXX
 * 业务ID		XXXX
 *==========================================================================
 * version		时间		修改人				描述     
 *--------------------------------------------------------------------------
 * 1.00  		2013-1-25 	BIS.Administrator 			文件做成     
 **************************************************************************/

package com.xindong.api.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

/**
 * 类 名：GetXml 功能名称： 功能描述：
 */
public class GetXml {
	
	private static final Logger log = LoggerFactory.getLogger(GetXml.class);
	public static String getMXML(String strName) {
		String strPath = System.getProperty("user.dir")
				+ "\\src\\java\\com\\bis\\common\\util\\";
		// 读取XML文件到DOCUMENT
		Document objD = GetXml.load(strPath + strName + ".xml");
		// 返回XML字符串
		String strXML = GetXml.doc2String(objD);
		String[] strRtnS = strXML.split("\n");
		strXML = strXML.replace(strRtnS[0], "");
		return strXML;
	}

	public static void saveXml(String strRtn, String strName) {
		String strPath = System.getProperty("user.dir")
				+ "\\src\\java\\com\\bis\\common\\util\\";
		String[] strRtnS = strRtn.split("\n");
		GetXml.string2XmlFile(strRtnS[2], strPath + strName + ".xml");
	}

	/**
	 * doc2String 将xml文档内容转为String
	 * 
	 * @return 字符串
	 * @param document
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用GBK编码
			OutputFormat format = new OutputFormat("  ", true, "GBK");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("GBK");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	/**
	 * string2Document 将字符串转为Document
	 * 
	 * @return
	 * @param s
	 *            xml格式的字符串
	 */
	public static Document string2Document(String s) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(s);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	/**
	 * doc2XmlFile 将Document对象保存为一个xml文件到本地
	 * 
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param document
	 *            需要保存的document对象
	 */
	public static boolean doc2XmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			/* 将document中的内容写入文件中 */
			// 默认为GBK格式，指定为"GB2312"
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(
					new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * string2XmlFile 将xml格式的字符串保存为本地文件，如果字符串格式不符合xml规则，则返回失败
	 * 
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param str
	 *            需要保存的字符串
	 */
	public static boolean string2XmlFile(String str, String filename) {
		boolean flag = true;
		try {
			Document doc = DocumentHelper.parseText(str);
			flag = doc2XmlFile(doc, filename);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	private String[] getProperties(String key[]) {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("ipConfig.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("ip:" + p.getProperty("ip") + ",port:"
				+ p.getProperty("port"));
		return null;
	}

	/*public static String getValueByTagName(org.w3c.dom.Document doc,
			String tagName) {
		if (doc == null || tagName.equals("")) {
			return "";
		}
		NodeList pl = doc.getElementsByTagName(tagName);
		if (pl != null && pl.getLength() > 0) {
			return pl.item(0).getTextContent();
		}
		return "";
	}*/

	// 向服务器发送报文并得到响应结果
	/*public HashMap sendXmlReturnRespCode(String sendXml,String type) {
		// ----------------------------发送xml报文请求----------------------------
		URLConnection connection = null;
		try {
			// 发送报文
			URL sendUrl = new URL(Constants.url.trim());
			connection = sendUrl.openConnection();

			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			out.write(sendXml);
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// ---------------------- 一旦发送成功，用以下方法就可以得到服务器的回应：获取xml返回报文
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream = null;
		try {
			l_urlStream = connection.getInputStream();
			
			 * InputStreamReader isr = new
			 * InputStreamReader(l_urlStream,"UTF-8"); BufferedReader l_reader =
			 * new BufferedReader(isr); while ((sCurrentLine =
			 * l_reader.readLine()) != null) { sTotalString += sCurrentLine +
			 * "\r\n"; } System.out.println("sTotalString="+sTotalString); if
			 * (sTotalString != null && sTotalString.length() > 0) { returnXml =
			 * new String(Base64Utils.decodeString(sTotalString .substring(4)));
			 * returnXml = sTotalString; } }catch (Exception ex) {
			 * log.error("ErrorInfo is ==[" + ex.getMessage() + "]");
			 * ex.printStackTrace(); } log.debug(
			 * "response ====================================================="
			 * ); log.debug(""); log.debug("responseXml is ==[" + returnXml +
			 * "]");
			 

			// -----------------------------------解析返回的xml文件----------------------------
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(l_urlStream);
			log.debug("客户下单返回报文内容",doc.toString());

			HashMap<String, String> paramMap = new HashMap();
			String[] params = null;
			if(type.equals("1")){
				params = Constants.return_creatTradeNo_params;
			}else if(type.equals("2")){
				params = Constants.return_getOrderState_params;
			}else{
				params = null;
			}
			for (String var : params) {
				paramMap.put(var, getValueByTagName(doc, var));
			}

			if ("0000".equals(paramMap.get("F39"))) {
				log.error("发送报文成功");
			} else {
				log.error("发送报文失败");
			}
			return paramMap;
		} catch (Exception es) {
			es.printStackTrace();
		}
		return null;
	}*/

	/**
	 * load 载入一个xml文档
	 * 
	 * @return 成功返回Document对象，失败返回null
	 * @param uri
	 *            文件路径
	 */
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * xmlWriteDemoByString 演示String保存为xml文件 xml格式标题
	 * "<?xml version='1.0' encoding='GBK'?>" 可以不用写 s = "<config>\r\n"
	 * +"   <ftp name='DongDian'>\r\n"
	 * +"     <ftp-host>127.0.0.1</ftp-host>\r\n"
	 * +"     <ftp-port>21</ftp-port>\r\n" +"     <ftp-user>cxl</ftp-user>\r\n"
	 * +"     <ftp-pwd>longshine</ftp-pwd>\r\n" +"     <!-- ftp最多尝试连接次数 -->\r\n"
	 * +"     <ftp-try>50</ftp-try>\r\n" +"     <!-- ftp尝试连接延迟时间 -->\r\n"
	 * +"     <ftp-delay>10</ftp-delay>\r\n" +"  </ftp>\r\n" +"</config>\r\n";
	 * 
	 * @throws Exception
	 */
	public void xmlWriteDemoByString(String xmlStr) throws Exception {
		if (xmlStr == null) {
			throw new Exception("xml构建字符串不能为空");
		}
		// 将文件生成到classes文件夹所在的目录里
		string2XmlFile(xmlStr, "xmlWriteDemoByString.xml");
		// 将文件生成到classes文件夹里
		string2XmlFile(xmlStr, "classes/xmlWriteDemoByString.xml");
	}

	/**
	 * 演示手动创建一个Document，并保存为XML文件
	 */
	public void xmlWriteDemoByDocument() {
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();
		/** 建立config根节点 */
		Element configElement = document.addElement("config");
		/** 建立ftp节点 */
		configElement.addComment("东电ftp配置");
		Element ftpElement = configElement.addElement("ftp");
		ftpElement.addAttribute("name", "DongDian");
		/** ftp 属性配置 */
		Element hostElement = ftpElement.addElement("ftp-host");
		hostElement.setText("127.0.0.1");
		(ftpElement.addElement("ftp-port")).setText("21");
		(ftpElement.addElement("ftp-user")).setText("cxl");
		(ftpElement.addElement("ftp-pwd")).setText("longshine");
		ftpElement.addComment("ftp最多尝试连接次数");
		(ftpElement.addElement("ftp-try")).setText("50");
		ftpElement.addComment("ftp尝试连接延迟时间");
		(ftpElement.addElement("ftp-delay")).setText("10");
		/** 保存Document */
		doc2XmlFile(document, "classes/xmlWriteDemoByDocument.xml");
	}

	/**
	 * 演示读取文件的具体某个节点的值
	 */
	public static void xmlReadDemo() {
		Document doc = load("classes/xmlWriteDemoByDocument.xml");
		// Element root = doc.getRootElement();
		/** 先用xpath查找所有ftp节点 并输出它的name属性值 */
		List list = doc.selectNodes("/config/ftp");
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element ftpElement = (Element) it.next();
			System.out.println("ftp_name="
					+ ftpElement.attribute("name").getValue());
		}
		/** 直接用属性path取得name值 */
		list = doc.selectNodes("/config/ftp/@name");
		it = list.iterator();
		while (it.hasNext()) {
			Attribute attribute = (Attribute) it.next();
			System.out.println("@name=" + attribute.getValue());
		}
		/** 直接取得DongDian ftp的 ftp-host 的值 */
		list = doc.selectNodes("/config/ftp/ftp-host");
		it = list.iterator();
		Element hostElement = (Element) it.next();
		System.out.println("DongDian's ftp_host=" + hostElement.getText());
	}
}