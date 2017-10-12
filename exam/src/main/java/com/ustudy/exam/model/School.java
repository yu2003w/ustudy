package com.ustudy.exam.model;

import java.io.Serializable;

public class School implements Serializable {

	private static final long serialVersionUID = 6046308116218434271L;
	
	private int id;
	private String schid = null;
	private String schname = null;
	private String type = null;
	private String province = null;
	private String city = null;
	private String district = null;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSchid() {
		return schid;
	}
	
	public void setSchid(String schid) {
		this.schid = schid;
	}
	
	public String getSchname() {
		return schname;
	}
	
	public void setSchname(String schname) {
		this.schname = schname;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
		
}
