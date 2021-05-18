package com.coreservlets.tutorial.requestHeaders;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import java.util.*;

@SuppressWarnings("serial")
@WebServlet("/requestHeaders/segunda.html")
public class ControlaOrden extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String title = "Segunda Página";
    String referer;
    boolean hasReferer = false;

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      if (headerName.equalsIgnoreCase("referer")) {
        referer = request.getHeader(headerName);
        if (referer != null) {
          hasReferer = true;
          if (!referer.contains("primera.html")) {
            response.sendRedirect("primera.html");
          } else {
            String docType = "<!DOCTYPE html>\n";
            out.println(docType
                    + "<html>\n"
                    + "<head>"
                    + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
                    + "<title>"
                    + title
                    + "</title></head>\n"
                    + "<body bgcolor=\"#FDF5E6\">\n"
                    + "<h1 align='center'>"
                    + title
                    + "</h1>\n"
                    + "<p style='text-align: center;'>Esta es la segunda pagina</p>\n"
                    + ServletUtilities.backToIndex());
            out.println("</body>\n</html>");
          }
        } else {
          response.sendRedirect("primera.html");
        }
      }
    }// Cierre While
    if (!hasReferer) {
      response.sendRedirect("primera.html");
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    doGet(request, response);
  }
}
