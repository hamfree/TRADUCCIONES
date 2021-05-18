package com.coreservlets.tutorial.forms;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/forms/three-params")
public class ThreeParams extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    this.log("Character Encoding: " + request.getCharacterEncoding());
    this.log("Content Type: " + request.getContentType());

    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String title = "Leyendo los tres parámetros de la petición";
    String docType = "<!DOCTYPE html>\n";
    out.println(docType
            + "<html>\n"
            + "<head>"
            + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
            + "<title>" + title + "</title></head>\n"
            + "<body bgcolor=\"#fdf5e6\">\n" + "<h1 align='center'>"
            + title + "</h1>\n" + "<ul>\n" + "  <li><b>Parámetro 1</b>: "
            + request.getParameter("param1") + "</li>\n"
            + "  <li><b>Parámetro 2</b>: " + request.getParameter("param2")
            + "</li>\n" + "  <li><b>Parámetro 3</b>: "
            + request.getParameter("param3") + "</li>\n" + "</ul>\n"
            + ServletUtilities.backToIndex() + "</body></html>");
  }
}
