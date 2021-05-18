<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/JSP-Styles.css" type="text/css">
    <title>Muestra Colores (1)</title>
  </head>
  <jsp:useBean id="colorBean1" class="com.coreservlets.tutorial.beans.ColorBean" />
  <jsp:setProperty name="colorBean1" property="*" />
  <body>
    <script type="text/javascript">
          document.body.style.backgroundColor = "<jsp:getProperty name="colorBean1" property="backgroundColor"/>";
          document.body.style.color = "<jsp:getProperty name="colorBean1" property="foregroundColor"/>";
    </script>
    <h1>Muestra Colores 1</h1>
    Blah, blah, blah, blah.
    <p class="inicio">
      <a href="index.jsp">Volver al Indice de Soluciones</a>
    </p>
    <p style="font-size: small;">
      Todo el código de los <a
        href="http://courses.coreservlets.com/Course-Materials/">
        tutoriales J2EE de coreservlets.com (servlets, JSP, Struts, JSF 1,
        JSF 2, PrimeFaces, Ajax [con jQuery], GWT 2, Spring, Hibernate, JPA,
        basado en SOAP y Servicios Web RESTful, Hadoop, &amp; programación
        Java 7)</a>. Hay también cursos de formación con un instructor en vivo <a
        href="http://courses.coreservlets.com/">sobre los mismos tópicos
        J2EE (servlets, JSP, Struts, JSF 1, JSF 2, PrimeFaces, Ajax [con
        jQuery], GWT 0, Spring, Hibernate, JPA, basado en SOAP y Servicios
        Web RESTful, Hadoop, &amp; programación Java 7)</a>.
    </p>
  </body>
</html>