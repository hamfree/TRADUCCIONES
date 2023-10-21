# Único (Singleton){#h2-69}

Type: Creational

Purpose: Ensure a class allows only one object to be created, providing a single point of access to it.

Example usage: Log files, configuration settings, etc.

Consequences: Often overused, difficult to subclass, can lead to tight coupling.

![_](../images/000035.jpg)

* Singleton defines a private constructor together with a public class (i.e. static) method as the only means of getting the instance.

Note that this book recommends using an enum to implement the Singleton pattern in most instances.
