package com.ustudy;

import java.io.Serializable;

/**
 * @author jared
 * 
 * General Item only includes id and name property
 *
 */
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7968676806511710018L;

	private int id = -1;
	private String name = null;
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + "]";
	}
	
}
