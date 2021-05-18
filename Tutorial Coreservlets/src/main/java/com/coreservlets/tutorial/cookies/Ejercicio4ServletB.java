package com.coreservlets.tutorial.cookies;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Ej4ServletB", urlPatterns = {"/handlingCookies/pag4"})
public class Ejercicio4ServletB extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public Ejercicio4ServletB() {
    super();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init();
  }

  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    String html;
    String codificacion = "UTF-8";
    String nombreCookie = "leidaPrimeraPagina";
    String pag2 = "ej4pagina2.html";
    StringBuilder sb = new StringBuilder();

    // Recogemos posibles cookies del navegador...
    String valorCookie = CookieUtilities.getCookieValue(request,
            nombreCookie, "no");

    if (valorCookie.equalsIgnoreCase("si")) {
      response.sendRedirect(pag2);
    } else {
      sb.append(ServletUtilities.headWithTitle("Aviso", codificacion))
              .append("<body>\n")
              .append("<h1 style='text-align:center'>Aviso</h1>\n")
              .append("<p style='text-align:center'>\n")
              .append("Debe acceder a la página 2 a través de la página 1.\n")
              .append("</p>\n").append("<p style='text-align:center'>\n")
              .append("<a href='ej4.html'>Volver a 'ej4.html'</a>\n")
              .append("</p>\n").append(ServletUtilities.htmlClose());
    }
    html = sb.toString();
    response.setContentType("text/html");
    response.setCharacterEncoding(codificacion);
    PrintWriter out = response.getWriter();
    out.println(html);
  }
}
