package com.coreservlets.tutorial.mvc;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

// The only differences in this example from the previous one:
// 1) StatePair bean is stored in the session (and thus input form can display it)
// 2) Bean name changes (don't use same names for beans in different places!), 
//    so page names also change so that they can refer to correct bean.
// 3) URL of servlet changes (all servlets must have unique URLs)
@WebServlet("/show-abbreviation-2")
public class ShowAbbreviation2 extends HttpServlet {

  /**
   *
   */
  private static final long serialVersionUID = 8799645642629501096L;
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
    HttpSession session = request.getSession();
    session.setAttribute("stateInfo2", stateInfo);
    String address;
    if (stateName.isEmpty()) {
      address = "/WEB-INF/results/missing-state.jsp";
    } else if (stateAbbreviation != null) {
      address = "/WEB-INF/results/show-abbreviation-2.jsp";
    } else {
      address = "/WEB-INF/results/unknown-state-2.jsp";
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
    dispatcher.forward(request, response);
  }
}
