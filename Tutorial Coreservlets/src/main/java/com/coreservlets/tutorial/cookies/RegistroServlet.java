package com.coreservlets.tutorial.cookies;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegServlet", urlPatterns = {"/handlingCookies/registraFormulario"})
public class RegistroServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final String VALOR_POR_DEFECTO = "Desconocido";

  public RegistroServlet() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {

    String html = ServletUtilities.headWithTitle(
            "Ejercicio 2 - Solución enunciado 2", "UTF-8");
    String nombre = request.getParameter("nombre");
    String apellidos = request.getParameter("apellidos");
    String correo = request.getParameter("correo");

    StringBuilder sb = new StringBuilder();

    Cookie ckNombre;
    Cookie ckApellidos;
    Cookie ckCorreo;

    sb.append(html);

    // Recojemos las posibles cookies, y en su defecto, se devuelve
    // "Desconocido"
    // siempre que el usuario no haya introducido nada en el formulario
    if (nombre == null || nombre.contentEquals("")) {
      nombre = CookieUtilities.getCookieValue(request, "nombre", "");
      if (nombre == null || nombre.contentEquals("")) {
        ckNombre = new Cookie("nombre", VALOR_POR_DEFECTO);
      } else {
        ckNombre = CookieUtilities.getCookie(request, "nombre");
      }
    } else {
      ckNombre = new Cookie("nombre", nombre);
    }
    response.addCookie(ckNombre);
    if (apellidos == null || apellidos.contentEquals("")) {
      apellidos = CookieUtilities
              .getCookieValue(request, "apellidos", "");
      if (apellidos == null || apellidos.contentEquals("")) {
        ckApellidos = new Cookie("apellidos", VALOR_POR_DEFECTO);
      } else {
        ckApellidos = CookieUtilities.getCookie(request, "apellidos");
      }
    } else {
      ckApellidos = new Cookie("apellidos", apellidos);
    }
    response.addCookie(ckApellidos);
    if (correo == null || correo.contentEquals("")) {
      correo = CookieUtilities.getCookieValue(request, "correo", "");
      if (correo == null || correo.contentEquals("")) {
        ckCorreo = new Cookie("correo", VALOR_POR_DEFECTO);
      } else {
        ckCorreo = CookieUtilities.getCookie(request, "correo");
      }
    } else {
      ckCorreo = new Cookie("correo", correo);
    }
    response.addCookie(ckCorreo);

    // Construimos la respuesta, reconstruyendo el formulario
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    sb.append("<body>\n")
            .append("<h1>Ejercicio 2</h1>\n")
            .append("<h2>Solución enunciado 2</h2>\n")
            .append("<br>\n")
            .append("<form action='registraFormulario' method='post' accept-charset='UTF-8' enctype='application/x-www-form-urlencoded'>\n")
            .append("<fieldset>\n")
            .append("<legend>Datos de registro</legend>\n")
            .append("<div>\n")
            .append("<label for='nombre'>Nombre :<br/>\n")
            .append("<input type='text' id='nombre' name='nombre' value='")
            .append(nombre)
            .append("' maxlength='40'/>\n")
            .append("</label>\n")
            .append("</div>\n")
            .append("<div>\n")
            .append("<label for='apellidos'>Apellidos :<br/>\n")
            .append("<input type='text' id='apellidos' name='apellidos' value='")
            .append(apellidos)
            .append("' maxlength='60'/>\n")
            .append("</label>\n")
            .append("</div>\n")
            .append("<div>\n")
            .append("<label for='correo'>Correo electrónico :<br/>\n")
            .append("<input type='text' id='correo' name='correo' value='")
            .append(correo)
            .append("' maxlength='30'/>\n")
            .append("</label>\n")
            .append("</div>\n")
            .append("<hr>\n")
            .append("<div style='text-align: center;'>\n")
            .append("<input type='submit' id='enviar' name='enviar' value='Enviar' />\n")
            .append("</div>\n").append("</fieldset>\n").append("</form>\n")
            .append("<br>\n").append(ServletUtilities.backToIndex())
            .append(ServletUtilities.htmlClose());
    html = sb.toString();
    out.println(html);
  }

}
