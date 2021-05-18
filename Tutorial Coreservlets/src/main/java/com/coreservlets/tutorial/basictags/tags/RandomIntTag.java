package com.coreservlets.tutorial.basictags.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

/**
 * Generates a random int between 1 and some specified number.
 */
public class RandomIntTag extends SimpleTagSupport {

  private int limit = 10;

  public void setLimit(String limitString) {
    try {
      limit = Integer.parseInt(limitString);
    } catch (NumberFormatException nfe) {
      // Do nothing, since limit already has default value
    }
  }

  @Override
  public void doTag() throws JspException, IOException {
    JspWriter out = getJspContext().getOut();
    out.print(1 + (int) (Math.random() * limit));
  }
}
