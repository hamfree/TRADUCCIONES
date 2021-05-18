package com.coreservlets.tutorial.beans;

public class ColorBeanTester {

  public static void main(String[] args) {
    ColorBean colorBean = new ColorBean();
    System.out.println("Original foreground color: "
            + colorBean.getForegroundColor());
    System.out.println("Original background color: "
            + colorBean.getBackgroundColor());
    if (args.length > 1) {
      colorBean.setForegroundColor(args[0]);
      colorBean.setBackgroundColor(args[1]);
      System.out.println("New foreground color: "
              + colorBean.getForegroundColor());
      System.out.println("New background color: "
              + colorBean.getBackgroundColor());
    }
  }
}
