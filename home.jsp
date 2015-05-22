<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>

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
<title>Home</title>
<link rel="stylesheet" type="text/css" media="all" href="styles.css" />
</head>
<body>

   <h1> home page </h1>


<c:forEach items="${fileKeys}" var="fileKey">
  <form>
   <c:out value="${fileKey}"/>
   <button type="submit" formaction="s3" formmethod="get">
      play
   </button>
   <button type="submit" formaction="download" formmethod="get">
      download
   </button>
   <input type="hidden" name="user" value=${cookie['FileShareSite'].value}>
   <input type="hidden" name="fileKey" value="${fileKey}">
  </form>
</c:forEach>

<!--
<div 
   <form action="upload" method="post" enctype="multipart/form-data">
      <input type="file" name="fileName" />
      <input type="submit" value="Upload"/>
      <input type="hidden" name="user" value=${cookie['FileShareSite'].value}> 
   </form>
</div>
-->

<form action="upload" method="post" enctype="multipart/form-data">
<fieldset>
<legend>File Upload</legend>      

      <input type="file" id="fileselect" name="fileName" />
      <input type="hidden" id="user" name="user" value=${cookie['FileShareSite'].value}>
      <!-- <input type="file" id="fileselect" name="fileName"/> -->
      <div id="filedrag">or drop files here</div>

<div id="submitbutton">
   <button type="submit"> Upload </button>
</div>

</fieldset>
</form>

<div id="messages">
<p>Status Messages</p>
</div>

<form>
   <button type="submit" formaction="addFriend" formmethod="post">
      Add Friend
   </button>
   <input type="hidden" name="user" value=${cookie['FileShareSite'].value}>
   <input name="friend" type="text" maxlength="15"><br>
</form>

<c:if test="${not empty message}">
   <h1>${message}</h1>
</c:if>

<c:forEach items="${friendFileKeys}" var="friendFileKey">
  <form>
   <c:set var="full" value="${friendFileKey}"/>
   <c:set var="splitKey" value="${fn:split(full, '=')}" />
   <c:set var="friend" value="${splitKey[0]}" />
   <c:set var="key" value="${splitKey[1]}" />
   <c:out value="${friend} => ${key}"/>
   <button type="submit" formaction="s3" formmethod="get">
      play
   </button>
   <button type="submit" formaction="download" formmethod="get">
      download
   </button>
   <input type="hidden" name="user" value="${friend}">
   <input type="hidden" name="fileKey" value="${key}">
  </form>
</c:forEach>

<script src="filedrag.js"></script>

</body>
</html>

