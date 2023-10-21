# Método de plantilla(Template Method)

Type: Behavioural

Purpose: Define the skeleton of an algorithm in a method, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

Example usage: When an algorithm's steps can be performed in different ways.

Consequences: Should prevent the template method from being overridden.

![_](../images/000017.jpg)

* AbstractClass defines the non-overridable templateMethod() that invokes a series of abstract methods defined in subclasses;
* Subclass1 and Subclass2 extend AbstractClass to define the code for each abstract method invoked by templateMethod().
