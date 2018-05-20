package com.ustudy.exam.model.answersheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jared
 * 
 * Class designed for answer sheet summary
 *
 */
public class ExamGrSubMeta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3017605179090765397L;
	
	private String examName = null;
	
	private String gradeName = null;
	
	@JsonProperty("GrSubDetails")
	private List<EgsDetail> egsSubL = null;
	
	@JsonIgnore
	private String details = null;

	public ExamGrSubMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamGrSubMeta(String examName, String gradeName, String details) {
		super();
		this.examName = examName;
		this.gradeName = gradeName;
		this.details = details;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
		if (this.details != null && this.details.length() > 0) {
			String [] items = this.details.split(",");
			this.egsSubL = new ArrayList<EgsDetail>();
			for (String it: items) {
				if (it != null && it.length() > 0) 
					this.egsSubL.add(new EgsDetail(it));
			}
		}
		
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public List<EgsDetail> getEgsSubL() {
		return egsSubL;
	}

	public void setEgsSubL(List<EgsDetail> egsSubL) {
		this.egsSubL = egsSubL;
	}

	@Override
	public String toString() {
		return "ExamGrSubMeta [examName=" + examName + ", gradeName=" + gradeName + ", egsSubL=" + egsSubL
				+ ", details=" + details + "]";
	}

}
