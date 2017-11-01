package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author jared
 * 
 * Student papers based on requested question block
 *
 */
public class QuestionPaper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2496317976515585282L;

	private int paperSeq = -1;
	
	@JsonProperty("papers")
	private List<BlockAnswer> blocks = null;

	public QuestionPaper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionPaper(int paperSeq, List<BlockAnswer> blocks) {
		super();
		this.paperSeq = paperSeq;
		this.blocks = blocks;
	}

	public int getPaperSeq() {
		return paperSeq;
	}

	public void setPaperSeq(int paperSeq) {
		this.paperSeq = paperSeq;
	}

	public List<BlockAnswer> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<BlockAnswer> blocks) {
		this.blocks = blocks;
	}

	@Override
	public String toString() {
		return "QuestionPaper [paperSeq=" + paperSeq + ", blocks=" + blocks + "]";
	}
	
}
