# Factoría abstracta (Abstract Factory)

Type: Creational

Purpose: Provide an interface for creating families of related or dependent objects without specifying their concrete classes.

Example usage: Commonly used when generating graphical 'widgets' for different look-and-feels. Used within the Java class libraries for that purpose.

Consequences: Isolates concrete classes. Enables easy exchange of product families. Promotes consistency among products.

![_](../images/000004.jpg)

* AbstractFactory defines an interface for methods that create abstract product objects;
* ConcreteFactory1 and ConcreteFactory2 take care of instantiating the appropriate product families (e.g. ConcreteFactory1 creates ProductA1 and ProductB1);
* AbstractProductA and AbstractProductB defines the interface of each different type of product;

Client programs only use the interfaces declared by AbstractFactory and AbstractProductA and AbstractProductB.
