<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<%
   Cookie[] cookies = null;
   // Get an array of Cookies associated with this domain
   cookies = request.getCookies();
   if( cookies != null ){
      for (Cookie cookie: cookies) {
         if (cookie.getName().equals("FileShareSite")) {
            response.sendRedirect("home.jsp");
         }
      }
  }else{
      out.println("<h2>No cookies founds</h2>");
  }

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<link href="signin.css" rel="stylesheet">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<title>Login Example</title>
</head>
<body>
<div class="container">
<form class="form-signin" name="loginform" action="login" method="post">
  <h2 class="form-signin-heading"> Please sign in</h2>
  <label for="inputUser" class="sr-only">Username</label>
  <input id="inputUser" name="username" type="text" maxlength="15" class="form-control" placeholder="Username" required autofocus>
  <label for="inputPass" class="sr-only">Password</label>
  <input id="inputPass" name="password" type="password" maxlength="15" class="form-control" placeholder="Password" required>
  <div class="somespace">
    <button class="btn btn-flat btn-lg btn-block" type="submit"> Sign in </button>
  </div>
  <div class="morespace">
    <a href="https://ec2-54-153-35-253.us-west-1.compute.amazonaws.com:8443/signup.jsp">Create Account</a>
  </div>
   <c:if test="${not empty message}">
  <div class="smallerheight alert alert-danger">
    <h5>${message}</h5>
  </div>
</c:if>

</form>

</div>
   <!-- Latest compiled and minified JavaScript -->
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>
