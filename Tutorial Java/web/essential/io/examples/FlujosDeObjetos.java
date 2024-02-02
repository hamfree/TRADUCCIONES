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
import java.io.*;
import java.math.BigDecimal;
import java.util.Calendar;

// fuente original: ObjectStreams.java
public class FlujosDeObjetos {

    static final String ficheroDatos = "invoicedata";

    static final BigDecimal[] precios = {
        new BigDecimal("19.99"),
        new BigDecimal("9.99"),
        new BigDecimal("15.99"),
        new BigDecimal("3.99"),
        new BigDecimal("4.99")};
    static final int[] unidades = {12, 8, 13, 29, 50};
    static final String[] descripciones = {"Camiseta Java",
        "Taza Java",
        "Duke Jugando con Muñecas",
        "Broche de Java",
        "Llavero de Java"};

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        ObjectOutputStream salida = null;
        try {
            salida = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(ficheroDatos)));

            salida.writeObject(Calendar.getInstance());
            for (int i = 0; i < precios.length; i++) {
                salida.writeObject(precios[i]);
                salida.writeInt(unidades[i]);
                salida.writeUTF(descripciones[i]);
            }
        } finally {
            salida.close();
        }

        ObjectInputStream entrada = null;
        try {
            entrada = new ObjectInputStream(new BufferedInputStream(new FileInputStream(ficheroDatos)));

            Calendar fecha = null;
            BigDecimal precio;
            int unidad;
            String descripcion;
            BigDecimal total = new BigDecimal(0);

            fecha = (Calendar) entrada.readObject();

            System.out.format("En %tA, %<tB %<te, %<tY:%n", fecha);

            try {
                while (true) {
                    precio = (BigDecimal) entrada.readObject();
                    unidad = entrada.readInt();
                    descripcion = entrada.readUTF();
                    System.out.format("Usted ordenó %d unidades de %s a $%.2f%n",
                            unidad, descripcion, precio);
                    total = total.add(precio.multiply(new BigDecimal(unidad)));
                }
            } catch (EOFException e) {
            }
            System.out.format("Por un TOTAL de: $%.2f%n", total);
        } finally {
            entrada.close();
        }
    }
}