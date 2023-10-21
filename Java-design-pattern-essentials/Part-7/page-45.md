# Puente (Bridge)

Type: Structural

Purpose: Decouple an abstraction from its implementation so that each may vary independently.

Example usage: GUI frameworks and persistence frameworks.

Consequences: An implementation is not permanently bound to an interface, and can be switched at run-time.

![_](../images/000058.jpg)

* Abstraction is the abstraction of the interface;
* ConcreteClass implements the `Abstraction interface and holds a reference to Implementor. It provides an implementation in terms of Implementor;
* Implementor is the implementation interface which may be quite different to the Abstraction interface;
* ConcreteImplementor1 and ConcreteImplementor2 implement the Implementor interface.
