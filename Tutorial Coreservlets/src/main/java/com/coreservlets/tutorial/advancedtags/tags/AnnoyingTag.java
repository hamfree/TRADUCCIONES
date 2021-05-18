package com.coreservlets.tutorial.advancedtags.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

/**
 * Makes a big, bold, red, blinking element.
 */
public class AnnoyingTag extends SimpleTagSupport {

  @Override
  public void doTag() throws JspException, IOException {
    JspWriter out = getJspContext().getOut();
    out.print("<H1><FONT COLOR=\"RED\"><BLINK>");
    getJspBody().invoke(null);  // Body goes here
    out.print("</BLINK></FONT></H1>");
  }
}
