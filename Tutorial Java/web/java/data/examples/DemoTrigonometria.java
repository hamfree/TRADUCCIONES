package tutorialjava.java.data.examples;

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

// fuente original: TrigonometricDemo.java
public class DemoTrigonometria {
    public static void main(String[] args) {
        double grados = 45.0;
        double radianes = Math.toRadians(grados);
        
        System.out.format("El valor de pi " + "es %.4f%n",
                           Math.PI);

        System.out.format("El seno de %.1f " + "grados es %.4f%n",
                          grados, Math.sin(radianes));

        System.out.format("El coseno de %.1f " + "grados es %.4f%n",
                          grados, Math.cos(radianes));

        System.out.format("La tangente de %.1f " + "grados es %.4f%n",
                          grados, Math.tan(radianes));

        System.out.format("El arcoseno de %.4f " + "es %.4f grados %n", 
                          Math.sin(radianes), 
                          Math.toDegrees(Math.asin(Math.sin(radianes))));

        System.out.format("El arcocoseno de %.4f " + "es %.4f grados %n", 
                          Math.cos(radianes),  
                          Math.toDegrees(Math.acos(Math.cos(radianes))));

        System.out.format("La arcotangente de %.4f " + "es %.4f grados %n", 
                          Math.tan(radianes), 
                          Math.toDegrees(Math.atan(Math.tan(radianes))));
    }
}
