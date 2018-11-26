package com.bcit.comp4613.lab03.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

public class EmployeesLazy extends LazyDataModel<Employee>{

	private static final long serialVersionUID = 6428158862180809328L;
	private static List<Employee>employees;
	
	static
	{
		employees = new Db().getAllEmployees("SELECT * FROM employees");
	}
	
	
	@Override
	public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		// last page
		if (first + pageSize > employees.size()) {
			employees = new ArrayList<Employee>(employees.subList(first, employees.size()));
		} // if you click on next button and you're not on the last page
		else if (first != 0 && pageSize < employees.size()) {
			employees = new ArrayList<Employee>(employees.subList(first, first + pageSize));
		}

		else { // first page
			employees = new ArrayList<Employee>(employees.subList(first, pageSize));
		}

		// musicians = Loader.getMusicions();
		// set the total of musicians
		if (getRowCount() <= 0) {
			setRowCount(employees.size());
		}
		setPageSize(pageSize);
		return employees;
	}

	@Override
	public List<Employee> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {

		System.out.println("List clalled");
		employees = employees.subList(first, pageSize);

		if (getRowCount() <= 0) {
			setRowCount(employees.size());
		}
		setPageSize(pageSize);
		return employees;
	}

	@Override
	public Object getRowKey(Employee employee) {
		return employee.getID();
	}

	@Override
	public Employee getRowData(String employeeId) {
		//Integer id = Integer.valueOf(employeeId);
		for (Employee employee : employees) {
			if (employeeId.equals(employee.getID())) {
				return employee;
			}
		}
		return null;
	}

}
