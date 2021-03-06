package com.ustudy.exam.utility;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Base64Utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;

import com.ustudy.exam.model.MarkImage;


public class OSSUtil {
	
	private final static Logger logger = LogManager.getLogger(OSSUtil.class);
	

	private static String bucketURL = null;
	private static String bucketName = null;
	
	private static OSSClient ossClient = null;
	
	public static OSSClient getClient() {
		return ossClient;
	}
	
	public static void initOSS(OSSMetaInfo omi) {
		ossClient = new OSSClient(omi.getEndpoint(), omi.getAccessKeyId(), omi.getAccessKeySecret());
		bucketURL = omi.getBucketURL();
		bucketName = omi.getBucketName();
		logger.info("initOSS(), OSS Client initialized!");
	}
	
	/** 
	 * put object into OSS bucket
     * @param bucketName, key, file 
     * @return 
     */
	public static void putObject(String key, File file) throws Exception{
    	try {    		
    		ossClient.putObject(bucketName, key, file);
    	} catch (OSSException oe) {
    		logger.error("Caught an OSSException");
    		logger.error("Error Message: " + oe.getErrorMessage());
    		logger.error("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.error("Caught an ClientException");
    		logger.error("Error Message: " + ce.getErrorMessage());
    		logger.error("Error Code: " + ce.getErrorCode());
    		throw new Exception("can not put object due to client exception", ce);
    	}
    }

	/** 
	 * put object into OSS bucket
     * @param bucketName, key, inputStream 
     * @return 
     */
	public static void putObject(String key, InputStream inputStream) throws Exception {
    	try {
    		ossClient.putObject(bucketName, key, inputStream);
    	} catch (OSSException oe) {
    		logger.error("Caught an OSSException");
    		logger.error("Error Message: " + oe.getErrorMessage());
    		logger.error("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.error("Caught an ClientException");
    		logger.error("Error Message: " + ce.getErrorMessage());
    		logger.error("Error Code: " + ce.getErrorCode());
    		throw new Exception("can not put object due to client exception", ce);
    	}
    }

    /** 
     * watermark a set of images on one base image, and then put object
     * @param baseKey,targetKey,marImgs,overlapped
	 * overlapped: does the basekey have the same size as the markImg?
     * @return 
     */
    public static void putObject(String baseKey, String targetKey, List<MarkImage> markImgs, boolean overlapped) throws Exception {
        try {

            String url = bucketURL + "/" + baseKey;
            url += "?x-oss-process=image";

            for(int i=0; i<markImgs.size(); i++) {
                String base64MarkKey = Base64Utils.encodeToUrlSafeString(markImgs.get(i).getMarkImg().getBytes());
                url += "/watermark,";
                url += "image_" + base64MarkKey;
                url += ",x_" + (overlapped? 0 : markImgs.get(i).getPosX()) + ",y_" + (overlapped? 0 : markImgs.get(i).getPosY()) + ",g_nw";
            }

            logger.trace("URL of the combined file: " + url);
            InputStream in = new URL(url).openStream();
            ossClient.putObject(bucketName, targetKey, in);
        } catch (OSSException oe) {
            logger.error("Caught an OSSException");
            logger.error("Error Message: " + oe.getErrorMessage());
            logger.error("Error Code: " + oe.getErrorCode());
            throw new Exception("can not put object due to oss exception", oe);
        } catch (ClientException ce) {
            logger.error("Caught an ClientException");
            logger.error("Error Message: " + ce.getErrorMessage());
            logger.error("Error Code: " + ce.getErrorCode());
            ce.getStackTrace();
            throw new Exception("can not put object due to client exception", ce);
        } catch (Exception e) {
            logger.error("Error Message: " + e.getMessage());
            throw new Exception("can not put object due to exception", e);
        }
    } 

	/** 
	 * crop base image, merge with mark image, and then put object
     * @param baseKey, markKey, targetKey, x, y, w, h
     * @return 
     */
	public static void putObject(String baseKey, String markKey, String targetKey, String x, 
			String y, String w, String h) throws Exception {
    	try {
    		String base64MarkKey = Base64Utils.encodeToUrlSafeString(markKey.getBytes());
    		String url = bucketURL + "/" + baseKey;
    		url += "?x-oss-process=image/crop,x_" + x + ",y_" + y + ",w_" + w + ",h_" + h;
    		url += "/watermark,";
    		url += "image_" + base64MarkKey;
    		url += ",x_0,y_0,g_nw";
    		logger.trace("URL of the combined file: " + url);
    		InputStream in = new URL(url).openStream();
    		ossClient.putObject(bucketName, targetKey, in);
    	} catch (OSSException oe) {
    		logger.error("Caught an OSSException");
    		logger.error("Error Message: " + oe.getErrorMessage());
    		logger.error("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.error("Caught an ClientException");
    		logger.error("Error Message: " + ce.getErrorMessage());
    		logger.error("Error Code: " + ce.getErrorCode());
    		ce.getStackTrace();
    		throw new Exception("can not put object due to client exception", ce);
    	} catch (Exception e) {
    		logger.error("Error Message: " + e.getMessage());
    		throw new Exception("can not put object due to exception", e);
    	}
    } 

    /** 
     * watermark a set of text on one base image, and then put object
     * @param baseKey,targetKey,marks,x,y 
     * @return 
     */
    public static void putObject(String baseKey, String targetKey, List<String> marks, int x, int y) throws Exception {
        try {
            int size = 30;
            String url = bucketURL + "/" + baseKey;
            url += "?x-oss-process=image";

            for(String mark : marks) {
                String text = Base64Utils.encodeToUrlSafeString(mark.getBytes());
                url += "/watermark";
                url += ",type_d3F5LXplbmhlaQ";
                url += ",size_" + size;
                url += ",text_" + text;
                url += ",color_" + "FF0000";
                url += ",t_100,g_nw";
                url += ",x_" + x;
                url += ",y_" + y;
                y += size;
            }

            logger.trace("URL of the combined file: " + url);
            InputStream in = new URL(url).openStream();
            ossClient.putObject(bucketName, targetKey, in);
        } catch (OSSException oe) {
            logger.error("Caught an OSSException");
            logger.error("Error Message: " + oe.getErrorMessage());
            logger.error("Error Code: " + oe.getErrorCode());
            throw new Exception("can not put object due to oss exception", oe);
        } catch (ClientException ce) {
            logger.error("Caught an ClientException");
            logger.error("Error Message: " + ce.getErrorMessage());
            logger.error("Error Code: " + ce.getErrorCode());
            ce.getStackTrace();
            throw new Exception("can not put object due to client exception", ce);
        } catch (Exception e) {
            logger.error("Error Message: " + e.getMessage());
            throw new Exception("can not put object due to exception", e);
        }
    } 

    /** 
     * watermark a text on one base image, and then put object
     * @param baseKey,targetKey,mark,x,y 
     * @return 
     */
    public static void putObject(String baseKey, String targetKey, String mark, int x, int y) throws Exception {
        try {
            int size = 60;
            String url = bucketURL + "/" + baseKey;
            url += "?x-oss-process=image";

            String text = Base64Utils.encodeToUrlSafeString(mark.getBytes());
            url += "/watermark";
            url += ",type_d3F5LXplbmhlaQ";
            url += ",size_" + size;
            url += ",text_" + text;
            url += ",color_" + "FF0000";
            url += ",t_100,g_nw";
            url += ",x_" + x;
            url += ",y_" + y;

            logger.trace("URL of the combined file: " + url);
            InputStream in = new URL(url).openStream();
            ossClient.putObject(bucketName, targetKey, in);
        } catch (OSSException oe) {
            logger.error("Caught an OSSException");
            logger.error("Error Message: " + oe.getErrorMessage());
            logger.error("Error Code: " + oe.getErrorCode());
            throw new Exception("can not put object due to oss exception", oe);
        } catch (ClientException ce) {
            logger.error("Caught an ClientException");
            logger.error("Error Message: " + ce.getErrorMessage());
            logger.error("Error Code: " + ce.getErrorCode());
            ce.getStackTrace();
            throw new Exception("can not put object due to client exception", ce);
        } catch (Exception e) {
            logger.error("Error Message: " + e.getMessage());
            throw new Exception("can not put object due to exception", e);
        }
    } 

    /** 
     * watermark a mark image on one base image, and then put object
     * @param baseKey,markKey,x,y 
     * @return 
     */
    public static void addMarkImage(String baseKey, String markKey, int x, int y) throws Exception {
    	try {
    		String base64MarkKey = Base64Utils.encodeToUrlSafeString(markKey.getBytes());
    		String url = bucketURL + "/" + baseKey;
    		url += "?x-oss-process=image";
    		url += "/watermark,";
    		url += "image_" + base64MarkKey;
    		url += ",x_" + x + ",y_" + y + ",g_nw";
    		logger.trace("URL of the combined file: " + url);
    		InputStream in = new URL(url).openStream();
    		ossClient.putObject(bucketName, baseKey, in);
    	} catch (OSSException oe) {
    		logger.error("Caught an OSSException");
    		logger.error("Error Message: " + oe.getErrorMessage());
    		logger.error("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.error("Caught an ClientException");
    		logger.error("Error Message: " + ce.getErrorMessage());
    		logger.error("Error Code: " + ce.getErrorCode());
    		ce.getStackTrace();
    		throw new Exception("can not put object due to client exception", ce);
    	} catch (Exception e) {
    		logger.error("Error Message: " + e.getMessage());
    		throw new Exception("can not put object due to exception", e);
    	}
    }
    /** 
     * watermark a set of images on one base image, and then put object
     * @param baseKey,targetKey,marImgs,x,y,w,h,score
     * overlapped: does the basekey have the same size as the markImg?
     * @return 
     */
    public static void addDblMarks(String baseKey, String targetKey, List<MarkImage> markImgs, Long x, Long y, Long w, Long h, float score) throws Exception {
    	try {
    		String url = bucketURL + "/" + baseKey;
    		url += "?x-oss-process=image/crop,x_" + x + ",y_" + y + ",w_" + w + ",h_" + h;
            for(int i=0; i<markImgs.size(); i++) {
            	String markImg = markImgs.get(i).getMarkImg() + "?x-oss-process=image/blur,r_5,s_3";
                String base64MarkKey = Base64Utils.encodeToUrlSafeString(markImg.getBytes());
                url += "/watermark,";
                url += "image_" + base64MarkKey;
                url += ",x_0,y_0,g_nw";
            }
            
            int size = 60;
            String scoreText = "" + score;
            String text = Base64Utils.encodeToUrlSafeString(scoreText.getBytes());
            url += "/watermark";
            url += ",type_d3F5LXplbmhlaQ";
            url += ",size_" + size;
            url += ",text_" + text;
            url += ",color_" + "FF0000";
            url += ",t_100,g_nw";
            url += ",x_" + Math.round(w/2);
            url += ",y_" + 0;
            
    		logger.trace("URL of the combined file: " + url);
    		InputStream in = new URL(url).openStream();
    		ossClient.putObject(bucketName, targetKey, in);
    	} catch (OSSException oe) {
    		logger.error("Caught an OSSException");
    		logger.error("Error Message: " + oe.getErrorMessage());
    		logger.error("Error Code: " + oe.getErrorCode());
    		throw new Exception("can not put object due to oss exception", oe);
    	} catch (ClientException ce) {
    		logger.error("Caught an ClientException");
    		logger.error("Error Message: " + ce.getErrorMessage());
    		logger.error("Error Code: " + ce.getErrorCode());
    		ce.getStackTrace();
    		throw new Exception("can not put object due to client exception", ce);
    	} catch (Exception e) {
    		logger.error("Error Message: " + e.getMessage());
    		throw new Exception("can not put object due to exception", e);
    	}
    } 
}