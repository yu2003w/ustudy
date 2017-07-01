package com.ustudy.dashboard.model;

import java.io.Serializable;

public class Permission implements Serializable {

	private static final long serialVersionUID = -7055935870910488212L;
	
	private int id;
	private String permissions = null;
	private String role_id = null;
	
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Permission(int id, String permissions, String role_id) {
		super();
		this.id = id;
		this.permissions = permissions;
		this.role_id = role_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", permissions=" + permissions + ", role_id=" + role_id + "]";
	}
	
}
