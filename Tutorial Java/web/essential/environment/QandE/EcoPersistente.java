package essential.environment.QandE;

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

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//fuente original: PersistentEcho.java
public class EcoPersistente {
    public static void main (String[] args) {
        String argumentos = "";
        boolean sinPropiedad = true;

        // ¿Hay argumentos? 
        // Si es así recupéralos.
        if (args.length > 0) {
            for (String arg: args) {
                argumentos += arg + " ";
            }
            argumentos = argumentos.trim();
        }
        // Sin argumentos, ¿Hay 
        // una variable de entorno?
        // Si es así, recupérala.
        else if ((argumentos = System.getenv("ECOPERSISTENTE")) != null) {}
        // No hay variable de entorno
        // Recupera el valor de la propiedad.
        else {
            sinPropiedad = false;
            // Estabelece argumentos a null.
            // Si es aún null después
            // de que salgamos del try-catch ,
            // habremos fallado en recuperar
            // el valor de la propiedad.
            argumentos = null;
            FileInputStream ficheroEntrada = null;
            try {
                ficheroEntrada =
                    new FileInputStream("EcoPersistente.txt");
                Properties propEntrada
                    = new Properties();
                propEntrada.load(ficheroEntrada);
                argumentos = propEntrada.getProperty("argumentos");
            } catch (IOException e) {
                System.err.println("No puedo leer el fihcero de propiedades.");
                System.exit(1);
            } finally {
                if (ficheroEntrada != null) {
                    try {
                        ficheroEntrada.close();
                    } catch(IOException e) {};
                }
            }
        }
        if (argumentos == null) {
            System.err.println("No pude encontrar la propiedad 'argumentos'.");
            System.exit(1);
        }

        // De alguna forma, hemos obtenido el
        // valor. ¡Lo mostramos ya!
        System.out.println(argumentos);

        // Si no recuperamos el 
        // valor desde la propiedad,
        // lo salvamos en la propiedad.
        if (sinPropiedad) {
            Properties outProperties =
                new Properties();
            outProperties.setProperty("argString",
                                      argumentos);
            FileOutputStream ficheroSalida = null;
            try {
                ficheroSalida =
                    new FileOutputStream("EcoPersistente.txt");
                outProperties.store(ficheroSalida,
                        "Propiedades EcoPersistente");
            } catch (IOException e) {}
            finally {
                if (ficheroSalida != null) {
                    try {
                        ficheroSalida.close();
                    } catch(IOException e) {};
                }
            }
        }
    }
}

