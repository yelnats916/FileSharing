<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<link href="signin.css" rel="stylesheet">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<title>Signup</title>
</head>
<body>
<div class="container">
<form class="form-signin" name="loginform" action="login" method="post">
   <h2 class="form-signin-heading"> Create Account</h2>
   <label for="inputUser" class="sr-only">Username</label>
	<input id="inputUser" name="username" type="text" maxlength="15" class="form-control" placeholder="Username" required autofocus>
	<label for="inputPass" class="sr-only">Password</label>
   <input id="inputPass" name="password" type="password" maxlength="15" class="form-control" placeholder="Password" required>
   <label for="reenterPass" class="sr-only">Reenter </label>
	<input id="reenterPass" name="passwordCopy" type="password" maxlength="15" class="form-control" placeholder="Reenter Password" required>

<div class="somespace">
  <button type="submit" formmethod="post" formaction="signup" class="btn btn-lg btn-flat btn-block">
	Sign Up
  </button>
</div>
<a href="https://ec2-54-153-35-253.us-west-1.compute.amazonaws.com:8443/login.jsp">Back To Login</a>

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
