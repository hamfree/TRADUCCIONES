<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Random List 2</title>
  <link rel=stylesheet href="../../css/JSP-Styles.css" TYPE="text/css">
</head>
<body>
  <h1>Random List 2</H1>
    <%-- random-int.jsp modified on 10/01/05 --%>
    <%@ include file="/WEB-INF/snippets/random-int.jspf" %>
  <ol>
    <li><%= randomInt(100)%>
    <li><%= randomInt(100)%>
    <li><%= randomInt(100)%>
    <li><%= randomInt(100)%>
  </ol>
</body>
</html>