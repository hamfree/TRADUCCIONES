package com.coreservlets.tutorial.responseheaders;

import java.io.*;
import java.util.Random;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name = "genera", urlPatterns = {"/responseHeaders/generaExcel"})
public class GeneraExcelServlet extends HttpServlet {

  private static final long serialVersionUID = 4378306771088635654L;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    int filas = 10;
    int columnas = 5;
    double numero;
    StringBuilder sb = new StringBuilder();
    String excel;
    for (int f = 0; f < filas; f++) {
      for (int c = 0; c < columnas; c++) {
        numero = siguienteEnteroEnRango(0, 50, new Random());
        sb.append(numero).append("\t");
      }
      sb.append("\n");
    }
    excel = sb.toString();
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition",
            "attachment; filename=hoja.xls");
    PrintWriter out = response.getWriter();
    out.println(excel);

  }

  private int siguienteEnteroEnRango(int min, int max, Random rng) {
    if (min > max) {
      throw new IllegalArgumentException(
              "No puedo generar un entero aleatorio en el rango erróneo ["
              + min + ", " + max + "].");
    }
    int diff = max - min;
    if (diff >= 0 && diff != Integer.MAX_VALUE) {
      return (min + rng.nextInt(diff + 1));
    }
    int i;
    do {
      i = rng.nextInt();
    } while (i < min || i > max);
    return i;
  }
}
