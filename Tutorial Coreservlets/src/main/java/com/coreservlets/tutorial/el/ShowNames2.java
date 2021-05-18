package com.coreservlets.tutorial.el;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ShowNames2", urlPatterns = {"/show-names2"})
public class ShowNames2 extends HttpServlet {

  /**
   *
   */
  private static final long serialVersionUID = 3850900032198004042L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Name[] names = {new Name("Harry", "Hacker"), new Name("Paula", "Programmer"), new Name("Cody", "Coder")};
    request.setAttribute("names", names);
    String address = "/WEB-INF/results/show-names2.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
    dispatcher.forward(request, response);
  }
}
