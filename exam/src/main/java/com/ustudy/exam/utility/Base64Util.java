package com.ustudy.exam.utility;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Base64Util {

	/** 
	 * 编码
     * @param token 
     * @return 
     */  
    @SuppressWarnings("restriction")
	public static String decode(final String token) {  
    	return new sun.misc.BASE64Encoder().encode(token.getBytes());    
    }  
  
    /** 
     * 二进制数据编码为BASE64字符串 
     * 解码
     * @param token 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("restriction")
    public static String encode(final String token) {  
    	byte[] bt = null;    
	   try {    
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
	       bt = decoder.decodeBuffer(token);    
	   } catch (IOException e) {    
	       e.printStackTrace();    
	   }
	   return new String(bt);
    }  
    
    /** 
	 * md5加密
     * @param password 
     * @return 
     */  
	public static String getMd5Pwd(final String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes(), 0, password.length());
			return String.format("%032x", new BigInteger(1, md.digest()));    
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
    }  
	
}
