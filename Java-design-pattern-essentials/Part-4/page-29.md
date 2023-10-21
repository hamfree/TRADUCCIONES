# 18. Mediador (Mediator)

Type: Behavioural

Purpose: Define an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and it lets you vary their interaction independently.

The Foobar Motor Company is looking to the future when vehicles can drive themselves. This, of course, would entail the various components (ignition, gearbox, accelerator and brakes, etc.) being controlled together and interacting in various ways. For example:

* _Until the ignition is switched on, the gearbox, accelerator and brakes do not operate (we will assume the parking brake is in effect);_
* _When accelerating, the brakes should be disabled;_
* _When braking the accelerator should be disabled;_
* _The appropriate gear should be engaged dependent upon the speed of the vehicle._

And all this should happen automatically so the driver can just enjoy the view! (we will assume the vehicle can sense it's position so as to avoid crashes, etc.).

We will naturally create Java classes to model the individual components, so there will be an Ignition class, a Gearbox class, an Accelerator class and a Brake class. But we can also see that there are some complex interactions between them, and yet one of our core object-oriented design principles is to keep classes loosely-coupled.

The Mediator pattern helps to solve this through the definition of a separate class (the mediator) that knows about the individual component classes and takes responsibility for managing their interaction. The component classes also each know about the mediator class, but this is the only coupling they have. For our example, we will call the mediator class EngineManagementSystem.

We can see the connections diagrammatically below:

![Patrón Mediador](../images/000012.jpg)

Figura 18.1 : Patrón Mediador

The two-way communication is achieved via each of the component classes' constructors, in that they each accept a reference to the mediator object (so they can send messages to it) and register themselves with the mediator (so they can receive messages from it). But each component class has no knowledge of any other component class; they only know about the mediator.

We can see this by looking at the Ignition class:

```java
public class Ignition {
    private EngineManagementSystem mediator;
    private boolean on;

    // Constructor accepts mediator as an argument
    public Ignition(EngineManagementSystem mediator) {
        this.mediator = mediator;
        on = false;
        // Register back with the mediator...
        mediator.registerIgnition(this);
    }

    public void start() {
        on = true;
        mediator.ignitionTurnedOn();
        System.out.println("Ignition turned on");
    }

    public void stop() {
        on = false;
        mediator.ignitionTurnedOff();
        System.out.println("Ignition turned off");
    }

    public boolean isOn() {
        return on;
    }
}
```

Note how the constructor establishes the two-way communication, and then how methods that perform events notify the mediator of those events.

The Gearbox class applies the same principles:

```java
public class Gearbox {
    public enum Gear {NEUTRAL, FIRST, SECOND, THIRD, FOURTH, FIFTH, REVERSE};

    private EngineManagementSystem mediator;
    private boolean enabled;
    private Gear currentGear;

    public Gearbox(EngineManagementSystem mediator) {
        this.mediator = mediator;
        enabled = false;
        currentGear = Gear.NEUTRAL;
        mediator.registerGearbox(this);
    }

    public void enable() {
        enabled = true;
        mediator.gearboxEnabled();
        System.out.println("Gearbox enabled");
    }

    public void disable() {
        enabled = false;
        mediator.gearboxDisabled();
        System.out.println("Gearbox disabled");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setGear(Gear g) {
        if ((isEnabled()) && (getGear() != g)) {
            currentGear = g;
            mediator.gearChanged();
            System.out.println("Now in " + getGear() + " gear");
        }
    }

    public Gear getGear() {
        return currentGear;
    }
}
```

The Accelerator and Brake classes follow a similar process:

```java
public class Accelerator {
    private EngineManagementSystem mediator;
    private boolean enabled;
    private int speed;

    public Accelerator(EngineManagementSystem mediator) {
        this.mediator = mediator;
        enabled = false;
        speed = 0;
        mediator.registerAccelerator(this);
    }

    public void enable() {
        enabled = true;
        mediator.acceleratorEnabled();
        System.out.println("Accelerator enabled");
    }

    public void disable() {
        enabled = false;
        mediator.acceleratorDisabled();
        System.out.println("Accelerator disabled");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void accelerateToSpeed(int speed) {
        if (isEnabled()) {
            this.speed = speed;
            mediator.acceleratorPressed();
            System.out.println("Speed now " + getSpeed());
        }
    }

    public int getSpeed() {
        return speed;
    }
}

public class Brake {
    private EngineManagementSystem mediator;
    private boolean enabled;
    private boolean applied;

    public Brake(EngineManagementSystem mediator) {
        this.mediator = mediator;
        enabled = false;
        applied = false;
        mediator.registerBrake(this);
    }

    public void enable() {
        enabled = true;
        mediator.brakeEnabled();
        System.out.println("Brakes enabled");
    }

    public void disable() {
        enabled = false;
        mediator.brakeDisabled();
        System.out.println("Brakes disabled");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void apply() {
        if (isEnabled()) {
            applied = true;
            mediator.brakePressed();
            System.out.println("Now braking");
        }
    }

    public void release() {
        if (isEnabled()) {
            applied = false;
        }
    }
}
```

So we now need the EngineManagementSystem class to serve as the mediator. This will hold a reference to each of the component classes with methods enabling their registration with the mediator. It also has methods to handle the interaction between the various components when particular events occur:

```java
public class EngineManagementSystem {
    private Ignition ignition;
    private Gearbox gearbox;
    private Accelerator accelerator;
    private Brake brake;

    private int currentSpeed;

    public EngineManagementSystem() {
        currentSpeed = 0;
    }


    // Methods that enable registration with this mediator...
    public void registerIgnition(Ignition ignition) {
        this.ignition = ignition;
    }

    public void registerGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }

    public void registerAccelerator(Accelerator accelerator) {
        this.accelerator = accelerator;
    }

    public void registerBrake(Brake brake) {
        this.brake = brake;
    }

    // Methods that handle object interactions...
    public void ignitionTurnedOn() {
        gearbox.enable();
        accelerator.enable();
        brake.enable();
    }

    public void ignitionTurnedOff() {
        gearbox.disable();
        accelerator.disable();
        brake.disable();
    }

    public void gearboxEnabled() {
        System.out.println("EMS now controlling the gearbox");
    }

    public void gearboxDisabled() {
        System.out.println("EMS no longer controlling the gearbox");
    }

    public void gearChanged() {
        System.out.println("EMS disengaging revs while gear changing");
    }

    public void acceleratorEnabled() {
        System.out.println("EMS now controlling the accelerator");
    }

    public void acceleratorDisabled() {
        System.out.println("EMS no longer controlling the accelerator");
    }
    public void acceleratorPressed() {
        brake.disable();
        while (currentSpeed < accelerator.getSpeed()) {
            currentSpeed ++;
            System.out.println("Speed currently " + currentSpeed);

            // Set gear according to speed...
            if (currentSpeed <= 10) {
                gearbox.setGear(Gearbox.Gear.FIRST);
            } else if (currentSpeed <= 20) {
                gearbox.setGear(Gearbox.Gear.SECOND);
            } else if (currentSpeed <= 30) {
                gearbox.setGear(Gearbox.Gear.THIRD);
            } else if (currentSpeed <= 50) {
                gearbox.setGear(Gearbox.Gear.FOURTH);
            } else {
                gearbox.setGear(Gearbox.Gear.FIFTH);
            }
        }
        brake.enable();
    }

    public void brakeEnabled() {
        System.out.println("EMS now controlling the brakes");
    }

    public void brakeDisabled() {
        System.out.println("EMS no longer controlling the brakes");
    }

    public void brakePressed() {
        accelerator.disable();
        currentSpeed = 0;
    }

    public void brakeReleased() {
        gearbox.setGear(Gearbox.Gear.FIRST);
        accelerator.enable();
    }
}
```

## Usos comunes{#h2-9}

A common use of the Mediator pattern is to manage the interaction of graphical components on a dialog. This frequently involves controlling when buttons, text fields, etc. should be enabled or disabled, or for passing data between components.

Note that it may be possible to reduce the coupling further by using the Observer pattern in place of Mediator. This would mean that the component classes (i.e. Ignition, etc.) would not need to hold a reference to a mediator but would instead fire events. The EngineManagementSystem class would then be an observer of the component classes and would still be able to invoke messages on them.
