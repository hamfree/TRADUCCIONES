# 26. Factoría simple

In the main section of this book we looked at both the Factory Method pattern and the Abstract Factory pattern. The Simple Factory pattern is a commonly used simplified means of delegating the instantiation of objects to a specific class (the 'factory').

We shall assume here that the Foobar Motor Company manufactures two types of gearbox; an automatic gearbox and a manual gearbox. Client programs might need to create one or the other based upon a condition, as illustrated by the following code fragment (assuming the classes are defined within a class hierarchy):

```java
Gearbox selectedGearbox = null;
if (typeWanted = "automatic") {
     selectedGearbox = new AutomaticGearbox();
} else if (typeWanted = "manual") {
     selectedGearbox = new ManualGearbox();

}


// Do something with selectedGearbox...
```

While the above code will of course work, what happens if more than one client program needs to perform a similar selection? We would have to repeat the if...else... statements in each client program, and if a new type of gearbox is subsequently manufactured we would have to track down every place the if...else... block is used.

Remembering the principle of encapsulating the concept that varies, we can instead delegate the selection and instantiation process to a specific class, known as the 'factory', just for that purpose. Client programs then only make use of the create() method of the factory, as illustrated in the diagram below:

![Patrón de Factoría Simple](../images/000048.jpg)

Figura 26.1 : Patrón de Factoría Simple

The abstract Gearbox class in our simple example merely defines a no-argument constructor:

```java
public abstract class Gearbox {
    public Gearbox() {}
}
```

The AutomaticGearbox and ManualGearbox classes each extend Gearbox for their respective types:

```java
public class AutomaticGearbox extends Gearbox {
    public AutomaticGearbox() {
        System.out.println("New automatic gearbox created");
    }
}


public class ManualGearbox extends Gearbox {
    public ManualGearbox() {
        System.out.println("New manual gearbox created");
    }
}
```

We now need to create our GearboxFactory class that is capable of instantiating the appropriate Gearbox:

```java
public class GearboxFactory {
    public enum Type {AUTOMATIC, MANUAL};
 
    public static Gearbox create(Type type) {
        if (type == Type.AUTOMATIC) {
            return new AutomaticGearbox();
 
        } else {
            return new ManualGearbox();
        }
    }
}
```

The create() method takes care of the selection and instantiation, and thus isolates each client program from repeating code. We have made the method static purely for convenience; it is not a requirement of the pattern.

Client programs now obtain the type of gearbox by means of the factory:

```java
// Create an automatic gearbox
Gearbox auto = GearboxFactory.create(GearboxFactory.Type.AUTOMATIC);

// Create a manual gearbox
Gearbox manual = GearboxFactory.create(GearboxFactory.Type.MANUAL);
```
