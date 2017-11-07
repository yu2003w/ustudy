package com.ustudy.exam.utility;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Base64Util {

	/** 
	 * 编码
     * @param token 
     * @return 
     */  
    @SuppressWarnings("restriction")
	public static String decode(final String token) {  
    	return new String(Base64.getEncoder().encode(token.getBytes()));
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
            Base64.Decoder decoder = Base64.getDecoder();
            bt = decoder.decode(token);
        } catch (Exception e) {
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
