package com.bcit.learn.yy.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class TableData implements Serializable {

	private static final long serialVersionUID = -5461466796865924645L;
	private Connection conn;
	private Result all;
	private String query = "SELECT * FROM Customers";
	private String[] columnNames;
	private List<ColumnModel> columns;
	private SortedMap<?, ?>[] rows;
	private Map<String, String> columnMap = new LinkedHashMap<>();
	private List<SortedMap<?, ?>> rowArray;
	private SortedMap<?, ?> mapper = new TreeMap();

	public SortedMap<?, ?> getMapper() {
		return mapper;
	}

	public void setMapper(SortedMap<?, ?> mapper) {
		this.mapper = mapper;
	}

	

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public TableData() throws SQLException, NamingException {
		columns = new ArrayList<ColumnModel>();

		Result all = getAll();
		rows = all.getRows();
		List<String>stringColumns = Arrays.asList(all.getColumnNames());
		for(String col : stringColumns)
		{
			columns.add(new ColumnModel(col.toUpperCase(), col));
		}
		rowArray = Arrays.asList(rows);
		
//		for (int i=0;i<rowArray.size();i++)
//		{
//			SortedMap<?, ?> map = rowArray.get(i);
//			System.out.println("");
//		}
//		for (int i = 0; i < rows.length; i++) {
//			SortedMap<String, Object> mp = (SortedMap<String, Object>) rows[i];
//			for (String k : mp.keySet()) {
//				columns.add(new ColumnModel(k, mp.get(k)));
//			}
//		}
       System.out.println("");
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public SortedMap[] getRows() throws SQLException, NamingException {
		Result all = getAll();
		return all.getRows();
	}

	public void setRows(SortedMap[] rows) {
		this.rows = rows;
	}

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

	public Result getAll() throws SQLException, NamingException {
		try {
			if (this.all != null) {
				return this.all;
			}
			conn = getConnection("javastudent", "compjava");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(this.query);
			all = ResultSupport.toResult(result);
			this.columnNames = all.getColumnNames();
			this.rows = all.getRows();

			return all;
		} finally {
			close();
		}
	}

	public Result getAll(String query) throws SQLException, NamingException {
		try {
			conn = getConnection("javastudent", "compjava");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			all = ResultSupport.toResult(result);

			this.columnNames = all.getColumnNames();
			this.rows = all.getRows();
			return all;
		} finally {
			close();
		}
	}

	public String queryAction(String value) throws SQLException, NamingException {
		this.query = value;
		this.all = getAll(value);
		return "/WEB-INF/jsp/output.xhtml";
	}

	public void close() throws SQLException {
		if (conn == null) {
			return;
		}
		conn.close();
		conn = null;
	}

	public Map<String, String> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}

	public List<SortedMap<?, ?>> getRowArray() {
		return rowArray;
	}

	public void setRowArray(List<SortedMap<?, ?>> rowArray) {
		this.rowArray = rowArray;
	}

	static public class ColumnModel implements Serializable {

		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}

}