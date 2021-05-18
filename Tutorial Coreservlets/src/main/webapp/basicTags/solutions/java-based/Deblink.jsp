<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/sample-tags.tld" prefix="sample"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/css/JSP-Styles.css" type="text/css">
    <title>Sin parpadear</title>
  </head>
  <body>
    <h1>Sin parpardear</h1>
    <table border="1">
      <tr>
        <th>Original</th>
        <th>Sin parpadear</th>
      </tr>
      <tr>
        <td>
      <blink>Esto es directamente de BLINK</blink>
      <p>
        <sample:annoying>
          Esto es indirecto, de una etiqueta molesta.
        </sample:annoying>
      </p>
    </td>
    <td>
      <sample:deblink>
      <blink>Esto es directamente de BLINK</blink>
      <p>
        <sample:annoying>
          Esto es indirecto, de una etiqueta molesta.
        </sample:annoying>
      </p>
    </sample:deblink>
  </td>
</tr>
</table>
<p class="inicio">
  <a href="../index.jsp">Volver al Índice de Soluciones</a>
</p>
<p style="font-size: small;">
  Todo el código de los <a
    href="http://courses.coreservlets.com/Course-Materials/">
    tutoriales J2EE de coreservlets.com (servlets, JSP, Struts, JSF 1,
    JSF 2, PrimeFaces, Ajax [con jQuery], GWT 2, Spring, Hibernate, JPA,
    basado en SOAP y Servicios Web RESTful, Hadoop, &amp; programación
    Java 7) </a>. Hay también cursos de formación con un instructor 
  en vivo <a href="http://courses.coreservlets.com/"> sobre los 
    mismos tópicos J2EE (servlets, JSP, Struts, JSF 1, JSF 2, 
    PrimeFaces, Ajax [con jQuery], GWT 0, Spring, Hibernate, 
    JPA, basado en SOAP y Servicios Web RESTful, Hadoop, &amp; 
    programación Java 7) </a>.
</p>
</body>
</html>