package com.coreservlets.tutorial.cookies;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ColorServlet", urlPatterns = {"/handlingCookies/recogeColor"})
public class ColorServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final String COLORFONDODEFECTO = "#ffffff";
  private static final String COLORTEXTODEFECTO = "#000000";

  public ColorServlet() {
    super();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {

  }

  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {

    String html;
    String colorFondo = request.getParameter("colorFondo");
    String colorTexto = request.getParameter("colorTexto");
    Cookie ckcolorFondo;
    Cookie ckcolorTexto;
    StringBuilder sb = new StringBuilder();

    ckcolorFondo = CookieUtilities.getCookie(request, "colorFondo");
    if (ckcolorFondo == null) {
      ckcolorFondo = new Cookie("colorFondo", colorFondo);
    } else {
      if (colorFondo == null || colorFondo.contentEquals("")) {
        colorFondo = CookieUtilities.getCookieValue(request,
                "colorFondo", COLORFONDODEFECTO);
      } else {
        ckcolorFondo = new Cookie("colorFondo", colorFondo);
      }
    }
    ckcolorTexto = CookieUtilities.getCookie(request, "colorTexto");
    if (ckcolorTexto == null) {
      ckcolorTexto = new Cookie("colorTexto", colorTexto);
    } else {
      if (colorTexto == null || colorTexto.contentEquals("")) {
        colorTexto = CookieUtilities.getCookieValue(request,
                "colorTexto", COLORTEXTODEFECTO);
      } else {
        ckcolorTexto = new Cookie("colorTexto", colorTexto);
      }
    }

    response.addCookie(ckcolorTexto);
    response.addCookie(ckcolorFondo);

    sb.append(ServletUtilities
            .headWithTitle(
                    "Soluciones Ejercicio 7 - Solución Enunciado 3 - Seleccione colores",
                    "UTF-8"));
    sb.append("<body bgcolor='")
            .append(colorFondo)
            .append("' text='")
            .append(colorTexto)
            .append("' >\n")
            .append("<h1>Soluciones Ejercicios 7</h1>\n")
            .append("<h2>Página inicial - Solución Enunciado 3</h2>\n")
            .append("<p>\n")
            .append("3. Haga una pequeña página que muestre alguna información simple de su\n")
            .append("elección. Haga otra página que permita a los usuarios elegir el color\n")
            .append("de primer plano y fondo que ellos quieran usar en la primera página.\n")
            .append("Por ejemplo, si un usuario nunca visita la página de elección de\n")
            .append("colores, la página principal informante usaría colores por defecto de\n")
            .append("tu elección. Pero si un usuario visita la página de elección de color\n")
            .append("y elije un amarillo para el fondo y un rojo para el primer plano,\n")
            .append("todas las visitas subsiguientes a la página principal por el usuario\n")
            .append("resultarán en rojo sobre amarillo. No hay necesidad de revisar los\n")
            .append("colores; puede aceptar cualquier valor de color que el usuario le de.<br>\n")
            .append("Si está un poco oxidado con el HTML, puede fijar los colores como\n")
            .append("sigue:\n")
            .append("</p>\n")
            .append("<pre>\n")
            .append("&lt;BODY BGCOLOR='colorName' TEXT='colorName'&gt;\n")
            .append("</pre>\n")
            .append("o\n")
            .append("<pre>\n")
            .append("&lt;BODY BGCOLOR='#RRGGBB' TEXT='#RRGGBB'&gt;\n")
            .append("</pre>\n")
            .append("<p>\n")
            .append("(Donde R,G y B son valores hexadecimales para los componentes\n")
            .append("rojo, verde y azul. P.e. #FF00FF es magenta -- 255 para el rojo, 0\n")
            .append("para el verde y 255 para el azul).\n")
            .append("</p>\n")
            .append("<a href='ej3pagina1.html'>Seleccione sus colores favoritos para esta página</a>\n")
            .append("").append("<br>\n")
            .append(ServletUtilities.backToIndex())
            .append(ServletUtilities.htmlClose());
    html = sb.toString();
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.println(html);
  }

}
