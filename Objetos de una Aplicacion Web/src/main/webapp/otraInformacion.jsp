<%-- Documento: otraInformacion.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Otra información</title>
<style type="text/css">
TD {
	border: 1px solid;
	padding: 2px;
}
</style>
</head>
<body style='font-family: monospace;'>
	<h1 style="text-align: center;">Informacion del contenedor</h1>
	<div style="text-align: center;">
		<table style="width: 40%;margin:0 auto; border: 1px solid">
			<tr>
				<td>Contenedor</td>
				<td><%=application.getServerInfo()%></td>
			</tr>
			<tr>
				<td>Version mayor del API Servlet</td>
				<td><%=application.getMajorVersion()%></td>
			</tr>
			<tr>
				<td>Version menor del API Servlet</td>
				<td><%=application.getMinorVersion()%></td>
			</tr>
			<tr>
				<%!JspFactory fac = JspFactory.getDefaultFactory();%>
				<td>Version del JSP</td>
				<td><%=fac.getEngineInfo().getSpecificationVersion()%></td>
			</tr>
		</table>
	</div>
	<p style="text-align: center">
        <a href="<%=request.getContextPath()%>/">Volver al inicio</a>
    </p>
</body>
</html>