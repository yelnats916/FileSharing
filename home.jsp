<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
   Cookie[] cookies = null;
   // Get an array of Cookies associated with this domain
   cookies = request.getCookies();
   boolean expired = true;
   if( cookies != null ){
      for (Cookie cookie: cookies) {
         if (cookie.getName().equals("FileShareSite")) {
            expired = false;
            out.println("<h2> Hello " + cookie.getValue() + "</h2>");
         }
      }
   }
   if (expired) {
      response.sendRedirect("login.jsp");
   }

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Signup</title>
</head>
<body>

   <h2> home page </h2>
<form>
   <button type="submit" formaction="s3" formmethod="get">
      mybutton
   </button>
   <button type="submit" formaction="download" formmethod="get">
      download
   </button>
</form>

<form action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="fileName" />
    <input type="submit" value="Upload"/>
    <input type="hidden" name="user" value=${cookie['FileShareSite'].value}> 
</form>

<c:if test="${not empty message}">
   <h1>${message}</h1>
</c:if>

</body>
</html>

