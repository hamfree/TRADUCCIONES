# Intérprete (Interpreter)

Type: Behavioural

Purpose: Given a language, define a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language.

Example usage: Simple grammars and mini-language processing.

Consequences: Not suitable for complex grammars and language processing.

![_](../images/000056.jpg)

* AbstractExpression defines the abstract method to interpret an element;
* TerminalExpression extends AbstractExpression for language elements that terminate an expression;
* NonTerminalExpression extends AbstractExpression for language elements that are just part of an expression;
* Context is the object that is being parsed (e.g. the grammar or language).
