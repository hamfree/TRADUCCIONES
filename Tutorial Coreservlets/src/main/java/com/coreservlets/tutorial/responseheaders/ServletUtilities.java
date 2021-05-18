package com.coreservlets.tutorial.responseheaders;

import javax.servlet.http.*;

public class ServletUtilities {

  public static final String DOCTYPE = "<!DOCTYPE html>";

  public static String headWithTitle(String title) {
    return (DOCTYPE
            + "\n"
            + "<html>\n"
            + "<head>\n"
            + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
            + "<title>" + title + "</title></title>\n");
  }

  public static String backToIndex() {
    StringBuilder sb = new StringBuilder();
    sb.append("<br\\>\n").append("<div align='center'>\n")
            .append("<a href='index.html'>Volver al inicio</a>\n")
            .append("</div>\n");
    return (sb.toString());
  }

  public static String exerciseNotImplemented(String titulo) {
    String html = ServletUtilities.headWithTitle(titulo)
            + "<body bgcolor='#FDF5E6'>\n" + "<h2 align='center'>" + titulo
            + "</h2>\n" + "<div align='center'>\n"
            + "Ejercicio a&uacute;n no implementado\n" + "</div>\n"
            + ServletUtilities.backToIndex() + "</body>\n</html>";
    return (html);
  }

  public static String htmlClose() {
    return "</body>\n</html>";
  }

  public static int getIntParameter(HttpServletRequest request,
          String paramName, int defaultValue) {
    String paramString = request.getParameter(paramName);
    int paramValue;
    try {
      paramValue = Integer.parseInt(paramString);
    } catch (NumberFormatException nfe) { // null or bad format
      paramValue = defaultValue;
    }
    return (paramValue);
  }

  public static double getDoubleParameter(HttpServletRequest request,
          String paramName, double defaultValue) {
    String paramString = request.getParameter(paramName);
    double paramValue;
    try {
      paramValue = Double.parseDouble(paramString);
    } catch (NumberFormatException nfe) { // null or bad format
      paramValue = defaultValue;
    }
    return (paramValue);
  }

  public static String filter(String input) {
    if (!hasSpecialChars(input)) {
      return (input);
    }
    StringBuilder filtered = new StringBuilder(input.length());
    char c;
    for (int i = 0; i < input.length(); i++) {
      c = input.charAt(i);
      switch (c) {
        case '<':
          filtered.append("&lt;");
          break;
        case '>':
          filtered.append("&gt;");
          break;
        case '"':
          filtered.append("&quot;");
          break;
        case '&':
          filtered.append("&amp;");
          break;
        default:
          filtered.append(c);
      }
    }
    return (filtered.toString());
  }

  private static boolean hasSpecialChars(String input) {
    boolean flag = false;
    if ((input != null) && (input.length() > 0)) {
      char c;
      for (int i = 0; i < input.length(); i++) {
        c = input.charAt(i);
        switch (c) {
          case '<':
            flag = true;
            break;
          case '>':
            flag = true;
            break;
          case '"':
            flag = true;
            break;
          case '&':
            flag = true;
            break;
        }
      }
    }
    return (flag);
  }
}
