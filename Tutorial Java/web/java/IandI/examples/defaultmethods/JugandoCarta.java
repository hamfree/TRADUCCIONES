/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

// Fuente original : PlayingCard.java
public class JugandoCarta implements Carta {

    private Carta.Rango rango;
    private Carta.Palo palo;

    public JugandoCarta(Carta.Rango rank, Carta.Palo suit) {
        this.rango = rank;
        this.palo = suit;
    }

    public Carta.Palo getPalo() {
        return palo;
    }

    public Carta.Rango getRango() {
        return rango;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Carta) {
            if (((Carta) obj).getRango() == this.rango
                    && ((Carta) obj).getPalo() == this.palo) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return ((palo.valor() - 1) * 13) + rango.valor();
    }

    public int compareTo(Carta o) {
        return this.hashCode() - o.hashCode();
    }

    public String toString() {
        return this.rango.texto() + " de " + this.palo.texto();
    }

    public static void main(String... args) {
        new JugandoCarta(Rango.AS, Palo.DIAMANTES);
        new JugandoCarta(Rango.REY, Palo.PICAS);
    }
}