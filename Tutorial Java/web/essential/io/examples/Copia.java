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
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import java.io.IOException;
import java.util.*;

/**
 * Código de ejemplo que copia ficheros en una manera similar al programa cp(1).
 */
// Fuente original: Copy.java
public class Copia {

    /**
     * Devuelve {@code true} si es ok sobreescribir un fichero ("cp -i")
     */
    static boolean puedoSobreescribir(Path file) {
        String answer = System.console().readLine("sobreescribir %s (si/no)? ", file);
        return (answer.equalsIgnoreCase("s") || answer.equalsIgnoreCase("si"));
    }

    /**
     * Copia el fichero origen a la localización de destino. Si {@code prompt} es true entonces
     * pregunta al usuario si se sobreescribe el destino si existe. El parámetro 
     * {@code preserve} determina si los atributos de fichero serán copiados/preservados.
     */
    static void copiaFichero(Path origen, Path destino, boolean prompt, boolean sePreserva) {
        CopyOption[] options = (sePreserva) ?
            new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING } :
            new CopyOption[] { REPLACE_EXISTING };
        if (!prompt || Files.notExists(destino) || puedoSobreescribir(destino)) {
            try {
                Files.copy(origen, destino, options);
            } catch (IOException x) {
                System.err.format("No se puede copiar: %s: %s%n", origen, x);
            }
        }
    }

    /**
     * Un {@code FileVisitor} que copia un árbol-de-ficheros ("cp -r")
     */
    static class CopiadorDeArbol implements FileVisitor<Path> {
        private final Path origen;
        private final Path destino;
        private final boolean sePregunta;
        private final boolean sePreserva;

        CopiadorDeArbol(Path origen, Path destino, boolean sePregunta, boolean sePreserva) {
            this.origen = origen;
            this.destino = destino;
            this.sePregunta = sePregunta;
            this.sePreserva = sePreserva;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            // antes de visitar las entradas en un directorio copiamos el directorio
            // (Ok si el directorio ya existe).
            CopyOption[] opciones = (sePreserva) ?
                new CopyOption[] { COPY_ATTRIBUTES } : new CopyOption[0];

            Path nuevoDir = destino.resolve(origen.relativize(dir));
            try {
                Files.copy(dir, nuevoDir, opciones);
            } catch (FileAlreadyExistsException x) {
                // se ignora
            } catch (IOException x) {
                System.err.format("No se puede crear: %s: %s%n", nuevoDir, x);
                return SKIP_SUBTREE;
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            copiaFichero(file, destino.resolve(origen.relativize(file)),
                     sePregunta, sePreserva);
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            // Arregla el tiempo de modificación del directorio al finalizar
            if (exc == null && sePreserva) {
                Path newdir = destino.resolve(origen.relativize(dir));
                try {
                    FileTime tiempo = Files.getLastModifiedTime(dir);
                    Files.setLastModifiedTime(newdir, tiempo);
                } catch (IOException x) {
                    System.err.format("No se pueden copiar todos los atributos en: %s: %s%n", newdir, x);
                }
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            if (exc instanceof FileSystemLoopException) {
                System.err.println("Ciclo detectado: " + file);
            } else {
                System.err.format("No se puede copiar: %s: %s%n", file, exc);
            }
            return CONTINUE;
        }
    }

    static void uso() {
        System.err.println("java Copia [-ip] origen... destino");
        System.err.println("java Copia -r [-ip] dir-origen... destino");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {
        boolean esRecursivo = false;
        boolean sePregunta = false;
        boolean sePreserva = false;

        // opciones de proceso
        int argi = 0;
        while (argi < args.length) {
            String arg = args[argi];
            if (!arg.startsWith("-"))
                break;
            if (arg.length() < 2)
                uso();
            for (int i=1; i<arg.length(); i++) {
                char c = arg.charAt(i);
                switch (c) {
                    case 'r' : esRecursivo = true; break;
                    case 'i' : sePregunta = true; break;
                    case 'p' : sePreserva = true; break;
                    default : uso();
                }
            }
            argi++;
        }

        // los argumentos siguientes son los ficheros fuente y la localización de destino
        int restantes = args.length - argi;
        if (restantes < 2)
            uso();
        Path[] origen = new Path[restantes-1];
        int i=0;
        while (restantes > 1) {
            origen[i++] = Paths.get(args[argi++]);
            restantes--;
        }
        Path destino = Paths.get(args[argi]);

        // comprueba si el destino es un directorio
        boolean isDir = Files.isDirectory(destino);

        // copia cada fichero/directorio al destino
        for (i=0; i<origen.length; i++) {
            Path dest = (isDir) ? destino.resolve(origen[i].getFileName()) : destino;

            if (esRecursivo) {
                // sigue los enlaces cuando copie ficheros
                EnumSet<FileVisitOption> opciones = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
                CopiadorDeArbol ca = new CopiadorDeArbol(origen[i], dest, sePregunta, sePreserva);
                Files.walkFileTree(origen[i], opciones, Integer.MAX_VALUE, ca);
            } else {
                // no recursivo así que el origine no puede ser un directorio
                if (Files.isDirectory(origen[i])) {
                    System.err.format("%s: es un directorio%n", origen[i]);
                    continue;
                }
                copiaFichero(origen[i], dest, sePregunta, sePreserva);
            }
        }
    }
}
