package com.yaowang.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * @ClassName: RSAUtil
 * @Description: TODO(非对称加密)
 * @author tandingbo
 * @date 2015年5月22日 下午5:32:12
 *
 */
public class RSAUtil {

	// 非对称加密密钥算法
	public static final String KEY_ALGORITHM = "RSA";
	// 公钥
	private static final String PUBLIC_KEY = "RSAPublicKey";
	// 私钥
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	// RSA密钥长度,默认为1024,密钥长度必须是64的倍数,范围在521~65526位之间
	private static final int KEY_SIZE = 512;

	/**
	 * 公钥加密
	 * 
	 * @param data待加密数据
	 * @param key公钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		return encryptByPublicKey(data, getKey(publicKey));
	}

	/**
	 * 公钥加密
	 * 
	 * @param data待加密数据
	 * @param key公钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥解密
	 * 
	 * @param data待解密数据
	 * @param key私钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 获取密钥
	 * 
	 * @param key密钥
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	public static byte[] getKey(String key) throws Exception {
		return Hex.decodeHex(key.toCharArray());
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap密钥Map
	 * @return byte[] 私钥
	 * @throws Exception
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap密钥Map
	 * @return byte[] 公钥
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * 初始化密钥
	 * 
	 * @return 密钥Map
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		// 实例化实钥对生成器
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥对生成器
		keyPairGen.initialize(KEY_SIZE);
		// 生成密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 封装密钥
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);

		return keyMap;
	}

	/**
	 * 取得私钥十六进制表示形式
	 * 
	 * @param keyMap密钥Map
	 * @return String 私钥十六进制字符串
	 * @throws Exception
	 */
	public static String getPrivateKeyString(Map<String, Object> keyMap) throws Exception {
		return Hex.encodeHexString(getPrivateKey(keyMap));
	}

	/**
	 * 取得公钥十六进制表示形式
	 * 
	 * @param keyMap密钥Map
	 * @return String 公钥十六进制字符串
	 * @throws Exception
	 */
	public static String getPublicKeyString(Map<String, Object> keyMap) throws Exception {
		return Hex.encodeHexString(getPublicKey(keyMap));
	}
	
	/**
	 * 初始化密钥
	 * 
	 * @throws Exception
	 */
	public static void initStringKey() throws Exception {
		// 初始化密钥
		Map<String, Object> keyMap = RSAUtil.initKey();
		byte[] publicKey = RSAUtil.getPublicKey(keyMap);
		byte[] privateKey = RSAUtil.getPrivateKey(keyMap);
		System.out.println("公钥:" + Base64.encodeBase64String(publicKey));
		System.out.println("私钥:" + Base64.encodeBase64String(privateKey));
	}
	
	/**
	 * 公钥加密返回字符串
	 * 
	 * @param key公钥
	 * @param enContent被加密内容
	 * @return
	 * @throws Exception
	 */
	public static String encryptString(String key, String enContent) throws Exception {
		byte[] publicKey = Base64.decodeBase64(key);
		byte[] enCodeData = RSAUtil.encryptByPublicKey(enContent.getBytes(), publicKey);
		return Base64.encodeBase64String(enCodeData);
	}

	/**
	 * 公钥加密返回URL中安全使用的字符串
	 * 
	 * @param key公钥
	 * @param enContent被加密内容
	 * @return
	 * @throws Exception
	 */
	public static String encryptURLSafeString(String key, String enContent) throws Exception {
		byte[] publicKey = Base64.decodeBase64(key);
		byte[] enCodeData = RSAUtil.encryptByPublicKey(enContent.getBytes(), publicKey);
		return Base64.encodeBase64URLSafeString(enCodeData);
	}

	/**
	 * 私钥解密返回字符串
	 * @param key
	 * @param deContent
	 * @return
	 * @throws Exception
	 */
	public static String decryptString(String key, String deContent) throws Exception {
		byte[] privateKey = Base64.decodeBase64(key);
		byte[] deCodeData = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(deContent), privateKey);
		return new String(deCodeData);
	}

	public static void main(String[] args) {

		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALjqGYSjqr2BNObNoLreXmlYsGlNAWNKEzPRe3jRU1t2wepMt0ofq2it1C6f1xk0pjjPT1QWQCTS5995RS/WAasCAwEAAQ==";
		String privateKey = "MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEAuOoZhKOqvYE05s2gut5eaViwaU0BY0oTM9F7eNFTW3bB6ky3Sh+raK3ULp/XGTSmOM9PVBZAJNLn33lFL9YBqwIDAQABAkEAms8KbBxlrl/EvVH7tVbDreidUJsyhUCX0PMZLu34noHibBaEvFooKvlPHOL+D3e3rIoPlyxwSK5C8mr/Lw2N+QIhAOOVCwfyadsvuuvvtqKtnFug8S6p2s9bhHut/Yb6YZpnAiEA0AEgl04MOz3wZriwRvlhkLIz7nue1HUMWzJ6OnzR3B0CIQCNAeXJ0Lsh4ZrxYJ9KgEMmyrtk7Dz0yYF2mEFDhpM+9QIhAMXKfKJEDi97S5FyR/WFiDgerHE3TiY7Ez/ZNQ9OyAQlAiEAzy1bMijIzrPiNGLa8rBwGUdkmQb5kDBA6jGQ0C9QBsQ=";
		try {
			String inputStr = "RSA加密算法,私钥加密,公钥解密";
			
			String enCode = RSAUtil.encryptURLSafeString(publicKey, inputStr);
			System.out.println(enCode);
			
			String deCode = RSAUtil.decryptString(privateKey, enCode);
			// 私钥解密
			System.out.println(deCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
