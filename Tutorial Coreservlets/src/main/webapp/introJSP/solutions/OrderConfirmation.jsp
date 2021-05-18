<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirmación de Orden</title>
<link rel=stylesheet href="JSP-Styles.css" type="text/css">
</head>
<body>
	<h2>Confirmación de Orden</h2>
	¡Gracias por pedir
	<i><%=request.getParameter("title")%></i>!
</body>
</html>