package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

public class Teacher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6091110002226750766L;
	
	private Long id;
	private String uid = null;
	private String uname = null;
	private String orgtype = null;
	private String orgid = null;
	private String orgname = null;
	private String role = null;
	private List<String> roles = null;
	
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Teacher(String uid, String uname, String orgtype, String orgid) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.orgtype = orgtype;
		this.orgid = orgid;
	}

	public Teacher(String uid, String uname) {
		super();
		this.uid = uid;
		this.uname = uname;
	}

	public Teacher(String uid, String uname, String orgtype, String orgid, String orgname, List<String> roles) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.orgtype = orgtype;
		this.orgid = orgid;
		this.orgname = orgname;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Teacher [uid=" + uid + ", uname=" + uname + ", orgtype=" + orgtype + ", orgid=" + orgid + ", orgname="
				+ orgname + ", roles=" + roles + "]";
	}

}
