package com.ustudy.exam.utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ustudy.exam.service.impl.ClientServiceImpl;

public class Base64Util {
	
	private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);

	/** 
	 * 编码
     * @param token 
     * @return 
     */  
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
    public static String encode(final String token) {    	
    	if(null != token) {
    		byte[] bt = null;
    		try {
    			Base64.Decoder decoder = Base64.getDecoder();
    			bt = decoder.decode(token);
    		} catch (Exception e) {
    			logger.error(e.getMessage());
    			e.printStackTrace();
    		}
    		return new String(bt);
    	}else {
    		logger.error("token is null ...");
			return null;
		}
    }

    /** 
	 * md5加密
     * @param password 
     * @return 
     */  
	public static String getMd5Pwd(final String password) {
		if(null != password) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes(), 0, password.length());
				return String.format("%032x", new BigInteger(1, md.digest()));    
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
    }  
	
}
