package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamSchoolAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4036132654234616877L;

	class GradeDetail {
		
		private String gradeName = null;
		
		private int clsNum = 0;
		
		// egsid -> subject name pairs
		private Map<Long, String> subs = null;
		
		// clsid -> class name pairs
		private Map<Long, String> clsinfo = null;
		
		public GradeDetail() {
			super();
		}
		
		public GradeDetail(String gn, String ssL, String clsL) {
			super();
			String [] paras = gn.split("-");
			if (paras != null && paras.length == 2) {
				this.gradeName = paras[0];
				this.clsNum = Integer.valueOf(paras[1]);
			}
			this.subs = new HashMap<Long, String>();
			this.clsinfo = new HashMap<Long, String>();
			//assemble egs, sub information
			assemble(ssL, this.subs);
			//assemble class information
			assemble(clsL, this.clsinfo);
		}

		public String getGradeName() {
			return gradeName;
		}
		
		public int getClsNum() {
			return clsNum;
		}

		public void setClsNum(int clsNum) {
			this.clsNum = clsNum;
		}

		public void setGradeName(String gradeName) {
			this.gradeName = gradeName;
		}
		
		public Map<Long, String> getSubs() {
			return subs;
		}
		
		public Map<Long, String> getClsinfo() {
			return clsinfo;
		}

		public void setClsinfo(Map<Long, String> clsinfo) {
			this.clsinfo = clsinfo;
		}

		public void setSubs(Map<Long, String> subs) {
			this.subs = subs;
		}
		
		/**
		 * @param ssl
		 * format as below,
		 * 1-语文@2-数学@3-英语@4-物理@5-生物@6-化学@7-政治@8-历史@9-地理@10-数学(文)
		 */
		private void assemble(String ssl, Map<Long, String> dm) {
			String []datas = ssl.split("@");
			if (datas != null && datas.length > 0) {
				for (String sub : datas) {
					String [] paras = sub.split("-");
					if (paras != null && paras.length == 2) {
						dm.put(Long.valueOf(paras[0]), paras[1]);
					}
				}
			}
		}
		
	}
	
	private String schoolName = null;
	
	@JsonProperty("GradeDetails")
	private List<GradeDetail> grDetails = null;

	public ExamSchoolAnaly() {
		super();
	}

	
	/**
	 * @param schoolName
	 * @param grDetails -- include information about egs and class information
	 */
	public ExamSchoolAnaly(String schoolName, String grDetails) {
		super();
		this.schoolName = schoolName;
		assembleGrSub(grDetails);
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public List<GradeDetail> getGrSubs() {
		return getGrDetails();
	}


	public List<GradeDetail> getGrDetails() {
		return grDetails;
	}

	public void setGrSubs(List<GradeDetail> grSubs) {
		setGrDetails(grSubs);
	}


	public void setGrDetails(List<GradeDetail> grSubs) {
		this.grDetails = grSubs;
	}

	/**
	 * @param gsL
	 * format similar as below:
	 * 高二-7:7-高二（1）班@8-高二（2）班@9-高二（3）班@10-高二（4）班@11-高二（5）班@12-高二（6）班@13-高二（7）班:
	 * 1-语文@2-数学@3-英语@4-物理@5-生物@6-化学@7-政治@8-历史@9-地理@10-数学(文)
	 * @return
	 */
	private void assembleGrSub(String gsL) {
		String [] grSubL = gsL.split("#");
		for (String grsub : grSubL) {
			String [] datas = grsub.split(":");
			if (datas != null && datas.length == 3 && datas[0] != null && !datas[0].isEmpty() && 
					datas[1] != null && !datas[1].isEmpty() && datas[2] != null && !datas[2].isEmpty()) {
				GradeDetail gs = new GradeDetail(datas[0], datas[1], datas[2]);
				if (this.grDetails == null) {
					this.grDetails = new ArrayList<GradeDetail>();
				}
				this.grDetails.add(gs);
			}
		}
	}
	
	@JsonIgnore
	public boolean isValid() {
		if ((this.schoolName == null || this.schoolName.isEmpty()) || 
				(this.grDetails == null || this.grDetails.isEmpty())) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "ExamSchoolAnaly [schoolName=" + schoolName + ", grDetails=" + grDetails + "]";
	}
	
}
