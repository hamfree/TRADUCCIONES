package com.coreservlets.tutorial.el;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;

@WebServlet(name = "ShowNames3", urlPatterns = {"/show-names3"})
public class ShowNames3 extends HttpServlet {

  /**
   *
   */
  private static final long serialVersionUID = -312392689185410810L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Name> names = new ArrayList<>();
    names.add(new Name("Harry", "Hacker"));
    names.add(new Name("Paula", "Programmer"));
    names.add(new Name("Cody", "Coder"));
    request.setAttribute("names", names);
    String address = "/WEB-INF/results/show-names2.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
    dispatcher.forward(request, response);
  }
}
