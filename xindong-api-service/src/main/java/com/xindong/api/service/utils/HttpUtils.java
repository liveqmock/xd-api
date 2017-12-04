package com.xindong.api.service.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtils {
	private final static Log log = LogFactory.getLog(HttpUtils.class);

	public static String httpGetData(String url, String param, String charSet) {
		return httpGetData(url, param, charSet, null);
	}

	public static String httpGetData(String url, String param, String charSet,
			String[] header) {
		String result = "";
		BufferedReader in = null;
		URLConnection conn = null;
		URL realUrl = null;
		try {
			String urlName = url + "?" + param;
			realUrl = new URL(urlName);
			// 打开和URL之间的连接
			conn = realUrl.openConnection();
			// 设置通用的请求属性
			// conn.setRequestProperty("accept", "*/*");
			// conn.setRequestProperty("connection", "Keep-Alive");
			// conn.setRequestProperty("user-agent",
			// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			if (header != null) {
				for (int i = 0; i < header.length; i++) {
					String[] content = header[i].split(":");
					conn.setRequestProperty(content[0], content[1]);
				}
			}
			// 建立实际的连接
			conn.connect();
			if (StringUtils.isNotBlank(charSet)) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), charSet));
			} else {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
			}

			String line = "";
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("访问url地址：" + url + "发送GET请求出现异常", e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				realUrl = null;
				conn = null;
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		return result;
	}

	public static String httpPostData(String urlPath, String data,
			String charSet) {
		return httpPostData(urlPath, data, charSet, null);
	}

	public static String httpPostData(String urlPath, String data,
			String charSet, String[] header) {
		String result = null;
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		try {
			url = new URL(urlPath);
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setDoInput(true);
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setConnectTimeout(20000);// 设置连接主机超时（单位：毫秒）
			httpurlconnection.setReadTimeout(20000);// 设置从主机读取数据超时（单位：毫秒）

			if (header != null) {
				for (int i = 0; i < header.length; i++) {
					String[] content = header[i].split(":");
					httpurlconnection
							.setRequestProperty(content[0], content[1]);
				}
			}

			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			if (StringUtils.isNotBlank(data)) {
				httpurlconnection.getOutputStream().write(data.getBytes("UTF-8"));
			}
			httpurlconnection.getOutputStream().flush();
			httpurlconnection.getOutputStream().close();
			int code = httpurlconnection.getResponseCode();

			if (code == 200) {
				DataInputStream in = new DataInputStream(
						httpurlconnection.getInputStream());
				int len = in.available();
				byte[] by = new byte[len];
				in.readFully(by);
				if (StringUtils.isNotBlank(charSet)) {
					result = new String(by, Charset.forName(charSet));
				} else {
					result = new String(by);
				}
				in.close();
			} else {
				log.error("请求地址：" + urlPath + "返回状态异常，异常号为：" + code);
			}
		} catch (Exception e) {
			log.error("访问url地址：" + urlPath + "发生异常", e);
		} finally {
			url = null;
			if (httpurlconnection != null) {
				httpurlconnection.disconnect();
			}
		}
		return result;
	}
	
	public static String httpMapParams(String url, Map<String, String> params) {  
        URL u = null;  
        HttpURLConnection con = null;  
        // 构建请求参数  
        StringBuffer sb = new StringBuffer();  
        if (params != null) {  
            for (Entry<String, String> e : params.entrySet()) {  
                sb.append(e.getKey());  
                sb.append("=");  
                sb.append(e.getValue());  
                sb.append("&");  
            }  
            sb.substring(0, sb.length() - 1);  
        }  
        System.out.println("send_url:" + url);  
        System.out.println("send_data:" + sb.toString());  
        // 尝试发送请求  
        try {  
            u = new URL(url);  
            con = (HttpURLConnection) u.openConnection();  
            //// POST 只能为大写，严格限制，post会不识别  
            con.setRequestMethod("POST");  
            con.setDoOutput(true);  
            con.setDoInput(true);  
            con.setUseCaches(false);  
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");  
            osw.write(sb.toString());  
            osw.flush();  
            osw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (con != null) {  
                con.disconnect();  
            }  
        }  
  
        // 读取返回内容  
        StringBuffer buffer = new StringBuffer();  
        try {  
            //一定要有返回值，否则无法把请求发送给server端。  
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                buffer.append(temp);  
                buffer.append("\n");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return buffer.toString();  
    }  
  
	/*public static String httpPostData(String urlPath, Byte[] data,
			String charSet, String[] header) {
		String result = null;
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		try {
			url = new URL(urlPath);
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setDoInput(true);
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setConnectTimeout(20000);// 设置连接主机超时（单位：毫秒）
			httpurlconnection.setReadTimeout(20000);// 设置从主机读取数据超时（单位：毫秒）

			if (header != null) {
				for (int i = 0; i < header.length; i++) {
					String[] content = header[i].split(":");
					httpurlconnection
							.setRequestProperty(content[0], content[1]);
				}
			}

			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// httpurlconnection.getOutputStream().write(data);

			httpurlconnection.getOutputStream().flush();
			httpurlconnection.getOutputStream().close();
			int code = httpurlconnection.getResponseCode();

			if (code == 200) {
				DataInputStream in = new DataInputStream(
						httpurlconnection.getInputStream());
				int len = in.available();
				byte[] by = new byte[len];
				in.readFully(by);
				if (StringUtils.isNotBlank(charSet)) {
					result = new String(by, Charset.forName(charSet));
				} else {
					result = new String(by);
				}
				in.close();
			} else {
				log.error("请求地址：" + urlPath + "返回状态异常，异常号为：" + code);
			}
		} catch (Exception e) {
			log.error("访问url地址：" + urlPath + "发生异常", e);
		} finally {
			url = null;
			if (httpurlconnection != null) {
				httpurlconnection.disconnect();
			}
		}
		return result;
	}

	public static String uploadImage(String url, String fromUrl) {
		String targetURL = null; // -- 指定URL
		File targetFile = null; // -- 指定上传文件

		targetFile = new File(fromUrl);
		targetURL = url; // servleturl
		PostMethod filePost = new PostMethod(targetURL);

		try {

			// 通过以下方法可以模拟页面参数提交
			// filePost.setParameter("name", "中文");
			// filePost.setParameter("pass", "1234");
			filePost.setParameter("pass", "1234");

			Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(50000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				return filePost.getResponseBodyAsString();
			} else {
				return "失败";
			}
		} catch (Exception ex) {
			log.error("", ex);
			return "异常";
		} finally {
			filePost.releaseConnection();
		}
	}*/

}
