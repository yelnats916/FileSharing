<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Example</title>
</head>
<body>

<form name="loginform" action="login" method="post">
	<p>Enter New User Name: <input name="username" type="text"><br>
	Enter New Password: <input name="password" type="password"><br>
	Reenter New Password: <input name="passwordCopy" type="password"><br>
	<input type="submit">

<button type="submit" formmethod="post" formaction="signup">
	Sign Up
</button>
</form>

<c:if test="${not empty message}">
    <h1>${message}</h1>
</c:if>

</body>
</html>
