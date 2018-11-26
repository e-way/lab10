package com.bcit.comp4613.lab03.bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public List<Employee> getAllEmployees(String query) {
		try {
			conn = getConnection("javastudent", "compjava");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);

			List<Employee> emps = new ArrayList<Employee>();
			if (result != null) {
				while (result.next()) {
					String id = result.getString("ID");
					String firstName = result.getString("firstName");
					String lastName = result.getString("lastName");
					Date dob = result.getDate("DOB");
					emps.add(new Employee(id, firstName, lastName, dob));
				}
				return emps;
			}
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
