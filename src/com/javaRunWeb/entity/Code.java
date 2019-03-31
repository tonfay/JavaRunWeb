package com.javaRunWeb.entity;

import javax.xml.crypto.Data;

public class Code {
	private int codeId;
	private String codeName;
	private String code;
	private Data saveDate;
	private int userId;
	
	private Users users;
	
	public int getCodeId() {
		return codeId;
	}
	public void setCodeId(int codeId) {
		this.codeId = codeId;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Data getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(Data saveDate) {
		this.saveDate = saveDate;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
