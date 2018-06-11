package com.ustudy.exam.model.report;

import java.io.Serializable;

/**
 * @author jared
 * 
 * Basic Item for report
 *
 */
public class ReItem implements Serializable {

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

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ReItem [quesno=" + quesno + ", value=" + value + "]";
	}
	
}
