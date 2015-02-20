<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
   Cookie[] cookies = null;
   // Get an array of Cookies associated with this domain
   cookies = request.getCookies();
   if( cookies != null ){
      for (Cookie cookie: cookies) {
         if (cookie.getName().equals("FileShareSite")) {
            out.print("Welcome back " + cookie.getValue() + "!");
         }
      }
  }else{
      out.println("<h2>No cookies founds</h2>");
  }

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Example</title>
</head>
<body>

<form name="loginform" action="login" method="post">
  <p>Enter User Name: <input name="username" type="text" maxlength="15"><br>
  Enter Password: <input name="password" type="password" maxlength="15"><br>
  <button type="submit"> Sign in </button>
</form>

<form>
	<button type="submit" formaction="signup.jsp" formmethod="get">
		Create Account
	</button>
</form>

<c:if test="${not empty message}">
    <h1>${message}</h1>
</c:if>

</body>
</html>
