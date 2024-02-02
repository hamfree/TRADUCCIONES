/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.IandI.examples.defaultmethods;

// Fuente original : Card.java
public interface Carta extends Comparable<Carta> {

    public enum Palo {
        DIAMANTES (1, "Diamantes"),
        TREBOLES  (2, "Tréboles" ),
        CORAZONES (3, "Corazones"),
        PICAS     (4, "Picas"    );

        private final int valor;
        private final String texto;

        Palo(int valor, String texto) {
            this.valor = valor;
            this.texto = texto;
        }

        public int valor() {
            return valor;
        }

        public String texto() {
            return texto;
        }
    }

    public enum Rango {
        DOS    (2, "Dos"    ),
        TRES   (3, "Tres"   ),
        CUATRO (4, "Cuatro" ),
        CINCO  (5, "Cinco"  ),
        SEIS   (6, "Seis"   ),
        SIETE  (7, "Siete"  ),
        OCHO   (8, "Ocho"   ),
        NUEVE  (9, "Nueve"  ),
        DIEZ   (10, "Diez"  ),
        SOTA   (11, "Sota"  ),
        REINA  (12, "Reina" ),
        REY    (13, "Rey"   ),
        AS     (14, "As"    );
        private final int valor;
        private final String texto;

        Rango(int valor, String texto) {
            this.valor = valor;
            this.texto = texto;
        }

        public int valor() {
            return valor;
        }

        public String texto() {
            return texto;
        }
    }

    public Carta.Palo getPalo();

    public Carta.Rango getRango();
}
