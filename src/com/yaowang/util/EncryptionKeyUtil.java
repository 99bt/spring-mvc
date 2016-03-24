package com.yaowang.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionKeyUtil {
	//Cipher负责完成加密或解密工作
	public static Cipher c;
	//SecretKey负责保存对称密钥
	public static SecretKey deskey;
	
	//初始化生成密钥文件
	public static void initKey(){
		//KeyGenerator提供对称密钥生成器的功能，支持各种算法
		KeyGenerator keygen = null;
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			//实例化支持AES算法的密钥生成器，算法名称用AES
			keygen = KeyGenerator.getInstance("AES");
			//生成密钥
			deskey = keygen.generateKey();
			FileOutputStream f;
			try {
				f = new FileOutputStream("conf/key.dat");
				ObjectOutputStream b = new ObjectOutputStream(f);
				b.writeObject(deskey);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	public static SecretKey getSecretKey() throws Exception{
		InputStream is = EncryptionKeyUtil.class.getClassLoader().getResourceAsStream("conf/key.dat");
		ObjectInputStream b=new ObjectInputStream(is);
		deskey=(SecretKey)b.readObject( );
		return deskey;
	}
	
	//加密
	public static String encryption(String msg) throws Exception {
		deskey = getSecretKey();
		//生成Cipher对象，指定其支持AES算法
		c = Cipher.getInstance("AES");
		//根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		String src = parseByte2HexStr(msg.getBytes("utf-8"));
		//加密，结果保存进enc
		byte[] enc = c.doFinal(parseHexStr2Byte(src));
		return parseByte2HexStr(enc);
	}
	
	//解密
	public static String decryption(String msg) throws Exception {
		deskey = getSecretKey();
		//生成Cipher对象，指定其支持AES算法
		c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, deskey);
		//解密，结果保存进dec
		byte[] src = parseHexStr2Byte(msg);
		byte[] dec = c.doFinal(src);
		return new String(dec);
	}
	
	//生成密钥
	public static void main(String[] args){
		String msg = "abc";
		try {
			String src = encryption(msg);
			System.out.println("加密后"+src);
			String tot = decryption(src);
			System.out.println("解密后"+tot);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**将二进制转换成16进制
	 * @param buf 
	 * @return 
	 */  
	public static String parseByte2HexStr(byte buf[]) {  
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                }  
	                sb.append(hex.toUpperCase());  
	        }  
	        return sb.toString();  
	}
	
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */  
	public static byte[] parseHexStr2Byte(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	}


}
