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
	 * format is similar as below,
	 * 江南中学;高二-7:7-高二（1）班@8-高二（2）班@9-高二（3）班@10-高二（4）班@11-高二（5）班@12-高二（6）班@13-高二（7）班:
	 * 1-语文@2-数学@3-英语@4-物理@5-生物@6-化学@7-政治@8-历史@9-地理@10-数学(文)
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
