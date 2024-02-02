/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.IandI.examples.defaultmethods;

import java.time.*;
import java.lang.*;
import java.util.*;

// Fuente original: SimpleTimeClient.java
public class ClienteTiempoSimple implements ClienteTiempo {

    private LocalDateTime fechaYTiempo;

    public ClienteTiempoSimple() {
        fechaYTiempo = LocalDateTime.now();
    }

    public void setTiempo(int hora, int minuto, int segundo) {
        LocalDate fechaActual = LocalDate.from(fechaYTiempo);
        LocalTime tiempoAFijar = LocalTime.of(hora, minuto, segundo);
        fechaYTiempo = LocalDateTime.of(fechaActual, tiempoAFijar);
    }

    public void setFecha(int dia, int mes, int año) {
        LocalDate fechaAFijar = LocalDate.of(dia, mes, año);
        LocalTime tiempoActual = LocalTime.from(fechaYTiempo);
        fechaYTiempo = LocalDateTime.of(fechaAFijar, tiempoActual);
    }

    public void setFechaYTiempo(int dia, int mes, int año,
            int hora, int minuto, int segundo) {
        LocalDate fechaAFijar = LocalDate.of(dia, mes, año);
        LocalTime tiempoAFijar = LocalTime.of(hora, minuto, segundo);
        fechaYTiempo = LocalDateTime.of(fechaAFijar, tiempoAFijar);
    }

    public LocalDateTime getFechaYTiempoLocal() {
        return fechaYTiempo;
    }

    public String toString() {
        return fechaYTiempo.toString();
    }

    public static void main(String... args) {
        ClienteTiempo miClienteTiempo = new ClienteTiempoSimple();
        System.out.println(miClienteTiempo.toString());
    }
}
