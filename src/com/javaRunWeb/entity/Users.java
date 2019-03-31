package com.javaRunWeb.entity;

import java.util.List;


public class Users {
	private int id;
	private String userName;
	private String userPassword;
	private List<Code> code;
	public int getId() {
		return id;
	}
	public List<Code> getCode() {
		return code;
	}
	public void setCode(List<Code> code) {
		this.code = code;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
