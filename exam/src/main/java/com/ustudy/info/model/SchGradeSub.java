package com.ustudy.info.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ustudy.Item;

/**
 * @author jared
 * 
 * Object contains information about grade and related subjects defined in a school
 *
 */
public class SchGradeSub implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 701163576783979472L;

	private int id = -1;
	private String name = null;
	
	@JsonProperty("subjects")
	private List<Item> subs = null;

	public SchGradeSub() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SchGradeSub(int id, String name, List<Item> subs) {
		super();
		this.id = id;
		this.name = name;
		this.subs = subs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getSubs() {
		return subs;
	}

	public void setSubs(List<Item> subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "TeaGrade [id=" + id + ", name=" + name + ", subs=" + subs + "]";
	}
	
}
