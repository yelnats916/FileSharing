<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Signup</title>
</head>
<body>

   <h2> hey </h2>
<form>
   <button type="submit" formaction="s3" formmethod="get">
      mybutton
   </button>
   <button type="submit" formaction="download" formmethod="get">
      download
   </button>
</form>

<c:if test="${not empty message}">
   <h1>${message}</h1>
</c:if>

</body>
</html>

