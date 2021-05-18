<%-- Documento: sesionEntrada.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Objeto session</title>
<style type="text/css">
TD {
    border: 1px inset;
    padding: 2px;
}
</style>
</head>
<body style="font-family: monospace;">
	<h1 style="text-align: center">Objeto session</h1>
	<%
		session.setAttribute("edad", 35);
		session.setAttribute("fecha", new Date());
	%>
	<p>En esta página se guardan mediante un scriplet dos atributos en
		el objeto session : edad y fecha, con los valores 35 y la fecha actual
		respectivamente. En la página siguiente, pulsando el enlace 'Recuperar
		valores' otra página JSP recuperará estos valores del objeto session y
		los mostrará en pantalla.</p>
	<p>El código que almacena los valores en la sesión es el siguiente:
	</p>
	<pre>
            session.setAttribute("edad", 35);
            session.setAttribute("fecha",new Date());
        </pre>
	<p style="text-align: center">
		<a href="sesionSalida.jsp">Recuperar valores</a>
	</p>
	<p style="text-align: center">
		<a href="<%=request.getContextPath()%>/">Volver al inicio</a>
	</p>
</body>
</html>