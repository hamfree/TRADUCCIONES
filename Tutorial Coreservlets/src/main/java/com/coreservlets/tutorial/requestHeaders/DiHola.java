package com.coreservlets.tutorial.requestHeaders;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/requestHeaders/di-hola")
public class DiHola extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String title, message;
    String rojo = "#FF0000";
    String amarillo = "#FFFF00";
    String userAgent = request.getHeader("User-Agent");
    if ((userAgent != null) && (userAgent.contains("MSIE"))) {
      title = "Usas Internet Explorer";
      message = "<body bgcolor='" + rojo + "' text='" + amarillo + "'>\n"
              + "<h1 align='center'>" + title + "</h1>\n"
              + "<p align='center'>&iexcl;Hola!</p>\n"
              + ServletUtilities.backToIndex() + "</body></html>";
    } else {
      title = "Usas Firefox";
      message = "<body bgcolor='" + amarillo + "' text='" + rojo + "'>\n"
              + "<h1 align='center'>" + title + "</h1>\n"
              + "<p align='center'>&iexcl;Hola!</p>\n"
              + ServletUtilities.backToIndex() + "</body></html>";
    }
    String docType = "<!DOCTYPE html>\n";
    out.println(docType
            + "<html>\n"
            + "<head>"
            + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
            + "<title>" + title + "</title></head>\n" + message);
  }
}
