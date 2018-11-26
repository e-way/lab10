package com.bcit.comp4613.lab03.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import javax.faces.view.ViewScoped;

import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "Employee")
@ViewScoped
public class EmployeeMangedBean implements Serializable {

	private static final long serialVersionUID = 3228022410876086205L;
//	private LazyDataModel<Employee>employees = null;
//	private Employee employee;
//	
//	
//	public LazyDataModel<Employee>getAllEmployees(){
//		if (employees == null) {
//			employees = new EmployeesLazy();
//		}
//		return employees;
//	}
//	
//	public Employee getEmployee() {
//		if (employee == null) {
//			employee = new Employee();
//		}
//		return employee;
//	}
//	
//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}

	private static IDataService dataService = new Db();
	private static String query = "SELECT * FROM Employees";
	private static List<Employee> employees;

//	static {
//		employees = dataService.getAllEmployees(query);
//	}

	public EmployeeMangedBean() {
         employees = new Db().getAllEmployees(query);
	}

	public IDataService getEmployeeService() {
		return dataService;
	}

	public void setEmployeeService(IDataService employeeService) {
		this.dataService = employeeService;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Employee> getEmployees() {
		if (dataService == null) {
			dataService = new Db();
		}
		if (employees == null) {
			return dataService.getAllEmployees(query);
		}
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
