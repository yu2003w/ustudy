package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Role implements Serializable {

	private static final long serialVersionUID = -7055935870910488212L;
	
	private int id;
	private String name = null;
	private String type = null;
	
	public Role() {}
	
	public Role(int id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public Role(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.type = rs.getString("type");
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
