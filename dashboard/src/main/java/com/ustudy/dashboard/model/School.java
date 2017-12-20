package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class School implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6054857261127399523L;
	
	// id auto generated by database
	private int id = -1;
	private String schoolId = null;
	private String schoolName = null;
	private String schoolType = null;
	private String province = null;
	private String city = null;
	private String district = null;
	private List<Grade> grades = null;


	// Noted here: default constructor is required to make http.post to transfer json
	// string into object of "school"
	public School() {
		super();
	}

	public School(int id, String schoolId, String schoolName, String schoolType, 
			String province, String city, String district) {
		super();
		this.id = id;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.schoolType = schoolType;
		this.province = province;
		this.city = city;
		this.district = district;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
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

	
	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	@Override
	public String toString() {
		String tmp = new String();
		if (grades != null) {
			for (Grade gr: grades) {
				tmp += "{" + gr.toString() + "},";
			}
		}
		return "School [id=" + id + ", schoolId=" + schoolId + ", schoolName=" + schoolName + ", schoolType="
				+ schoolType + ", province=" + province + ", city=" + city + ", district=" + district + ", grades="
				+ tmp + "]";
	}
	
	public Map<String,String> compare(School target) {
		HashMap<String, String> comRes = new HashMap<String,String>();
		if (target == this)
			return null;
		if (target.getSchoolId() != this.schoolId)
			comRes.put("schid", this.schoolId);
		if (target.getSchoolName() != this.schoolName)
			comRes.put("schname", this.schoolName);
		if (target.getSchoolType() != this.schoolType)
			comRes.put("type", this.schoolType);
		if (target.getProvince() != this.province)
			comRes.put("province", this.province);
		if (target.getCity() != this.city)
			comRes.put("city", this.city);
		if (target.getDistrict() != this.district)
			comRes.put("district", this.district);
		return comRes;
		
	}
}
