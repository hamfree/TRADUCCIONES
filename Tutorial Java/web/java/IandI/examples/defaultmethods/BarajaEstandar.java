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

import java.util.*;
import java.util.stream.*;
import java.lang.*;

// Fuente original : StandardDeck.java
public class BarajaEstandar implements Baraja {

    private List<Carta> barajaEntera;

    public BarajaEstandar(List<Carta> listaExistente) {
        this.barajaEntera = listaExistente;
    }

    public BarajaEstandar() {
        this.barajaEntera = new ArrayList<>();
        for (Carta.Palo p : Carta.Palo.values()) {
            for (Carta.Rango r : Carta.Rango.values()) {
                this.barajaEntera.add(new JugandoCarta(r, p));
            }
        }
    }

    public Baraja barajaFactoria() {
        return new BarajaEstandar(new ArrayList<Carta>());
    }

    public int tamaño() {
        return barajaEntera.size();
    }

    public List<Carta> getCartas() {
        return barajaEntera;
    }

    public void añadeCarta(Carta carta) {
        barajaEntera.add(carta);
    }

    public void añadeCartas(List<Carta> cartas) {
        barajaEntera.addAll(cartas);
    }

    public void añadeBaraja(Baraja baraja) {
        List<Carta> listToAdd = baraja.getCartas();
        barajaEntera.addAll(listToAdd);
    }

    public void ordenar() {
        Collections.sort(barajaEntera);
    }

    public void ordenar(Comparator<Carta> c) {
        Collections.sort(barajaEntera, c);
    }

    public void barajar() {
        Collections.shuffle(barajaEntera);
    }

    public Map<Integer, Baraja> repartir(int jugadores, int numeroDeCartas)
            throws IllegalArgumentException {
        int cartasRepartidas = jugadores * numeroDeCartas;
        int tamañoDeLaBaraja = barajaEntera.size();
        if (cartasRepartidas > tamañoDeLaBaraja) {
            throw new IllegalArgumentException(
                    "Número de jugadores (" + jugadores
                    + ") número de veces de cartas a ser repartidas (" + numeroDeCartas
                    + ") es mayor que el número de cartas en la baraja ("
                    + tamañoDeLaBaraja + ").");
        }

        Map<Integer, List<Carta>> barajaRepartida = barajaEntera
                .stream()
                .collect(
                        Collectors.groupingBy(
                                carta -> {
                                    int indiceCarta = barajaEntera.indexOf(carta);
                                    if (indiceCarta >= cartasRepartidas) {
                                        return (jugadores + 1);
                                    } else {
                                        return (indiceCarta % jugadores) + 1;
                                    }
                                }));

        // Convert Map<Integer, List<Carta>> to Map<Integer, Baraja>
        Map<Integer, Baraja> mapa = new HashMap<>();

        for (int i = 1; i <= (jugadores + 1); i++) {
            Baraja currentDeck = barajaFactoria();
            currentDeck.añadeCartas(barajaRepartida.get(i));
            mapa.put(i, currentDeck);
        }
        return mapa;
    }

    public String barajaToString() {
        return this.barajaEntera
                .stream()
                .map(Carta::toString)
                .collect(Collectors.joining("\n"));
    }

    public static void main(String... args) {
        BarajaEstandar miBaraja = new BarajaEstandar();
        System.out.println("Creando baraja:");
        miBaraja.ordenar();
        System.out.println("Baraja ordenada");
        System.out.println(miBaraja.barajaToString());
        miBaraja.barajar();
        miBaraja.ordenar(new OrdenarPorRangoEntoncesPorPalo());
        System.out.println("Ordenado por rango, y luego por palo");
        System.out.println(miBaraja.barajaToString());
        miBaraja.barajar();
        miBaraja.ordenar(
                Comparator.comparing(Carta::getRango)
                .thenComparing(Comparator.comparing(Carta::getPalo)));
        System.out.println("Ordenado por rango, y luego por palo "
                + "con métodos estáticos y por defecto");
        System.out.println(miBaraja.barajaToString());
        miBaraja.ordenar(
                Comparator.comparing(Carta::getRango)
                .reversed()
                .thenComparing(Comparator.comparing(Carta::getPalo)));
        System.out.println("Ordenado por rango inverso, entonces por palo "
                + "con métodos estáticos y por defecto");
        System.out.println(miBaraja.barajaToString());
    }
}
