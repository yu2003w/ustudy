package com.ustudy.exam.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.TreeMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonReader;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Subject implements Serializable {

	private static final long serialVersionUID = -6746378698426998299L;
	
	private Long id;
	private String name = null;
	private String type = null;
	
	// child subs contains subject ids with format as below, 
	// [7,8,9]
	@JsonIgnore
	private String child = null;
	
	private TreeMap<Integer, String> childSubs = null;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public TreeMap<Integer, String> getChildSubs() {
		return childSubs;
	}

	public void setChildSubs(TreeMap<Integer, String> childSubs) {
		this.childSubs = childSubs;
	}
	
	public void populateChild(TreeMap<Long, String> subM) {
		if (this.child != null && this.child.length() > 0) {
			
			JsonReader reader = Json.createReader(new StringReader(this.child));
			JsonArray ids = reader.readArray();
			reader.close();
			if (this.childSubs == null) {
				childSubs = new TreeMap<Integer, String>();
			}

			for (int i = 0; i < ids.size(); i++) {
				JsonNumber sid = ids.getJsonNumber(i);
				childSubs.put(Integer.valueOf(sid.intValue()), subM.get(sid.longValue()));
			}
		}
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", type=" + type + ", child=" + child + ", childSubs="
				+ childSubs + "]";
	}
	
}
