<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">

<%@page import="ca.bcit.comp4613.dao.DBBean"%>
<%@page import="java.util.Vector"%>

<h:head>
<title>Lab09 Select Query Results</title>
<link rel="stylesheet" href="mainstyle.css" type="text/css">
</h:head>

<h:body>

<div id="mainDoc">

<%@ page errorPage="WEB-INF/jsp/error.jsp"%>



<h2><a href="user.jsp">Query Again</a></h2><br>
<h2><a id="logout" title="Log out" href="logout">Log out</a></h2>
<table border="1">
	
	<TR>
	<%
		DBBean db = (DBBean) request.getSession().getAttribute("db");
		for ( String col: db.getColumnNames() )
		{
			out.println( "<th>" + col + "</th>" );
		}
		
		System.out.println (db.getResults());
		for ( Vector<String> row: db.getResults() )
		{
			out.println("<TR>");
			for (String data: row ){
				out.println( "<TD>" + data + "</TD>");
			}
			out.println("</TR>");
		}
		
	 %>
	 </TR>
  
</table>

<h:form>

</h:form>

</div>
</h:body>
</html>