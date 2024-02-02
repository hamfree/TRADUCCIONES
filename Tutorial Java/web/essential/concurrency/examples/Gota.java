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

// Fuente original: Drop.java
public class Gota{ 
    // Mensaje enviado del productor 
    // al consumidor.
    private String mensaje;
    // Verdadero si el consumidor debe esperar
    // para que el productor envíe un mensaje, 
    // falso si el productor debe esperar para 
    // que el consumidor recupere el mensaje.
    private boolean estaVacio = true;

    public synchronized String tomar() {
        // Esperar hasta que el mensaje esté
        // disponible.
        while (estaVacio) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Cambia el estado.
        estaVacio = true;
        // Notifica al productor que el 
        // estado ha cambiado.
        notifyAll();
        return mensaje;
    }

    public synchronized void poner(String mensaje) {
        // Esperar hasta que el mensaje ha 
        // sido recuperado.
        while (!estaVacio) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        // Cambia el estado.
        estaVacio = false;
        // Almacena el mensaje.
        this.mensaje = mensaje;
        // Notifica al consumidor que el estado 
        // ha cambiado.
        notifyAll();
    }
}
