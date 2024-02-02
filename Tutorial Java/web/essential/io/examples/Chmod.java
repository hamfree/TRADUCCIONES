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
import java.nio.file.attribute.*;
import static java.nio.file.attribute.PosixFilePermission.*;
import static java.nio.file.FileVisitResult.*;
import java.io.IOException;
import java.util.*;

/**
 * Código de ejemplo que cambia los permisos de los ficheros en una manera similar al 
 * programa chmod(1).
 */
// fuente original: Chmod.java
public class Chmod {

    /**
     * Compila una lista de uno o más <em>expresiones de modo simbólico</em> que
     * pueden ser usadas para cambiar un conjunto de permisos del fichero. Este método se
     * usa cuando los permisos del fichero son cambiados de una manera similar al programa
     * de UNIX <i>chmod</i>.
     *
     * <p> El parámetro {@code exprs} es una lista de expresiones separadas por comas
     * donde cada una toma la forma:
     * <blockquote>
     * <i>quién operador</i> [<i>permisos</i>]
     * </blockquote>
     * donde <i>quién</i> es uno o más de los caracteres {@code 'u'}, {@code 'g'},
     * {@code 'o'}, o {@code 'a'} significando el propietario (usurio), grupo, otros, o
     * todos (propietario, grupo, y otros) respectivamente.
     *
     * <p> <i>operador</i> es el carácter {@code '+'}, {@code '-'}, o {@code
     * '='} significando cómo los permisos van a ser cambiados. {@code '+'} significa que
     * los permisos son añadidos, {@code '-'} significa que los permisos son eliminados, y
     * {@code '='} significa que los permisos son asignados de forma absoluta.
     *
     * <p> <i>permisos</i> es una secuencia de cero o más de los siguientes:
     * {@code 'r'} para permiso de lectura, {@code 'w'} para permiso de escritura, y
     * {@code 'x'} para permiso de ejecución. Si <i>permisos</i> es omitido 
     * cuando se asignan de forma absoluta, entonces los permisos son eliminados para 
     * el propietario, grupo, u otros como se identifaron en <i>quién</i>. Cuando se omiten 
     * al añadir o eliminar entonces la expresión es ignorada.
     *
     * <p> Los siguientes ejemplos demuestran valores posibles para el parámetro 
     * {@code * exprs}:
     *
     * <table border="0">
     * <tr>
     *   <td> {@code u=rw} </td>
     *   <td> Establece los permisos del propietario a lectura y escritura. </td>
     * </tr>
     * <tr>
     *   <td> {@code ug+w} </td>
     *   <td> Establece el permiso de escritura para el propietario y el de escritura para el grupo. </td>
     * </tr>
     * <tr>
     *   <td> {@code u+w,o-rwx} </td>
     *   <td> Establece el permiso de escritura para el propietario, y elimina el de lectura, escritura y ejecución para los
     *        otros. </td>
     * </tr>
     * <tr>
     *   <td> {@code o=} </td>
     *   <td> Establece el permiso para otros a nada (los permisos de lectura, escritura y ejecución para 
     *        los otros son eliminados) </td>
     * </tr>
     * </table>
     *
     * @param   exprs
     *          Lista de un o más <em>expresiones de modo simbólico</em>
     *
     * @return  un {@code Changer} que puede ser usado como cambiador de un conjunto de permisos de ficheros
     *          
     *
     * @throws  IllegalArgumentException
     *          Si el valor del parámetro {@code exprs} es inválido.
     */
    public static Cambiador compila(String exprs) {
        // el mínimo es quién y operador (u= por ejemplo)
        if (exprs.length() < 2)
            throw new IllegalArgumentException("Modo no válido");

        // permisos que el cambiador añadirá o eliminará
        final Set<PosixFilePermission> aAñadir = new HashSet<PosixFilePermission>();
        final Set<PosixFilePermission> aBorrar = new HashSet<PosixFilePermission>();

        // iterate over each of expression modes
        // itera sobre cada expresión de modo
        for (String expr: exprs.split(",")) {
            // mínimo de quién y operador
            if (expr.length() < 2)
                throw new IllegalArgumentException("Modo no válido");

            int pos = 0;

            // quién
            boolean u = false;
            boolean g = false;
            boolean o = false;
            boolean hecho = false;
            for (;;) {
                switch (expr.charAt(pos)) {
                    case 'u' : u = true; break;
                    case 'g' : g = true; break;
                    case 'o' : o = true; break;
                    case 'a' : u = true; g = true; o = true; break;
                    default : hecho = true;
                }
                if (hecho)
                    break;
                pos++;
            }
            if (!u && !g && !o)
                throw new IllegalArgumentException("Modo no válido");

            // obtenemos el operador y los permisos
            char op = expr.charAt(pos++);
            String mascara = (expr.length() == pos) ? "" : expr.substring(pos);

            // operador
            boolean añade = (op == '+');
            boolean elimina = (op == '-');
            boolean asigna = (op == '=');
            if (!añade && !elimina && !asigna)
                throw new IllegalArgumentException("Modo no válido");

            // quien= significa borrar todo
            if (asigna && mascara.length() == 0) {
                asigna = false;
                elimina = true;
                mascara = "rwx";
            }

            // permisos
            boolean r = false;
            boolean w = false;
            boolean x = false;
            for (int i=0; i<mascara.length(); i++) {
                switch (mascara.charAt(i)) {
                    case 'r' : r = true; break;
                    case 'w' : w = true; break;
                    case 'x' : x = true; break;
                    default:
                        throw new IllegalArgumentException("Modo no válido");
                }
            }

            // actualizamos el conjunto de permisos
            if (añade) {
                if (u) {
                    if (r) aAñadir.add(OWNER_READ);
                    if (w) aAñadir.add(OWNER_WRITE);
                    if (x) aAñadir.add(OWNER_EXECUTE);
                }
                if (g) {
                    if (r) aAñadir.add(GROUP_READ);
                    if (w) aAñadir.add(GROUP_WRITE);
                    if (x) aAñadir.add(GROUP_EXECUTE);
                }
                if (o) {
                    if (r) aAñadir.add(OTHERS_READ);
                    if (w) aAñadir.add(OTHERS_WRITE);
                    if (x) aAñadir.add(OTHERS_EXECUTE);
                }
            }
            if (elimina) {
                if (u) {
                    if (r) aBorrar.add(OWNER_READ);
                    if (w) aBorrar.add(OWNER_WRITE);
                    if (x) aBorrar.add(OWNER_EXECUTE);
                }
                if (g) {
                    if (r) aBorrar.add(GROUP_READ);
                    if (w) aBorrar.add(GROUP_WRITE);
                    if (x) aBorrar.add(GROUP_EXECUTE);
                }
                if (o) {
                    if (r) aBorrar.add(OTHERS_READ);
                    if (w) aBorrar.add(OTHERS_WRITE);
                    if (x) aBorrar.add(OTHERS_EXECUTE);
                }
            }
            if (asigna) {
                if (u) {
                    if (r) aAñadir.add(OWNER_READ);
                      else aBorrar.add(OWNER_READ);
                    if (w) aAñadir.add(OWNER_WRITE);
                      else aBorrar.add(OWNER_WRITE);
                    if (x) aAñadir.add(OWNER_EXECUTE);
                      else aBorrar.add(OWNER_EXECUTE);
                }
                if (g) {
                    if (r) aAñadir.add(GROUP_READ);
                      else aBorrar.add(GROUP_READ);
                    if (w) aAñadir.add(GROUP_WRITE);
                      else aBorrar.add(GROUP_WRITE);
                    if (x) aAñadir.add(GROUP_EXECUTE);
                      else aBorrar.add(GROUP_EXECUTE);
                }
                if (o) {
                    if (r) aAñadir.add(OTHERS_READ);
                      else aBorrar.add(OTHERS_READ);
                    if (w) aAñadir.add(OTHERS_WRITE);
                      else aBorrar.add(OTHERS_WRITE);
                    if (x) aAñadir.add(OTHERS_EXECUTE);
                      else aBorrar.add(OTHERS_EXECUTE);
                }
            }
        }

        // devolvemos el cambiador
        return new Cambiador() {
            @Override
            public Set<PosixFilePermission> cambia(Set<PosixFilePermission> perms) {
                perms.addAll(aAñadir);
                perms.removeAll(aBorrar);
                return perms;
            }
        };
    }

    /**
     * Una tarea que <i>cambia</i> un conjunto de elementos {@link PosixFilePermission}.
     */
    public interface Cambiador {
        /**
         * Aplica los cambios al conjunto dado de permisos.
         *
         * @param   perms
         *          El conjunto de permisos a cambiar
         *
         * @return  El parámetro {@code perms}
         */
        Set<PosixFilePermission> cambia(Set<PosixFilePermission> perms);
    }

    /**
     * Cambia los permisos del fichero usando el Cambiador dado.
     */
    static void chmod(Path fichero, Cambiador cambiador) {
        try {
            Set<PosixFilePermission> perms = Files.getPosixFilePermissions(fichero);
            Files.setPosixFilePermissions(fichero, cambiador.cambia(perms));
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    /**
     * Cambia el permiso de cada fichero y directorio visitado
     */
    static class VisitanteArbol implements FileVisitor<Path> {
        private final Cambiador changer;

        VisitanteArbol(Cambiador changer) {
            this.changer = changer;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            chmod(dir, changer);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            chmod(file, changer);
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            if (exc != null)
                System.err.println("ADVERTENCIA: " + exc);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            System.err.println("ADVERTENCIA: " + exc);
            return CONTINUE;
        }
    }

    static void uso() {
        System.err.println("java Chmod [-R] lista-modos-simbolica file...");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2)
            uso();
        int argi = 0;
        int maxDepth = 0;
        if (args[argi].equals("-R")) {
            if (args.length < 3)
                uso();
            argi++;
            maxDepth = Integer.MAX_VALUE;
        }

        // compila las expresiones de modo simbólicas
        Cambiador changer = compila(args[argi++]);
        VisitanteArbol visitor = new VisitanteArbol(changer);

        Set<FileVisitOption> opts = Collections.emptySet();
        while (argi < args.length) {
            Path file = Paths.get(args[argi]);
            Files.walkFileTree(file, opts, maxDepth, visitor);
            argi++;
        }
    }
}
