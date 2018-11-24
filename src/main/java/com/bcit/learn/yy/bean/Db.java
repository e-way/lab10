package com.bcit.learn.yy.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class Db implements IDataService {
	private Connection conn;

	public Connection getConnection() throws SQLException {
		return getConnection("javastudent", "compjava");
	}

	public Connection getConnection(String user, String pass) throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://Beangrinder.bcit.ca", user, pass);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("FAILED TO CONNECT TO DATABASE");
		}

		return conn;
	}

	public Result getAll(String query) {
		try {
			conn = getConnection("javastudent", "compjava");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);

			return ResultSupport.toResult(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void close() throws SQLException {
		if (conn == null) {
			return;
		}
		conn.close();
		conn = null;
	}
}
