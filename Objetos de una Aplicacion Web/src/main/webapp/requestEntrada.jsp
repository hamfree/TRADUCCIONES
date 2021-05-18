<%-- Documento: requestEntrada.jsp  --%>
<%@page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%
	request.setAttribute("nombre", "Juan Francisco Ruiz Fernández");
	request.setAttribute("edad", new Integer(47));
	request.setAttribute("fechaActual", new Date());
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Objeto request</title>
<style type="text/css">
TD {
	border: 1px inset;
	padding: 2px;
}
</style>
</head>
<body style='font-family: monospace;'>
	<h1 style="text-align: center">
		Objeto implicito <b>request</b>
	</h1>
	<p>En esta página se guardan mediante un scriplet tres atributos en
		el objeto request : nombre, edad y fecha, con los valores 'Juan
		Francisco Ruiz Fernández', 35 y la fecha actual respectivamente. En la
		página siguiente, pulsando el enlace 'Recuperar valores' otra página
		JSP recuperará estos valores del objeto request (junto con otros) y
		los mostrará en pantalla.</p>
	<p>El código que almacena los valores en la sesión es el siguiente:
	</p>
	<pre>
	    request.setAttribute("nombre", "Juan Francisco Ruiz Fernández");
	    request.setAttribute("edad", new Integer(47));
	    request.setAttribute("fechaActual", new Date());
    </pre>
	<%
		request.getRequestDispatcher("requestSalida.jsp").forward(request, response);
	%>
	<p style="text-align: center">
		<a href="<%=request.getContextPath()%>/">Volver al inicio</a>
	</p>
</body>
</html>