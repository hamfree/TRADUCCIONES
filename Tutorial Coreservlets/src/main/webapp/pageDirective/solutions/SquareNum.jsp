<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Squaring Big Numbers</title>
    <link rel="stylesheet" href="../../css/JSP-Styles.css" TYPE="text/css">
  </head>
  <body>
    <h1>Squaring Big Numbers</h1>
      <%@ page import="java.math.*" %>
      <% String baseNum = request.getParameter("baseNum");
        BigInteger orig;
        try {
          orig = new BigInteger(baseNum);
        } catch (Exception e) {
          orig = new BigInteger("1234567890");
        }
        BigInteger result = orig.pow(2);
      %>
    <h3>The square of <%= orig%> is <%= result%></h3>
  </body>
</html>