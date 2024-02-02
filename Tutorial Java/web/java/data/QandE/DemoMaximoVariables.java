package java.data.QandE;

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


// fuente original: MaxVariablesDemo.java
public class DemoMaximoVariables {
    public static void main(String args[]) {

        //enteros
        byte byteMasGrande = Byte.MAX_VALUE;
        short shortMasGrande = Short.MAX_VALUE;
        int integerMasGrande = Integer.MAX_VALUE;
        long longMasGrande = Long.MAX_VALUE;

        //números reales
        float floatMasGrande = Float.MAX_VALUE;
        double doubleMasGrande = Double.MAX_VALUE;

        //otros tipos primitivos
        char unCaracter = 'S';
        boolean unBooleano = true;

        //Los mostramos todos.
        System.out.println("El mayor valor para byte es "
                           + byteMasGrande + ".");
        System.out.println("El mayor valor para short es "
                           + shortMasGrande + ".");
        System.out.println("El mayor valor para integer es "
                           + integerMasGrande + ".");
        System.out.println("El mayor valor para long es "
                           + longMasGrande + ".");

        System.out.println("El mayor valor para float es "
                           + floatMasGrande + ".");
        System.out.println("El mayor valor para double es "
                           + doubleMasGrande + ".");

        if (Character.isUpperCase(unCaracter)) {
            System.out.println("El caracter " + unCaracter
                               + " está en mayúsculas");
        } else {
            System.out.println("El caracter " + unCaracter
                               + " está en minúsculas.");
        }
        System.out.println("El valor de unBooleano es "
                           + unBooleano + ".");
    }
}
