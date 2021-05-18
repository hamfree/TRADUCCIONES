package com.coreservlets.tutorial.sessiontracking;

import java.io.IOException;

import javax.servlet.http.*;

public class ServletUtilities {

  /**
   *
   */
  public static final String DOCTYPE = "<!DOCTYPE html>";

  /**
   *
   * @param title
   * @return
   */
  public static String headWithTitle(String title) {
    return (DOCTYPE
            + "\n"
            + "<html>\n"
            + "<head>\n"
            + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"
            + "<title>" + title + "</title></title>\n");
  }

  /**
   *
   * @param title
   * @param codificacion
   * @return
   */
  public static String headWithTitle(String title, String codificacion) {
    StringBuilder sb = new StringBuilder();

    sb.append(DOCTYPE).append("\n").append("<html>\n")
            .append("<head>\n<title>\n");
    if (title != null) {
      sb.append(title);
    } else {
      sb.append("");
    }

    sb.append("\n</title>\n")
            .append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=");
    if (codificacion != null) {
      sb.append(codificacion);
    } else {
      sb.append("UTF-8");
    }
    sb.append("\">\n").append(ServletUtilities.styleInHead())
            .append("</head>\n");

    return (sb.toString());
  }

  /**
   *
   * @return
   */
  public static String backToIndex() {
    StringBuilder sb = new StringBuilder();
    sb.append("<br/>\n").append("<div align='center'>\n")
            .append("<a href='index.html'>Volver al inicio</a>\n")
            .append("</div>\n");
    return (sb.toString());
  }

  /**
   *
   * @param titulo
   * @return
   */
  public static String exerciseNotImplemented(String titulo) {
    String html = "<body>\n" + "<h2>" + titulo
            + "</h2>\n" + "<p style='text-align:center'>\n"
            + "Ejercicio aún no implementado\n" + "</p>\n"
            + ServletUtilities.backToIndex();
    return (html);
  }

  /**
   *
   * @param request
   * @param response
   * @param titulo
   * @param codificacion
   * @return
   * @throws IOException
   */
  public static String htmlExerciseNotImplemented(HttpServletRequest request,
          HttpServletResponse response, String titulo, String codificacion)
          throws IOException {
    String html;
    if (codificacion == null) {
      codificacion = "UTF-8";
    }
    if (titulo == null) {
      titulo = "";
    }
    response.setContentType("text/html");
    response.setCharacterEncoding(codificacion);
    html = ServletUtilities.headWithTitle(titulo, codificacion)
            + ServletUtilities.exerciseNotImplemented(titulo)
            + ServletUtilities.htmlClose();
    return html;
  }

  /**
   *
   * @return
   */
  public static String styleInHead() {
    String html = "<style type=\"text/css\">\n"
            + "h1 {text-align: center;}\n" + "h2 {text-align: center;}\n"
            + "h3 {text-align: center;}\n"
            + "body {font-family: Verdana, sans-serif;font-size: small;}\n"
            + "label {font-weight: bold;}\n" + "legend {\n"
            + "font-weight: bold;\n" + "font-size: medium;\n"
            + "background-color: #FF9966;\n" + "border: 1px solid black;\n"
            + "border-style: double;}\n" + "form {margin: 5px;}\n"
            + "input,select,textarea {background-color: #FFFFCC;}\n"
            + "</style>\n";
    return html;
  }

  /**
   *
   * @return
   */
  public static String htmlClose() {
    return "</body>\n</html>";
  }

  /**
   *
   * @param request
   * @param paramName
   * @param defaultValue
   * @return
   */
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

  /**
   *
   * @param request
   * @param paramName
   * @param defaultValue
   * @return
   */
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

  /**
   *
   * @param input
   * @return
   */
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

  /**
   *
   * @param input
   * @return
   */
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
