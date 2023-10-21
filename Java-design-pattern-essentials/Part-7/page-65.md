# Visitante (Visitor)

Type: Behavioural

Purpose: Represent a method to be performed on the elements of an object structure. Visitor lets you define a new method without changing the classes of the elements on which it operates.

Example usage: Similar operations need performing on different types in a structure, or as a means to add functionality without extensive modifications.

Consequences: Adding new visitable objects can require modifying visitors.

![_](../images/000022.jpg)

Visitor defines the interface that declares methods to visit each kind of visitable Element;

* ConcreteVisitor1 and ConcreteVisitor2 implement the Visitor interface for each element that could be visited;
* Element defines the interface for all classes which could be visited;
* ConcreteElementA and ConcreteElementB implement the Element interface for each class that could be visited.
* ObjectStructure contains references to all objects that can be visited, enabling iteration through them.
