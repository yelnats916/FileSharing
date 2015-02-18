<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Example</title>
</head>
<body>

<form name="loginform" action="login" method="post">
  <p>Enter User Name: <input name="username" type="text"><br>
  Enter Password: <input name="password" type="password"><br>
  <input type="submit">
</form>

<form>
	<button type="submit" formaction="signup.jsp" formmethod="get">
		Sign Up
	</button>
</form>

</body>
</html>