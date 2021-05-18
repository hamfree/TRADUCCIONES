<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>News</title>
    <link rel="stylesheet" href="../../css/JSP-Styles.css" type="text/css">
  </head>
  <body>
    <% String newsPage;
      if (Math.random() < 0.5)
        newsPage = "/WEB-INF/snippets/good-news.jsp";
      else
        newsPage = "/WEB-INF/snippets/bad-news.jsp";
    %>
    <jsp:include page="<%= newsPage%>"/>



    <br/><br/><br/><br/><br/><br/>
    <font size="-3">All code from the
    <a href="http://courses.coreservlets.com/Course-Materials/">
      coreservlets.com J2EE tutorials (servlets, JSP, Struts, JSF 1, JSF 2, PrimeFaces, Ajax [with jQuery], GWT 2, Spring, 
      Hibernate, JPA, SOAP-based and RESTful Web Services, Hadoop, &amp; Java 7 programming)</a>. There are also live 
      instructor-led
    <a href="http://courses.coreservlets.com/">training courses on
      the same J2EE topics (servlets, JSP, Struts, JSF 1, JSF 2, PrimeFaces, Ajax [with jQuery], GWT 0, Spring, Hibernate, JPA,
      SOAP-based and RESTful Web Services, Hadoop, &amp; Java 7 programming)</a>.
    </font>
  </body>
</html>