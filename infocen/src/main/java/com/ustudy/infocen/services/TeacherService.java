package com.ustudy.infocen.services;

import java.util.List;

import com.ustudy.infocen.model.Teacher;

public interface TeacherService {
	
	public List<Teacher> getList(int id);
	
	public Teacher displayItem(int id);
	
	public int createItem(Teacher item);
	
	public int updateItem(Teacher item, int id);
	
	public int delItemSet(String ids);
	
	public int deleteItem(int id);

}
