<%-- Documento: requestSalida.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Objeto request - Recuperando valores</title>
<style type="text/css">
TD {
	border: 1px inset;
	padding: 2px;
}
</style>
</head>
<body style='font-family: monospace;'>
	<h1 style="text-align: center">Objeto request - Recuperando
		valores</h1>
	<p>En la página 'requestEntrada.jsp' se guardaron mediante un
		scriplet tres atributos en el objeto request : nombre, edad y fecha,
		con los valores 'Juan Francisco Ruiz Fernández', 35 y la fecha actual
		respectivamente. En esta pagina, después de que la página anterior
		hiciera un "forward" se recuperan los valores de los atributos de la
		request. NOTA: Se hace un forward o redireccion porque la request sólo
		"dura" la vida de una página JSP por lo que si se navega a otra de
		forma normal se pierden la request y sus atributos. Con el "forward" o
		redirección no pasa eso.</p>
	<p>El código que almacena los valores en la sesión es el siguiente:
	</p>
	<pre>
        request.setAttribute("nombre", "Juan Francisco Ruiz Fernández");
        request.setAttribute("edad", new Integer(47));
        request.setAttribute("fechaActual", new Date());
    </pre>
	<table style="width: 70%; margin: 0 auto; border: 1px inset;">
		<colgroup>
			<col width="10%">
			<col width="50%">
		</colgroup>
		<tbody>
			<%
				String nombre = (String) request.getAttribute("nombre");
				Enumeration<String> enumeration = request.getAttributeNames();
				while (enumeration.hasMoreElements()) {
					String nombreAtributo = enumeration.nextElement();
			%>
			<tr>
				<td><%=nombreAtributo%></td>
				<td><%=request.getAttribute(nombreAtributo)%></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<p style="text-align: center">
		<a href="<%=request.getContextPath()%>/">Volver al inicio</a>
	</p>
</body>
</html>