package com.ustudy.infocenter.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jared
 * This class is different from that in dashboard. There is more additional information
 * needs to be transported to front end.
 *
 */
public class School implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7337387524679907904L;
	
	private String id = null;
	
	@JsonProperty("schoolOwner")
	private TeacherBrife owner = null;
	@JsonProperty("examOwner")
	private TeacherBrife exam = null;
	
	private String schoolId = null;
	private String schoolName = null;
	private String schoolType = null;
	private String province = null;
	private String city = null;
	private String district = null;
	
	@JsonProperty("departments")
	private List<Department> departs = null;
	
	@JsonProperty("subjects")
	private List<SubjectOwner> subOs = null;

	public School() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public School(String id, String schoolId, String schoolName, String schoolType, String province, String city,
			String district) {
		super();
		this.id = id;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.schoolType = schoolType;
		this.province = province;
		this.city = city;
		this.district = district;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TeacherBrife getOwner() {
		return owner;
	}

	public void setOwner(TeacherBrife owner) {
		this.owner = owner;
	}

	public TeacherBrife getExam() {
		return exam;
	}

	public void setExam(TeacherBrife exam) {
		this.exam = exam;
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

	public List<Department> getDeparts() {
		return departs;
	}

	public void setDeparts(List<Department> departs) {
		this.departs = departs;
	}

	public List<SubjectOwner> getSubOs() {
		return subOs;
	}

	public void setSubOs(List<SubjectOwner> subOs) {
		this.subOs = subOs;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", owner=" + owner + ", exam=" + exam + ", schoolId=" + schoolId + ", schoolName="
				+ schoolName + ", schoolType=" + schoolType + ", province=" + province + ", city=" + city + ", departs="
				+ departs + ", subOs=" + subOs + "]";
	}

}
