<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.coreservlets.tutorial.scriptingjsp.utiles.UtilesColor" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>10 - Ejercicios-Invoking-Java-Code-with-JSP-Scripting -
      Solución Enunciado 1 - Cambio de color del fondo aleatorio</title>
    <style type="text/css">
      h1 {
        text-align: center;
      }

      h2 {
        text-align: center;
      }

      h3 {
        text-align: center;
      }

      body {
        font-family: Verdana, sans-serif;
        font-size: small;
      }

      label {
        font-weight: bold;
      }

      legend {
        font-weight: bold;
        font-size: medium;
        background-color: #FF9966;
        border: 1px solid black;
        border-style: double;
      }

      form {
        margin: 5px;
      }

      input,select,textarea {
        background-color: #FFFFCC;
      }
    </style>
  </head>
  <body bgcolor="<%=UtilesColor.colorAleatorio()%>">
    <h1>10 - Ejercicios-Invoking-Java-Code-with-JSP-Scripting</h1>
    <h2>Solución Enunciado 1</h2>
    <h3>Cambio de color del fondo aleatorio</h3>
    <p>
      Esta página, cada vez que se recarga, mostrará aleatoriamente el color
      de fondo como <span style="color: blue; background-color: white">azul</span>
      o <span style="color: red; background-color: white">rojo</span> ...
    </p>
    <p style="text-align: center;">
      <a href="ej1.html">Volver al inicio del Ejercicio Uno</a>
    </p>
    <p style="text-align: center;">
      <a href="index.html">Volver al inicio</a>
    </p>
  </body>
</html>