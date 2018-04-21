package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamSchoolAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4036132654234616877L;

	class GradeSub {
		
		private String gradeName = null;
		// egsid -> subject name pairs
		private Map<Long, String> subs = null;
		
		public GradeSub() {
			super();
		}
		
		public GradeSub(String gradeName, String ssL) {
			super();
			this.gradeName = gradeName;
			assembleSub(ssL);
		}

		public String getGradeName() {
			return gradeName;
		}
		
		public void setGradeName(String gradeName) {
			this.gradeName = gradeName;
		}
		
		public Map<Long, String> getSubs() {
			return subs;
		}
		
		public void setSubs(Map<Long, String> subs) {
			this.subs = subs;
		}
		
		/**
		 * @param ssl
		 * format as below,
		 * 5-数学$6-英语$7-文综$8-理综$4-语文
		 */
		private void assembleSub(String ssl) {
			if (ssl != null && !ssl.isEmpty()) {
				String []datas = ssl.split("$");
				if (datas != null && datas.length > 0) {
					for (String sub : datas) {
						String [] paras = sub.split("-");
						if (paras != null && paras.length == 2) {
							if (this.subs == null) {
								this.subs = new HashMap<Long, String>();
							}
							this.subs.put(Long.valueOf(paras[0]), paras[1]);
						}
					}
				}
			}
		}
		
	}
	
	private String schoolName = null;
	
	private List<GradeSub> grSubs = null;

	public ExamSchoolAnaly() {
		super();
	}

	public ExamSchoolAnaly(String schoolName, String gsL) {
		super();
		this.schoolName = schoolName;
		assembleGrSub(gsL);
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public List<GradeSub> getGrSubs() {
		return grSubs;
	}

	public void setGrSubs(List<GradeSub> grSubs) {
		this.grSubs = grSubs;
	}

	/**
	 * @param gsL
	 * format as below:
	 * 九年级-5:5-数学$6-英语$7-文综$8-理综$4-语文
	 * @return
	 */
	private void assembleGrSub(String gsL) {
		String [] grSubL = gsL.split("#");
		for (String grsub : grSubL) {
			String [] datas = grsub.split(":");
			if (datas != null && datas.length == 2) {
				GradeSub gs = new GradeSub(datas[0], datas[1]);
				if (this.grSubs != null) {
					this.grSubs = new ArrayList<GradeSub>();
				}
				this.grSubs.add(gs);
			}
		}
	}
	
	public boolean isValid() {
		if ((this.schoolName == null || !this.schoolName.isEmpty()) || 
				(this.grSubs == null || this.grSubs.isEmpty())) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "ExamSchoolAnaly [schoolName=" + schoolName + ", grSubs=" + grSubs + "]";
	}
	
}
