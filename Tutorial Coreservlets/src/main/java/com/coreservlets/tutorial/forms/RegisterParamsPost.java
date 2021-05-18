package com.coreservlets.tutorial.forms;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/forms/register-form-post")
public class RegisterParamsPost extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    doPost(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String title = "Leyendo los tres parámetros de la petición";
    String param1 = ServletUtilities.filter(request
            .getParameter("firstName"));
    param1 = replaceIfMissing(param1, "Nombre desconocido.");
    String param2 = ServletUtilities.filter(request
            .getParameter("lastName"));
    param2 = replaceIfMissing(param2, "Apellidos desconocidos.");
    String param3 = ServletUtilities.filter(request
            .getParameter("emailAddress"));
    param3 = replaceIfMissing(param3, "Correo electrónico desconocido.");
    String docType = "<!DOCTYPE html>\n";
    out.println(docType
            + "<html>\n"
            + "<head>\n"
            + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
            + "<title>" + title + "</title></head>\n"
            + "<body bgcolor='#fdfd5e6'>\n" + "<h1 align='center'>" + title
            + "</h1>\n" + "<ul>\n" + "<li><b>Nombre</b>: " + param1
            + "</li>\n" + "<li><b>Apellidos</b>: " + param2 + "</li>\n"
            + "<li><b>Correo electrónico</b>: " + param3 + "</li>\n"
            + "</ul>\n" + ServletUtilities.backToIndex() + "</body></html>");
  }

  private String replaceIfMissing(String orig, String replacement) {
    if ((orig == null) || (orig.trim().equals(""))) {
      return (replacement);
    } else {
      return (orig);
    }
  }
}
