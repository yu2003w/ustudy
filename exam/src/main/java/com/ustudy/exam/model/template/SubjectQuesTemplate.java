package com.ustudy.exam.model.template;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SubjectQuesTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8066286132265663122L;

	@JsonProperty("TopicType")
	private int quesType = -1;
	
	@JsonProperty("StartQid")
	private int sQid = -1;
	
	@JsonProperty("EndQid")
	private int eQid = -1;

	@JsonProperty("AreaID")
	private int areaId = -1;
	
	private List<QuesRegion> regionList = null;
	
	public SubjectQuesTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getQuesType() {
		return quesType;
	}

	public void setQuesType(int quesType) {
		this.quesType = quesType;
	}

	public int getsQid() {
		return sQid;
	}

	public void setsQid(int sQid) {
		this.sQid = sQid;
	}

	public int geteQid() {
		return eQid;
	}

	public void seteQid(int eQid) {
		this.eQid = eQid;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public List<QuesRegion> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<QuesRegion> regionList) {
		this.regionList = regionList;
	}

	@Override
	public String toString() {
		return "SubjectQuesTemplate [quesType=" + quesType + ", sQid=" + sQid + ", eQid=" + eQid + ", areaId=" + areaId
				+ ", regionList=" + regionList + "]";
	}
	
}
