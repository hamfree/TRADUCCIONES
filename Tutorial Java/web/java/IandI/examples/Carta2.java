package java.IandI.examples;

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
// fuente original : Card2.java
public class Carta2 {

    private int rango;
    private int palo;

    public final static int DIAMANTES = 1;
    public final static int TREBOLES = 2;
    public final static int CORAZONES = 3;
    public final static int PICAS = 4;

    public final static int AS = 1;
    public final static int DOS = 2;
    public final static int TRES = 3;
    public final static int CUATRO = 4;
    public final static int CINCO = 5;
    public final static int SEIS = 6;
    public final static int SIETE = 7;
    public final static int OCHO = 8;
    public final static int NUEVE = 9;
    public final static int DIEZ = 10;
    public final static int SOTA = 11;
    public final static int REINA = 12;
    public final static int REY = 13;

    public Carta2(int rango, int palo) {
        this.rango = rango;
        this.palo = palo;
    }

    public int getPalo() {
        return palo;
    }

    public int getRango() {
        return rango;
    }

    public static boolean esRangoValido(int rango) {
        return AS <= rango && rango <= REY;
    }

    public static boolean esPaloValido(int palo) {
        return DIAMANTES <= palo && palo <= PICAS;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Carta2) {
            if (((Carta2) obj).getRango() == this.rango
                    && ((Carta2) obj).getPalo() == this.palo) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return ((palo - 1) * 13) + rango;
    }

    public String toString() {
        return rangoToString(this.rango) + " de "
                + paloToString(this.palo);
    }

    public static String rangoToString(int rango) {
        switch (rango) {
            case AS:
                return "As";
            case DOS:
                return "Dos";
            case TRES:
                return "Tres";
            case CUATRO:
                return "Cuatro";
            case CINCO:
                return "Cinco";
            case SEIS:
                return "Seis";
            case SIETE:
                return "Siete";
            case OCHO:
                return "Ocho";
            case NUEVE:
                return "Nueve";
            case DIEZ:
                return "Diez";
            case SOTA:
                return "Sota";
            case REINA:
                return "Reina";
            case REY:
                return "Rey";
            default:
                //Maneja un argumento ilegal. Hay generalmente dos 
                //formas de manejar argumentos no válidos, lanzando una excepción
                //(vea la sección de Manejar Excepciones) o devolver null
                throw new IllegalArgumentException("Rango no válido " + rango);
            //o
            //return null;
        }
    }

    public static String paloToString(int palo) {
        String resultado = "";
        switch (palo) {
            case DIAMANTES:
                return "Diamonds";
            case TREBOLES:
                return "Clubs";
            case CORAZONES:
                return "Hearts";
            case PICAS:
                return "Spades";
            default:
                throw new IllegalArgumentException("Invalid palo " + palo);
        }
    }

    public static void main(String... args) {
        new Carta2(AS, DIAMANTES);
        new Carta2(REY, PICAS);
    }
}
