package java.nutsandbolts.examples;

/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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

public class StringSwitchDemo {

    public static int getMonthNumber(String mes) {

        int numeroMes = 0;

        if (mes == null) {
            return numeroMes;
        }

        switch (mes.toLowerCase()) {
            case "enero":
                numeroMes = 1;
                break;
            case "febrero":
                numeroMes = 2;
                break;
            case "marzo":
                numeroMes = 3;
                break;
            case "abril":
                numeroMes = 4;
                break;
            case "mayo":
                numeroMes = 5;
                break;
            case "junio":
                numeroMes = 6;
                break;
            case "julio":
                numeroMes = 7;
                break;
            case "agosto":
                numeroMes = 8;
                break;
            case "septiembre":
                numeroMes = 9;
                break;
            case "octubre":
                numeroMes = 10;
                break;
            case "noviembre":
                numeroMes = 11;
                break;
            case "diciembre":
                numeroMes = 12;
                break;
            default:
                numeroMes = 0;
                break;
        }

        return numeroMes;
    }

    public static void main(String[] args) {

        String mes = "Agosto";

        int numeroMesRetornado
                = StringSwitchDemo.getMonthNumber(mes);

        if (numeroMesRetornado == 0) {
            System.out.println("Mes no v\u00E1lido");
        } else {
            System.out.println(numeroMesRetornado);
        }
    }
}
