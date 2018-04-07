package com.ustudy.exam.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Subject implements Serializable {

	private static final long serialVersionUID = -6746378698426998299L;
	
	private Long id;
	private String name = null;
	private String type = null;
	
	// child subs contains subject ids with format as below, 
	// {"ids":[7,8,9]}
	@JsonIgnore
	private String childsubs = null;
	
	private List<Integer> childSubIds = null;
	
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

	public String getChildsubs() {
		return childsubs;
	}

	public void setChildsubs(String childsubs) {
		this.childsubs = childsubs;
		if (this.childsubs != null && this.childsubs.length() > 0) {
			try {
				JsonReader reader = Json.createReader(new StringReader(this.childsubs));
				JsonObject jObj = reader.readObject();
				reader.close();
				if (jObj.containsKey("ids")) {
					JsonArray jArr = jObj.getJsonArray("ids");
					int len = jArr.size();
					for (int i = 0; i < len; i++) {
						if (this.childSubIds == null) {
							this.childSubIds = new ArrayList<Integer>();
						}
						this.childSubIds.add(jArr.getInt(i));
					}
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public List<Integer> getChildSubIds() {
		return childSubIds;
	}

	public void setChildSubIds(List<Integer> childSubIds) {
		this.childSubIds = childSubIds;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", type=" + type + ", childsubs=" + childsubs + ", childSubIds="
				+ childSubIds + "]";
	}
	
}
