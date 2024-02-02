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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

// Fuente original: SafeLock.java
public class BloqueoSeguro {
    static class Amigo {
        private final String nombre;
        private final Lock bloqueo = new ReentrantLock();

        public Amigo(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return this.nombre;
        }

        public boolean esInminenteLaInclinacion(Amigo inclinador) {
            Boolean esMiBloqueo = false;
            Boolean esTuBloqueo = false;
            try {
                esMiBloqueo = bloqueo.tryLock();
                esTuBloqueo = inclinador.bloqueo.tryLock();
            } finally {
                if (! (esMiBloqueo && esTuBloqueo)) {
                    if (esMiBloqueo) {
                        bloqueo.unlock();
                    }
                    if (esTuBloqueo) {
                        inclinador.bloqueo.unlock();
                    }
                }
            }
            return esMiBloqueo && esTuBloqueo;
        }
            
        public void inclinar(Amigo inclinador) {
            if (esInminenteLaInclinacion(inclinador)) {
                try {
                    System.out.format("%s: ¡%s se ha"
                        + " inclinado ante mí!%n", 
                        this.nombre, inclinador.getNombre());
                    inclinador.responderALaInclinacion(this);
                } finally {
                    bloqueo.unlock();
                    inclinador.bloqueo.unlock();
                }
            } else {
                System.out.format("%s: %s empezó"
                    + " a inclinarse ante mí, pero vio que"
                    + " yo estaba ya inclinándome ante"
                    + " él.%n",
                    this.nombre, inclinador.getNombre());
            }
        }

        public void responderALaInclinacion(Amigo bower) {
            System.out.format("%s: ¡%s me ha" +
                " respondido inclinándose a mi!%n",
                this.nombre, bower.getNombre());
        }
    }

    static class BucleInclinaciones implements Runnable {
        private Amigo inclinador1;
        private Amigo inclinador2;

        public BucleInclinaciones(Amigo inclinador1, Amigo inclinador2) {
            this.inclinador1 = inclinador1;
            this.inclinador2 = inclinador2;
        }
    
        public void run() {
            Random aleatorio = new Random();
            for (;;) {
                try {
                    Thread.sleep(aleatorio.nextInt(10));
                } catch (InterruptedException e) {}
                inclinador2.inclinar(inclinador1);
            }
        }
    }
            

    public static void main(String[] args) {
        final Amigo alfonso =
            new Amigo("Alfonso");
        final Amigo gaston =
            new Amigo("Gastón");
        new Thread(new BucleInclinaciones(alfonso, gaston)).start();
        new Thread(new BucleInclinaciones(gaston, alfonso)).start();
    }
}
