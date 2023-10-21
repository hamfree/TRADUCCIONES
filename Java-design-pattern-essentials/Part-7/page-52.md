# Método de Factoría (Factory Method)

Type: Creational

Purpose: Define an interface for creating an object, but let subclasses decide which class to instantiate.

Example usage: When you can't anticipate the specific type of object to be created, or you want to localise the knowledge of which class gets created.

Consequences: Reduces the need for clients to use 'new' to instantiate objects.

![_](../images/000020.jpg)

* Product defines the interface of the product that is to be created;
* ConcreteProduct is an implementation of a particular product;
* Factory declares the factory method that returns a Product object;
* ConcreteFactory implements the factory method defined in Factory to return an instance of Product.
