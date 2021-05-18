package com.coreservlets.tutorial.statuscodes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "soloJava", urlPatterns = {"/statusCodes/soloJava"})
public class SoloJava extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public SoloJava() {
    super();
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
    String lenguajeElegido = request.getParameter("lenguaje");
    String lenguajeFavorito = "Java";
    if (lenguajeElegido.equalsIgnoreCase(lenguajeFavorito)) {
      response.sendRedirect("http://www.java.com/");
    } else {
      response.sendError(HttpServletResponse.SC_NOT_FOUND,
              "No se ha encontrado el lenguaje.");
    }
  }
}
