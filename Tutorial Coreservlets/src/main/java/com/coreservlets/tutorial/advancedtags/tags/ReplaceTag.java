package com.coreservlets.tutorial.advancedtags.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

/**
 * Tag that replaces all occurrences of target with replacement in tag body. Target can include regular expressions.
 */
public class ReplaceTag extends SimpleTagSupport {

  private String target, replacement;

  public void setTarget(String target) {
    this.target = target;
  }

  public void setReplacement(String replacement) {
    this.replacement = replacement;
  }

  @Override
  public void doTag() throws JspException, IOException {
    if ((target != null) && (!target.equals(""))
            && (replacement != null)) {
      StringWriter stringWriter = new StringWriter();
      getJspBody().invoke(stringWriter);
      String body = stringWriter.toString();
      body = body.replaceAll(target, replacement);
      JspWriter out = getJspContext().getOut();
      out.print(body);
    }
  }
}
