<%-- Documento: sesionSalida.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Objeto session - Recuperando valores del objeto session</title>
<style type="text/css">
TD {
    border: 1px inset;
    padding: 2px;
}
</style>
</head>
<body style="font-family: monospace;">
	<h1 style="text-align: center">Recuperando valores de la session</h1>
	<table style="width: 70%; margin: 0 auto; border: 1px inset;">
		<colgroup>
			<col width="10%">
			<col width="50%">
		</colgroup>
		<tbody>
			<%
				Enumeration<String> atributos = session.getAttributeNames();
				while (atributos.hasMoreElements()) {
					String nombreAtributo = (String) atributos.nextElement();
			%>
			<tr>
				<td><%=nombreAtributo%></td>
				<td><%=session.getAttribute(nombreAtributo)%></td>
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