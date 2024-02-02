package tutorialjava.essential.concurrency.examples;

/*
* Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import javax.imageio.ImageIO;

/**
 * DifuminadoConFork implementa un difuminado simple de imagen horizontal. Promedia los pixeles en la
 * matriz origen y los escribe en la matriz de destino. El valor sUmbral 
 * determina si el difuminado será realizado directamente o troceado en dos tareas.
 *
 * Esto no es la forma recomendad de difuminar imágenes, sólo tiene la intención de
 * ilustrar el uso del marco de trabajo Fork/Join.
 */

// Fuente original : ForkBlur.java
public class DifuminadoConFork extends RecursiveAction {

    private int[] mOrigen;
    private int mInicio;
    private int mLongitud;
    private int[] mDestino;
    private int mAnchoDifuminado = 15; // Tamaño de la ventana de procesado, debe ser impar.

    public DifuminadoConFork(int[] orig, int inicio, int longitud, int[] dest) {
        mOrigen = orig;
        mInicio = inicio;
        mLongitud = longitud;
        mDestino = dest;
    }

    // Promedia los píxeles de la fuente, escribe los resultados en el destino.
    protected void calculaDirectamente() {
        int sidePixels = (mAnchoDifuminado - 1) / 2;
        for (int indice = mInicio; indice < mInicio + mLongitud; indice++) {
            // Calcula el promedio.
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
                int mindex = Math.min(Math.max(mi + indice, 0), mOrigen.length - 1);
                int pixel = mOrigen[mindex];
                rt += (float) ((pixel & 0x00ff0000) >> 16) / mAnchoDifuminado;
                gt += (float) ((pixel & 0x0000ff00) >> 8) / mAnchoDifuminado;
                bt += (float) ((pixel & 0x000000ff) >> 0) / mAnchoDifuminado;
            }

            // Vuelve a montar el píxel en destino.
            int dpixel = (0xff000000)
                    | (((int) rt) << 16)
                    | (((int) gt) << 8)
                    | (((int) bt) << 0);
            mDestino[indice] = dpixel;
        }
    }
    protected static int sUmbral = 10000;

    @Override
    protected void compute() {
        if (mLongitud < sUmbral) {
            calculaDirectamente();
            return;
        }

        int division = mLongitud / 2;

        invokeAll(new DifuminadoConFork(mOrigen, mInicio, division, mDestino),
                new DifuminadoConFork(mOrigen, mInicio + division, mLongitud - division, 
                mDestino));
    }

    // La fontanería a continuación...
    public static void main(String[] args) throws Exception {
        String origenNombre = "red-tulips.jpg";
        File origenFichero = new File(origenNombre);
        BufferedImage imagen = ImageIO.read(origenFichero);
        
        System.out.println("Imagen origen: " + origenNombre);
        
        BufferedImage imagenDifuminada = difumina(imagen);
        
        String destinoNombre = "blurred-tulips.jpg";
        File destinoFichero = new File(destinoNombre);
        ImageIO.write(imagenDifuminada, "jpg", destinoFichero);
        
        System.out.println("Imagen de salida: " + destinoNombre);
        
    }

    public static BufferedImage difumina(BufferedImage imagenOrigen) {
        int ancho = imagenOrigen.getWidth();
        int alto = imagenOrigen.getHeight();

        int[] origen = imagenOrigen.getRGB(0, 0, ancho, alto, null, 0, ancho);
        int[] destino = new int[origen.length];

        System.out.println("El tamaño de la matriz es " + origen.length);
        System.out.println("El umbral es " + sUmbral);

        int procesadores = Runtime.getRuntime().availableProcessors();
        System.out.println(Integer.toString(procesadores) + " procesador"
                + (procesadores != 1 ? "es están " : " está ")
                + "disponible");

        DifuminadoConFork fb = new DifuminadoConFork(origen, 0, origen.length, destino);

        ForkJoinPool agrupacionHilos = new ForkJoinPool();

        long horaInicio = System.currentTimeMillis();
        agrupacionHilos.invoke(fb);
        long horaFinalizacion = System.currentTimeMillis();

        System.out.println("El difuminado de la imágen tomó " + (horaFinalizacion - horaInicio) + 
                " milisegundos.");

        BufferedImage imagenDestino =
                new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        imagenDestino.setRGB(0, 0, ancho, alto, destino, 0, ancho);

        return imagenDestino;
    }
}
