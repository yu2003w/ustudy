package com.ustudy.dashboard.services;

import java.util.List;

import com.ustudy.dashboard.model.OrgOwner;

public interface OrgOwnerService {

	public List<OrgOwner> getList(int id);
	
	public OrgOwner displayItem(int id);
	
	public int createItem(OrgOwner item);
	
	public int updateItem(OrgOwner item, int id);
	
	// Input parameter "ids" is JSON string
	public int delItemSet(String ids);
	
	public int deleteItem(int id);
}
