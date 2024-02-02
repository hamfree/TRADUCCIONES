package essential.exceptions.examples;

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
import java.util.Vector;

// fuente original: ListOfNumbers2.java
public class ListaDeNumeros2 {
    private Vector<Integer> vector;
    private static final int TAMAÑO = 10;

    public ListaDeNumeros2() {
        vector = new Vector<Integer>(TAMAÑO);
        for (int i = 0; i < TAMAÑO; i++)
            vector.addElement(new Integer(i));

        this.leeLista("ficheroEntrada.txt");
        this.escribeLista();
    }

    public void leeLista(String nombreFichero) {
        String linea = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(nombreFichero, "r");
            while ((linea = raf.readLine()) != null) {
                Integer i = new Integer(Integer.parseInt(linea));
                System.out.println(i);
                vector.addElement(i);
            }
        } catch(FileNotFoundException fnf) {
            System.err.println("Fichero: " + nombreFichero + " no encontrado.");
        } catch (IOException io) {
            System.err.println(io.toString());
        }
    }

    public void escribeLista() {
        PrintWriter salida = null;

        try {
            salida = new PrintWriter(new FileWriter("ficheroSalida.txt"));
        
            for (int i = 0; i < vector.size(); i++)
                salida.println("Valor en: " + i + " = " + vector.elementAt(i));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Capturada ArrayIndexOutOfBoundsException: " +
                                 e.getMessage());
        } catch (IOException e) {
            System.err.println("Capturada IOException: " + e.getMessage());
        } finally {
            if (salida != null) {
                System.out.println("Cerrando PrintWriter");
                salida.close();
            } else {
                System.out.println("PrintWriter no abierto");
            }
        }
    }

    public static void main(String[] args) {
        new ListaDeNumeros2();
    }
}