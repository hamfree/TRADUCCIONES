package com.coreservlets.tutorial.scriptingjsp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Dos métodos estáticos para utilizar en el manejo de cookies.
 */
public class CookieUtilities {

  /**
   * Dado el objeto request, un nombre, y un valor por defecto este método intenta encontrar el valor de la cookie con el nombre
   * ddado.Si ninguna cookie coincide con el nombre, el valor por defecto es devuelto.
   *
   * @param request
   * @param cookieName
   * @param defaultValue
   * @return
   */
  public static String getCookieValue(HttpServletRequest request,
          String cookieName, String defaultValue) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookieName.equals(cookie.getName())) {
          return (cookie.getValue());
        }
      }
    }
    return (defaultValue);
  }

  /**
   * Dado el objeto request (petición) y un nombre, este método intenta encontrar y devolver la cookie que tiene el nombre dado.Si
   * ninguna cookie coincide con el nombre, se devuelve null.
   *
   * @param request
   * @param cookieName
   * @return
   */
  public static Cookie getCookie(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookieName.equals(cookie.getName())) {
          return (cookie);
        }
      }
    }
    return (null);
  }
}
