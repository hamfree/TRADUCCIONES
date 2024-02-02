package tutorialjava.essential.io.examples;

/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import static java.nio.file.FileVisitOption.*;
import java.util.*;

/**
 * Código de ejemplo que busca ficheros que coinciden con el patrón glob especificado. Para más información sobre qué constituye un
 * patrón glob, vea http://docs.oracle.com/javase/javatutorials/tutorial/essential/io/fileOps.html#glob
 *
 * El fichero o directorios que coinciden con el patrón son impresos en la salida estándar. El número de coincidencias es también
 * impreso.
 *
 * Cuando se ejecuta esta aplicación, debe colocar el patrón glob entre comillas, de forma que el shell no expanda ningún comodín:
 * java Find . -name "*.java"
 */
// fuente original: Find.java
public class Busca {

    /**
     * A {@code FileVisitor} that finds all files that match the specified pattern.
     */
    public static class Buscador
            extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;
        private int numCoincidencias = 0;

        Buscador(String patron) {
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + patron);
        }

        // Compara el patrón glob contra
        // el nombre del fichero o directorio.
        void busca(Path fichero) {
            Path nombre = fichero.getFileName();
            if (nombre != null && matcher.matches(nombre)) {
                numCoincidencias++;
                System.out.println(fichero);
            }
        }

        // Imprime el número total de
        // coincidencias en la salida estándar.
        void hecho() {
            System.out.println("Coincidieron: "
                    + numCoincidencias);
        }

        // Invoca el método de 
        // coincidencia de patrones en cada fichero.
        @Override
        public FileVisitResult visitFile(Path fichero,
                BasicFileAttributes attrs) {
            busca(fichero);
            return CONTINUE;
        }

        // Invoca el método de 
        // patrón de coincidencia en cada directorio.
        @Override
        public FileVisitResult preVisitDirectory(Path dir,
                BasicFileAttributes attrs) {
            busca(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file,
                IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
    }

    static void uso() {
        System.err.println("java Busca <path>"
                + " -name \"<glob_pattern>\"");
        System.exit(-1);
    }

    public static void main(String[] args)
            throws IOException {

        if (args.length < 3 || !args[1].equals("-name")) {
            uso();
        }

        Path dirInicial = Paths.get(args[0]);
        String pattern = args[2];

        Buscador finder = new Buscador(pattern);
        Files.walkFileTree(dirInicial, finder);
        finder.hecho();
    }
}
