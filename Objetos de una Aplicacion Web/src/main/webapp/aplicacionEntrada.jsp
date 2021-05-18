<%--Documento: entradaAplicacion.jsp  --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Application</title>
<style type="text/css">
TD {
	border: 1px inset;
	padding: 2px;
}
</style>
</head>
<body style="font-family: monospace;">
	<h1 style="text-align: center;">Objeto Application</h1>
	<%
		application.setAttribute("nombre", "Josué");
		application.setAttribute("edad", 15);
		application.setAttribute("fecha", new Date());
	%>
	<p>En este JSP se guardan tres atributos en el objeto application:
	</p>
	<ol>
		<li>nombre, con el valor 'Josué'</li>
		<li>edad, con el valor 15</li>
		<li>fecha, con la fecha y hora actual</li>
	</ol>
	<p>En en el JSP al que se accede pulsando el texto 'Recuperar
		valores' se obtendrán estos valores del objeto application.
	<div style="text-align: center;">
		<p>
			<a href="aplicacionSalida.jsp">Recuperar valores</a>
		</p>
		<p>
			<a href="otraInformacion.jsp">Otra informacion de la aplicacion</a>
		</p>
		<p>
			<a href="<%=request.getContextPath()%>/">Volver al inicio</a>
		</p>
	</div>
</body>
</html>