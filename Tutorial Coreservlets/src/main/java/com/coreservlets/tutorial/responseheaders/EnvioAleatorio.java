package com.coreservlets.tutorial.responseheaders;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EnvioAleatorio
 */
@WebServlet(name = "envio", urlPatterns = {"/responseHeaders/envio"})
public class EnvioAleatorio extends HttpServlet {

  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public EnvioAleatorio() {
    super();
  }

  /**
   * @param request
   * @param response
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  /**
   * @param request
   * @param response
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override
  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    String urlBing = "http://www.bing.com/";
    String urlGoogle = "http://www.google.com/";
    if (Math.random() > 0.5) {
      response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
      response.setHeader("Location", urlBing);
    } else {
      response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
      response.setHeader("Location", urlGoogle);
    }
  }
}
