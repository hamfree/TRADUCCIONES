# Cadena de responsabilidad (Chain of Responsibility)

Type: Behavioural

Purpose: Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it.

Example usage: When more than one object can handle a request and the handler is not known in advance.

Consequences: Not every request needs to be handled, or maybe it needs to be handled by more than one handler.

![_](../images/000050.jpg)

* Handler defines an interface for handling requests;
* ConcreteHandler1 and ConcreteHandler2 each decide if they can handle the request itself or if it should be passed on to its successor.

Client programs send their requests to the first object in the chain.
