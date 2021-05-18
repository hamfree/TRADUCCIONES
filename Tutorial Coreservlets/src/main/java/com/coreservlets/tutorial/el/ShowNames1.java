package com.coreservlets.tutorial.el;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ShowNames1", urlPatterns = {"/show-names1"})
public class ShowNames1 extends HttpServlet {

  /**
   *
   */
  private static final long serialVersionUID = -2349822068845896015L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String[] names = {"John", "Jane", "Juan"};
    request.setAttribute("firstNames", names);
    String address = "/WEB-INF/results/show-names1.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
    dispatcher.forward(request, response);
  }
}
