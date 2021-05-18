package com.coreservlets.tutorial.scriptingjsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Plantilla de servlet, para no empezar desde cero. Con anotaciones para especificación Servlet 3.0 (Tomcat 7 y superiores...)
 *
 * @author Juan F. Ruiz
 *
 */
@WebServlet(name = "PltServlet", urlPatterns = {"/plt"})
public class PlantillaServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public PlantillaServlet() {
    super();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
  }

  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    // Borrar la llamada si hay que implementar doGet()
    doPost(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    // Modificar al implementar ...
    String html;
    String titulo = "titulo de la pagina";
    String codificacion = "UTF-8"; // O ISO-8859-1,ISO-8859-15,...
    html = ServletUtilities.htmlExerciseNotImplemented(request, response,
            titulo, codificacion);
    PrintWriter out = response.getWriter();
    out.println(html);
  }

}
