# 25. Objeto nulo

As any Java programmer soon discovers, software testing often throws up NullPointerException messages. Sometimes, the only way around this is to specifically test for null before performing an operation, which puts an extra onus on the programmer.

Suppose a vehicle's instrument panel contains three slots for warning lights (such as for low oil level or low brake fluid level). A particular vehicle might only use these two lights, with the third slot empty, represented by null within Java. Looping through the slots would require a specific test to prevent a NullPointerException being thrown:

```java
OilLevelLight &  BrakeFluidLight are each types of WarningLight
WarningLight[] lights = new WarningLight[3];
lights[0] = new OilLevelLight();
lights[1] = new BrakeFluidLight();
lights[2] = null; //  empty slot

for (WarningLight currentLight : lights) {
    if (currentLight != null) {
        currentLight.turnOn();
        currentLight.turnOff();
        System.out.println(currentLight.isOn());
    }
}
```

An approach that can help prevent the need to test for null is to create a 'null object' class as part of the class hierarchy. This class will implement the same interface but perform no actual function, as illustrated in the following figure:

![Patrón de Objeto Nulo](../images/000029.jpg)

Figura 25.1 : Patrón de Objeto Nulo

The WarningLight interface defines the methods turnOn(), turnOff() and isOn():

```java
public interface WarningLight {
    public void turnOn();
    public void turnOff();
    public boolean isOn();
}
```

The OilLightLevel and BrakeFluidLevel classes each implement the WarningLight interface and provide the appropriate code to switch the light on or off:

```java
public class OilLevelLight implements WarningLight {
    private boolean on;
 
    public void turnOn() {
        on = true;
    System.out.println("Oil level light ON");
    }
 
    public void turnOff() {
        on = false;
        System.out.println("Oil level light OFF");
    }
   
    public boolean isOn() {
        return on;
    }
}


public class BrakeFluidLight implements WarningLight {
    private boolean on;
 
    public void turnOn() {
        on = true;
        System.out.println("Brake fluid light ON");
    }
 
    public void turnOff() {
        on = false;
        System.out.println("Brake fluid light OFF");
    }
   
    public boolean isOn() {
        return on;
    }
}
```

For the Null Object pattern we also create a NullObjectLight class that implements the WarningLight interface but performs no actual processing:

```java
public class NullObjectLight implements WarningLight {
    public void turnOn() {
        // Do nothing...
    }
 
    public void turnOff() {
        // Do nothing...
    }
   
    public boolean isOn() {
        return false;
    }
}
```

Now our client code can be simplified since we no longer need to test if a slot is null, provided we make use of the null object:

```java
WarningLight[] lights = new WarningLight[3];
lights[0] = new OilLevelLight();
lights[1] = new BrakeFluidLight();
lights[2] = new NullObjectLight(); // empty slot

// No need to test for null...
for (WarningLight currentLight : lights) {
    currentLight.turnOn();
    currentLight.turnOff();
    System.out.println(currentLight.isOn());
}
```

Note that for Null Object getter methods you will need to return whatever seems sensible as a default; hence above the isOn() method returns false since it represents a non-extentent light.
