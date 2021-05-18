package com.coreservlets.tutorial.mvc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/show-abbreviation-1")
public class ShowAbbreviation1 extends HttpServlet {

  /**
   *
   */
  private static final long serialVersionUID = -4711353270679373534L;
  private final StateAbbreviationService abbreviationMapper = new StateAbbreviationMapper();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String stateName = request.getParameter("state-name");
    if (stateName == null) {
      stateName = "";
    }
    stateName = stateName.trim();
    String stateAbbreviation = abbreviationMapper.findAbbreviation(stateName);
    StatePair stateInfo = new StatePair(stateName, stateAbbreviation);
    request.setAttribute("stateInfo1", stateInfo);
    String address;
    if (stateName.isEmpty()) {
      address = "/WEB-INF/results/missing-state.jsp";
    } else if (stateAbbreviation != null) {
      address = "/WEB-INF/results/show-abbreviation-1.jsp";
    } else {
      address = "/WEB-INF/results/unknown-state-1.jsp";
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
    dispatcher.forward(request, response);
  }
}
