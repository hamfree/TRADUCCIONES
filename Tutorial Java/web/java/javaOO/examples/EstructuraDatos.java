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
 
public class EstructuraDatos {
    
    // Crea una matriz
    private final static int TAMAÑO = 15;
    private int[] matrizDeEnteros = new int[TAMAÑO];
    
    public EstructuraDatos() {
        // llena la matriz con valores enteros ascendentes
        for (int i = 0; i < TAMAÑO; i++) {
            matrizDeEnteros[i] = i;
        }
    }
    
    public void imprimePares() {
        
        // Imprime los valores de los índices pares de la matriz
        EstructuraDatosIterador iterador = this.new ParIterador();
        while (iterador.hasNext()) {
            System.out.print(iterador.next() + " ");
        }
        System.out.println();
    }
    
    interface EstructuraDatosIterador extends java.util.Iterator<Integer> { } 

    // clase interna que implementa el interfaz EstructuraDatosIterador,
    // la cual extiende el interfaz del Iterator<Integer> 
    
    private class ParIterador implements EstructuraDatosIterador {
        
        // Inicia el recorrido a través de la matriz desde el principio
        private int siguienteIndice = 0;
        
        public boolean hasNext() {
            
            // Comprueba si el elemento actual es el último en la matriz
            return (siguienteIndice <= TAMAÑO - 1);
        }        
        
        public Integer next() {
            
            // Graba un valor de un índice para de la matriz
            Integer valRetorno = Integer.valueOf(matrizDeEnteros[siguienteIndice]);
            
            // Obtiene el siguiente elemento par
            siguienteIndice += 2;
            return valRetorno;
        }
    }
    
    public static void main(String s[]) {
        
        // Llena la matriz con valores enteros e imprime solo
        // los valores de los índices pares
        EstructuraDatos ds = new EstructuraDatos();
        ds.imprimePares();
    }
}
