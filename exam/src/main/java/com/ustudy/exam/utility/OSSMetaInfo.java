package com.ustudy.exam.utility;

import java.io.Serializable;

public class OSSMetaInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6894708631369003683L;

	private String bucketURL = null;
	private String endpoint = null;
	private String accessKeyId = null;
	private String accessKeySecret = null;
	private String bucketName = null;
	
	public OSSMetaInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OSSMetaInfo(String bucketURL, String endpoint, String accessKeyId, String accessKeySecret,
			String bucketName) {
		super();
		this.bucketURL = bucketURL;
		this.endpoint = endpoint;
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.bucketName = bucketName;
	}

	public String getBucketURL() {
		return bucketURL;
	}

	public void setBucketURL(String bucketURL) {
		this.bucketURL = bucketURL;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	@Override
	public String toString() {
		return "OSSMetaInfo [bucketURL=" + bucketURL + ", endpoint=" + endpoint + ", accessKeyId=" + accessKeyId
				+ ", accessKeySecret=" + accessKeySecret + ", bucketName=" + bucketName + "]";
	}
	
}
