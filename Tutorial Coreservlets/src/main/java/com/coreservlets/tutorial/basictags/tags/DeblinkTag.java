package com.coreservlets.tutorial.basictags.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

/**
 * Tag that removes all the BLINK tags from body.
 */
public class DeblinkTag extends SimpleTagSupport {

  @Override
  public void doTag() throws JspException, IOException {
    StringWriter stringWriter = new StringWriter();
    getJspBody().invoke(stringWriter);
    String body = stringWriter.toString();
    body = body.replaceAll("(?i)</?BLINK>", "");
    JspWriter out = getJspContext().getOut();
    out.print(body);
  }
}
