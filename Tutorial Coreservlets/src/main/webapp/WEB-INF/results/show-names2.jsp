<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nombres 2 y 3</title>
    <link rel="stylesheet" href="../../JSP-Styles.css" type="text/css">
  </head>
  <body>
    <h1>Nombres 2 y 3</h1>
    <ul>
      <li>${names[0].first}${names[0].last}</li>
      <li>${names[1].first}${names[1].last}</li>
      <li>${names[2].first}${names[2].last}</li>
    </ul>
    <p style="text-align: center;">
      <a href="<%=request.getContextPath()%>/index.jsp">Volver al Inicio</a>
    </p>
  </body>
</html>