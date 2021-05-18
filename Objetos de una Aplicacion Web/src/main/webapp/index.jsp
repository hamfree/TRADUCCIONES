<%-- Documento: index.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>Inicio</title>
<style type="text/css">
TD {
	border: 1px inset;
	padding: 2px;
}
</style>
</head>
<body style='font-family: monospace;'>
    <h1 style="text-align: center">Objetos de una Aplicación Web</h1>
    <ul>
        <li>Servidor.....................: <%=pageContext.getServletContext().getServerInfo()%></li>
        <li>Codificación de caracteres...: <%=request.getCharacterEncoding()%></li>
        <li>Tipo mime....................: <%=request.getContentType()%></li>
        <li>Camino de contexto...........: <%=request.getContextPath()%></li>
        <li>Tipo de autentificacion......: <%=request.getAuthType()%></li>
        <li>Dirección del host local.....: <%=request.getLocalAddr()%></li>
        <li>Nombre del host local........: <%=request.getLocalName()%></li>
        <li>Puerto del host local........: <%=request.getLocalPort()%></li>
        <li>Lenguaje.....................: <%=request.getLocale().toString()%></li>
        <li>Metodo usado.................: <%=request.getMethod()%></li>
        <li>Protocolo usado..............: <%=request.getProtocol()%></li>
        <li>Usuario conectado............: <%=request.getUserPrincipal()%></li>
        <li>Expresion EL.................: ${7 > 3}</li>
    </ul>
    <h2 style="text-align: center;">Menú de la aplicación</h2>
    <table
        style="width: 50%; margin: 0 auto; border-collapse: separate; border: 1px inset;">
        <tr>
            <td>
                <ul>
                    <li><a href="info">Información del servidor</a><br></li>
                    <li><a href="requestEntrada.jsp">Objeto
                            Request</a></li>
                    <li><a href="sesionEntrada.jsp">Objeto
                            Sesión</a></li>
                    <li><a href="aplicacionEntrada.jsp">Objeto
                            Aplicacion</a></li>
                    <li><a href="pageContextEntrada.jsp">Objeto
                            pageContext</a></li>
                </ul>
            </td>
        </tr>
    </table>
</body>
</html>