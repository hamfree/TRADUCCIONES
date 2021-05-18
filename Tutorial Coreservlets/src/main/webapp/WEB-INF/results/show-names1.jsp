<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nombres 1</title>
    <link rel="stylesheet" href="JSP-Styles.css" type="text/css">
  </head>
  <body>
    <h1>Nombres 1</h1>
    <ul>
      <li>${firstNames[0]}
      <li>${firstNames[1]}
      <li>${firstNames[2]}
    </ul>
    <p style="text-align: center;">
      <a href="<%=request.getContextPath()%>/index.jsp">Volver al Inicio</a>
    </p>
  </body>
</html>