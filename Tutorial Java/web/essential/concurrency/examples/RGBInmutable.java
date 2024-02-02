package essential.concurrency.examples;

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

// Fuente original: ImmutableRGB.java
final public class RGBInmutable {

    // Los valores deben estar entre 0 y 255.
    final private int rojo;
    final private int verde;
    final private int azul;
    final private String nombre;

    private void comprueba(int rojo,
                       int verde,
                       int azul) {
        if (rojo < 0 || rojo > 255
            || verde < 0 || verde > 255
            || azul < 0 || azul > 255) {
            throw new IllegalArgumentException();
        }
    }

    public RGBInmutable(int rojo,
                        int verde,
                        int azul,
                        String nombre) {
        comprueba(rojo, verde, azul);
        this.rojo = rojo;
        this.verde = verde;
        this.azul = azul;
        this.nombre = nombre;
    }


    public int dameRGB() {
        return ((rojo << 16) | (verde << 8) | azul);
    }

    public String dameNombre() {
        return nombre;
    }

    public RGBInmutable invierte() {
        return new RGBInmutable(255 - rojo,
                       255 - verde,
                       255 - azul,
                       "Inverso de " + nombre);
    }
}
