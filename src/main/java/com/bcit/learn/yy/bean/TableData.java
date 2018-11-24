package com.bcit.learn.yy.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;
import javax.servlet.jsp.jstl.sql.Result;

@ViewScoped
public class TableData implements Serializable {

	private static final long serialVersionUID = -5461466796865924645L;

	private IDataService dataService;
	private Result all;
	private String query = "SELECT * FROM Employees";
	private List<ColumnModel> columns;
	private List<Map<String, String>> rowsArrMap;

	public TableData() throws SQLException, NamingException {
		Result all = getAll();

		columns = getFormatedColumnNames(all.getColumnNames());
		rowsArrMap = getDataForTable(all.getRows());
	}

	private List<ColumnModel> getFormatedColumnNames(String[] columnNames) {
		List<ColumnModel> model = new ArrayList<ColumnModel>();
		List<String> stringColumns = Arrays.asList(columnNames);
		for (String col : stringColumns) {
			model.add(new ColumnModel(col.toUpperCase(), col));
		}

		return model;
	}

	private List<Map<String, String>> getDataForTable(SortedMap[] maps) {
		List<Map<String, String>> arrMap = new ArrayList<Map<String, String>>();
		for (int i = 0; i < maps.length; i++) {
			Map<String, String> map = new HashMap<String, String>();

			Iterator it = maps[i].entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = (Entry) it.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();

				map.put(key, value);
			}
			arrMap.add(map);
		}
		return arrMap;
	}

	public List<Map<String, String>> getRowsArrMap() {
		return rowsArrMap;
	}

	public void setRowsArrMap(List<Map<String, String>> rowsArrMap) {
		this.rowsArrMap = rowsArrMap;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public Result getAll() throws SQLException, NamingException {
		if (dataService == null) {
			dataService = new Db();
		}
		return dataService.getAll(query);
	}

	public String queryAction(String value) throws SQLException, NamingException {
		if (dataService == null) {
			dataService = new Db();
		}
		this.all = dataService.getAll(value);

		columns = getFormatedColumnNames(all.getColumnNames());
		rowsArrMap = getDataForTable(all.getRows());

		return "/WEB-INF/jsp/output.xhtml";
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
