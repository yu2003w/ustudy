package com.ustudy.exam.utility;

import java.io.File;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;


public class OSSUtil {
	
	private final static Logger logger = LogManager.getLogger(OSSUtil.class);
	
	private final static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
	private final static String accessKeyId = "LTAIRf0qaQSS6Y2x";
	private final static String accessKeySecret = "IaPIEYKXxtZ5k6XynMPhFPfLoqnXAn";
	private final static String bucketName = "ustudypaper";
	
	private static OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

	/** 
	 * put object into OSS bucket
     * @param bucketName, key, file 
     * @return 
     */  
    @SuppressWarnings("restriction")
	public static void putObject(String key, File file) throws Exception{
    	try {
    		ossClient.putObject(bucketName, key, file);
    	} catch (OSSException oe) {
    		logger.warn("Caught an OSSException");
    		logger.warn("Error Message: " + oe.getErrorMessage());
    		logger.warn("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.warn("Caught an ClientException");
    		logger.warn("Error Message: " + ce.getErrorMessage());
    		logger.warn("Error Code: " + ce.getErrorCode());
    		throw new Exception("can not put object due to client exception", ce);
    	}
    }

	/** 
	 * put object into OSS bucket
     * @param bucketName, key, inputStream 
     * @return 
     */  
    @SuppressWarnings("restriction")
	public static void putObject(String key, InputStream inputStream) {
    	try {
    		ossClient.putObject(bucketName, key, inputStream);
    	} catch (OSSException oe) {
    		logger.warn("Caught an OSSException");
    		logger.warn("Error Message: " + oe.getErrorMessage());
    		logger.warn("Error Code: " + oe.getErrorCode());
    	} catch (ClientException ce) {
    		logger.warn("Caught an ClientException");
    		logger.warn("Error Message: " + ce.getErrorMessage());
    		logger.warn("Error Code: " + ce.getErrorCode());
    	}
    }    
}
