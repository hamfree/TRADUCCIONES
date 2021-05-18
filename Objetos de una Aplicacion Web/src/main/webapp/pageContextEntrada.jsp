<%-- Documento: pageContextEntrada.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%!public String ambito(int ambi) {
		if (ambi == PageContext.SESSION_SCOPE) {
			return "SESSION_SCOPE";
		}
		if (ambi == PageContext.APPLICATION_SCOPE) {
			return "APPLICATION_SCOPE";
		}
		if (ambi == PageContext.REQUEST_SCOPE) {
			return "REQUEST_SCOPE";
		}
		if (ambi == PageContext.PAGE_SCOPE) {
			return "PAGE_SCOPE";
		}
		return "";
	}%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PageContext</title>
<style type="text/css">
TD {
    border: 1px solid;
    padding: 2px;
}
</style>
</head>
<body style="font-family: monospace;">
    <h1 style="text-align:center">Objeto pageContext</h1>
    <h2 style="text-align:center">El objeto pageContext</h2>
    <p>
        Este objeto permite acceder al espacio de nombres de la página JSP actual, asimismo, ofrece el 
        acceso a varios atributos de la página así como una capa sobre los detalles de implementación. 
        Este objeto también cuenta con la capacidad de almacenar y recuperar atributos en el ámbito 
        de la página, sin embargo su uso más frecuente es el acceso y obtención de los objetos 
        integrados de JSP. El objeto pageContext se utiliza para representar a toda la página JSP.
        Este objeto está concebido como un medio para acceder a información sobre la página 
        evitando al mismo tiempo la mayor parte de los detalles de implementación.
        Este objeto almacena referencias a la solicitud y los objetos de respuesta para cada 
        solicitud. La aplicación, la configuración, la sesión y objetos fuera derivan accediendo 
        a los atributos de este objeto.
    </p>
    <p>
        El objeto pageContext también contiene información sobre las directivas emitidas a la 
        página JSP, incluyendo la información de buffering, el errorPageURL y alcance de página.
        La clase PageContext define varios campos, incluyendo PAGE_SCOPE, REQUEST_SCOPE, 
        SESSION_SCOPE y APPLICATION_SCOPE, que identifican los cuatro ámbitos.
    </p>
    <p>
        Uno de los métodos importantes es removeAttribute, que acepta uno o dos argumentos. 
        Por ejemplo, pageContext.removeAttribute(“nombreAtributo”) elimina el atributo de todos 
        los ámbitos, mientras que el siguiente código sólo la quita del ámbito de la página:
    </p>
    <pre>
        pageContext.removeAttribute(“nombreAtributo”, PAGE_SCOPE);
    </pre>
    <p>
        Finalmente, cuando se trabaja con Servlets este objeto puede proporcionar los accesos 
        a los objetos implícitos.
    </p>
    <pre>
        application = pageContext.getServletContext();
        config = pageContext.getServletConfig();
        session = pageContext.getSession();
        out = pageContext.getOut();
    </pre>
    <p>
        Ahora vamos a realizar un ejemplo de uso del objeto pageContext. El código que se
        ejecuta en esta página es:
    </p>
    <pre>
        session.setAttribute("Ciudad", "La Paz");
        pageContext.setAttribute("Nombre", "Juan Carlos", PageContext.SESSION_SCOPE);
        pageContext.setAttribute("Pais", "Bolivia", PageContext.SESSION_SCOPE);
        pageContext.setAttribute("pais", "Brasil", PageContext.PAGE_SCOPE);

        // Se obtienen todos los nombres de atributos de session
        Enumeration&lt;String&gt; atributos = pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
    </pre>
    <%
    	session.setAttribute("Ciudad", "La Paz");
    	pageContext.setAttribute("Nombre", "Juan Carlos", PageContext.SESSION_SCOPE);
    	pageContext.setAttribute("Pais", "Bolivia", PageContext.SESSION_SCOPE);
    	pageContext.setAttribute("pais", "Brasil", PageContext.PAGE_SCOPE);

    	// Se obtienen todos los nombres de atributos de session

    	Enumeration<String> atributos = pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
    %>
    <div style="text-align: center">
        <table style="width: 75%; margin: 0 auto;border: 1px solid;">
            <colgroup>
                <col width="50%">
                <col width="50%">
            </colgroup>
            <tbody>
                <tr>
                    <th colspan="2">SESSION SCOPE</th>
                </tr>
                <%
                	// Se muestran los nombres y los valores de session
                	while (atributos.hasMoreElements()) {
                		String nombre = (String) atributos.nextElement();
                %>
                <tr style="text-align: left;">
                    <td><%=nombre%></td>
                    <td><%=pageContext.getAttribute(nombre, PageContext.SESSION_SCOPE)%></td>
                </tr>
                <%
                	}
                %>
                <tr>
                    <th colspan="2">PAGE SCOPE</th>
                </tr>
                <tr style="text-align: left;">
                    <td>Pais</td>
                    <td><%=pageContext.findAttribute("pais")%></td>
                </tr>
                <tr style="text-align: left;">
                    <td>Ambito de pais</td>
                    <td><%=ambito(pageContext.getAttributesScope("pais"))%></td>
                </tr>
            </tbody>
        </table>
    </div>
    <p style="text-align: center">
        <a href="<%=request.getContextPath()%>/">Volver al inicio</a>
    </p>
</body>
</html>
