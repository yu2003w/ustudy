package com.ustudy.exam.model.template;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PageTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4435121805900717592L;
	
	@JsonProperty("OmrObjectives")
	private List<ObjectQuesTemplate> objQuesL = null;
	
	@JsonProperty("OmrSubjectiveList")
	private List<SubjectQuesTemplate> subQuesL = null;
	
	private int pageIndex = -1;
	
	private String fileName = null;

	public PageTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ObjectQuesTemplate> getObjQuesL() {
		return objQuesL;
	}


	public void setObjQuesL(List<ObjectQuesTemplate> objQuesL) {
		this.objQuesL = objQuesL;
	}


	public List<SubjectQuesTemplate> getSubQuesL() {
		return subQuesL;
	}

	public void setSubQuesL(List<SubjectQuesTemplate> subQuesL) {
		this.subQuesL = subQuesL;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Override
	public String toString() {
		return "PageTemplate [objQuesL=" + objQuesL + ", subQuesL=" + subQuesL + ", pageIndex=" + pageIndex
				+ ", fileName=" + fileName + "]";
	}	

}
