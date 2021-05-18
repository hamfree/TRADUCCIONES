package com.coreservlets.tutorial.sessiontracking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Historial", urlPatterns = {"/sessionTracking/mostrarHistorial"})
public class HistorialServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public HistorialServlet() {
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
    String titulo = "Solucion Enunciado 3 - Mostrar Historial";
    String codificacion = "UTF-8";

    HttpSession session = request.getSession();
    synchronized (session) {
      @SuppressWarnings("unchecked")
      List<String> itemsAnteriores = (List<String>) session
              .getAttribute("itemsAnteriores");
      if (itemsAnteriores == null) {
        itemsAnteriores = new ArrayList<>();
      }
      String nuevoItem = request.getHeader("referer");
      if ((nuevoItem != null) && (!nuevoItem.trim().equals(""))) {
        // Comprobamos que no exista ya en la lista ...
        if (!itemsAnteriores.contains(nuevoItem)) {
          itemsAnteriores.add(nuevoItem);
        }

      }
      session.setAttribute("itemsAnteriores", itemsAnteriores);
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String html = ServletUtilities.headWithTitle(titulo, codificacion);
      out.println(html + "<body>\n" + "<h1>" + titulo + "</h1>");
      if (itemsAnteriores.isEmpty()) {
        out.println("<i>No hay páginas visitadas</i>");
      } else {
        out.println("<ul>");
        for (String item : itemsAnteriores) {
          out.println("<li>" + item + "</li>");
        }
        out.println("</ul>");
      }
      out.println("<p style='text-align:center;'>");
      out.println("<a href='ej3.html'>\n");
      out.println("Volver al inicio de la solución\n");
      out.println("</a>\n");
      out.println("</p>\n");
      out.println(ServletUtilities.htmlClose());
    }

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
    doGet(request, response);
  }

}
