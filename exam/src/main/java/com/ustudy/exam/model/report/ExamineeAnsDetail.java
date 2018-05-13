package com.ustudy.exam.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jared
 * 
 * This class contains data for answer details including both subject answers and object answers per egs
 *
 */
public class ExamineeAnsDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7208961653859205752L;
	
	private String eeCode = null;
	private String eeName = null;
	private String clsName = null;
	
	private List<ReItem> objL = null;
	private List<ReItem> subL = null;
	
	@JsonIgnore
	private String objans = null;
	@JsonIgnore
	private String subans = null;
	
	
	public ExamineeAnsDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExamineeAnsDetail(String eeCode, String eeName, String clsName, List<ReItem> objL, List<ReItem> subL,
			String objans, String subans) {
		super();
		this.eeCode = eeCode;
		this.eeName = eeName;
		this.clsName = clsName;
		this.objL = objL;
		this.subL = subL;
		this.objans = objans;
		this.subans = subans;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public String getEeCode() {
		return eeCode;
	}

	public void setEeCode(String eeCode) {
		this.eeCode = eeCode;
	}

	public String getEeName() {
		return eeName;
	}

	public void setEeName(String eeName) {
		this.eeName = eeName;
	}

	public List<ReItem> getObjL() {
		return objL;
	}

	public void setObjL(List<ReItem> objL) {
		this.objL = objL;
	}

	public List<ReItem> getSubL() {
		return subL;
	}

	public void setSubL(List<ReItem> subL) {
		this.subL = subL;
	}

	public String getObjans() {
		return objans;
	}

	public void setObjans(String objans) {
		this.objans = objans;
		if (this.objans != null && this.objans.length() > 0) {
			this.objL = new ArrayList<ReItem>();
			String [] datas = this.objans.split(",");
			for (String item: datas) {
				String [] paras = item.split("-");
				if (paras != null && paras.length >= 1) {
					this.objL.add(new ReItem(paras[0], paras[1]));
				}
			}
		}
	}

	public String getSubans() {
		return subans;
	}

	public void setSubans(String subans) {
		this.subans = subans;
		if (this.subans != null && this.subans.length() > 0) {
			this.subL = new ArrayList<ReItem>();
			String [] datas = this.subans.split(",");
			for (String item: datas) {
				String [] paras = item.split("-");
				if (paras != null && paras.length >= 1) {
					this.subL.add(new ReItem(paras[0], paras[1]));
				}
			}
		}
	}

	@Override
	public String toString() {
		return "ExamineeAnsDetail [eeCode=" + eeCode + ", eeName=" + eeName + ", clsName=" + clsName + ", objL=" + objL
				+ ", subL=" + subL + ", objans=" + objans + ", subans=" + subans + "]";
	}
	
}
