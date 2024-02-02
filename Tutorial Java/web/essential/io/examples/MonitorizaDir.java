package essential.io.examples;

/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

/**
 * Ejemplo de monitorizar un directorio (o árbol) por el cambio de ficheros.
 */
// fuente original : WatchDir.java
public class MonitorizaDir {

    private final WatchService monitor;
    private final Map<WatchKey,Path> claves;
    private final boolean esRecursivo;
    private boolean traza = false;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> moldea(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    /**
     * Registra el directorio dado con el  WatchService
     */
    private void registra(Path dir) throws IOException {
        WatchKey clave = dir.register(monitor, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (traza) {
            Path prev = claves.get(clave);
            if (prev == null) {
                System.out.format("registra: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("actualiza: %s -> %s\n", prev, dir);
                }
            }
        }
        claves.put(clave, dir);
    }

    /**
     * Registra el directorio dadi, y todos sus subdirectorios, con el 
     * WatchService.
     */
    private void registraTodo(final Path inicio) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(inicio, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                registra(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Crea un WatchService y registra el directorio dado
     */
    MonitorizaDir(Path dir, boolean esRecursivo) throws IOException {
        this.monitor = FileSystems.getDefault().newWatchService();
        this.claves = new HashMap<WatchKey,Path>();
        this.esRecursivo = esRecursivo;

        if (esRecursivo) {
            System.out.format("Escaneando %s ...\n", dir);
            registraTodo(dir);
            System.out.println("Hecho.");
        } else {
            registra(dir);
        }

        // habilita el trazado después del registro inicial
        this.traza = true;
    }

    /**
     * Procesa todos los eventos para las claves encoladas en el watcher
     */
    void procesaEventos() {
        for (;;) {

            // espera por la clave a ser señalada
            WatchKey clave;
            try {
                clave = monitor.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = claves.get(clave);
            if (dir == null) {
                System.err.println("¡¡WatchKey no reconocida!!");
                continue;
            }

            for (WatchEvent<?> event: clave.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // TBD - ofrece un ejemplo de cómo el evento OVERFLOW es manejado
                if (kind == OVERFLOW) {
                    continue;
                }

                // Contexto por si el evento de entrada de directorio es el nombre de fichero de la entrada
                WatchEvent<Path> ev = moldea(event);
                Path nombre = ev.context();
                Path hijo = dir.resolve(nombre);

                // imprime el evento
                System.out.format("%s: %s\n", event.kind().name(), hijo);

                // si el directorio es creado, y se monitoriza recursivamente, entonces
                // lo registra y a sus subdirectorios
                if (esRecursivo && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(hijo, NOFOLLOW_LINKS)) {
                            registraTodo(hijo);
                        }
                    } catch (IOException x) {
                        // se ignora para mantener el ejemplo legible
                    }
                }
            }

            // restablece la clave y borra del conjunto si el directorio ya no es accesible
            boolean esValido = clave.reset();
            if (!esValido) {
                claves.remove(clave);

                // todos los directorios son inaaccesibles
                if (claves.isEmpty()) {
                    break;
                }
            }
        }
    }

    static void uso() {
        System.err.println("uso: java MonitorizaDir [-r] dir");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {
        // analiza los argumentos
        if (args.length == 0 || args.length > 2)
            uso();
        boolean esRecursivo = false;
        int dirArg = 0;
        if (args[0].equals("-r")) {
            if (args.length < 2)
                uso();
            esRecursivo = true;
            dirArg++;
        }

        // registra el directorio y procesa sus eventos
        Path dir = Paths.get(args[dirArg]);
        new MonitorizaDir(dir, esRecursivo).procesaEventos();
    }
}
