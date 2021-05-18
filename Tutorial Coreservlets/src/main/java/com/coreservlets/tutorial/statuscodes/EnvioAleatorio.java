package com.coreservlets.tutorial.statuscodes;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "envio", urlPatterns = {"/statusCodes/envio"})
public class EnvioAleatorio extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public EnvioAleatorio() {
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
    String urlBing = "http://www.bing.com/";
    String urlGoogle = "http://www.google.com/";
    if (Math.random() > 0.5) {
      response.sendRedirect(urlGoogle);
    } else {
      response.sendRedirect(urlBing);
    }
  }
}
