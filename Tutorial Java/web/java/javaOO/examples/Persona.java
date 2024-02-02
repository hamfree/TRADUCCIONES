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
 
import java.util.List;
import java.util.ArrayList;
import java.time.chrono.IsoChronology;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.Period;

// Person.java
public class Persona {
  
    public enum Sexo {
        MASCULINO, FEMENINO
    }
  
    String nombre; 
    LocalDate cumpleaños;
    Sexo genero;
    String correoElectronico;
  
    Persona(String nameArg, LocalDate birthdayArg,
        Sexo genderArg, String emailArg) {
        nombre = nameArg;
        cumpleaños = birthdayArg;
        genero = genderArg;
        correoElectronico = emailArg;
    }  

    public int getEdad() {
        return cumpleaños
            .until(IsoChronology.INSTANCE.dateNow())
            .getYears();
    }

    public void printPersona() {
      System.out.println(nombre + ", " + this.getEdad());
    }
    
    public Sexo getGenero() {
        return genero;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    
    public LocalDate getCumpleaños() {
        return cumpleaños;
    }
    
    public static int comparaPorEdad(Persona a, Persona b) {
        return a.cumpleaños.compareTo(b.cumpleaños);
    }

    public static List<Persona> creaRegistro() {
        
        List<Persona> registro = new ArrayList<>();
        registro.add(
            new Persona(
            "Fred",
            IsoChronology.INSTANCE.date(1980, 6, 20),
            Persona.Sexo.MASCULINO,
            "fred@example.com"));
        registro.add(
            new Persona(
            "Jane",
            IsoChronology.INSTANCE.date(1990, 7, 15),
            Persona.Sexo.FEMENINO, "jane@example.com"));
        registro.add(
            new Persona(
            "George",
            IsoChronology.INSTANCE.date(1991, 8, 13),
            Persona.Sexo.MASCULINO, "george@example.com"));
        registro.add(
            new Persona(
            "Bob",
            IsoChronology.INSTANCE.date(2000, 9, 12),
            Persona.Sexo.MASCULINO, "bob@example.com"));
        
        return registro;
    }
    
}
