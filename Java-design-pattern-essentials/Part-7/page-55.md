# Iterador (Iterator)

Type: Behavioural

Purpose: Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

Example usage: Wherever a collection or array of objects or values need to be processed in turn.

Consequences: The for-each syntax simplifies usage.

![_](../images/000039.jpg)

* Iterator defines the interface for the iterator;
* ConcreteIterator implements Iterator to perform the processing of each element in Aggregate;
* Aggregate defines the interface for the collection to be processed;
* ConcreteAggregate implements Aggregate for the actual collection.
