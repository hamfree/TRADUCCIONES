<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Random List 1</title>
    <link rel=stylesheet href="../../css/JSP-Styles.css" type="text/css">
  </head>
  <body>
    <h1>Random List 1</h1>
      <%-- random-int.jsp modified on 10/01/05 --%>
      <%@ include file="/WEB-INF/snippets/random-int.jspf" %>
    <ul>
      <li><%= randomInt(10)%>
      <li><%= randomInt(10)%>
      <li><%= randomInt(10)%>
    </ul>
  </body>
</html>