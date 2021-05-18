<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/sample-tags.tld" prefix="sample"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/css/JSP-Styles.css" type="text/css">
    <title>Prueba de Reemplazo</title>
  </head>
  <body>
    <sample:replace target="Marty" replacement="Monty">
      <h1>Página de inicio de Marty Hall</h1>
    </sample:replace>
    <p>Blah, blah, blah.</p>
    <p class="inicio">
      <a href="../index.jsp">Volver al Índice de Soluciones</a>
    </p>
    <p style="font-size: small;">
      Todo el código de los <a
        href="http://courses.coreservlets.com/Course-Materials/">
        tutoriales J2EE de coreservlets.com (servlets, JSP, Struts, JSF 1,
        JSF 2, PrimeFaces, Ajax [con jQuery], GWT 2, Spring, Hibernate, JPA,
        basado en SOAP y Servicios Web RESTful, Hadoop, &amp; programación
        Java 7) </a>. Hay también cursos de formación con un instructor en vivo <a
        href="http://courses.coreservlets.com/"> sobre los mismos tópicos
        J2EE (servlets, JSP, Struts, JSF 1, JSF 2, PrimeFaces, Ajax [con
        jQuery], GWT 0, Spring, Hibernate, JPA, basado en SOAP y Servicios
        Web RESTful, Hadoop, &amp; programación Java 7) </a>.
    </p>
  </body>
</html>