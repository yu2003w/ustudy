package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExamGrBrife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1161759956426291915L;

	private int examId = 0;
	private String examName = null;
	private List<GrClsBrife> grades = null;
	
	public ExamGrBrife() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExamGrBrife(int examId, String examName, String grs) {
		super();
		this.examId = examId;
		this.examName = examName;
		this.parseGrs(grs);
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

	private void parseGrs(String grs) {
		// string grs with the format "gradeid-gradename"
		if (grs == null || grs.isEmpty()) {
			return;
		}
		
		this.grades = new ArrayList<GrClsBrife>();
		String[] grL = grs.split(",");
		for (String gr: grL) {
			String[] its = gr.split("-");				
			GrClsBrife it = new GrClsBrife(Integer.valueOf(its[0]), its[1]);
			this.grades.add(it);
		}
		
	}
	
	@Override
	public String toString() {
		return "ExamGrBrife [examId=" + examId + ", examName=" + examName + ", grades=" + grades + "]";
	}
	
}
