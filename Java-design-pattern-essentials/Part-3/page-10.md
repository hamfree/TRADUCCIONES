# 7. Adaptador (Adapter)

Type: Structural
Purpose: Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.

You will recall from the introduction that the Foobar Motor Company makes the engines for their vehicles. Here is a reminder of the Engine hierarchy:

![Jerarquía de la clase Engine](../images/000049.jpg)

Figura 7.1 : Jerarquía de la clase Engine

And here is a reminder of the code of the abstract AbstractEngine class:

```java
public abstract class AbstractEngine implements Engine {
    private int size;
    private boolean turbo;

    public AbstractEngine(int size, boolean turbo) {
        this.size = size;
        this.turbo = turbo;
    }
    public int getSize() {
        return size;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public String toString() {
        return getClass().getSimpleName() + " (" + size + ")");
    }
}
```

Let's say our client program takes engines stored in a collection and loops through them one at a time displaying the engine size and type:

```java
List<Engine> engines = new ArrayList<Engine>();

engines.add(new StandardEngine(1300));
engines.add(new StandardEngine(1600));
engines.add(new TurboEngine(2000));

for (Engine engine : engines) {
System.out.println(engine);
}
```

Running the above code would result in the following display:

```text
StandardEngine (1300)
StandardEngine (1600)
TurboEngine (2000)
```

For this chapter we will assume that in addition to the two concrete subclasses (StandardEngine and TurboEngine) Foobar have decided to use a further engine class named SuperGreenEngine which is made by a different manufacturer. Because the SuperGreenEngine class is provided by a third-party it does not implement our Engine interface. Furthermore, Foobar do not have access to the Java source code and can therefore not modify it, but the following class details are known from the documentation:

* The class extends `Object`;
* The constructor takes one argument for the engine size;
* There is a `getEngineSize()` method that returns the engine size as an `int`;
* These types of engines are never turbocharged;
* The `toString()` method returns a String in the format: **SUPER ENGINE nnnn** (where nnnn is the engine size).

We can therefore see that SuperGreenEngine uses a different method name to access the engine size and there is no method related to whether it is turbocharged, and that it is not within the Engine hierarchy. As it stands it would not be possible to add instances of SuperGreenEngine to the reporting collection and even if you could the method names are different.

The Adapter pattern provides an approach to resolve this through the definition of a new class that 'adapts' the class we want to use into the format existing classes require. For our purposes, therefore, we shall create a SuperGreenEngineAdapter class:

![Jerarquía de la clase Adapter](../images/000032.jpg)

Figura 7.2 : Jerarquía de la clase Adapter

The code for the adapter is as follows:

```java
public class SuperGreenEngineAdapter extends AbstractEngine {
    public SuperGreenEngineAdapter(SuperGreenEngine greenEngine) {
        super(greenEngine.getEngineSize(), false);
    }
}
```

Note the following from the above Java code:

We extend the class we are adapting to;
We accept a reference in the constructor to the class we are adapting from;
The constructor obtains the necessary state from the referenced object and passes it to the superclass constructor.

Now we are in a position to include SuperGreenEngine objects in our reporting collection (additional code indicated in bold):

```java
List<Engine> engines = new ArrayList<Engine>();
engines.add(new StandardEngine(1300));
engines.add(new StandardEngine(1600));
engines.add(new TurboEngine(2000));
engines.add(new StandardEngine(1600));
engines.add(new TurboEngine(2000));
```

**`// "Adapt" the new engine type...`**  
**`SuperGreenEngine greenEngine = new SuperGreenEngine(1200);`**  
**`engines.add(new SuperGreenEngineAdapter(greenEngine));`**  
**`// Unchanged from before...`**  

```java
for (Engine engine : engines) {
    System.out.println(engine);
}
```

The output should now be:

```text
StandardEngine (1300)
StandardEngine (1600)
TurboEngine (2000)
SuperGreenEngine (1200)
```

Note how the output made use of the toString() method as inherited from AbstractEngine rather than that of SuperGreenEngine.

## Variaciones para implementar adaptadores{#h2-10}

We were somewhat fortunate in that the design of the Engine and SuperGreenEngine classes made it easy for the adapter class to do the work inside its constructor. Often however, we need to take a few additional steps inside the code of the adapter class, so here is a general formula to apply:

_1. Extend the class you are adapting to (or implement it, if it's an interface);_  
_2. Specify the class you are adapting from in the constructor and store a reference to it in an instance variable;_  
_3. For each method in the class you are extending (or interface you are implementing), override it to delegate to the corresponding method of the class you are adapting from._  

Here is a generic example adapter class:

```java
public class ObjectAdapter extends ClassAdaptingTo {
    private ClassAdaptingFrom fromObject;

    public ObjectAdapter(ClassAdaptingFrom fromObject) {
        this.fromObject = fromObject;
    }

    // Overridden method
    public void methodInToClass() {
        fromObject.methodInFromClass();
    }
}
```

---

## 8. Puente (Bridge){#h2-11}

Type: Structural
Purpose: Decouple an abstraction from its implementation so that each may vary independently.

The Foobar Motor Company manufactures engines for its vehicles. Here is a reminder of the Engine class hierarchy:

![Jerarquía de la clase Engine](../images/000049.jpg)

Figura 8.1 : Jerarquía de la clase Engine

The implementation of the Engine classes as detailed in the introduction, merely stores the engine size (e.g. 1600cc) and whether it is turbocharged. For the purposes of this chapter this class will be enhanced to enable the engine to be started and stopped and for the power to the engine to be increased or decreased.

The modified version of the Engine interface and AbstractEngine class is listed below with the changes marked in bold:

```java
public interface Engine {
    public int getSize();
    public boolean isTurbo();
    public void start();
    public void stop();
    public void increasePower();
    public void decreasePower();
}

 

public abstract class AbstractEngine implements Engine {
    private int size;
    private boolean turbo;
    private boolean running;
    private int power;

    public AbstractEngine(int size, boolean turbo) {
        this.size = size;
        this.turbo = turbo;
        running = false;
        power = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public void start() {
        running = true;
        System.out.println("Engine started");
    }

    public void stop() {
        running = false;
        power = 0;
        System.out.println("Engine stopped");
    }

    public void increasePower() {
        if (running && (power < 10)) {
            power++;
            System.out.println("Engine power increased to " + power);
        }
    }

    public void decreasePower() {
        if (running && (power > 0)) {
            power--;
            System.out.println("Engine power decreased to " + power);
        }
    }

    public String toString() {
        return getClass().getSimpleName() + " (" + size ")");
    }
}
```

Within a vehicle, the driver controls the functions of the engine indirectly by means of various hand and foot controls, such as the ignition switch, accelerator pedal and brake pedal. To retain flexibility, it is important to design the connection between the engine and the controls so that each can vary independently of the other. In other words:

A new engine can be designed and plugged into a vehicle without needing any driver controls to be changed; and
New driver controls (for example, to assist disabled drivers) can be designed and plugged into a vehicle without needing the engines to change.

The Bridge pattern addresses this requirement by separating the 'abstraction' from the 'implementation' into two separate but connected hierarchies such that each can vary independently of the other. In our example, the 'abstraction' is the driver controls and the 'implementation' is the engine.

The following diagram shows this relationship:

![Patrón puente](../images/000014.jpg)

Figure 8.2 : Patrón puente

As the above figure shows, there is an abstract AbstractDriverControls class with two concrete subclasses; StandardControls and SportControls:

The AbstractDriverControls class requires an Engine object passed to its constructor and then delegates to the engine for each of its methods:

```java
public abstract class AbstractDriverControls {
    private Engine engine;

    public AbstractDriverControls(Engine engine) {
        this.engine = engine;
    }

    public AbstractDriverControls(Engine engine) {
        this.engine = engine;
    }

    public void ignitionOn() {
        engine.start();
    }

    public void ignitionOff() {
        engine.stop();
    }

    public void accelerate() {
        engine.increasePower();
    }

    public void brake() {
        engine.decreasePower();
    }
}
```

Subclasses of AbstractDriverControls can either use the superclass methods as-is or define additional functionality. The StandardControls class uses AbstractDriverControls as-is:

```java
public class StandardControls extends AbstractDriverControls {
    public StandardControls(Engine engine) {
    super(engine);
    }

    // No extra features
}
```

Whereas the SportControls class defines an additional method:

```java
    public class SportControls extends AbstractDriverControls {
        public SportControls(Engine engine) {
        super(engine);
    }

    public void accelerateHard() {
        accelerate();
        accelerate();
    }
}
```

The important point to note from the above is that the additional method is coded in terms of the superclass 'abstraction' and not the 'implementation' (engine). So in the above example the accelerateHard() method invokes the accelerate() method as defined in AbstractDriverControls. It is this approach that allows the abstraction and the implementation to vary independently if needed.

Thus we could incorporate a brand-new type of engine without modifying the driver controls classes, provided the engine adheres to the Engine contract. Conversely we could develop a new set of driver controls (such as enabling voice activation) without having to modify anything in the Engine hierarchy.

Client programs can use the bridge as follows:

```java
Engine engine = new StandardEngine(1300);
StandardControls controls1 = new StandardControls(engine);
controls1.ignitionOn();
controls1.accelerate();
controls1.brake();
controls1.ignitionOff();

// Now use sport controls
SportControls controls2 = new SportControls(engine);
controls2.ignitionOn();
controls2.accelerate();
controls2.accelerateHard();
controls2.brake();
controls2.ignitionOff();
```

---

## 9. Compuesto (Composite) {#h2-12}

Type: Structural

Purpose : Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.

In the Foobar Motor Company workshop they build various items from component parts such as nuts, bolts, panels, etc. Each individual component item has an associated description and unit cost, and when items are assembled into larger items the cost is therefore the sum of its component parts.

The Composite pattern enables us to treat both individual parts and assemblies of parts as if they are the same, thus enabling them to be processed in a consistent manner, simplifying code. The class hierarchy looks like this:

![Patrón compuesto](../images/000067.jpg)

Figura 9.1 : Patrón compuesto

The abstract Item class defines all possible methods for both parts and assemblies of parts:

```java
public abstract class Item {
    private String description;
    private int cost;

    public Item(String description, int cost) {
        this.description = description;
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }
    public int getCost() {
        return cost;
    }

    public abstract void addItem(Item item);
    public abstract void removeItem(Item item);
    public abstract Item[] getItems();

    public String toString() {
        return description + " (cost " + getCost() + ")";
    }
}
```

The above class provides default implementations for getDescription() and getCost(), and defines the abstract methods addItem(), removeItem() and getItems().

Individual parts are modelled using the Part subclass:

```java
public class Part extends Item {
    public Part(String description, int cost) {
        super(description, cost);
    }

    // Empty implementation for unit parts...
    public void addItem(Item item) {}
    public void removeItem(Item item) {}
    public Item[] getItems() {return new Item[0];}
}
```

As you can see, the methods related to managing assemblies of items have empty implementations since a 'part' is the smallest unit possible, and therefore unable to have sub-parts, unlike 'assemblies'.

Assemblies of parts are modelled using the Assembly subclass:

```java
public class Assembly extends Item {
    private List<Item> items;

    public Assembly(String description) {
        super(description, 0);
        items = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item[] getItems() {
        return items.toArray(new Item[items.size()]);
    }

    // Also have to override getCost() to add cost of items in list
    public int getCost() {
        int total = 0;
        for (Item item : items) {
            total += item.getCost();
        }
        return total;
    }
}
```

 For assemblies, we have implemented the abstract methods to add other Item objects into an internal List collection. We have also overridden the getCost() method to loop through the collection to sum the cost of all contained items within this assembly.

All types of Item objects can now be used in a uniform manner:

```java
Item nut = new Part("Nut", 5);
Item bolt = new Part("Bolt", 9);
Item panel = new Part("Panel", 35);

Item gizmo = new Assembly("Gizmo");
gizmo.addItem(panel);
gizmo.addItem(nut);
gizmo.addItem(bolt);

Item widget = new Assembly("Widget");
widget.addItem(gizmo);
widget.addItem(nut);
```

In the above extract, nuts, bolts and panels are defined as individual parts, a "Gizmo" is assembled from one nut, one bolt and one panel, and a "Widget" is assembled from one "Gizmo" and another nut. Displaying the objects would result in this output:

```text
Nut (cost 5)
Bolt (cost 9)
Panel (cost 35)
Gizmo (cost 49)
Widget (cost 54)
```

The assemblies have computed the total cost without the client program needing to know how.

---

## 10. Decorador (Decorator){#h2-13}

Type: Structural

Purpose: Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

You will recall the Foobar Motor Company Vehicle class hierarchy:

![Jerarquía de la clase Vehicle](../images/000010.jpg)
Figura 10.1 : Jerarquía de la clase Vehicle

For the purposes of this chapter, we shall add one additional method called getPrice() to the Vehicle interface. We will also modify the toString() method in AbstractVehicle to include the price. The modified interface and class is shown below with the changes marked in bold:

```java
public interface Vehicle {
    public enum Colour {UNPAINTED, BLUE, BLACK, GREEN, RED, SILVER, WHITE, YELLOW};
    public Engine getEngine();
    public void paint(Vehicle.Colour colour);
    public Vehicle.Colour getColour();
    public int getPrice();
}

public abstract class AbstractVehicle implements Vehicle {
    private Engine engine;
    private Vehicle.Colour colour;

    public AbstractVehicle(Engine engine) {
        this(engine, Vehicle.Colour.UNPAINTED);
    }

    public AbstractVehicle(Engine engine, Vehicle.Colour colour) {
        this.engine = engine;
        this.colour = colour;
    }

    public Engine getEngine() {
        return engine;
    }

    public Vehicle.Colour getColour() {
        return colour;
    }

    public void paint(Vehicle.Colour colour) {
        colour = colour;
    }

    public String toString() {
        return getClass().getSimpleName() + " (" + engine + ", " + colour + ", price " + getPrice() + ")";
    }
}
```

Each of the concrete subclasses implement the getPrice() method as appropriate. For example, the Saloon class now looks like this (changes in bold):

```java
public class Saloon extends AbstractCar {
    public Saloon(Engine engine) {
        this(engine, Vehicle.Colour.UNPAINTED)
    }

    public Saloon(Engine engine, Vehicle.Colour colour) {
        super(engine, colour);
    }

    public int getPrice() {
        return 6000;
    }
}
```

The other subclasses are similarly defined, and the getPrice() method returns:

* 6,000 for Saloon objects;
* 7,000 for Coupe objects;
* 8,000 for Sport objects;
* 9,000 for Pickup objects;
* 10,000 for BoxVan objects.  

When a customer buys a vehicle they have the choice of adding any number of optional extras. They can choose from an air-conditioning system, alloy wheels, leather seats, metallic paint, or a satellite-navigation unit. They can choose none at all, or any combination up to all five.

The Decorator pattern is designed to facilitate the addition of state and/or behaviour without having to modify the inheritance hierarchy of the classes being added to. This is accomplished by defining a new hierarchy which itself extends the root of the main tree.

This is shown diagrammatically below:

![Jerarquía del patrón Decorator](../images/000052.jpg)
Figura 10.2 : Jerarquía del patrón Decorator

From the diagram you can see that a new abstract class has been defined called AbstractVehicleOption that inherits from AbstractVehicle. AbstractVehicleOption has five concrete subclasses; one for each option that can be selected.

The AbstractVehicleOption class looks like this:

```java
public abstract class AbstractVehicleOption extends AbstractVehicle {
    protected Vehicle decoratedVehicle;

    public AbstractVehicleOption(Vehicle vehicle) {
        super(vehicle.getEngine());
        decoratedVehicle = vehicle;
    }
}
```

AbstractVehicleOption is the abstract "decorator" class and it requires a reference to the Vehicle class which is to be decorated.

Each of the option subclasses is straightforward. They all override the getPrice() method to add the price of the option to the price of the object that is being decorated. In the case of the AirConditionedVehicle and SatNavVehicle classes, we have also defined an extra method:

```java
public class AirConditionedVehicle extends AbstractVehicleOption {
    public AirConditioning(Vehicle vehicle) {
        super(vehicle);
    }

    public int getPrice() {
        return decoratedVehicle.getPrice() + 600;
    }

    public void setTemperature(int value) {
        // code to set the temperature...
    }
}

public class AlloyWheeledVehicle extends AbstractVehicleOption {
    public AlloyWheels(Vehicle vehicle) {
        super(vehicle);
    }

    public int getPrice() {
        return decoratedVehicle.getPrice() + 250;
    }
}

 

public class LeatherSeatedVehicle extends AbstractVehicleOption {
    public LeatherSeats(Vehicle vehicle) {
        super(vehicle);
    }

    public int getPrice() {
        return decoratedVehicle.getPrice() + 1200;
    }
}

 

public class MetallicPaintedVehicle extends AbstractVehicleOption {
    public MetallicPaint(Vehicle vehicle) {
        super(vehicle);
    }

    public int getPrice() {
        return decoratedVehicle.getPrice() + 750;
    }
}

public class SatNavVehicle extends AbstractVehicleOption {
    public SatNav(Vehicle vehicle) {
        super(vehicle);
    }

    public int getPrice() {
        return decoratedVehicle.getPrice() + 1500;
    }

    public void setDestination(String target) {
        // code to set the destination...
    }
}
```

To use the 'decorators' we initially instantiate the car or van we require and then "wrap" them inside the required decorator or decorators. Here is an example:

```java
// Create a blue saloon car...
Vehicle myCar = new Saloon(new StandardEngine(1300));
myCar.paint(Vehicle.Colour.BLUE);

// Add air-conditioning to the car...
myCar = new AirConditionedVehicle(myCar);

// Now add alloy wheels...
myCar = new AlloyWheeledVehicle(myCar);

// Now add leather seats...
myCar = new LeatherSeatedVehicle(myCar);

// Now add metallic paint...
myCar = new MetallicPaintedVehicle(myCar);

// Now add satellite-navigation...
myCar = new SatNavVehicle(myCar);
```

If you invoke System.out.prinln() on the myCar object at each stage you should see this output:

```text
Saloon (StandardEngine (1300), BLUE, price 6000)
AirConditionedVehicle (StandardEngine (1300), BLUE, price 6600)
AlloyWheeledVehicle (>StandardEngine (1300), BLUE, price 6850)
LeatherSeatedVehicle (StandardEngine (1300), BLUE, price 8050)
MetallicPaintedVehicle (StandardEngine (1300), BLUE, price 8800)
SatNavVehicle (StandardEngine (1300), BLUE, price 10300)
```

The price shown at each stage is the total of the vehicle plus the selected options as each is "added".

The Decorator pattern is a good example of preferring object composition over inheritance. Had we attempted to use inheritance for the various vehicle options we would have needed to create many different combinations of subclasses to model each combination of selectable options.

Decorator classes are sometimes called "wrapper" classes, since they serve to "wrap" an object inside another object, usually to add or modify its functionality.

---

## 11. Fachada (Facade){#h2-14}  

Type: Structural

Purpose: Provide a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.

Sometimes you need to perform a series of steps to undertake a particular task, often involving multiple objects. The Facade pattern involves the creation of a separate object that simplifies the execution of such steps.

As an example, when the Foobar Motor Company are preparing their vehicles for sale there are a number of steps they have to undertake that utilise various objects. In this chapter we shall assume that the Vehicle interface defines the following additional methods beyond those defined in the introduction.

```java
// Extra methods defined in Vehicle...

public void cleanInterior();
public void cleanExteriorBody();
public void polishWindows();
public void takeForTestDrive();
```

The above methods are implemented in AbstractVehicle as follows:

```java
public void cleanInterior() {
    System.out.println("Cleaning interior");
}

public void cleanExteriorBody() {
    System.out.println("Cleaning exterior");
}

public void polishWindows() {
    System.out.println("polishing windows");
}

public void takeForTestDrive() {
    System.out.println("taking for test drive");
}
```

We shall introduce two further simple classes called Registration and Documentation:

```java
public class Registration {
    private Vehicle vehicle;

    public Registration(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void allocateLicensePlate() {
        // Code omitted...
        System.out.println("License plate allocated");
    }

    public void allocateVehicleNumber() {
        // Code omitted...
        System.out.println("Vehicle number allocated");
    }
}
 

public class Documentation {
    public static void printBrochure(Vehicle vehicle) {
        // code omitted...
        System.out.println("Brochure printed");
    }
}
```

To implement the pattern we will create a VehicleFacade class that defines a method to prepare the specified vehicle by using the above classes on our behalf:

![Patrón Facade](../images/000047.jpg)

Figura 11.1 : Patrón Facade

``` java
public class VehicleFacade {
    public void prepareForSale(Vehicle vehicle) {
        Registration reg = new Registration(vehicle);
        reg.allocateVehicleNumber();
        reg.allocateLicensePlate();

        Documentation.printBrochure(vehicle);

        vehicle.cleanInterior();
        vehicle.cleanExteriorBody();
        vehicle.polishWindows();
        vehicle.takeForTestDrive();
    }
}
```

Client programs then only need invoke the prepareForSale() method on a VehicleFacade instance, and therefore need no knowledge of what needs to be done and what other objects are needed. And if something different is needed in a special circumstance, then the individual methods are still available for calling as required.

---

## 12. Flyweight{#h2-15}

Type: Structural

Purpose: Use sharing to support large numbers of fine-grained objects efficiently.

Some programs need to create a large number of objects of one particular type, and if those objects happen to have a large amount of state then instantiating lots of them can quickly use up memory. When considering object state, we often note that at least some of it could potentially be shared among a group of objects.

For the Foobar Motor Company, the Engine hierarchy is a case in point:

![Jerarquía de la clase Engine](../images/000049.jpg)

Figura 12.1 : Jerarquía de la clase Engine

Our simple implementation of Engine only defines two methods; getSize() and isTurbo(). Let's suppose we instantiate two engines as follows:

```java
Engine engine1 = new StandardEngine(1300);
Engine engine2 = new StandardEngine(1300);
```

The above would create two separate objects in memory, even though their state is identical. This can be thought of as its intrinsic state; i.e. all 1300cc standard engines will be storing 1300 for the engine size and false for whether it is turbocharged. Creating hundreds or thousands of these would be wasteful of memory, especially since a more realistic Engine class would require many more variables whose values would also be shared.

For the purposes of this chapter another method will be added to the Engine interface, called diagnose(). This new method will take a DiagnosticTool object as its argument, and this argument can be thought of as its extrinsic state, since its value is not actually stored in the Engine object; it is used purely so that the engine can use it to run a diagnostic check.

The DiagnosticTool interface looks like this:

```java
public interface DiagnosticTool {
    public void runDiagnosis(Object obj);
}
```

The EngineDiagnosticTool implements the above for running diagnostics on an engine:

```java
public class EngineDiagnosticTool implements DiagnosticTool {
    public void runDiagnosis(Object obj) {
        System.out.println("Starting engine diagnostic tool for " + obj);
        try {
            Thread.sleep(5000);
            System.out.println("Engine diagnosis complete");
        } catch (InterruptedException ex) {
            System.out.println("Engine diagnosis interrupted");
        }
    }
}
```

To simulate a long-running process the method pauses for five seconds. With the above in place we can now add a suitable method to the Engine interface:

```java
public interface Engine {
    // Methods having intrinsic (i.e. shared) state
    public int getSize();
    public boolean isTurbo();

    // Methods having extrinsic (i.e. unshared) state
    public void diagnose(DiagnosticTool tool);
}
```

The implementation of this new method in AbstractEngine simply issues a call-back to the DiagnosticTool:

```java
public void diagnose(DiagnosticTool tool) {
    tool.runDiagnosis(this);
}
```

The Flyweight pattern allows you to reference a multitude of objects of the same type and having the same state, but only by instantiating the minimum number of actual objects needed. This is typically done by allocating a 'pool' of objects which can be shared, and this is determined by a 'flyweight factory' class. Client programs get access to engines only through the factory:

![Patrón Flyweight](../images/000026.jpg)

Figura 12.2 : Patrón Flyweight

The EngineFlyweightFactory class looks like this:

```java
public class EngineFlyweightFactory {
    private Map<Integer, Engine> standardEnginePool;
    private Map<Integer, Engine> turboEnginePool;

    public EngineFlyweightFactory() {
        standardEnginePool = new HashMap<Integer, Engine>();
        turboEnginePool = new HashMap<Integer, Engine>();
    }

    public Engine getStandardEngine(int size) {
        Engine e = standardEnginePool.get(size);
        if (e == null) {
            e = new StandardEngine(size);
            standardEnginePool.put(size, e);
        }
        return e;
    }

    public Engine getTurboEngine(int size) {
        Engine e = turboEnginePool.get(size);
        if (e == null) {
            e = new TurboEngine(size);
            turboEnginePool.put(size, e);
        }
        return e;
    }
}
```

This class utilises two maps (one for standard engines and the other for turbo engines). Each time an engine of a particular type and size is requested, if a similar one has already been created it is returned rather than instantiating a new one. Client programs use the factory like this:

```java
// Create the flyweight factory...
EngineFlyweightFactory factory = new EngineFlyweightFactory();

// Create the diagnostic tool
DiagnosticTool tool = new EngineDiagnosticTool();

// Get the flyweights and run diagnostics on them
Engine standard1 = factory.getStandardEngine(1300);
standard1.diagnose(tool);

Engine standard2 = factory.getStandardEngine(1300);
standard2.diagnose(tool);

Engine standard3 = factory.getStandardEngine(1300);
standard3.diagnose(tool);

Engine standard4 = factory.getStandardEngine(1600);
standard4.diagnose(tool);

Engine standard5 = factory.getStandardEngine(1600);
standard5.diagnose(tool);

// Show that objects are shared
System.out.println(standard1.hashCode());
System.out.println(standard2.hashCode());
System.out.println(standard3.hashCode());
System.out.println(standard4.hashCode());
System.out.println(standard5.hashCode());
```

In the above, the variables standard1, standard2 and standard3 all reference the same Engine object (since they all 1300cc standard engines). Likewise, standard4 references the same object as standard5. Of course, whether it is worth running the diagnostics multiple times on the same objects is arguable depending upon the circumstances!

If the arguments passed to the extrinsic method (DiagnosticTool in our example) need to be stored, this should be done in the client program.

---

## 13. Apoderado (Proxy){#h2-16}

Type: Structural
Purpose: Provide a surrogate or place-holder for another object to control access to it.

Some methods can be time-consuming, such as those that load complex graphical components or need network connections. In these instances, the Proxy pattern provides a 'standin' object until such time that the time-consuming resource is complete, allowing the rest of your application to load.

In the chapter discussing the Flyweight pattern, the Engine hierarchy was enhanced to define the additional method diagnose(). As you saw, the implementation of runDiagnosis() in EngineDiagnosticTool is slow (we made it sleep for five seconds to simulate this), so we might consider making this run is a separate thread.

Here is a reminder of the Engine hierarchy with the additional method:

![Jerarquía de la clase Engine](../images/000009.jpg)

Figura 13.1 : Jerarquía de la clase Engine

The Proxy pattern involves creating a class that implements the same interface that we are standing-in for, in our case Engine. The proxy then forwards requests to the "real" object which it stores internally. Clients just access the proxy:

![Patrón Apoderado](../images/000063.jpg)

Figure 13.2 : Patrón Apoderado

Here is the code for the EngineProxy class:

```java
public class EngineProxy implements Engine {
    private Engine engine;

    public EngineProxy(int size, boolean turbo) {
        if (turbo) {
            engine = new TurboEngine(size);
        } else {
            engine = new StandardEngine(size);
        }
    }

    public int getSize() {
        return engine.getSize();
    }

    public boolean isTurbo() {
        return engine.isTurbo();
    }

    // This method is time-consuming...
    public void diagnose(final DiagnosticTool tool) {
        // Run the method as a separate thread
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("(Running tool as thread)");
                engine.diagnose(tool);
            }
        });
    t.start();
    System.out.println("EngineProxy diagnose() method finished");
    }
}
```

The constructor creates either a StandardEngine or TurboEngine object and stores a reference to it as an instance variable. Calls to getSize() and isTurbo() simply forward to the referenced engine object. Calls to diagnose() will invoke a separate thread to run the actual diagnosis. This can be useful if you cannot modify the original source for some reason.

This leaves the question of how you can 'force' client programs to use the proxy class instead of the normal class. One approach would be to make the constructors of StandardEngine and TurboEngine package-private (i.e. using no access modifier); then provided EngineProxy is in the same package it will be able to instantiate them but outside objects won't. It is also common to have a 'factory' class to make instantiation simpler, e.g. by providing a createStandardEngine() method.
