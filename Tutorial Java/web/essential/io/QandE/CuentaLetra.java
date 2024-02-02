package essential.io.QandE;

/*
 * Copyright (c) 2009-2011 Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.nio.file.*;
// fuente original: CountLetter.java
public class CuentaLetra {
    private char caracterBuscado;
    private Path fichero;

    CuentaLetra(char caracterBuscado, Path fichero) {
        this.caracterBuscado = caracterBuscado;
        this.fichero = fichero;
    }

    public int cuenta() throws IOException {
        int contador = 0;
        try (InputStream in = Files.newInputStream(fichero);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
            {
            String linea = null;
            while ((linea = reader.readLine()) != null) {
                for (int i = 0; i < linea.length(); i++) {
                    if (caracterBuscado == linea.charAt(i)) {
                        contador++;
                    }
                }
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return contador;
    }

    static void uso() {
        System.out.println("uso: java CuentaLetra <letra>");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 1 || args[0].length() != 1)
            uso();

        char caracterBuscado = args[0].charAt(0);
        Path fichero = Paths.get("xanadu.txt");
        int contador = new CuentaLetra(caracterBuscado, fichero).cuenta();
        System.out.format("El fichero '%s' tiene %d instancias de la letra '%c'.%n",
                fichero, contador, caracterBuscado);
    }
}
