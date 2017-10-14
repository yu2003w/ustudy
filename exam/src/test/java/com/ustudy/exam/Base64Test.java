package com.ustudy.exam;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Base64Test {

	public static void main(String[] args) {
		
		String token = "admin:admin";
		
		System.out.println(token);
		
		token = decode(token);
		
		System.out.println(token);
		
		token = encode(token);
		
		System.out.println(token);
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update("hello".getBytes(), 0, "hello".length());
			System.out.println(String.format("%032x", new BigInteger(1, md.digest())));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 
	 * 编码
     * @param bytes 
     * @return 
     */  
    public static String decode(final String token) {  
    	return new sun.misc.BASE64Encoder().encode(token.getBytes());    
    }  
  
    /** 
     * 二进制数据编码为BASE64字符串 
     * 解码
     * @param bytes 
     * @return 
     * @throws Exception 
     */  
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
	
}
