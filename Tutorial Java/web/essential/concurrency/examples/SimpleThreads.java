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
public class SimpleThreads {

    // Muestra un mensaje, precedido por 
    // el nombre del hilo actual
    static void hiloMensaje(String mensaje) {
        String nombreHilo
                = Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                nombreHilo,
                mensaje);
    }

    private static class BucleMensajes
            implements Runnable {

        public void run() {
            String infoImportante[] = {
                "Las yeguas comen avena",
                "Los conejos comen avena",
                "Los pequeños corderos comen hiedra",
                "Un niño también comerá hiedra"
            };
            try {
                for (int i = 0;
                        i < infoImportante.length;
                        i++) {
                    // Pausa por 4 segundos
                    Thread.sleep(4000);
                    // Imprime un mensaje
                    hiloMensaje(infoImportante[i]);
                }
            } catch (InterruptedException e) {
                hiloMensaje("¡No he terminado!");
            }
        }
    }

    public static void main(String args[])
            throws InterruptedException {

        // Retardo, en milisegundos antes
        // de que interrumpamos el hilo 
        // BucleMensajes (por defecto una hora).
        long paciencia = 1000 * 60 * 60;

        // Si hay un argumento en la línea de comandos
        // presente, da la paciencia 
        // en segundos.
        if (args.length > 0) {
            try {
                paciencia = Long.parseLong(args[0]) * 1000;
            } catch (NumberFormatException e) {
                System.err.println("El argumento debe ser un entero.");
                System.exit(1);
            }
        }

        hiloMensaje("Iniciando hilo BucleMensajes ");
        long horaInicio = System.currentTimeMillis();
        Thread t = new Thread(new BucleMensajes());
        t.start();

        hiloMensaje("Esperando a que el hilo BucleMensajes termine...");
        // Bucle hasta que el hilo 
        // BucleMensajes termine
        while (t.isAlive()) {
            hiloMensaje("Aún esperando...");
            // Se espera un máximo de 1 segundo
            // para que el hilo BucleMensajes 
            // termine.
            t.join(1000);
            if (((System.currentTimeMillis() - horaInicio) > paciencia)
                    && t.isAlive()) {
                hiloMensaje("¡Cansado de esperar!");
                t.interrupt();
                // No debería ser largo ahora
                // -- espera indefinidamente
                t.join();
            }
        }
        hiloMensaje("¡Por fin!");
    }
}
