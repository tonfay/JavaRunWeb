package com.javaRunWeb.dao;
import com.javaRunWeb.util.ConfigManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaseDao {
	protected Connection conn=null;
	protected PreparedStatement pst=null;
	protected ResultSet rs=null;
	//jdbc操作，第一件事，打开数据库连接
	public boolean openDatabase(){
		boolean result=false;
		try {
			String className=ConfigManager.getInstance().getString("driverclassname");//"com.mysql.jdbc.Driver";//COnfigManager.getInstance().getString(driverclassname) url user password
			String url=ConfigManager.getInstance().getString("url");//"jdbc:mysql://localhost:3306/esms?useUnicode=true&characterEncoding=utf-8";//
			String user=ConfigManager.getInstance().getString("user");
			String password=ConfigManager.getInstance().getString("password");
			Class.forName(className);
			conn= DriverManager.getConnection(url, user, password);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//第二件事，执行查询方法
	public ResultSet executeQuery(String sql,Object[] params){
		try {
			pst=conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i+1, params[i]);
			}
			//pst.setBlob(arg0, arg1, arg2);
			rs=pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	//第三件事，执行增删改方法
	public int executeUpdate(String sql,Object[] params){
		try {
			pst=conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i+1, params[i]);
			}
			return pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	//第四件事，关闭资源
	public void closeResource(){
		try {
			if(rs!=null) rs.close();
			if(pst!=null) pst.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}







