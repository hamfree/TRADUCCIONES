<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Elije Colores (1)</title>
    <link rel="stylesheet" href="../../css/JSP-Styles.css" type="text/css">
  </head>
  <body style="background-color: #FDF5E6">
    <div style="text-align: center">
      <h1>Elije Colores 1</h1>
      <form action="ShowColors1.jsp" accept-charset="utf-8">
        Color de Primer Plano: <input type="text" name="foregroundColor"><br>
        Color de Fondo: <input type="text" name="backgroundColor"><br>
        <br> <input type="submit" value="Muestra Colores">
      </form>
    </div>
    <p class="inicio">
      <a href="index.jsp">Volver al Indice de Soluciones</a>
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