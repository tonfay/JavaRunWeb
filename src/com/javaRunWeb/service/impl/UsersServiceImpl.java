package com.javaRunWeb.service.impl;

import com.javaRunWeb.dao.UsersDao;
import com.javaRunWeb.dao.impl.UsersDaoImpl;
import com.javaRunWeb.entity.Users;
import com.javaRunWeb.service.UsersService;

public class UsersServiceImpl implements UsersService {
	UsersDao userDao = new UsersDaoImpl();
	@Override
	public Users getUsersByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	@Override
	public boolean saveUsers(Users users) {
		if(userDao.savaUsers(users)>0){
			return true;
		}else{
			return false;
		}
	}

}
