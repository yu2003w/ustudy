package com.ustudy.info.services;

import java.util.List;

import com.ustudy.info.model.Teacher;

public interface TeacherService {
	
	public List<Teacher> getList(int id);
	
	public Teacher displayItem(int id);
	
	public int createItem(Teacher item);
	
	public int updateItem(Teacher item, int id);
	
	public int delItemSet(String ids);
	
	public int deleteItem(int id);

	public Teacher findTeacherById(String teaid);
	
	public boolean updateLLTime(String teaid);
	
	public String findPriRoleById(String teaid);

}
