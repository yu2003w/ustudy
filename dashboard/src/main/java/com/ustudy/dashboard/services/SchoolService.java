package com.ustudy.dashboard.services;

import java.util.List;

import com.ustudy.dashboard.model.School;

/**
 * @author jared
 *
 */

public interface SchoolService {

	
	/**
	 * @param id  --- retrieve item list from id
	 * @return
	 */
	public List<School> getList(int id);
	
	/**
	 * @param data --- to be stored into database
	 * @return  --- index in the datababse
	 */
	public int createItem(School data);
	
	public int deleteItem(int id);
	
	public School displayItem(int id);
	
	public int updateItem(School data, int id);
	
}
