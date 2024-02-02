package collections.algorithms.examples;

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
import java.io.*;
import java.util.*;

public class Anagrams2 {

    public static void main(String[] args) {
        int minTamGrupo = Integer.parseInt(args[1]);

        // Lee las palabras del archivo y las pone en un multimapa simulado
        Map<String, List<String>> m
                = new HashMap<>();
        try {
            Scanner s = new Scanner(new File(args[0]));
            while (s.hasNext()) {
                String palabra = s.next();
                String alfa = alphabetize(palabra);
                List<String> l = m.get(alfa);
                if (l == null) {
                    m.put(alfa, l = new ArrayList<>());
                }
                l.add(palabra);
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        // Hace una lista de todos los grupos de permutación por encima del umbral de tamaño
        List<List<String>> ganadores = new ArrayList<>();
        for (List<String> l : m.values()) {
            if (l.size() >= minTamGrupo) {
                ganadores.add(l);
            }
        }

        // Ordena los grupos de permutación según el tamaño
        Collections.sort(ganadores, new Comparator<List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                return o2.size() - o1.size();
            }
        });

        // Visualiza los grupos de permutación
        for (List<String> l : ganadores) {
            System.out.println(l.size() + ": " + l);
        }
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
