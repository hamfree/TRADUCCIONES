# 5. Prototipo (Prototype) {#h2-8}

Tipo: Creacional

Objetivo: Especifica los tipos de objetos que se crearán utilizando una instancia prototípica y crea nuevos objetos copiando el prototipo.

En este capítulo asumiremos que crear instancias de objetos de automóviles y camionetas es un proceso que requiere mucho tiempo y, por lo tanto, necesitamos encontrar una manera de acelerar el tiempo de creación de instancias cada vez que necesitemos un nuevo objeto de vehículo. Aquí hay un recordatorio de la jerarquía de clases de vehículos:

![Jerarquía de la clase Vehicle](../images/000010.jpg)

Figura 5.1 : Jerarquía de la clase Vehicle

Un enfoque que puede mejorar el tiempo de creación de instancias es utilizar el método clone() de Java. Por lo tanto, especificaremos que la interfaz Vehículo extiende Cloneable y definiremos el método clone(). El código para realizar la clonación se definirá en AbstractVehicle. Por lo tanto, este capítulo utiliza una versión modificada de la interfaz Vehicle y la clase AbstractVehicle como se enumera a continuación, donde el código adicional se indica en negrita:

```java
public interface Vehicle extends Cloneable {
    public enum Colour {UNPAINTED, BLUE, BLACK, GREEN, RED, SILVER, WHITE, YELLOW};
    public Engine getEngine();
    public Vehicle.Colour getColour();
    public void paint(Vehicle.Colour colour);
    public Object clone();
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
         // ... followed by lots of time-consuming stuff
    }
 
    public Engine getEngine() {
        return engine;
    }
 
    public Vehicle.Colour getColour() {
        return colour;
    }
 
    public void paint(Vehicle.Colour colour) {
        this.colour = colour;
    }
 
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException x) {
            // Should not happen...
        }
        return obj;
    }
 
    public String toString() {
        return getClass().getSimpleName() +
                " (" + engine + ", + colour + ");
    }
}
```

El método clone() sobreescribible se ha hecho público para permitir más fácilmente su uso por parte de otros objetos.

Ninguna de las subclases de automóviles o camionetas necesita cambiar ya que heredan de Vehicle en la raíz de la jerarquía.

Ahora definiremos una clase VehicleManager que creará los vehículos iniciales de los que podremos obtener clones:

```java
public class VehicleManager {
    private Vehicle saloon, coupe, sport, boxVan, pickup;
 
    public VehicleManager() {
        // Para simplificar, todos los vehículos utilizan el mismo tipo de motor...
        saloon = new Saloon(new StandardEngine(1300));
        coupe = new Coupe(new StandardEngine(1300));
        sport = new Sport(new StandardEngine(1300));
        boxVan = new BoxVan(new StandardEngine(1300));
        pickup = new Pickup(new StandardEngine(1300));
    }
 
    public Vehicle createSaloon() {
        return (Vehicle) saloon.clone();
    }
 
    public Vehicle createCoupe() {
        return (Vehicle) coupe.clone();
    }

    public Vehicle createSport() {
        return (Vehicle) sport.clone();
    }
 
    public Vehicle createBoxVan() {
        return (Vehicle) boxVan.clone();
    }
 
    public Vehicle createPickup() {
        return (Vehicle) pickup.clone();
    }
}
```

Los programas cliente pueden utilizar VehicleManager de la siguiente manera:

```java
VehicleManager manager = new VehicleManager();
Vehicle saloon1 = manager.createSaloon();
Vehicle saloon2 = manager.createSaloon();
Vehicle pickup1 = manager.createPickup();
```

Una desventaja de VehicleManager tal como está codificado es que siempre crea una instancia de al menos un vehículo de cada tipo como parte del proceso de construcción. Si no se necesitan todos los tipos de vehículos, una técnica más eficiente sería realizar una carga diferida creando solo una instancia de la primera vez que se necesita cada uno. Esto se ilustra en la versión modificada de la clase (que llamaremos VehicleManagerLazy) a continuación:

```java
public class VehicleManagerLazy {
    private Vehicle saloon, coupe, sport, boxVan, pickup;
 
    public VehicleManagerLazy() {
    }
 
    public Vehicle createSaloon() {
        if (saloon == null) {
            saloon = new Saloon(new StandardEngine(1300));
            return saloon;
        } else {
            return (Vehicle) saloon.clone();
        }
    }
 
    public Vehicle createCoupe() {
        if (coupe == null) {
            coupe = new Coupe(new StandardEngine(1300));
            return coupe;
        } else {
            return (Vehicle) coupe.clone();
        }
    }
 
    public Vehicle createSport() {
        if (sport == null) {
            sport = new Sport(new StandardEngine(1300));
            return sport;
        } else {
            return (Vehicle) sport.clone();
        }
    }
 
    public Vehicle createBoxVan() {
        if (boxVan == null) {
            boxVan = new BoxVan(new StandardEngine(1300));
            return boxVan;
        } else {
            return (Vehicle) boxVan.clone();
        }
    }
 
    public Vehicle createPickup() {
        if (pickup == null) {
            pickup = new Pickup(new StandardEngine(1300));
            return pickup;
        } else {
            return (Vehicle) pickup.clone();
        }
    }
}
```

Antes de devolver un clon, se realiza una verificación para garantizar que el objeto 'prototipo' existe y se creará una instancia si es necesario. A partir de entonces, simplemente clona el objeto instanciado previamente. Los programas cliente pueden usar VehicleManagerLazy de la misma manera que antes:

```java
VehicleManagerLazy manager = new VehicleManagerLazy();
 
Vehicle saloon1 = manager.createSaloon();
Vehicle saloon2 = manager.createSaloon();
Vehicle pickup1 = manager.createPickup();
```
