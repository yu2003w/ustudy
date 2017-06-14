package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Permission implements Serializable {

	private static final long serialVersionUID = -7055935870910488212L;
	
	private int id;
	private String name = null;
	private String url = null;
	
	public Permission() {}
	
	public Permission(int id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}
	
	public Permission(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.url = rs.getString("url");
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
