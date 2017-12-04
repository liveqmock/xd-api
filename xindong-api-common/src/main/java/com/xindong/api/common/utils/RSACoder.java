package com.xindong.api.common.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA安全编码组件
 * 
 * @author 曹威
 * @version 1.0
 */
public class RSACoder {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNatuRE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	private static String path = RSACoder.class.getClass().getResource("/").getPath();
	private static PrivateKey privateKey = null;
	private static PublicKey publicKey = null;

	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sign(byte[] data, String privateKey) throws Exception {
		
		
		// 解密由base64编码的私钥
		byte[] keyBytes = Base64Utils.decode(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNatuRE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return Base64Utils.encode(signature.sign());
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sign(byte[] data) throws Exception {
		
//		PrivateKey priKey = initPrivateKey();
	
		initPrivateKey();
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNatuRE_ALGORITHM);
		signature.initSign(privateKey);
		signature.update(data);

		return Base64Utils.encode(signature.sign());
	}
	
	

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		
		
		// 解密由base64编码的公钥
		byte[] keyBytes = Base64Utils.decode(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNatuRE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(Base64Utils.decode(sign));
	}
	
	
	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public boolean verify(byte[] data, String sign)
			throws Exception {
		initPublicKey();
		Signature signature = Signature.getInstance(SIGNatuRE_ALGORITHM);
		signature.initVerify(publicKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(Base64Utils.decode(sign));
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = Base64Utils.decode(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		//Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = Base64Utils.decode(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		//Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对公钥解密
		byte[] keyBytes = Base64Utils.decode(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		//Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = Base64Utils.decode(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据加密
		//Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 字节数据转十六进制字符串
	 * 
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return Base64Utils.encode(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return Base64Utils.encode(key.getEncoded());
	}

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 读取私钥
	 * 
	 * @throws Exception
	 */
	private static void initPrivateKey() throws Exception {
		try {
		    BufferedReader br = new BufferedReader(new FileReader(path
					+ "RSAPrivateKey.pem"));

			StringBuffer privatekey = new StringBuffer();
			String readline = null;
			while((readline= br.readLine())!=null){  
	                if(readline.charAt(0)=='-'){  
	                    continue;  
	                }else{  
	                	privatekey.append(readline);  
	                	//privatekey.append('\r');  
                }  
            }  
			br.close();
//            System.out.println(privatekey.toString());
            byte[] keybyte = Base64Utils.decode(privatekey.toString());
//           byte[] keybyte = Base64Utils.decode("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJYo04tUsZJnIk5WkKfLIzhzf8wg+D8VwbmiRuVUTYmSk/fx/RleCuyWLYPgF0aoZWbUa9o9RZKVOXlpj2g0fUaxuFJnrmRH/l7nZ6KpM4llNMftQIhaDHbassP3pus8AoZZ3akEr4YcbsjfiF4FMPIzryLpkzKMY6/JoG50jrmRAgMBAAECgYAr4jiYwqK2p592B4R1FT/w0ohpzsTeIuyf5NYVVYLnjsfhsX4HeN12cRlSLh5/Tt17pN70Q+3ePfAzFG+Nw7FMjwIiKyBimIFdYBo5I1i58T9zlEBCwz2BkHCl8fmiow6m5zATRUICJRqWqtKc1/+bBprsTHkr7HejqEZBvRSSAQJBAPgD0w+bfmBGE5PTRoWA676I2durRdbtDoKxH/aeKPPHnC7ridPP2BAlb6MUdeTA6KnwGbCThw9CRDnK64paNGECQQCa/nZ6l0d3JssBkh/kGpYjwlxKBEPDDIYjLlF12IM9FDh0ci0FNAA7/ldi2YUaUsdVTGxkueTOp+vMAldIl5MxAkAm6SKx7ilTnWrw5FLKG0HhMMNNTX+1ipeeNX/YR1QRex2r1tRW4OL7FYSAR3N/oixejQbaxyqHYFQn5QgzOpfBAkEAhYnyaIMIltc8lxjgEzb43sxpmXf9LgMPWyZgFwm5YRmX3nQ058O64/WqMse4z0o85fgXd91WDttM5JOrwiaeMQJAFVZvMCJYxikaynzfGLSAk2HBxHvoWhg9PkmqzJvrKi0xjIvF5j+WbjK/pTGtbwGW68+15ejouRL/k0ZrjtAgXg==");
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keybyte);
			//X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte);
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			privateKey =  kf.generatePrivate(keySpec);
			System.out.println("22222222222"+privatekey.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return privateKey; 
	}

	/**
	 * 读取公钥 9996.pub.sdk.key.pem RSAPublicKey.pem
	 * 
	 * @throws Exception
	 */
	private static void initPublicKey() throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path
					+ "9996.pub.sdk.key.pem"));
			
			StringBuffer publickey = new StringBuffer();
			String readline = null;  
            while((readline= br.readLine())!=null){  
	                if(readline.charAt(0)=='-'){  
	                    continue;  
	                }else{  
	                	publickey.append(readline);  
	                	publickey.append('\r');  
                }  
            }  
            br.close();
			// 对密钥解密
			byte[] keybyte = Base64Utils.decode(publickey.toString());
			System.out.println(publickey.toString());
			//PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keybyte);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte);
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			publicKey = (RSAPublicKey)kf.generatePublic(keySpec);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

/*	public static void main(String[] args) throws Exception {

		java.util.Map<String, Object> map = RSACoder.initKey();
		OutputStreamWriter writer = null;
		OutputStreamWriter writer2 = null;
		try {
			File file = new File("D:/" + PUBLIC_KEY + ".pem");
			File file2 = new File("D:/" + PRIVATE_KEY + ".pem");
			if (!file.exists()) {
				file.createNewFile();
			}
			if (!file2.exists()) {
				file2.createNewFile();
			}
			writer = new OutputStreamWriter(new FileOutputStream(file));
			writer2 = new OutputStreamWriter(new FileOutputStream(file2));
			writer.write(RSACoder.getPublicKey(map));
			writer2.write(RSACoder.getPrivateKey(map));
		} catch (Exception e) {
			throw e;
		} finally {
			writer2.flush();
			writer2.close();
			writer.flush();
			writer.close();
		}

	}*/
	
	
	public static void main(String[] args) throws Exception {


		RSACoder coder = new RSACoder();
		coder.initPrivateKey();
		coder.initPublicKey();

	}
}
