package collections.interfaces.examples;

/*
* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

public class Deal {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: repartir cartas de manos");
            return;
        }
        int numManos = Integer.parseInt(args[0]);
        int cartasPorMano = Integer.parseInt(args[1]);

        // Construye una baraja normal de 52 cartas.
        String[] conjunto = new String[]{
            "oros", "copas", "espadas", "bastos"};
        String[] rango = new String[]{
            "as", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "sota", "reina", "rey"};
        List<String> baraja = new ArrayList<>();
        for (int i = 0; i < conjunto.length; i++) {
            for (int j = 0; j < rango.length; j++) {
                baraja.add(rango[j] + " de " + conjunto[i]);
            }
        }

        // Shuffle the deck.
        Collections.shuffle(baraja);

        if (numManos * cartasPorMano > baraja.size()) {
            System.out.println("No hay suficientes cartas.");
            return;
        }

        for (int i = 0; i < numManos; i++) {
            System.out.println(repartirLaMano(baraja, cartasPorMano));
        }
    }

    public static <E> List<E> repartirLaMano(List<E> deck, int n) {
        int deckSize = deck.size();
        List<E> handView = deck.subList(deckSize - n, deckSize);
        List<E> hand = new ArrayList<>(handView);
        handView.clear();
        return hand;
    }
}
