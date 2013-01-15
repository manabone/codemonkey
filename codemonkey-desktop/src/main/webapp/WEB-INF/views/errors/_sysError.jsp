<%@page import="com.codemonkey.error.SysError"%>
<%
	SysError ie = (SysError) request.getAttribute("exception");
	String errorMsg = ie.json().toString();
	
	System.out.print(exPageUrl);
	
	response.write(errorMsg);
%>