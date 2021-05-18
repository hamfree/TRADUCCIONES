package com.coreservlets.tutorial.cookies;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Ej4ServletA", urlPatterns = {"/handlingCookies/pag3"})
public class Ejercicio4ServletA extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public Ejercicio4ServletA() {
    super();
  }

  /**
   *
   * @param config
   * @throws ServletException
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init();
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
    String codificacion = "UTF-8";
    String nombreCookie = "leidaPrimeraPagina";
    String pag1 = "ej4pagina1.html";
    Cookie cookie;

    // Recogemos posibles cookies del navegador...
    String valorCookie = CookieUtilities.getCookieValue(request,
            nombreCookie, "no");

    if (valorCookie.equalsIgnoreCase("no")) {
      // Es la primera vez y el usuario accede a la primera página.
      // Creamos la cookie y la enviamos de vuelta al navegador
      cookie = new Cookie(nombreCookie, "si");
      response.addCookie(cookie);
    }

    response.setContentType("text/html");
    response.setCharacterEncoding(codificacion);
    response.sendRedirect(pag1);
  }

}
