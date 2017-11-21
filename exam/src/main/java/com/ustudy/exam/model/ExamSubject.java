package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

public class ExamSubject implements Serializable {

	private static final long serialVersionUID = -9022319462035362070L;
	
	private Long id;
	private Long examid;
	private Long gradeId;
	private String gradeName = null;
	private Long subId;
	private String subName = null;
	private int stuNum;
	private int teacNum;
	private int examPaper;
	private int examAnswer;
	private int examPaperNum;
	private int template;
	private int objItemNum;
	private int subItemNum;
	private int taskDispatch;
	private String answerSeted ;
	private int uploadBathCount ;
	private String blankAnswerPaper ;
	private String blankQuestionsPaper ;
	private String xmlServerPath;
	private String originalData;
	private String examName;
	private List<QuesAnswer> questions;

	public ExamSubject() {
	}

	public ExamSubject(Long id, Long examid, Long gradeId, Long subId) {
		this.id = id;
		this.examid = examid;
		this.gradeId = gradeId;
		this.subId = subId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExamid() {
		return examid;
	}

	public void setExamid(Long examid) {
		this.examid = examid;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public int getStuNum() {
		return stuNum;
	}

	public void setStuNum(int stuNum) {
		this.stuNum = stuNum;
	}

	public int getTeacNum() {
		return teacNum;
	}

	public void setTeacNum(int teacNum) {
		this.teacNum = teacNum;
	}

	public int getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(int examPaper) {
		this.examPaper = examPaper;
	}

	public int getExamAnswer() {
		return examAnswer;
	}

	public void setExamAnswer(int examAnswer) {
		this.examAnswer = examAnswer;
	}

	public int getExamPaperNum() {
		return examPaperNum;
	}

	public void setExamPaperNum(int examPaperNum) {
		this.examPaperNum = examPaperNum;
	}

	public int getTemplate() {
		return template;
	}

	public void setTemplate(int template) {
		this.template = template;
	}

	public int getObjItemNum() {
		return objItemNum;
	}

	public void setObjItemNum(int objItemNum) {
		this.objItemNum = objItemNum;
	}

	public int getSubItemNum() {
		return subItemNum;
	}

	public void setSubItemNum(int subItemNum) {
		this.subItemNum = subItemNum;
	}

	public int getTaskDispatch() {
		return taskDispatch;
	}

	public void setTaskDispatch(int taskDispatch) {
		this.taskDispatch = taskDispatch;
	}

	public String getAnswerSeted() {
		return answerSeted;
	}

	public void setAnswerSeted(String answerSeted) {
		this.answerSeted = answerSeted;
	}

	public int getUploadBathCount() {
		return uploadBathCount;
	}

	public void setUploadBathCount(int uploadBathCount) {
		this.uploadBathCount = uploadBathCount;
	}

	public String getBlankAnswerPaper() {
		return blankAnswerPaper;
	}

	public void setBlankAnswerPaper(String blankAnswerPaper) {
		this.blankAnswerPaper = blankAnswerPaper;
	}

	public String getBlankQuestionsPaper() {
		return blankQuestionsPaper;
	}

	public void setBlankQuestionsPaper(String blankQuestionsPaper) {
		this.blankQuestionsPaper = blankQuestionsPaper;
	}
	
	public String getOriginalData() {
		return originalData;
	}

	public void setOriginalData(String originalData) {
		this.originalData = originalData;
	}
	
	public String getXmlServerPath() {
		return xmlServerPath;
	}

	public void setXmlServerPath(String xmlServerPath) {
		this.xmlServerPath = xmlServerPath;
	}

	public List<QuesAnswer> getQuestions() { return questions; }

	public void setQuestions(List<QuesAnswer> questions) { this.questions = questions; }

	public String getExamName() { return examName; }

	public void setExamName(String examName) { this.examName = examName; }
}
