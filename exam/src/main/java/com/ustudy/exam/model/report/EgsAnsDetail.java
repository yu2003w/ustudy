package com.ustudy.exam.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jared
 * 
 * Answer details for egs
 *
 */
public class EgsAnsDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6244650852580294698L;
	
	private long egsid = 0;
	private String subName = null;
	
	private List<ReItem> objRef = null;
	private List<ReItem> subRef = null;
	
	private float score = 0;
	
	@JsonIgnore
	private String oref = null;
	@JsonIgnore
	private String sref = null;
	
	private List<ExamineeAnsDetail> details = null;

	public EgsAnsDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EgsAnsDetail(long egsid, String subName, List<ReItem> objRef, List<ReItem> subRef,
			List<ExamineeAnsDetail> details) {
		super();
		this.egsid = egsid;
		this.subName = subName;
		this.objRef = objRef;
		this.subRef = subRef;
		this.details = details;
	}

	public long getEgsid() {
		return egsid;
	}

	public void setEgsid(long egsid) {
		this.egsid = egsid;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public List<ReItem> getObjRef() {
		return objRef;
	}

	public void setObjRef(List<ReItem> objRef) {
		this.objRef = objRef;
	}

	public List<ReItem> getSubRef() {
		return subRef;
	}

	public void setSubRef(List<ReItem> subRef) {
		this.subRef = subRef;
	}

	public List<ExamineeAnsDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ExamineeAnsDetail> details) {
		this.details = details;
	}

	public String getOref() {
		return oref;
	}

	public void setOref(String oref) {
		this.oref = oref;
		if (oref != null && oref.length() > 0) {
			this.objRef = new ArrayList<ReItem>();
			String []datas = this.oref.split(",");
			for (String item: datas) {
				String [] paras = item.split("-");
				this.objRef.add(new ReItem(paras[0], paras[1]));
			}
		}
	}

	public String getSref() {
		return sref;
	}

	public void setSref(String sref) {
		this.sref = sref;
		if (sref != null && sref.length() > 0) {
			this.subRef = new ArrayList<ReItem>();
			String []datas = this.sref.split(",");
			for (String item: datas) {
				String [] paras = item.split("-");
				this.subRef.add(new ReItem(paras[0], paras[1]));
			}
		}
		
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "EgsAnsDetail [egsid=" + egsid + ", subName=" + subName + ", objRef=" + objRef + ", subRef=" + subRef
				+ ", score=" + score + ", oref=" + oref + ", sref=" + sref + ", details=" + details + "]";
	}

}
