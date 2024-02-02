package java.IandI.examples;

/*
 * Copyright (c) 2008,2012 Oracle and/or its affiliates. All rights reserved.
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

// Fuente original: CharSequenceDemo.java
// DemoSecuenciaCaracter presenta un valor de Cadena - invertido.
public class DemoSecuenciaCaracter implements CharSequence {

    private String s;

    public DemoSecuenciaCaracter(String s) {
        // Sería mucho más eficiente justo invertir la cadena 
        // en el constructor. ¡Pero un montón menos divertido!
        this.s = s;
    }

    // Si la cadena está invertida, ¡el fin es el principio!
    private int fromEnd(int i) {
        return s.length() - 1 - i;
    }

    public char charAt(int i) {
        if ((i < 0) || (i >= s.length())) {
            throw new StringIndexOutOfBoundsException(i);
        }
        return s.charAt(fromEnd(i));
    }

    public int length() {
        return s.length();
    }

    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > s.length()) {
            throw new StringIndexOutOfBoundsException(end);
        }
        if (start > end) {
            throw new StringIndexOutOfBoundsException(start - end);
        }
        StringBuilder sub
                = new StringBuilder(s.subSequence(fromEnd(end), fromEnd(start)));
        return sub.reverse();
    }

    public String toString() {
        StringBuilder s = new StringBuilder(this.s);
        return s.reverse().toString();
    }

    //Random int de 0 a máximo. Como random() genera valores entre 0 y 0.9999
    private static int random(int max) {
        return (int) Math.round(Math.random() * (max + 1));
    }

    public static void main(String[] args) {
        
        // Texto original : Write a class that implements the CharSequence interface found in the java.lang package.
        DemoSecuenciaCaracter s
                = new DemoSecuenciaCaracter("Escriba una clase que implemente la interfaz CharSequence encontrada en el paquete java.lang.");

        //ejercicio charAt() y length()
        for (int i = 0; i < s.length(); i++) {
            System.out.print(s.charAt(i));
        }

        System.out.println("");

        //ejercicio subSequence() y length();
        int inicio = random(s.length() - 1);
        int fin = random(s.length() - 1 - inicio) + inicio;
        System.out.println(s.subSequence(inicio, fin));

        //ejercicio toString();
        System.out.println(s);

    }
}
