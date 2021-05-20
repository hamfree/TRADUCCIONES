<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="sample"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../../css/JSP-Styles.css" type="text/css">
    <title>Algunos Enteros Aleatorios</title>
  </head>
  <body>
    <h1>Algunos Enteros Aleatorios</h1>
    <p>Pulse F5 o Ctrl + R en su navegador para actualizar la página y
      así­ generar nuevos números aleatorios.</p>
    <ul>
      <li><sample:randomInt limit="10" />
      <li><sample:randomInt limit="100" />
      <li><sample:randomInt limit="1000" />
      <li><sample:randomInt limit="10000" />
      <li><sample:randomInt limit="100000" />
      <li><sample:randomInt limit="1000000" />
    </ul>
    <div style="padding: 10px">&nbsp;</div>
    <p class="inicio">
      <a href="../index.jsp">Volver al Índice de Soluciones</a>
    </p>
    <p style="font-size: small;">
      Todo el código de los <a href="http://courses.coreservlets.com/Course-Materials/">tutoriales J2EE de coreservlets.com 
        (servlets, JSP, Struts, JSF 1, JSF 2, PrimeFaces, Ajax [con jQuery], GWT 2, Spring, Hibernate, JPA, basado en SOAP y 
        Servicios Web RESTful, Hadoop, &amp; programación Java 7) </a>. Hay también cursos de formación con un instructor en vivo 
      <a href="http://courses.coreservlets.com/"> sobre los mismos tópicos J2EE (servlets, JSP, Struts, JSF 1, JSF 2, PrimeFaces, 
        Ajax [con jQuery], GWT 0, Spring, Hibernate, JPA, basado en SOAP y Servicios Web RESTful, Hadoop, &amp; programación Java 
        7)</a>.
    </p>
  </body>
</html>