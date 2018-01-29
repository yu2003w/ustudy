package com.ustudy.exam.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.utility.OSSMetaInfo;

@Mapper
public interface ConfigMapper {

	@Select("select cgval->'$.endpoint' as endpoint, cgval->'$.bucketURL' as bucketURL, "
			+ "cgval->'$.bucketName' as bucketName, cgval->'$.accessKeyId' as accessKeyId, "
			+ "cgval->'$.accessKeySecret' as accessKeySecret from ustudy.config where cgname = #{key}")
	public OSSMetaInfo getOSSInfo(String key);
	
}
