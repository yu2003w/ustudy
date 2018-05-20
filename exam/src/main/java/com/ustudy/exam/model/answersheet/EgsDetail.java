package com.ustudy.exam.model.answersheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jared
 * 
 * contains information about question list, faq list, best list per egs
 *
 */
public class EgsDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6450568638532754622L;
	
	private String subName = null;
	private long egsid = 0;
	List<String> quesName = null;
	List<String> bestL = null;
	List<String> faqL = null;
	
	public EgsDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detail with format as below
	 * 语文-28@5-6-8-9-13-14-15-16-20-21-22@-@-
	 */
	public EgsDetail(String detail) {
		String [] paras = detail.split("@");
		if (paras != null && paras.length == 4) {
			String []datas = paras[0].split("-");
			if (datas != null && datas.length == 2) {
				this.subName = datas[0];
				this.egsid = Long.valueOf(datas[1]);
				this.quesName = new ArrayList<String>();
				assembleList(paras[1], this.quesName);
				this.bestL = new ArrayList<String>();
				assembleList(paras[2], this.bestL);
				this.faqL = new ArrayList<String>();
				assembleList(paras[3], this.faqL);
			}
		}
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

	public List<String> getQuesName() {
		return quesName;
	}

	public void setQuesName(List<String> quesName) {
		this.quesName = quesName;
	}

	public List<String> getBestL() {
		return bestL;
	}

	public void setBestL(List<String> bestL) {
		this.bestL = bestL;
	}

	public List<String> getFaqL() {
		return faqL;
	}

	public void setFaqL(List<String> faqL) {
		this.faqL = faqL;
	}

	/**
	 * @param details format as below
	 */
	private void assembleList(String para, List<String> pList) {
		String [] items = para.split("-");
		if (items != null && items.length > 0) {
			for (String item: items) {
				pList.add(item);
			}
		}
	}
	@Override
	public String toString() {
		return "EgsDetail [subName=" + subName + ", egsid=" + egsid + ", quesName=" + quesName + ", bestL=" + bestL
				+ ", faqL=" + faqL + "]";
	}

}
