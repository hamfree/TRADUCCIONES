package java.data.examples;

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


// fuente original: FileName.java
/**
 * Esta clase asume que la cadena usada para inicializar 
 * caminoCompleto tiene un camino de directorio, un nombre de fichero, y extensión.
 * Los métodos no funcionarán si no están.
 */
public class NombreFichero {
    private String rutaCompleta;
    private char separadorRuta, 
                 separadorExtension;

    public NombreFichero(String str, char sep, char ext) {
        rutaCompleta = str;
        separadorRuta = sep;
        separadorExtension = ext;
    }

    public String extension() {
        int dot = rutaCompleta.lastIndexOf(separadorExtension);
        return rutaCompleta.substring(dot + 1);
    }

    // gets filename without extension
    public String filename() {
        int dot = rutaCompleta.lastIndexOf(separadorExtension);
        int sep = rutaCompleta.lastIndexOf(separadorRuta);
        return rutaCompleta.substring(sep + 1, dot);
    }

    public String path() {
        int sep = rutaCompleta.lastIndexOf(separadorRuta);
        return rutaCompleta.substring(0, sep);
    }
}
