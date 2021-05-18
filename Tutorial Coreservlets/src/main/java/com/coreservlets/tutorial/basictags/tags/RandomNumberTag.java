package com.coreservlets.tutorial.basictags.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

/**
 * Generates a random number between 0 and 1.
 */
public class RandomNumberTag extends SimpleTagSupport {

  @Override
  public void doTag() throws JspException, IOException {
    JspWriter out = getJspContext().getOut();
    out.print(Math.random());
  }
}
