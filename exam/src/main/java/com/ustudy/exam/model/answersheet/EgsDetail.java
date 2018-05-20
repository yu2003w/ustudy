package com.ustudy.exam.model.answersheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	private String details = null;
	
	public EgsDetail() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getDetails() {
		return details;
	}

	/**
	 * @param details format as below
	 * 语文-28@5-6-8-9-13-14-15-16-20-21-22@-@-,
	 * 数学-29@13:16-17-18-19-20-21-22@-@18,
	 * 英语-30@61-62-63@61@-,
	 * 物理-31@14-15-16-17-18@17-18@-,
	 * 生物-32@36-37-38-39@-@-,
	 * 化学-33@16-17-18-19-20-21@-@-,
	 * 政治-34@26-27-28-29@-@-,
	 * 历史-35@36-37@-@-,
	 * 地理-36@26-27-28-29@-@-
	 */
	public void setDetails(String details) {
		this.details = details;
		if (this.details != null && this.details.length() > 0) {
			String [] items = this.details.split(",");
			for (String egs: items) {
				String [] paras = egs.split("@");
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
		}
	}

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
