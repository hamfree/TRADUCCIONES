<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Directiva Page - Índice de soluciones</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/JSP-Styles.css" type="text/css">
</head>
<body>
	<!-- //TODO Voy por aquí -->
	<h1>Directiva Page</h1>
	<h2>Índice de soluciones</h2>
	<ol>
		<li>Use JSP to make an Excel spreadsheet where the first row says
			“Year,” “Apples,” and “Oranges.” It should then have two rows of data
			(2008 and 2009), where each entry is a random number between 0 and
			10. I.e. the result should look something like this:
			<div style="text-align: center;">
				<table style="margin: 0 auto; border-collapse: collapse; width: 30%">
					<tr>
						<td>Año</td>
						<td>Manzanas</td>
						<td>Naranjas</td>
					</tr>
					<tr>
						<td>2012</td>
						<td>9.23456</td>
						<td>3.98765</td>
					</tr>
					<tr>
						<td>2013</td>
						<td>4.45678</td>
						<td>2.223344</td>
					</tr>
				</table>
			</div> <br />If you choose to use the tab-separated data approach (which
			is probably best here), be careful because Eclipse might change your
			tabs to spaces automatically. If so, you can edit the file outside of
			Eclipse using a normal text editor, or you can do Window -->
			Preferences --> General --> Editors --> Text Editors --> Insert
			spaces for tabs.
			<p>
				<a href="ExcelRandomNums.jsp">Solución Ejercicio 1</a>
			</p>
		</li>
		<li>Make an Excel spreadsheet with a random number of rows.
			<p>
				<a href="ExcelRandomRows.jsp">Solución Ejercicio 2</a>
			</p>
		</li>
		<li>The java.math package has a class called BigInteger that lets
			you create whole numbers with an arbitrary number of digits. Create a
			JSP page that makes a large BigInteger from a String you supply as a
			request parameter, squares it, and prints out the result. Use the
			online API at http://docs.oracle.com/javase/6/docs/api/ to see the
			syntax for the BigInteger constructor and squaring operations.
			<p>
				<a href="SquareNum.jsp">Solución Ejercicio 3</a>
			</p>
		</li>
		<li>Make a JSP page that sleeps for 20 seconds before returning
			the page. (Call TimeUnit.SECONDS. sleep(20) inside a try/catch block
			that catches InterruptedException). Access it “simultaneously” from
			Firefox and Internet Explorer. Repeat the experiment with the JSP
			page not allowing concurrent access. Verify the slower result.
			<p>
				<a href="ThreadSafeTest.jsp">Solución Ejercicio 4</a>
			</p>
		</li>
		<li><b>[Very hard; for the truly inspired.]</b> The above example
			worked because our version of Tomcat implements isThreadSafe="false"
			by queueing up the requests and passing them one at a time to the
			servlet instance. But, that is not the only legal implementation.
			Create a test case that will definitively show which of the following
			three approaches a server uses for isThread- Safe="false":<br>&nbsp;
			<ul>
				<li>Keeps a single servlet instance and queues up the requests
					to it<br>&nbsp;
				</li>
				<li>Makes a pool of instances but lets each instance only
					handle one request at a time<br>&nbsp;
				</li>
				<li>Ignores isThreadSafe altogether Note that all three
					approaches<br>&nbsp;
				</li>
			</ul> have been represented by Tomcat versions in the past.<br>&nbsp;
		</li>
		<li><b>[Just for fun.]</b> Grab the ComputeSpeed and SpeedError
			pages from the page-directive project. Access the ComputeSpeed page
			with numeric values for the “furlongs” and “fortnights” form
			parameters attached to the end of the URL. (See page 366 if you want
			more detail). Now, supply a non-numeric value for one of the
			parameters. Next, supply a legal number for furlongs, but 0 for
			fortnights. Can you explain the unexpected result you get?</li>
	</ol>
	<p style="text-align: center;">
		<a href="../index.html">Volver al Índice de la Sección</a>
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