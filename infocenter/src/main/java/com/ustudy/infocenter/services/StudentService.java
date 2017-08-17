package com.ustudy.infocenter.services;

import java.util.List;

import com.ustudy.infocenter.model.Student;

public interface StudentService {

	public List<Student> getList(int id);
	
	public int createItem(Student data);
	
	public int deleteItem(int id);
	
	public int delItemSet(String ids);
	
	public Student displayItem(int id);
	
	public int updateItem(Student data); 

}
