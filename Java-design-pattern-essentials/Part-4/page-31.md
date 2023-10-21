# 20. Observador (Observer)

Type: Behavioural

Purpose: Define a one-to-many dependency between objects so that when one object changes its state, all its dependants are notified and updated automatically.

The Foobar Motor Company has decided that an alert should sound to the driver whenever a certain speed is exceeded. They also envisage that other things may need to happen depending upon the current speed (such as an automatic gearbox selecting the appropriate gear to match the speed). But they realise the need to keep objects loosely-coupled, so naturally don't wish the Speedometer class to have any direct knowledge of speed monitors or automatic gearboxes (or any other future class that might be interested in the speed a vehicle is travelling).

The Observer pattern enables a loose-coupling to be established between a 'subject' (the object that is of interest; Speedometer on our example) and its 'observers' (any other class that needs to be kept informed when interesting stuff happens).

Because this is a very common need in object-oriented systems, the Java libraries already contains mechanisms that enable the pattern to be implemented. One of these is by utilising the Observable class and the Observer interface:

![Patrón Observador](../images/000051.jpg)

Figura 20.1 : Patrón Observador

The 'subject' (Speedometer) can have multiple observers (which can in fact be any class that implements the Observer interface, not just SpeedMonitor objects).

The Speedometer class looks like this:

```java
public class Speedometer extends Observable {
    private int currentSpeed;
 
    public Speedometer() {
        speed = 0;
    }
 
    public void setCurrentSpeed(int speed) {
        currentSpeed = speed;
 
        // Tell all observers so they know speed has changed...
        setChanged();
        notifyObservers();
    }
 
    public int getCurrentSpeed() {
        return currentSpeed;
    }
}
```

The Speedometer class extends java.util.Observable and thus inherits for our convenience its methods concerned with the registration and notification of observers. For our example, whenever the speed has changed we invoke the inherited setChanged() and notifyObservers() methods which takes care of the notifications for us.

The SpeedMonitor class implements the java.util.Observer interface and has the appropriate code for its required method update():

```java
public class SpeedMonitor implements Observer {
    public static final int SPEED_TO_ALERT = 70;
 
    public void update(Observable obs, Object obj) {
        Speedometer speedo = (Speedometer) obs;
        if (speedo.getCurrentSpeed() > SPEED_TO_ALERT) {
            System.out.println("** ALERT ** Driving too fast! (" + speedo.getCurrentSpeed() + ")");
        } else {
            System.out.println("... nice and steady ... (" + speedo.getCurrentSpeed() + ")");
        }
    }
}
```

Client programs simply pass a SpeedMonitor reference to an instance of Speedometer:

```java
// Create a monitor...
SpeedMonitor monitor = new SpeedMonitor();

// Create a speedometer and register the monitor to it...
Speedometer speedo = new Speedometer();
speedo.addObserver(monitor);

// Drive at different speeds...
speedo.setCurrentSpeed(50);
speedo.setCurrentSpeed(70);
speedo.setCurrentSpeed(40);
speedo.setCurrentSpeed(100);
speedo.setCurrentSpeed(69);
```

Running the above will result in the following output:

```text
... nice and steady ... (50)
... nice and steady ... (70)
... nice and steady ... (40)
** ALERT ** Driving too fast! (100)
... nice and steady ... (69)
```

The real power behind the Observer pattern is that any type of class can now become a monitor provided they implement the Observer interface, and without requiring any changes to be made to Speedometer. Let's create a simulation of an automatic gearbox:

```java
public class AutomaticGearbox implements Observer {
    public void update(Observable obs, Object obj) {
        Speedometer speedo = (Speedometer) obs;
 
        if (speedo.getCurrentSpeed() <= 10) {
            System.out.println("Now in first gear");
       
        } else if (speedo.getCurrentSpeed() <= 20) {
            System.out.println("Now in second gear");
       
        } else if (speedo.getCurrentSpeed() <= 30) {
            System.out.println("Now in third gear");
       
        } else {
            System.out.println("Now in fourth gear");
        }
    }
}
```

Our client program can now just add this as an additional observer and get notifications of speed changes as well:

```java
speedo.addObserver(new AutomaticGearbox());
```

## Un enfoque alternativo utilizando eventos y oyentes{#h2-12}

An alternative approach using events & listeners

The inherited code that makes Observable classes work does have an obvious downside; if your subject class already extends another class then you can't extend it as well, since Java only supports single inheritance (of classes). However, providing your own implementation is fairly straightforward, and here we will make use of an alternative approach provided by the Java libraries using 'events' and 'listeners'.

The first thing we shall do is separate out the events that can occur into a class called SpeedometerEvent that extends java.util.EventObject:

```java
public class SpeedometerEvent extends EventObject {
    private int speed;
 
    public SpeedometerEvent(Object source, int speed) {
        super(source);
        this.speed = speed;
    }
 
    public int getSpeed() {
        return speed;
    }
}
```

The only event of interest is when the speed changes. The inherited EventObject class provides a getSource() method so listeners will be able to tell the exact object that gave rise to the event, if they need to know it for some reason.

Going hand-in-hand with SpeedometerEvent is an interface we shall define called SpeedometerListener, which extends the java.util.EventListener interface:

```java
public interface SpeedometerListener extends EventListener {
    public void speedChanged(SpeedometerEvent event);
}
```

All classes that implement SpeedometerListener will need to provide appropriate code for the speedChanged() method. They can get any required data through the SpeedometerEvent reference passed in the argument. Note that our simple example only defines the one method, but it is common to have several methods that each notify a different piece of state that has changed.

The Speedometer class will now be modified to no longer extend java.util.Obervable and to instead handle listener registration and notification internally:

```java
public class Speedometer {
    private int currentSpeed;
    private List<SpeedometerListener> listeners;
 
    public Speedometer() {
        currentSpeed= 0;
        listeners = new ArrayList<SpeedometerListener>();
    }
 
    public void setCurrentSpeed(int speed) {
        currentSpeed = speed;
 
        // Tell all observers so they know speed has changed...
        fireSpeedChanged();
    }
 
    public int getCurrentSpeed() {
        return currentSpeed;
    }
 
    public void addSpeedometerListener(SpeedometerListener obj) {
        listeners.add(obj);
    }
 
    public void removeSpeedometerListener(SpeedometerListener obj) {
        listeners.remove(obj);
    }
 
    protected void fireSpeedChanged() {
        SpeedometerEvent speedEvent = new SpeedometerEvent(this, getCurrentSpeed());
 
        for (SpeedometerListener eachListener : listeners) {
            eachListener.speedChanged(speedEvent);
        }
    }
}
```

Note the use of an ArrayList to maintain the list of listeners, along with methods to add and remove them and loop through them when an SpeedometerEvent needs to be sent.

The SpeedMonitor class is our listener and now needs to implement the SpeedometerListener interface instead of java.util.Observable:

```java
public class SpeedMonitor implements SpeedometerListener {
    public static final int SPEED_TO_ALERT = 70;
 
    public void speedChanged(SpeedometerEvent event) {
        if (event.getSpeed() > SPEED_TO_ALERT) {
            System.out.println("** ALERT ** Driving too fast! (" + event.getSpeed() + ")");
        } else {
            System.out.println("... nice and steady ... (" + event.getSpeed() + ")");
        }
    }
}
```

Our client program is almost identical to before, the only change being a different method name when registering the listener:

```java
// Create a listener
SpeedMonitor monitor = new SpeedMonitor();

// Create a speedometer and register the monitor to it...
Speedometer speedo = new Speedometer();

speedo.addSpeedometerListener(monitor);

// Drive at different speeds...
speedo.setCurrentSpeed(50);
speedo.setCurrentSpeed(70);
speedo.setCurrentSpeed(40);
speedo.setCurrentSpeed(100);
speedo.setCurrentSpeed(69);
```

If your classes are JavaBeans then the Java libraries also supply a PropertyChangeEvent class and PropertyChangeListener interface that follow a similar approach.
