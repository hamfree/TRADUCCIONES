package com.coreservlets.tutorial.sessiontracking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Bienvenida", urlPatterns = {"/sessionTracking/darBienvenida"})
public class BienvenidoServlet extends HttpServlet {

  private static final long serialVersionUID = -3287425162764251603L;

  public BienvenidoServlet() {
    super();
  }

  /**
   *
   * @param config
   * @throws ServletException
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {

    HttpSession sesion = request.getSession();
    synchronized (sesion) {
      String mensaje;
      int contadorAcceso;
      if (sesion.getAttribute("contadorAcceso") == null) {
        contadorAcceso = 0;
        mensaje = "Bienvenido a bordo";
      } else {
        contadorAcceso = Integer.parseInt(sesion.getAttribute("contadorAcceso").toString());
        mensaje = "Bienvenido de vuelta";
        contadorAcceso = contadorAcceso + 1;
      }

      sesion.setAttribute("contadorAcceso", contadorAcceso);
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "08-Ejercicios-Session-Tracking - Solución Enunciado 1";

      String docType = ServletUtilities.headWithTitle(title, "UTF-8");
      out.println(docType + "<body>\n" + "<center>\n" + "<h1>" + mensaje
              + "</h1>\n" + "<h2>Información de su sesión:</h2>\n"
              + "<table border='1'>\n" + "<tr>\n"
              + "  <th>Tipo información<TH>Valor\n" + "<tr>\n"
              + "  <td>ID\n" + "  <td>" + sesion.getId() + "\n"
              + "<tr>\n" + "  <td>Hora de creación\n" + "  <td>"
              + new Date(sesion.getCreationTime()) + "\n" + "<tr>\n"
              + "  <td>Hora de último acceso\n" + "  <td>"
              + new Date(sesion.getLastAccessedTime()) + "\n" + "<tr>\n"
              + "  <td>Número de accesos previos\n" + "  <td>"
              + contadorAcceso + "\n" + "</table>\n" + "</center>"
              + ServletUtilities.backToIndex()
              + ServletUtilities.htmlClose());
    }

  }
}
