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
import java.util.function.Consumer;

// LambdaScopeTest.java
public class PruebaAlcanceLambda {

    public int x = 0;

    class PrimerNivel {

        public int x = 1;

        void metodoInPrimerNivel(int x) {

            // La siguiente sentencia causa que el compilador genera el 
            // el error "variables locales referencias desde una expresión lambda 
            // deben ser finales o efectivamente finales" en la sentencia A:
            //
            // x = 99;
            Consumer<Integer> miConsumer = (y)
                    -> {
                System.out.println("x = " + x); // Sentencia A
                System.out.println("y = " + y);
                System.out.println("this.x = " + this.x);
                System.out.println("PruebaAlcanceLambda.this.x = "
                        + PruebaAlcanceLambda.this.x);
            };

            miConsumer.accept(x);

        }
    }

    public static void main(String... args) {
        PruebaAlcanceLambda pal = new PruebaAlcanceLambda();
        PruebaAlcanceLambda.PrimerNivel pn = pal.new PrimerNivel();
        pn.metodoInPrimerNivel(23);
    }
}
