package com.coreservlets.tutorial.responseheaders;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "refresca", urlPatterns = {"/responseHeaders/refrescaCadaCincoSegundos"})
public class RefrescaServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public RefrescaServlet() {
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
    String titulo = "Tiempo y fecha  cada cinco segundos";
    String html;
    Date fechaActual = new Date();

    response.setIntHeader("Refresh", 5);
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    html = "<body bgcolor='#FDF5E6'>\n" + "<h2 align='center'>" + titulo
            + "</h2>\n" + "<div align='center'>\n"
            + String.valueOf(fechaActual) + "\n" + "</div>\n"
            + ServletUtilities.backToIndex()
            + "</body>\n</html>";
    out.println(ServletUtilities.headWithTitle(titulo) + html);
  }
}
