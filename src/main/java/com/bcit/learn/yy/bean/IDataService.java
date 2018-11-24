package com.bcit.learn.yy.bean;

import javax.servlet.jsp.jstl.sql.Result;

public interface IDataService {
	Result getAll(String query);
}
