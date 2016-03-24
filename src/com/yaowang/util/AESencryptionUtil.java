package com.yaowang.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESencryptionUtil {
	
	private static String key = "GwIkI4tYm8174I6h";
	private static String iv = "GwIkI4tYm8174I6h";
	
	public static void main(String args[]) throws Exception {
		System.out.println(encrypt("{\"username\":\"lansha\",\"password\":\"lanshatv123456\"}"));
		System.out.println(desEncrypt("765B5AEAF1F2EC96603EC4844C75759CA31FB85538B00DAEED71FDE5D17F3DC4FA1C0BDA95E00CE8119BB450CFD238947D96E096F8863664FE4D9D2933119A65"));
	}
	
	/**
	 * 加密
	 * @return
	 * @throws Exception String
	 * @creationDate. 2016-3-16 下午4:10:03
	 */
	public static String encrypt(String data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			
			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
		
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
		
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			
			return parseByte2HexStr(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解密
	 * @return
	 * @throws Exception String
	 * @creationDate. 2016-3-16 下午4:10:08
	 */
	public static String desEncrypt(String data) throws Exception {
		try{
			byte[] encrypted1 = parseHexStr2Byte(data);
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
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
