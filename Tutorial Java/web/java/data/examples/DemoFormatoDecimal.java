package java.data.examples;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 


import java.util.*;
import java.text.*;

// fuente original : DecimalFormatDemo.java
public class DemoFormatoDecimal {

   static public void formatoPersonalizado(String patron, double valor ) {
      DecimalFormat miFormateador = new DecimalFormat(patron);
      String output = miFormateador.format(valor);
      System.out.println(valor + "  " + patron + "  " + output);
   }

   static public void formatoLocalizado(String patron, double valor, 
                                      Locale loc ) {
      NumberFormat nf = NumberFormat.getNumberInstance(loc);
      DecimalFormat df = (DecimalFormat)nf;
      df.applyPattern(patron);
      String salida = df.format(valor);
      System.out.println(patron + "  " + salida + "  " + loc.toString());
   }

   static public void main(String[] args) {

      formatoPersonalizado("###,###.###", 123456.789);
      formatoPersonalizado("###.##", 123456.789);
      formatoPersonalizado("000000.000", 123.78);
      formatoPersonalizado("$###,###.###", 12345.67);
      formatoPersonalizado("\u00a5###,###.###", 12345.67);

      Locale currentLocale = new Locale("en", "US");

      DecimalFormatSymbols simbolosInusuales = 
         new DecimalFormatSymbols(currentLocale);
      simbolosInusuales.setDecimalSeparator('|');
      simbolosInusuales.setGroupingSeparator('^');
      String extraño = "#,##0.###";
      DecimalFormat weirdFormatter = new DecimalFormat(extraño, simbolosInusuales);
      weirdFormatter.setGroupingSize(4);
      String bizarro = weirdFormatter.format(12345.678);
      System.out.println(bizarro);

      Locale[] locales = {
         new Locale("en", "US"),
         new Locale("de", "DE"),
         new Locale("fr", "FR"),
         new Locale("es", "ES")
      };

      for (int i = 0; i < locales.length; i++) {
         formatoLocalizado("###,###.###", 123456.789, locales[i]);
      }

   }
}

