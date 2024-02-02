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

// fuente original: Anagram.java
public class Anagrama {

    /**
     * Comprueba si las cadenas introducidas son anagramas --
     * conteniendo el mismo número exacto de cada letra.
     * La puntuación, el caso, y el orden no importan.
     * 
     * @return verdadero si las cadenas son anagramas; de otra forma, falso
     */
    public static boolean sonAnagramas(String cadena1,
                                      String cadena2) {

        String copiaTrabajo1 = eliminarDesperdicios(cadena1);
        String copiaTrabajo2 = eliminarDesperdicios(cadena2);

	    copiaTrabajo1 = copiaTrabajo1.toLowerCase();
	    copiaTrabajo2 = copiaTrabajo2.toLowerCase();

	    copiaTrabajo1 = ordena(copiaTrabajo1);
	    copiaTrabajo2 = ordena(copiaTrabajo2);

        return copiaTrabajo1.equals(copiaTrabajo2);
    }

    /**
     * Borra la puntuación & los espacios -- todo excepto
     * las letras de la cadena introducida.
     * 
     * @return una copia despojada de la cadena introducida
     */
    protected static String eliminarDesperdicios(String cadena) {
        int i, len = cadena.length();
        StringBuilder dest = new StringBuilder(len);
  		char c;

	    for (i = (len - 1); i >= 0; i--) {
	        c = cadena.charAt(i);
	        if (Character.isLetter(c)) {
		        dest.append(c);
	        }
	    }

        return dest.toString();
    }

    /** 
     * Ordena la cadena introducida.
     * 
     * @return una copia ordenada de la cadena introducida
     */
    protected static String ordena(String cadena) {
	    char[] matrizCaracteres = cadena.toCharArray();

	    java.util.Arrays.sort(matrizCaracteres);

        return new String(matrizCaracteres);
    }

    public static void main(String[] args) {
        String string1 = "Cosmo and Laine:";
        String string2 = "Maid, clean soon!";

        System.out.println();
        System.out.println("Comprobando si las siguientes "
                         + "cadenas son anagramas:");
        System.out.println("    Cadena 1: " + string1);
        System.out.println("    Cadena 2: " + string2);
        System.out.println();

        if (sonAnagramas(string1, string2)) {
            System.out.println("¡SON anagramas!");
        } else {
            System.out.println("¡NO son anagramas!");
        }
        System.out.println();
    }
}