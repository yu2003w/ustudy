package com.ustudy.info.services;

import java.util.List;

import com.ustudy.info.model.Examinee;

public interface ExamineeService {

	public int createExaminee(List<Examinee> ex);

	public int updateExaminee(List<Examinee> exs);

	public int deleteExaminee(int id);
	
}
