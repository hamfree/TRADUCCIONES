package java.javaOO.examples;

/*
 * Copyright (c) 2013, 2014, Oracle and/or its affiliates. All rights reserved.
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
 
public class EjemploClaseLocal {
  
    static String expresionRegular = "[^0-9]";
  
    public static void validaNumeroTelefono(
        String numeroTelefono1, String numeroTelefono2) {
      
        final int longitudNumero = 10;
        
        // Válido en JDK 8 y posteriores:
       
        // int longitudNumero = 10;
       
        class NumeroTelefono {
            
            String numeroTelefonoFormateado = null;

            NumeroTelefono(String numeroTelefono){
                // longitudNumero = 7;
                String numeroActual = numeroTelefono.replaceAll(expresionRegular, "");
                if (numeroActual.length() == longitudNumero)
                    numeroTelefonoFormateado = numeroActual;
                else
                    numeroTelefonoFormateado = null;
            }

            public String getNumber() {
                return numeroTelefonoFormateado;
            }
            
            // Válido en JDK 8 y posteriores:

//            public void imprimeNumerosOriginales() {
//                System.out.println("Los números originales son " + phoneNumber1 +
//                    " y " + phoneNumber2);
//            }
        }

        NumeroTelefono miNumero1 = new NumeroTelefono(numeroTelefono1);
        NumeroTelefono miNumero2 = new NumeroTelefono(numeroTelefono2);
        
        // Válido en JDK 8 y posteriores:

//        miNumero1.imprimeNumerosOriginales();

        if (miNumero1.getNumber() == null) 
            System.out.println("El primer número no es válido");
        else
            System.out.println("El primer número es " + miNumero1.getNumber());
        if (miNumero2.getNumber() == null)
            System.out.println("El segundo número no es válido");
        else
            System.out.println("El segundo número es " + miNumero2.getNumber());

    }

    public static void main(String... args) {
        validaNumeroTelefono("123-456-7890", "456-7890");
    }
}