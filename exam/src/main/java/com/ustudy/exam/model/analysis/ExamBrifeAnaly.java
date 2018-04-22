package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamBrifeAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4027969231857279990L;
	
	private String examName = null;
	
	@JsonProperty("schools")
	private List<ExamSchoolAnaly> schL = null;
	
	@JsonIgnore
	private String schoolDetails = null;
	
	public ExamBrifeAnaly() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public List<ExamSchoolAnaly> getSchL() {
		return schL;
	}

	public void setSchL(List<ExamSchoolAnaly> schL) {
		this.schL = schL;
	}

	public String getSchoolDetails() {
		return schoolDetails;
	}

	public void setSchoolDetails(String schoolDetails) {
		this.schoolDetails = schoolDetails;
		if (schoolDetails != null && schoolDetails.length() > 0) {
			String [] schools = schoolDetails.split(",");
			for (String sch: schools) {
				if (this.schL == null) {
					this.schL = new ArrayList<ExamSchoolAnaly>();
				}
				ExamSchoolAnaly exSch = assembleExamSch(sch);
				if (exSch != null)
					this.schL.add(exSch);
			}
		}
	}
	
	/**
	 * @param sch
	 * format is as below,
	 * 潼关四知学校;九年级-5:5-数学$6-英语$7-文综$8-理综$4-语文
	 * @return
	 */
	private ExamSchoolAnaly assembleExamSch(String sch) {
		ExamSchoolAnaly exSch = null;
		if (sch != null && !sch.isEmpty()) {
			String [] datas = sch.split(";");
			if (datas != null && datas.length == 2) {
				exSch = new ExamSchoolAnaly(datas[0], datas[1]);
			}
		}
		if (exSch != null && exSch.isValid()) {
			return exSch;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "ExamBrifeAnaly [examName=" + examName + ", schL=" + schL + ", schoolDetails=" + schoolDetails + "]";
	}

}
