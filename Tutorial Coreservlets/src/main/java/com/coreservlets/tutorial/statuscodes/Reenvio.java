package com.coreservlets.tutorial.statuscodes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Reenvio", urlPatterns = {"/statusCodes/reenvio"})
public class Reenvio extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static int contador = 0;

  public Reenvio() {
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
    String url;
    String urlWP = "http://www.washingtonpost.com/";
    String urlNYT = "http://www.nytimes.com/";
    // Para depuracion
    System.out.println("contador=" + contador);
    if (contador < 9) {
      contador++;
      url = urlWP;
    } else {
      contador = 0;
      url = urlNYT;
    }
    response.sendRedirect(url);
  }
}
