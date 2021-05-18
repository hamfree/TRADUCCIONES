<%@page import="com.coreservlets.scriptingjsp.utiles.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>10 - Ejercicios-Invoking-Java-Code-with-JSP-Scripting -
	Solución Enunciado 3 - Color de fondo seleccionado por el usuario, con
	valor por defecto cogido de una petición previa</title>
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

.dummy {
	color:#000000
}
</style>
</head>
<%
	String color = request.getParameter("color");
	if (color == null || color.isEmpty()) {
		color = (String) application.getAttribute("color");
		if (color == null || color.isEmpty()) {
			color = "WHITE";
		}
	}
%>
<body bgcolor="<%=color%>">
	<h1>10 - Ejercicios-Invoking-Java-Code-with-JSP-Scripting</h1>
	<h2>Solución Enunciado 3</h2>
	<h3>Color de fondo seleccionado por el usuario, con valor por
		defecto cogido de una petición previa</h3>
	<p>Esta página, cada vez que se recarga, mostrará el color de fondo
		seleccionado por el usuario en el formulario de debajo. En caso de no
		elegirse ningún color, el color se tomará de la anterior selección de
		color de cualquier usuario que usó la aplicación.</p>
	<!-- Plantilla de formulario con toda clase de campos -->
	<form method="post">
		<fieldset>
			<legend>Selección del color de fondo</legend>
			<!-- Campo tipo cuadro de lista desplegable -->
			<div>
				<label for="color">Color del fondo :<br /> <select
					id="color" name="color" size="1">
						<option value="">(Elija un color)</option>
						<option value="aqua">Aqua</option>
						<option value="black">Black</option>
						<option value="blue">Blue</option>
						<option value="fuchsia">Fuchsia</option>
						<option value="gray">Gray</option>
						<option value="green">Green</option>
						<option value="lime">Lime</option>
						<option value="maroon">Maroon</option>
						<option value="navy">Navy</option>
						<option value="olive">Olive</option>
						<option value="purple">Purple</option>
						<option value="red">Red</option>
						<option value="silver">Silver</option>
						<option value="teal">Teal</option>
						<option value="white">White</option>
						<option value="yellow">Yellow</option>
				</select>
				</label>
			</div>
			<hr>
			<!-- Campo tipo texto deshabilitado -->
			<div>
				<label for="colorActual">Color actual del fondo :<br/> <input
					type="text" id="colorActual" name="colorActual"
					value="<%=color.toUpperCase()%>" disabled="disabled" />
				</label>
			</div>

			<hr>
			<!-- Botón de tipo submit, para enviar el formulario -->
			<div style="text-align: center;">
				<input type="submit" id="enviar" name="enviar" value="Enviar" />
			</div>
		</fieldset>
	</form>
	<p style="text-align: center;">
		<a href="ej3.html">Volver al inicio del Ejercicio Tres</a>
	</p>
	<p style="text-align: center;">
		<a href="index.html">Volver al inicio</a>
	</p>

</body>
<%
	application.setAttribute("color", color);
%>
</html>