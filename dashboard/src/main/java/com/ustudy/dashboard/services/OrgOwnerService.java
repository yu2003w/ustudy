package com.ustudy.dashboard.services;

import java.util.List;

import com.ustudy.dashboard.model.OrgOwner;

public interface OrgOwnerService {

	public List<OrgOwner> getOwnerList(long id);
	
	public OrgOwner getOwner(long id);
	
	public long createItem(OrgOwner item);
	
	public int updateOwner(OrgOwner item, long id);
	
	// Input parameter "ids" is JSON string
	public int delItemSet(String ids);
	
	public int deleteItem(long id);
	
}
