package com.ustudy.info.services;

import java.util.List;

import com.ustudy.info.model.Examinee;

public interface ExamineeService {

	int createExaminee(List<Examinee> ex);

	int updateExaminee(List<Examinee> exs);

	int deleteExaminee(int id);
	
	List<Examinee> getExamineeByFilter(long examid, long gradeid, long clsid, String key);
	
	/**
	 * calculate absent examinee list for egs
	 * @param egsid
	 * @return
	 */
	List<Examinee> getAbsentListPerEgs(long egsid);
	
	/**
	 * retrieve examinee list by client with examid and gradeid
	 * @param examid
	 * @param gradeid
	 * @return
	 */
	List<Examinee> getExamineeListByClient(long examid, long gradeid);
	
}
