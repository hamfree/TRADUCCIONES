package tutorialjava.essential.io.examples;

/*
 * Copyright (c) 2008, 2009, Oracle and/or its affiliates. All rights reserved.
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
 *   - Neither the name of Oracle nor the names of its
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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;

/**
 * Código de ejemplo para listar/establecer/obtener/borrar los atributos definidos-por-el-usuario de un fichero.
 */
// fuente original: Xdd.java
public class Xdd {

    static void uso() {
        System.out.println("Uso:  java Xdd <fichero>");
        System.out.println("      java Xdd -set <nombre>=<valor> <fichero>");
        System.out.println("      java Xdd -get <nombre> <fichero>");
        System.out.println("      java Xdd -del <nombre> <fichero>");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {
        // uno o tres parámetros
        if (args.length != 1 && args.length != 3)
            uso();

        Path fichero = (args.length == 1) ?
            Paths.get(args[0]) : Paths.get(args[2]);

        // comprueba que el usuario definión atributos que son soportados por el almacén del fichero
        FileStore almacen = Files.getFileStore(fichero);
        if (!almacen.supportsFileAttributeView(UserDefinedFileAttributeView.class)) {
            System.err.format("UserDefinedFileAttributeView no soportada en %s\n", almacen);
            System.exit(-1);

        }
        UserDefinedFileAttributeView vista = Files.
            getFileAttributeView(fichero, UserDefinedFileAttributeView.class);

        // lista los atributos definidos por el usuario
        if (args.length == 1) {
            System.out.println("  Tamaño  Nombre");
            System.out.println("--------  --------------------------------------");
            for (String nombre: vista.list()) {
                System.out.format("%8d  %s\n", vista.size(nombre), nombre);
            }
            return;
        }

        // Añade/reemplaza un atributo definido por el usuario de un fichero
        if (args[0].equals("-set")) {
            // nombre=valor
            String[] s = args[1].split("=");
            if (s.length != 2)
                uso();
            String nombre = s[0];
            String valor = s[1];
            vista.write(nombre, Charset.defaultCharset().encode(valor));
            return;
        }

        // Imprime el valor de un atributo definido por el usuario de un fichero
        if (args[0].equals("-get")) {
            String nombre = args[1];
            int tamaño = vista.size(nombre);
            ByteBuffer buf = ByteBuffer.allocateDirect(tamaño);
            vista.read(nombre, buf);
            buf.flip();
            System.out.println(Charset.defaultCharset().decode(buf).toString());
            return;
        }

        // Borra un atributo definido por el usuario de un fichero
        if (args[0].equals("-del")) {
            vista.delete(args[1]);
            return;
        }

        // opción no reconocida
        uso();
    }
 }
