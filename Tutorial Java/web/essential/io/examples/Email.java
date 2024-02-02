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

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

/**
 * El Ejemplo monitoriza un directorio especificado por ficheros nuevos.
 * Si un fichero añadido nuevo es un fichero de texto plano, el fichero puede
 * ser enviado por correo al alias apropiado. Los detalles del envío de correo
 * son dejados al lector. Lo que el ejemplo en realidad hace es
 * imprimir los detalles a la salida estándar.
 */
// fuente original: Email.java
public class Email {

    private final WatchService monitor;
    private final Path dir;

    /**
     * Crea un WatchService y registra el directorio dado
     */
    Email(Path dir) throws IOException {
        this.monitor = FileSystems.getDefault().newWatchService();
        dir.register(monitor, ENTRY_CREATE);
        this.dir = dir;
    }

    /**
     * Procesa todos los eventos para la clave encolada en el monitor.
     */
    void procesaEventos() {
        for (;;) {

            // espera para que la clave sea señalizada.
            WatchKey clave;
            try {
                clave = monitor.take();
            } catch (InterruptedException x) {
                return;
            }

            for (WatchEvent<?> event: clave.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                //El nombre del fichero es el contexto del evento.
                WatchEvent<Path> ev = (WatchEvent<Path>)event;
                Path nombrefichero = ev.context();

                //Verficamos que el nuevo fichero es un fichero de texto.
                try {
                    Path hijo = dir.resolve(nombrefichero);
                    if (!Files.probeContentType(hijo).equals("text/plain")) {
                        System.err.format("El nuevo fichero '%s' no es un fichero de texto plano.%n", nombrefichero);
                        continue;
                    }
                } catch (IOException x) {
                    System.err.println(x);
                    continue;
                }

                //Enviamos por correo el fichero al alias de correo especificado.
                System.out.format("Enviando por correo el fichero %s%n", nombrefichero);
                //Los detalles son dejados al lector....
            }

            //Restablecemos la clave -- este paso es crítico si quiere recibir 
            //más eventos de vigilancia. Si la clave no es ya válida, el directorio
            //es inacessible de forma que salimos del bucle.
            boolean valid = clave.reset();
            if (!valid) {
                    break;
            }
        }
    }

    static void uso() {
        System.err.println("uso: java Email dir");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {
        //analizamos argumentos
        if (args.length < 1)
            uso();

        //registramos el directorio y procesamos sus eventos
        Path dir = Paths.get(args[0]);
        new Email(dir).procesaEventos();
    }
}
