package com.coreservlets.tutorial.requestHeaders;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import java.util.*;

/**
 * Muestra todas las cabeceras de la petición enviadas en la petición actual.
 *
 */
@SuppressWarnings("serial")
@WebServlet("/requestHeaders/show-request-headers")
public class ShowRequestHeaders extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String title = "Ejemplo de Servlet: Mostrando las Cabeceras de la Petición";
    String docType = "<!DOCTYPE html>\n";
    out.println(docType + "<html>\n"
            + "<head>"
            + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
            + "<title>" + title
            + "</title></head>\n" + "<body bgcolor=\"#FDF5E6\">\n"
            + "<h1 align=\"center\">" + title + "</h1>\n"
            + "<b>Método Petición: </b>" + request.getMethod() + "<br>\n"
            + "<b>URI de la Petición: </b>" + request.getRequestURI()
            + "<br>\n" + "<b>Protocolo de la Petición: </b>"
            + request.getProtocol() + "<br><br>\n"
            + "<table border=\"1\" align=\"center\">\n"
            + "<tr bgcolor=\"#ffad00\">\n"
            + "<th>Nombre de la cabecera<th>Valor de la cabecera");
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      out.println("<tr><td>" + headerName);
      out.println("    <td>" + request.getHeader(headerName));
    }
    out.println("</table>\n</body></html>");
  }

  /**
   * Ya que este servlet es para depuración, se manejan los GET y POST idénticamente.
   *
   * @param request
   * @param response
   * @throws javax.servlet.ServletException
   * @throws java.io.IOException
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    doGet(request, response);
  }
}
