package com.javaRunWeb.service;

import com.javaRunWeb.entity.Users;

public interface UsersService {
	Users getUsersByUserName(String userName);
	boolean saveUsers(Users users);
}
