# Mediador (Mediator)

Type: Behavioural

Purpose: Define an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and it lets you vary their interaction independently.

Example usage: Dialogs that control UI components, etc.

Consequences: The Mediator could be defined to use the Observer pattern to monitor the components.

![_](../images/000006.jpg)

* Mediator defines the interface for communication with Colleague objects;
* ConcreteMediator implements the Mediator interface and performs the communication;
* Colleague defines the interface for a component that needs communication with the Mediator;
* ConcreteColleague1 and ConcreteColleague2 implement the Colleague interface and performs the communication with the Mediator such that it needs no knowledge of any other Colleague.
