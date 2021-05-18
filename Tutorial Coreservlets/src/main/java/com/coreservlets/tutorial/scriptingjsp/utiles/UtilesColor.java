package com.coreservlets.tutorial.scriptingjsp.utiles;

public class UtilesColor {

  public static String colorAleatorio() {
    if (Math.random() < 0.5) {
      return ("BLUE");
    } else {
      return ("RED");
    }
  }
}
