package com.coreservlets.tutorial.sessiontracking;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Pedidos", urlPatterns = {"/sessionTracking/mostrarElementos"})
public class MuestraPedidosServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public MuestraPedidosServlet() {
    super();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
  }

  @Override
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    String titulo = "Solucion Enunciado 4 - Mostrar Historial";
    String codificacion = "UTF-8";
    String clave;

    HttpSession session = request.getSession();
    synchronized (session) {
      @SuppressWarnings("unchecked")
      HashMap<String, Integer> itemsAnteriores = (HashMap<String, Integer>) session
              .getAttribute("itemsAnteriores");
      if (itemsAnteriores == null) {
        itemsAnteriores = new HashMap<String, Integer>();
      }
      String nuevoItem = request.getParameter("nuevoElemento");
      if ((nuevoItem != null) && (!nuevoItem.trim().equals(""))) {
        Set<Entry<String, Integer>> es = itemsAnteriores.entrySet();
        Iterator<Entry<String, Integer>> i = es.iterator();
        boolean existe = false;
        while (i.hasNext()) {
          Entry<String, Integer> me = (Map.Entry<String, Integer>) i
                  .next();
          clave = (String) me.getKey();
          if (clave.equalsIgnoreCase(nuevoItem)) {
            Integer valor = me.getValue();
            valor = valor + 1;
            me.setValue(valor);
            existe = true;
          }
        }
        if (!existe) {
          itemsAnteriores.put(nuevoItem, 1);
        }
      }

      session.setAttribute("itemsAnteriores", itemsAnteriores);

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String html = ServletUtilities.headWithTitle(titulo, codificacion);
      out.println(html + "<body>\n" + "<h1>" + titulo + "</h1>");
      if (itemsAnteriores.isEmpty()) {
        out.println("<i>No hay páginas visitadas</i>");
      } else {
        out.println("<ul>");
        Set<Entry<String, Integer>> es = itemsAnteriores.entrySet();
        Iterator<Entry<String, Integer>> iter = es.iterator();

        while (iter.hasNext()) {
          Entry<String, Integer> me = (Map.Entry<String, Integer>) iter
                  .next();
          clave = (String) me.getKey();
          Integer valor = me.getValue();
          out.println("<li>" + clave + " : " + valor
                  + "</li>");
        }
        out.println("</ul>");
      }
      out.println("<p style='text-align:center;'>");
      out.println("<a href='ej4formulario.html'>\n");
      out.println("Volver a la hoja de pedidos\n");
      out.println("</a>\n");
      out.println("</p>\n");
      out.println(ServletUtilities.htmlClose());
    }

  }

  @Override
  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
