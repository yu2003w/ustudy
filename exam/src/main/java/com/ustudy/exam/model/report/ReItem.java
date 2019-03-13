package com.ustudy.exam.model.report;

import java.io.Serializable;

/**
 * @author jared
 * 
 * Basic Item for report
 *
 */
public class ReItem implements Serializable, Comparable<ReItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5482521387894041984L;
	
	private String quesno = null;
	private String value = null;
	
	public ReItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReItem(String quesno, String value) {
		super();
		this.quesno = quesno;
		this.value = value;
	}

	public String getQuesno() {
		return quesno;
	}

	public String getValue() {
		return value;
	}

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public int compareTo(ReItem re) {
		if (Float.valueOf(this.quesno) > Float.valueOf(re.getQuesno())) {
            return 1;
        } else if (Float.valueOf(this.quesno) < Float.valueOf(re.getQuesno())) {
            return -1;
        } else {
            return 0;
        }
	}
	
	@Override
	public String toString() {
		return "ReItem [quesno=" + quesno + ", value=" + value + "]";
	}
	
}
