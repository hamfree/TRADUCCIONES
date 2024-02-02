package essential.io.examples;

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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.EOFException;


// fuente original : DataStreams
public class FlujosDeDatos {
    static final String ficheroDatos = "invoicedata";

    static final double[] precios = { 19.99, 9.99, 15.99, 3.99, 4.99 };
    static final int[] unidades = { 12, 8, 13, 29, 50 };
    static final String[] descripciones = { "Camiseta Java",
            "Taza Java",
            "Duke Jugando con Muñecas",
            "Broche de Java",
            "Llavero de Java" };

    public static void main(String[] args) throws IOException {

 
        DataOutputStream salida = null;
        
        try {
            salida = new DataOutputStream(new
                    BufferedOutputStream(new FileOutputStream(ficheroDatos)));

            for (int i = 0; i < precios.length; i ++) {
                salida.writeDouble(precios[i]);
                salida.writeInt(unidades[i]);
                salida.writeUTF(descripciones[i]);
            }
        } finally {
            salida.close();
        }

        DataInputStream entrada = null;
        double total = 0.0;
        try {
            entrada = new DataInputStream(new
                    BufferedInputStream(new FileInputStream(ficheroDatos)));

            double precio;
            int unidad;
            String descripcion;

            try {
                while (true) {
                    precio = entrada.readDouble();
                    unidad = entrada.readInt();
                    descripcion = entrada.readUTF();
                    System.out.format("Usted ordenó %d unidades de %s a $%.2f%n",
                            unidad, descripcion, precio);
                    total += unidad * precio;
                }
            } catch (EOFException e) { }
            System.out.format("Por un TOTAL de: $%.2f%n", total);
        }
        finally {
            entrada.close();
        }
    }
}
