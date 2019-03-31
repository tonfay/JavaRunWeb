package com.javaRunWeb.dao.impl;

import java.util.List;

import com.javaRunWeb.dao.BaseDao;
import com.javaRunWeb.dao.UsersDao;
import com.javaRunWeb.entity.Users;


public class UsersDaoImpl extends BaseDao implements UsersDao {

	@Override
	public Users getByUserName(String userName) {
		Users users = null;
		try {
			StringBuilder sql = new StringBuilder(
					"select id,userName,userPassword from users where userName =?");
			Object[] params = { userName };
			rs = executeQuery(sql.toString(), params);
			while (rs.next()) {
				users=new Users();
				users.setId(rs.getInt("id"));
				users.setUserName(rs.getString("userName"));
				users.setUserPassword(rs.getString("userPassword"));
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return users;
	}

	@Override
	public int savaUsers(Users users) {
		int result = -1;
		try {
			if (openDatabase()) {
				String sql = "insert into users(id,userName,userPassword)values(?,?,?)";
				Object[] params = {users.getId(),users.getUserName(),users.getUserPassword() };
				result = executeUpdate(sql, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource();
		}
		return result;
	}

}
