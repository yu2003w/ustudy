package com.ustudy.info.services;

import java.util.List;

import com.ustudy.info.model.Teacher;

public interface TeacherService {
	
	public List<Teacher> getList(int id);
	
	public Teacher displayTeacher(int id);
	
	public int createTeacher(Teacher item);
	
	public void updateTeacher(Teacher item, int id);
	
	public int delTeas(String ids);
	
	public int deleteTeacher(int id);

	public Teacher findTeacherById(String teaid);
	
	public boolean updateLLTime(String teaid);
	
	public String findPriRoleById(String teaid);

	public void setLLTime(String tid);

}
