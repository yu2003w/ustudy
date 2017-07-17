package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Role implements Serializable {

	private static final long serialVersionUID = -7055935870910488212L;
	
	@JsonIgnore
	private String id = null;
	
	@JsonProperty("roleName")
	private String role_name = null;
	
	@JsonIgnore
	private String user_name = null;
	
	private List<String> perms = null;
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String id, String role_name, String user_name) {
		super();
		this.id = id;
		this.role_name = role_name;
		this.user_name = user_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public List<String> getPerms() {
		return perms;
	}

	public void setPerms(List<String> perms) {
		this.perms = perms;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role_name=" + role_name + ", user_name=" +
	        user_name + ", perms=" + perms + "]";
	}
	
	
}
