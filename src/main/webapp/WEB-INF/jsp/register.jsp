<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>REGISTER</title>
<%@ include file="taglib.jsp"%>
<link href="../css/register/style.css?t=<%=t%>" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="wrapper">
    <div class="content">
        <div class="formRegister">
        	<form:form method="POST" action="register" modelAttribute="form">
        		<ul>
		            <li>
		                <form:input path="username" class="inputClass" placeholder="Username" value="${form.username}"/>
		            </li>
		            <li>
		                <form:password path="password" class="inputClass" placeholder="Password" value="${form.password}"/>
		            </li>
		            <li>
		                <form:input path="submit" type="submit" value="Register" class="inputClass btnSubmit" />
		            </li>
		        </ul>
		        
	            <c:if test="${!empty errorMessage}">
                	<div class="errors">
                		<c:forEach items="${errorMessage.errorMessageList}" var="message">
	                		<c:out value="${message}"></c:out> <br />
	                	</c:forEach>
                	</div>
                </c:if>				
        	</form:form>
        </div>
    </div>
</div>
</body>
</html>