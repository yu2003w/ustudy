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
	
	/**
	 * @author jared
	 * only contains question id and question name information
	 */
	class QuesMeta {
		private long quesid = 0;
		private String quesno = null;
		
		public QuesMeta(long quesid, String quesno) {
			super();
			this.quesid = quesid;
			this.quesno = quesno;
		}

		public long getQuesid() {
			return quesid;
		}

		public void setQuesid(long quesid) {
			this.quesid = quesid;
		}

		public String getQuesno() {
			return quesno;
		}

		public void setQuesno(String quesno) {
			this.quesno = quesno;
		}

	}
	private String subName = null;
	private long egsid = 0;
	List<QuesMeta> quesL = null;
	List<QuesMeta> bestL = null;
	List<QuesMeta> faqL = null;
	
	public EgsDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detail with format as below
	 * 语文-28@254-5-255-6-252-8-257-9-258-13-250-14-261-15-260-16-259-20-251-21-253-22@-@-
	 * 物理-31@295-14-299-15-297-16-294-17-298-18@294-17-298-18@-
	 */
	public EgsDetail(String detail) {
		String [] paras = detail.split("@");
		if (paras != null && paras.length == 4) {
			String []datas = paras[0].split("-");
			if (datas != null && datas.length == 2) {
				this.subName = datas[0];
				this.egsid = Long.valueOf(datas[1]);
				this.quesL = new ArrayList<QuesMeta>();
				assembleList(paras[1], this.quesL);
				this.bestL = new ArrayList<QuesMeta>();
				assembleList(paras[2], this.bestL);
				this.faqL = new ArrayList<QuesMeta>();
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

	public List<QuesMeta> getQuesL() {
		return quesL;
	}

	public void setQuesL(List<QuesMeta> quesL) {
		this.quesL = quesL;
	}

	public List<QuesMeta> getBestL() {
		return bestL;
	}

	public void setBestL(List<QuesMeta> bestL) {
		this.bestL = bestL;
	}

	public List<QuesMeta> getFaqL() {
		return faqL;
	}

	public void setFaqL(List<QuesMeta> faqL) {
		this.faqL = faqL;
	}

	/**
	 * @param details format as below, question id and question no pair
	 * 177-13:16-170-17-175-18-176-19-174-20-171-21-173-22
	 */
	private void assembleList(String para, List<QuesMeta> pList) {
		String [] items = para.split("-");
		if (items != null && items.length > 0 && (items.length %2  == 0)) {
			for (int i = 0; i < items.length; i += 2) {
				pList.add(new QuesMeta(Long.valueOf(items[i]), items[i+1].replaceAll(":", "-")));
			}
		}
	}
	
	@Override
	public String toString() {
		return "EgsDetail [subName=" + subName + ", egsid=" + egsid + ", quesL=" + quesL + ", bestL=" + bestL
				+ ", faqL=" + faqL + "]";
	}

}
