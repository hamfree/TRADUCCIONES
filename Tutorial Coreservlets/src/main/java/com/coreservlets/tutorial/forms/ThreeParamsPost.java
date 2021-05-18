package com.coreservlets.tutorial.forms;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/forms/three-params-post")
public class ThreeParamsPost extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String title = "Leyendo los tres parámetros de la petición (GET)";
    String param1 = ServletUtilities.filter(request.getParameter("param1"));
    String param2 = ServletUtilities.filter(request.getParameter("param2"));
    String param3 = ServletUtilities.filter(request.getParameter("param3"));
    String docType = "<!DOCTYPE html>\n";
    out.println(docType
            + "<html>\n"
            + "<head>\n"
            + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
            + "<title>" + title + "</title></head>\n"
            + "<body bgcolor='#fdf5e6'>\n"
            + "<h1 align='center'>" + title + "</h1>\n"
            + "<ul>\n"
            + "<li><b>Parámetro 1</b>: " + param1 + "</li>\n"
            + "<li><b>Parámetro 2</b>: " + param2 + "</li>\n"
            + "<li><b>Parámetro 3</b>: " + param3 + "</li>\n"
            + "</ul>\n"
            + ServletUtilities.backToIndex() + "</body></html>");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String title = "Leyendo los tres parámetros de la petición (POST)";
    String docType = "<!DOCTYPE html>\n";
    out.println(docType
            + "<html>\n"
            + "<head>\n"
            + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
            + "<title>" + title + "</title></head>\n"
            + "<body bgcolor='#fdf5e6'>\n"
            + "<h1 align='center'>" + title + "</h1>\n" + "<ul>\n"
            + "<li><b>param1</b>: " + request.getParameter("param1") + "</li>\n"
            + "<li><b>param2</b>: " + request.getParameter("param2") + "</li>\n"
            + "<li><b>param3</b>: " + request.getParameter("param3") + "</li>\n"
            + "</ul>\n" + ServletUtilities.backToIndex()
            + "</body></html>");
  }
}
