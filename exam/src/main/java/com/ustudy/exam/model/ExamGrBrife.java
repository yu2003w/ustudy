package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExamGrBrife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1161759956426291915L;

	private int examId = 0;
	private String examName = null;
	private List<GrClsBrife> grades = null;
	
	// field to hold grade information
	@JsonIgnore
	private String grs = null;
	
	public ExamGrBrife() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public List<GrClsBrife> getGrades() {
		return grades;
	}

	public void setGrades(List<GrClsBrife> grades) {
		this.grades = grades;
	}

	public String getGrs() {
		return grs;
	}

	public void setGrs(String grs) {
		this.grs = grs;
		parseGrs();
	}

	private void parseGrs() {
		// string grs with the format "gradeid-gradename"
		if (this.grs == null || this.grs.isEmpty()) {
			return;
		}
		
		this.grades = new ArrayList<GrClsBrife>();
		String[] grL = this.grs.split(",");
		for (String gr: grL) {
			String[] its = gr.split("-");
			GrClsBrife gcb = new GrClsBrife(Integer.valueOf(its[0]), its[1]);
			this.grades.add(gcb);
		}
		
	}
	
	@Override
	public String toString() {
		return "ExamGrBrife [examId=" + examId + ", examName=" + examName + ", grades=" + grades + "]";
	}
	
}
