package tutorialjava.essential.regex.examples;

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

import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

// fuente original: RegexTestHarness2.java
public class PruebasExpReg2 {

    public static void main(String[] args){
        Pattern patron = null;
        Matcher analizador = null;

        Console consola = System.console();
        if (consola == null) {
            System.err.println("Sin consola.");
            System.exit(1);
        }
        while (true) {
            try{
                patron = 
                Pattern.compile(consola.readLine("%nIntroduza su expresión regular: "));

                analizador = 
                patron.matcher(consola.readLine("Introduzca la cadena de entrada en la que buscar: "));
            }
            catch(PatternSyntaxException pse){
                consola.format("¡Hay un problema" +
                               " con la expresión regular!%n");
                consola.format("El patrón en cuestión es: %s%n",
                               pse.getPattern());
                consola.format("La descripción es : %s%n",
                               pse.getDescription());
                consola.format("El mensaje es: %s%n",
                               pse.getMessage());
                consola.format("El índice es: %s%n",
                               pse.getIndex());
                System.exit(0);
            }
            boolean found = false;
            while (analizador.find()) {
                consola.format("He encontrado el texto" +
                    " \"%s\" empezando en el índice  " +
                    "%d y terminando en el índice %d.%n",
                    analizador.group(),
                    analizador.start(),
                    analizador.end());
                found = true;
            }
            if(!found){
                consola.format("No se encontraron coincidencias.%n");
            }
        }
    }
}

