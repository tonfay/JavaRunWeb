package com.javaRunWeb.dao;

import java.util.List;

import com.javaRunWeb.entity.Users;

public interface UsersDao {
	Users getByUserName(String userName);
	int savaUsers(Users users);
	//List<Users> getUsersList();
}
