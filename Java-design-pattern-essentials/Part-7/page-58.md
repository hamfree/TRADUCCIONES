# Observador (Observer)

Type: Behavioural

Purpose: Define a one-to-many dependency between objects so that when one object changes its state, all its dependants are notified and updated automatically.

Example usage: GUI controls, events, etc.

Consequences: Decouples classes through a common interface.

![_](../images/000016.jpg)

* Observable defines the mechanisms to register observers and notify them of events;
* ConcreteObservable extends Observable for a particular subject class;
* Observer defines an interface for interested classes;
* ConcreteObserver implements Observer for a particular interested class.
