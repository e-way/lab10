package com.bcit.comp4613.lab03.bean;


import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;

public interface IDataService {
	Result getAll(String query);
	List<Employee> getAllEmployees(String query);
}
