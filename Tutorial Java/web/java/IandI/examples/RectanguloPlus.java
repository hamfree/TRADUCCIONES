package java.IandI.examples;

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
public class RectanguloPlus
        implements Relatable {

    public int ancho = 0;
    public int alto = 0;
    public Punto origen;

    // cuatro constructores
    public RectanguloPlus() {
        origen = new Punto(0, 0);
    }

    public RectanguloPlus(Punto p) {
        origen = p;
    }

    public RectanguloPlus(int w, int h) {
        origen = new Punto(0, 0);
        ancho = w;
        alto = h;
    }

    public RectanguloPlus(Punto p, int w, int h) {
        origen = p;
        ancho = w;
        alto = h;
    }

    // un método para mover el rectángulo
    public void mueve(int x, int y) {
        origen.x = x;
        origen.y = y;
    }

    // un método para calcular
    // el área de un rectángulo
    public int getArea() {
        return ancho * alto;
    }

    // un método requerido para implementar
    // el Interfaz Comparable
    public int esMayorQue(Relatable otro) {
        RectanguloPlus otroRectangulo = (RectanguloPlus) otro;
        if (this.getArea() < otroRectangulo.getArea()) {
            return -1;
        } else if (this.getArea() > otroRectangulo.getArea()) {
            return 1;
        } else {
            return 0;
        }
    }
}
