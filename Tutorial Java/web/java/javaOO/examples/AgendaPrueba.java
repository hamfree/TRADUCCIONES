package java.javaOO.examples;

/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.Comparator;
import java.util.function.Predicate;
import java.lang.Iterable;
import java.time.chrono.IsoChronology;

// RosterTest.java
public class AgendaPrueba {

    interface CompruebaPersona {

        boolean test(Persona p);
    }

    // Enfoque 1: Crea Métodos que Busquen por Personas que Coincidan en Una Característica
    public static void impPersonasMasViejasQue(List<Persona> agenda, int edad) {
        for (Persona p : agenda) {
            if (p.getEdad() >= edad) {
                p.printPersona();
            }
        }
    }

    // Enfoque 2: Crea Métodos de Búsqueda Más Generalizados
    public static void impPersonasEnElRangoDeEdad(
            List<Persona> agenda, int bajo, int alto) {
        for (Persona p : agenda) {
            if (bajo <= p.getEdad() && p.getEdad() < alto) {
                p.printPersona();
            }
        }
    }

    // Enfoque 3: Especifica el Criterio de Búsqueda en una Clase Local
    // Enfoque 4: Especifica el Criterio de Búsqueda en una Clase Anónima
    // Enfoque 5: Especifica el Criterio de Búsqueda con una Expresión Lambda
    public static void imprimePersonas(
            List<Persona> agenda, CompruebaPersona comprobador) {
        for (Persona p : agenda) {
            if (comprobador.test(p)) {
                p.printPersona();
            }
        }
    }

    // Enfoque 6: Usa Interfaces Funcionales Estándar con Expresiones Lambda
    public static void imprimePersonasConPredicate(
            List<Persona> agenda, Predicate<Persona> comprobador) {
        for (Persona p : agenda) {
            if (comprobador.test(p)) {
                p.printPersona();
            }
        }
    }

    // Enfoque 7: Usa Expresiones Lambda A Través de su Aplicación
    public static void procesaPersonas(
            List<Persona> agenda,
            Predicate<Persona> comprobador,
            Consumer<Persona> bloque) {
        for (Persona p : agenda) {
            if (comprobador.test(p)) {
                bloque.accept(p);
            }
        }
    }

    // Enfoque 7, segundo ejemplo
    public static void procesaPersonasConFuncion(
            List<Persona> agenda,
            Predicate<Persona> comprobador,
            Function<Persona, String> mapeador,
            Consumer<String> bloque) {
        for (Persona p : agenda) {
            if (comprobador.test(p)) {
                String data = mapeador.apply(p);
                bloque.accept(data);
            }
        }
    }

    // Enfoque 8: Usa Genéricos Más Extensamente
    public static <X, Y> void procesaElementos(
            Iterable<X> fuente,
            Predicate<X> comprobador,
            Function<X, Y> mapeador,
            Consumer<Y> bloque) {
        for (X p : fuente) {
            if (comprobador.test(p)) {
                Y data = mapeador.apply(p);
                bloque.accept(data);
            }
        }
    }

    public static void main(String... args) {

        List<Persona> agenda = Persona.creaRegistro();

        for (Persona p : agenda) {
            p.printPersona();
        }

        // Enfoque 1: Crea Métodos que Busquen por Personas que Coincidan en Una Característica
        System.out.println("Personas más viejas que 20:");
        impPersonasMasViejasQue(agenda, 20);
        System.out.println();

        // Enfoque 2: Crea Métodos de Búsqueda Más Generalizados
        System.out.println("Personas entre las edades de 14 y 30:");
        impPersonasEnElRangoDeEdad(agenda, 14, 30);
        System.out.println();

        // Enfoque 3: Especifica el Criterio de Búsqueda en una Clase Local
        System.out.println("Personas que son elegibles para el Servicio Selectivo:");

        class CompruebaPersonaElegibleParaServicioSelectivo implements CompruebaPersona {

            public boolean test(Persona p) {
                return p.getGenero() == Persona.Sexo.MASCULINO
                        && p.getEdad() >= 18
                        && p.getEdad() <= 25;
            }
        }

        imprimePersonas(
                agenda, new CompruebaPersonaElegibleParaServicioSelectivo());

        System.out.println();

        // Enfoque 4: Especifica el Criterio de Búsqueda en una Clase Anónima
        System.out.println("Personas que son elegibles para el Servicio Selectivo "
                + "(clase anónima):");

        imprimePersonas(
                agenda,
                new CompruebaPersona() {
            public boolean test(Persona p) {
                return p.getGenero() == Persona.Sexo.MASCULINO
                        && p.getEdad() >= 18
                        && p.getEdad() <= 25;
            }
        }
        );

        System.out.println();

        // Enfoque 5: Especifica el Criterio de Búsqueda con una Expresión Lambda
        System.out.println("Personas que son elegibles para el Servicio Selectivo "
                + "(expresión lambda):");

        imprimePersonas(
                agenda,
                (Persona p) -> p.getGenero() == Persona.Sexo.MASCULINO
                && p.getEdad() >= 18
                && p.getEdad() <= 25
        );

        System.out.println();

        // Enfoque 6: Usa Interfaces Funcionales Estándar con Expresiones Lambda
        System.out.println("Personas que son elegibles para el Servicio Selectivo "
                + "(con parámetro Predicate):");

        imprimePersonasConPredicate(
                agenda,
                p -> p.getGenero() == Persona.Sexo.MASCULINO
                && p.getEdad() >= 18
                && p.getEdad() <= 25
        );

        System.out.println();

        // Enfoque 7: Usa Expresiones Lambda A Través de su Aplicación
        System.out.println("Personas que son elegibles para el Servicio Selectivo "
                + "(con parámetros Predicate y Consumer):");

        procesaPersonas(
                agenda,
                p -> p.getGenero() == Persona.Sexo.MASCULINO
                && p.getEdad() >= 18
                && p.getEdad() <= 25,
                p -> p.printPersona()
        );

        System.out.println();

        // Enfoque 7, segundo ejemplo
        System.out.println("Personas que son elegibles para el Servicio Selectivo "
                + "(con parámetros Predicate, Function, y Consumer):");

        procesaPersonasConFuncion(
                agenda,
                p -> p.getGenero() == Persona.Sexo.MASCULINO
                && p.getEdad() >= 18
                && p.getEdad() <= 25,
                p -> p.getCorreoElectronico(),
                email -> System.out.println(email)
        );

        System.out.println();

        // Enfoque 8: Usa Genéricos Más Extensamente
        System.out.println("Personas que son elegibles para el Servicio Selectivo "
                + "(versión genérica):");

        procesaElementos(
                agenda,
                p -> p.getGenero() == Persona.Sexo.MASCULINO
                && p.getEdad() >= 18
                && p.getEdad() <= 25,
                p -> p.getCorreoElectronico(),
                email -> System.out.println(email)
        );

        System.out.println();

        // Enfoque 9: Usa Operaciones de Datos en Masa que Acepten Expresiones Lambda como Parámetros
        System.out.println("Personas que son elegibles para el Servicio Selectivo "
                + "(con operaciones de datos en masa):");

        agenda
                .stream()
                .filter(
                        p -> p.getGenero() == Persona.Sexo.MASCULINO
                        && p.getEdad() >= 18
                        && p.getEdad() <= 25)
                .map(p -> p.getCorreoElectronico())
                .forEach(email -> System.out.println(email));
    }
}
