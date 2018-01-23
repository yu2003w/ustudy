package com.ustudy.exam.model.statics;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeaMarkProgress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6324108430363200437L;

	private String teacId = null;
	private String teacName = null;
	
	private String gradeId = null;
	private String gradeName = null;
	
	private String schName = null;
	private String subName = null;
	
	@JsonIgnore
	private String metrics = null;
	
	private String markProgress = null;
	
	private List<TeaMarkMetrics> questions = null;
	
	public TeaMarkProgress() {
		super();
		// TODO Auto-generated constructor stub
		questions = new ArrayList<TeaMarkMetrics>();
	}

	public String getTeacId() {
		return teacId;
	}

	public void setTeacId(String teacId) {
		this.teacId = teacId;
	}

	public String getTeacName() {
		return teacName;
	}

	public void setTeacName(String teacName) {
		this.teacName = teacName;
	}

	public String getMetrics() {
		return metrics;
	}

	public String getSchName() {
		return schName;
	}

	public void setSchName(String schName) {
		this.schName = schName;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	/*
	 * metrics contains information with format as below,
	 * 1-71-71-标准-2,3-61-70-标准-4
	 * need to parse before processing
	 */
	public void setMetrics(String metrics) {
		this.metrics = metrics;
		String[] data = metrics.split(",");
		if (data == null || data.length < 1) {
			throw new RuntimeException("setMetrics(), failed to construct TeaMarkProgress with->" + data);
		}
		
		for (String da : data) {
			TeaMarkMetrics tmm = new TeaMarkMetrics(da);
			this.questions.add(tmm);
		}
	}

	public List<TeaMarkMetrics> getQuestions() {
		return questions;
	}

	public void setQuestions(List<TeaMarkMetrics> questions) {
		this.questions = questions;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getMarkProgress() {
		return markProgress;
	}

	public void setMarkProgress(String markProgress) {
		this.markProgress = markProgress;
	}

	public void calMarkProgress() {
		if (questions != null) {
			int marked = 0, total = 0;
			for (TeaMarkMetrics tmm: questions) {
				marked += tmm.getMarked();
				total += tmm.getTotal();
			}
			if (total != 0) {
				DecimalFormat progf = new DecimalFormat("##0.00");
				this.markProgress = progf.format((double)marked/total*100) + "%";
			}
		}
	}
	
	@Override
	public String toString() {
		return "TeaMarkProgress [teacId=" + teacId + ", teacName=" + teacName + ", gradeId=" + gradeId + ", gradeName="
				+ gradeName + ", schName=" + schName + ", subName=" + subName + ", metrics=" + metrics
				+ ", markProgress=" + markProgress + ", questions=" + questions + "]";
	}
	
}
