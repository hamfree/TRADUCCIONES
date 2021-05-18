package com.coreservlets.tutorial.sessiontracking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Registro", urlPatterns = {"/sessionTracking/registrar"})
public class RegistroServlet extends HttpServlet {

  private static final long serialVersionUID = 8476344025221606461L;
  private static final String DESC = "Desconocido";

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
    HttpSession sesion;
    String titulo = "08-Ejercicios-Session-Tracking - Solución Enunciado 2";
    String html;
    String nombre;
    String apellidos;
    String correo;
    String nombreReq;
    String apellidosReq;
    String correoReq;

    StringBuilder sb = new StringBuilder();

    sesion = request.getSession();
    synchronized (sesion) {
      nombreReq = request.getParameter("nombre");
      apellidosReq = request.getParameter("apellidos");
      correoReq = request.getParameter("correo");

      nombreReq = (nombreReq == null || nombreReq.contentEquals("")) ? DESC
              : nombreReq.trim();
      apellidosReq = (apellidosReq == null || apellidosReq
              .contentEquals("")) ? DESC : apellidosReq.trim();
      correoReq = (correoReq == null || correoReq.contentEquals("")) ? DESC
              : correoReq.trim();

      if (sesion.getAttribute("nombre") != null) {
        nombre = (String) sesion.getAttribute("nombre");
        if (!nombreReq.equalsIgnoreCase(nombre)) {
          if (!nombreReq.isEmpty()
                  && !nombreReq.equalsIgnoreCase(DESC)) {
            nombre = nombreReq;
          }
        }
      } else {
        nombre = nombreReq;
      }

      if (sesion.getAttribute("apellidos") != null) {
        apellidos = (String) sesion.getAttribute("apellidos");
        if (!apellidosReq.equalsIgnoreCase(apellidos)) {
          if (!apellidosReq.isEmpty()
                  && !apellidosReq.equalsIgnoreCase(DESC)) {
            apellidos = apellidosReq;
          }
        }
      } else {
        apellidos = apellidosReq;
      }

      if (sesion.getAttribute("correo") != null) {
        correo = (String) sesion.getAttribute("correo");
        if (!correoReq.equalsIgnoreCase(correo)) {
          if (!correoReq.isEmpty()
                  && !correoReq.equalsIgnoreCase(DESC)) {
            correo = correoReq;
          }
        }
      } else {
        correo = correoReq;
      }

      sesion.setAttribute("nombre", nombre);
      sesion.setAttribute("apellidos", apellidos);
      sesion.setAttribute("correo", correo);

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      // Montamos el cuerpo, con el formulario 'dinámico'...
      sb.setLength(0);
      sb.append(ServletUtilities.headWithTitle(titulo, "UTF-8"));
      sb.append("<body>\n")
              .append("<h1>08-Ejercicios Rastreo Sesión</h1>\n")
              .append("<h2>Solución Enunciado 2</h2>\n")
              .append("<p style='text-align: center;'>\n")
              .append("Introduzca sus datos de registro,por favor.\n")
              .append("</p>\n")
              .append("<form action='registrar' method='post'>\n")
              .append("<fieldset>\n")
              .append("<legend>Registro de usuario</legend>\n")
              .append("<div>\n<label for='nombre'>\nNombre :<br/>\n")
              .append("<input type='text' id='nombre' name='nombre' value='")
              .append(nombre)
              .append("' maxlength='40'/>\n")
              .append("</label>\n</div>\n<hr>\n")
              .append("<div>\n<label for='apellidos'>\nApellidos :<br/>\n")
              .append("<input type='text' id='apellidos' name='apellidos' value='")
              .append(apellidos)
              .append("' maxlength='60'/>\n")
              .append("</label>\n</div>\n<hr>\n")
              .append("<div>\n<label for='correo'>\nCorreo electrónico :<br/>\n")
              .append("<input type='text' id='correo' name='correo' value='")
              .append(correo)
              .append("' maxlength='20'/>\n")
              .append("</label>\n</div>\n<hr>\n")
              .append("<div style='text-align: center;'>\n")
              .append("<input type='submit' id='enviar' name='enviar' value='Enviar' />\n")
              .append("</div>\n</fieldset>\n</form>\n")
              .append(ServletUtilities.backToIndex())
              .append(ServletUtilities.htmlClose());
      html = sb.toString();
      out.println(html);
    }
  }
}
