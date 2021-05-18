<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/sample-tags.tld" prefix="sample" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/css/JSP-Styles.css" type="text/css">
    <title>Etiqueta Molesta</title>
  </head>
  <body>
    <h1>Etiqueta Molesta</h1>
    <sample:annoying>Mi, mi, qu&eacute; mirada tan agradable. Ah.</sample:annoying>
    <div style="padding: 50px">&nbsp;</div>
    <p class="inicio">
      <a href="../index.jsp">Volver al &Iacute;ndice de Soluciones</a>
    </p>
    <p style="font-size: small;">
      Todo el c&oacute;digo de los <a
        href="http://courses.coreservlets.com/Course-Materials/">
        tutoriales J2EE de coreservlets.com (servlets, JSP, Struts, JSF 1,
        JSF 2, PrimeFaces, Ajax [con jQuery], GWT 2, Spring, Hibernate, JPA,
        basado en SOAP y Servicios Web RESTful, Hadoop, &amp; programaci&oacute;n
        Java 7) </a>. Hay tambi&eacute;n cursos de formaci&oacute;n con un instructor en vivo <a
        href="http://courses.coreservlets.com/"> sobre los mismos t&oacute;picos
        J2EE (servlets, JSP, Struts, JSF 1, JSF 2, PrimeFaces, Ajax [con
        jQuery], GWT 0, Spring, Hibernate, JPA, basado en SOAP y Servicios
        Web RESTful, Hadoop, &amp; programaci&oacute;n Java 7) </a>.
    </p>
  </body>
</html>