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
import java.util.Calendar;
import java.util.Locale;

// fuente original : TestFormat.java
public class PruebaFormato {

    public static void main(String[] args) {
        long n = 461012;

        System.out.format("%s%n",  n);                    //  -->  "461012"
        System.out.format("%08d%n",n);                    //  -->  "00461012"
        System.out.format("%+8d%n", n);                   //  -->  "+461012"
        System.out.format("%,8d%n", n);                   //  -->  "461.012"
        System.out.format("%+,8d%n%n", n);                //  -->  "+461.012"

        double pi = Math.PI;
        System.out.format("%f%n", pi);                    //  -->  "3,141593"
        System.out.format("%.3f%n", pi);                  //  -->  "3,142"
        System.out.format("%10.3f%n", pi);                //  -->  "     3,142"
        System.out.format("%-10.3f%n", pi);               //  -->  "3,142     "
        System.out.format(Locale.forLanguageTag("ES"),
                "%-10.4f%n%n", pi);                       //  -->  "3.1416    "

        Calendar c = Calendar.getInstance();
        System.out.format("%tB %te, %tY%n", c, c, c);     //  -->  "octubre 21, 2016"
        System.out.format("%tl:%tM %tp%n", c, c, c);      //  -->  "10:07 pm"
        System.out.format("%tD%n", c);                    //  -->  "10/21/16"

    }
}
