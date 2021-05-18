package com.coreservlets.tutorial.responseheaders;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RefrescoRetardado", urlPatterns = {"/responseHeaders/redireccion"})
public class RefrescoRetardadoServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public RefrescoRetardadoServlet() {
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
    String titulo;
    String urlDestino;
    String html;
    String userAgent = request.getHeader("User-Agent");
    if ((userAgent != null) && (userAgent.contains("MSIE"))) {
      titulo = "Usas Internet Explorer";
      urlDestino = "http://www.microsoft.com/";
    } else {
      titulo = "Usas Firefox u otro navegador que no es Internet Explorer";
      urlDestino = "http://www.mozilla.org";
    }
    response.setContentType("text/html");
    response.setHeader("Refresh", "10; url=" + urlDestino);
    PrintWriter out = response.getWriter();
    html = ServletUtilities.headWithTitle(titulo)
            + "<body>\n"
            + "<h1 style='text-align:center;'>" + titulo + "</h1>\n"
            + "<div style='text-align:center;'>\n"
            + "Será redirigido a " + urlDestino + " en 10 segundos..."
            + "</div>"
            + ServletUtilities.htmlClose();
    out.println(html);
  }
}
