# 1. What are Design Patterns?

Imagine that you have just been assigned the task of designing a software system. Your customer tells you that he needs to model the Gadgets his factory makes and that each Gadget comprises the same component parts but those parts are a bit different for each type of Gadget. And he also makes Gizmos, where each Gizmo comes with a selection of optional extras any combination of which can be chosen. And he also needs a unique sequential serial number stamped on each item made.

Just how would you go about designing these classes?

The chances are that no matter what problem domain you are working in, somebody else has had to design a similar solution in the past. Not necessarily for Gadgets and Gizmos of course, but conceptually similar in terms of objectives and structure. In other words there's a good chance that a generic solution or approach already exists, and all you need to do is to apply that approach to solve your design conundrum.

This is what Design Patterns are for. They describe generic solutions to software design problems. Once versed in patterns, you might think to yourself "those Gadgets could be modelled using the *Abstract Factory* pattern, the Gizmos using the *Decorator* pattern, and the serial number generation using the *Singleton* pattern."

## Cómo utiliza este libro los patrones {#h2-1}

This book gives worked examples for each of the 23 patterns described in the classic reference work *Design Patterns: Elements of Reusable Object-Oriented Software* \(Gamma, 1995\) plus four additional useful patterns, including Model-View-Controller \(MVC\).

Each of the worked examples in this book uses a common theme drawn from the business world, being that of a fictional vehicle manufacturer called the Foobar Motor Company. The company makes a range of cars and vans together with the engines used to power the vehicles. You should therefore familiarise yourself with the classes described in this introduction.

The class hierarchy looks like this:

Figure 1.1 : Vehicle and Engine class hierarchies

Vehicle and Engine are the root interfaces of the hierarchies, with each vehicle object requiring a reference to an Engine object. AbstractVehicle is an abstract class that implements the Vehicle interface, and AbstractEngine likewise implements the Engine interface. For vehicles, we also have AbstractCar and AbstractVan together with concrete subclassses Saloon, Coupe and Sport as types of cars. AbstractVan has the concrete subclasses BoxVan and Pickup as types of van.

The concrete subclasses of AbstractEngine are StandardEngine and TurboEngine.

Despite there being several classes in the hierarchies the code for each has been kept deliberately simple so you can focus on understanding the patterns rather than having to decipher complex code. To illustrate this, here is the Java source code for the Engine interface:

public interface Engine \{  
    public int getSize\(\);  
    public boolean isTurbo\(\);  
\} 


This simple interface merely requires method getters to return the engine size \(in cubic centimetres\) and whether it is turbocharged. The AbstractEngine class looks like this:

public abstract class AbstractEngine implements Engine \{  
    private int size;  
    private boolean turbo;  
   
    public AbstractEngine\(int size, boolean turbo\) \{  
        this.size = size;  
        this.turbo = turbo;  
    \}  
   
    public int getSize\(\) \{  
        return size;  
    \}  
   
    public boolean isTurbo\(\) \{  
        return turbo;  
    \}  
   
    public String toString\(\) \{  
        return getClass\(\).getSimpleName\(\) \+ " \(" \+ size \+ "\)";  
    \}  
\} 


This simplified implementation of an engine requires the appropriate attributes to be supplied in the constructor. The toString\(\) method has been implemented to produce output in this format:

StandardEngine \(1300\)  
TurboEngine \(2000\) 


The equals\(\) and hashCode\(\) methods will inherit from Object and therefore use object identity. This makes sense, since for example, two separate 1300cc Standard engines are logically different entities and so should be treated as such \(one engine would go into one vehicle and the other engine into a different vehicle\).

The concrete subclasses are trivially simple:

public class StandardEngine extends AbstractEngine \{  
    public StandardEngine\(int size\) \{  
        super\(size, false\); // not turbocharged  
    \}  
\} 


public class TurboEngine extends AbstractEngine \{  
    public TurboEngine\(int size\) \{  
        super\(size, true\); // turbocharged  
    \}  
\} 


Now that you have seen the Engine hierarchy we can look at the Vehicle interface:

public interface Vehicle \{  
    public enum Colour \{UNPAINTED, BLUE, BLACK, GREEN, RED, SILVER, WHITE, YELLOW\};  
   
    public Engine getEngine\(\);  
    public Vehicle.Colour getColour\(\);  
    public void paint\(Vehicle.Colour colour\);  
\} 


A nested enum called Colour defines the possible colours that each Vehicle object could be.

This is how the AbstractVehicle class implements Vehicle:

public abstract class AbstractVehicle implements Vehicle \{  
    private Engine engine;  
    private Vehicle.Colour colour;  
   
    public AbstractVehicle\(Engine engine\) \{  
        this\(engine, Vehicle.Colour.UNPAINTED\);  
    \}  
   
    public AbstractVehicle\(Engine engine, Vehicle.Colour colour\) \{  
        this.engine = engine;  
        this.colour = colour;  
    \}  
   
    public Engine getEngine\(\) \{  
        return engine;  
    \}  
   
    public Vehicle.Colour getColour\(\) \{  
        return colour;  
    \}  
   
    public void paint\(Vehicle.Colour colour\) \{  
        this.colour = colour;  
    \}  
   
    public String toString\(\) \{  
        return getClass\(\).getSimpleName\(\) \+ " \(" \+ engine \+ ", " \+ colour \+ "\);  
    \}  
\} 


The overloaded constructors in AbstractVehicle require an Engine object and optionally a vehicle colour to be supplied.

The output of calls to toString\(\) will be in this format:

Saloon \(StandardEngine \(1300\), RED\)  
BoxVan \(TurboEngine \(2200\), WHITE\) 


The AbstractCar and AbstractVan classes just forward to the constructors \(obviously real classes would define whatever is different between cars and vans\):

public abstract class AbstractCar extends AbstractVehicle \{  
    public AbstractCar\(Engine engine\) \{  
        this\(engine, Vehicle.Colour.UNPAINTED\)  
    \}  
   
    public AbstractCar\(Engine engine, Vehicle.Colour colour\) \{  
        super\(engine, colour\);  
    \}  
\} 


public abstract class AbstractVan extends AbstractVehicle \{  
    public AbstractVan\(Engine engine\) \{  
        this\(engine, Vehicle.Colour.UNPAINTED\)  
    \}  
   
    public AbstractVan\(Engine engine, Vehicle.Colour colour\) \{  
        super\(engine, colour\);  
    \}     
\} 


The concrete subclasses also just forward to the constructors:

public class Saloon extends AbstractCar \{  
    public Saloon\(Engine engine\) \{  
        this\(engine, Vehicle.Colour.UNPAINTED\)  
    \}  
   
    public Saloon\(Engine engine, Vehicle.Colour colour\) \{  
        super\(engine, colour\);  
    \}  
\} 


public class Coupe extends AbstractCar \{  
    public Coupe\(Engine engine\) \{  
        this\(engine, Vehicle.Colour.UNPAINTED\)  
    \}  
   
    public Coupe\(Engine engine, Vehicle.Colour colour\) \{  
        super\(engine, colour\);  
    \}     
\} 


public class Sport extends AbstractCar \{  
    public Sport\(Engine engine\) \{  
        this\(engine, Vehicle.Colour.UNPAINTED\)  
    \}  
   
    public Sport\(Engine engine, Vehicle.Colour colour\) \{  
        super\(engine, colour\);  
    \}  
\} 


public class BoxVan extends AbstractVan \{  
    public BoxVan\(Engine engine\) \{  
        this\(engine, Vehicle.Colour.UNPAINTED\)  
    \}  
   
    public BoxVan\(Engine engine, Vehicle.Colour colour\) \{  
        super\(engine, colour\);  
    \}  
\} 


public class Pickup extends AbstractVan \{  
    public Pickup\(Engine engine\) \{  
        this\(engine, Vehicle.Colour.UNPAINTED\)  
    \}  
   
    public Pickup\(Engine engine, Vehicle.Colour colour\) \{  
        super\(engine, colour\);  
    \}  
\} 


Many of the patterns in this book utilise one or more of the above classes in some way, often adding additional functionality or classes for the purposes of explaining the pattern in question. You will also frequently see reference to a Client class; this just refers to whatever class is making use of the pattern under discussion.

## Cómo se clasifican los patrones {#h2-2}

Each of the patterns described in this book fall under one of three categories; *Creational*, *Structural* or *Behavioural*:

+ *Creational* patterns provide approaches to object instantiation. Where you place the new keyword affects how tightly or loosely coupled your classes are;
+ *Structural* patterns provide approaches for combining classes and objects to form larger structures. Deciding whether to use inheritance or composition affects how flexible and adaptable your software is;
+ *Behavioural* patterns provide approaches for handling communication between objects.

## Principios comunes en patrones de diseño {#h2-3}

Experience has shown that some object-oriented approaches are more flexible than others. Here is a summary of the main principles that the patterns in this book strive to adhere to:

1. ***Program to an interface, not an implementation.*** By "interface" is meant the general concept of abstraction, which could refer to a Java interface or an abstract class. To accomplish this, use the most general type \(e.g. interface\) possible when declaring variables, constructor and method arguments, etc. Doing so gives extra flexibility as to the actual types that are used at run-time.

2. ***Prefer object composition over inheritance.*** Where a class is related to another in some way, you should distinguish between "is a" \(or "is a type of"\) and "has a" relationships. In the Vehicle and Engine hierarchies described earlier, it is true to say that AbstractCar "is a" Vehicle, and that Saloon "is a" AbstractCar. But it would not be true to say that Vehicle "is a" Engine, but rather that a Vehicle "has a" Engine. Therefore, inheritance is legitimately used for AbstractCar and Saloon, but object composition is used between Vehicle and Engine. Do not be tempted to use inheritance just to save having to write some methods. Sometimes using a "has a" relationship is more flexible even when an "is a" relationship seems the natural choice. You will see an example of this in the *Decorator* pattern.

3. ***Keep objects loosely-coupled.*** Ideally, classes should model just one thing, and only be composed of other objects that are genuinely required \(such as a Vehicle requiring an Engine\). Ask yourself what would happen if you wanted to use a class you have written in a completely different application; what "baggage" \(i.e. other classes\) would also need to be copied? By keeping this to a minimum, you make your class more re-usable. A good example of a pattern that uses loose-coupling is *Observer*.

4. ***Encapsulate the concept that varies.*** If you've written a class in which some parts are the same for each instance but another part of the class varies for each instance, consider extracting the latter into a class of its own, which is referenced by the original class. An example pattern that uses principle is *Strategy*.

## Algunos consejos generales {#h2-4}

The principles listed above will become more apparent as we explore the patterns in detail. You should also note that the patterns described in this book give a general approach to a particular problem. It is quite acceptable for you to modify or adapt them to better fit your particular problem. And it is very common for multiple patterns to be combined to solve complex problems.

However, do remember that you should strive to keep things simple. It is easy, after reading a book such as this, to think that you have to find a pattern to solve a particular problem when an even simpler solution might be available. One of the mantras of Extreme Programming \(XP\) is *"You aren't going to need it"*, the idea being that you should avoid adding features before they are required, and this philosophy could also be applied to patterns; beware of adding an unnecessary feature just so you can apply a pattern. Patterns are not a "magic bullet", just another set of tools in your toolkit, albeit an indispensable set.

Use your knowledge and experience to judge whether a pattern should be applied to your circumstances, and if so to what extent you need to adapt it. A good example of when applying patterns may be beneficial is when you are "refactoring" existing code. Refactoring is when you are changing the structure of some software but not its behaviour, to improve its maintainability and flexibility. This provides a good opportunity to examine your code to see if a pattern might provide a better structure, such as replacing conditionals, or defining factory classes to aid object instantiation.

Patterns have been applied to many programming languages besides Java, particularly object-oriented languages, and indeed other fields, having originated by being applied to architectural design. And new patterns are being developed and applied on a regular basis, so you may view this book as merely a starting point in the subject.
