# 22. Estrategia (Strategy)

Tipo: conductual

Objetivo: Define una familia de algoritmos, encapsula cada uno y los hace intercambiables. La estrategia permite que el algoritmo varíe independientemente de los clientes que lo utilicen.

La Compañía de Motores Foobar desea implementar un nuevo tipo de caja de cambios automática para sus coches que podrá cambiarse entre su modo estándar y un modo especial "deportivo". Los diferentes modos basarán la decisión de qué marcha se debe seleccionar dependiendo de la velocidad de desplazamiento, el tamaño del motor y si está turboalimentado. Y es muy posible que quieran otros modos en el futuro, como la conducción todoterreno.

Al igual que con la discusión en el capítulo sobre el patrón State, sería inflexible usar una serie de declaraciones if...else... para controlar los diferentes modos de caja de cambios directamente dentro de nuestras clases de vehículos. En su lugar, resumiremos el concepto que varía y definiremos una jerarquía separada para que cada modo de caja de cambios diferente sea una clase separada, siendo cada uno de ellos una "estrategia" diferente que se aplica. Este enfoque permite aislar la estrategia real que se está utilizando del vehículo. En nuestro ejemplo, sólo aplicaremos esto a los coches:

![Patrón Estrategia](../images/000055.jpg)

Figure 22.1 : Patrón Estrategia

La interfaz GearboxStrategy define el método para controlar el engranaje:

```java
public interface GearboxStrategy {
    public void ensureCorrectGear(Engine engine, int speed);
}
```

Hay dos clases de implementación; StandardGearboxStrategy y SportGearboxStrategy:

```java
public class StandardGearboxStrategy implements GearboxStrategy {
    public void ensureCorrectGear(Engine engine, int speed) {
        int engineSize = engine.getSize();
        boolean turbo = engine.isTurbo();
 
        // Algún código complicado para determinar la marcha correcta
        // configuración basada en el tamaño del motor, turbo y velocidad, etc.
        //... omitido...
         System.out.println("Calcula la marcha correcta a " + speed + " mph para una caja de cambios ESTÁNDAR");
    }
}


public class SportGearboxStrategy implements GearboxStrategy {
    public void ensureCorrectGear(Engine engine, int speed) {
        int engineSize = engine.getSize();
        boolean turbo = engine.isTurbo();
 
        // Algún código complicado para determinar la marcha correcta
        // configuración basada en el tamaño del motor, turbo y velocidad, etc.
        //... omitido...
        System.out.println("Calcula la marcha correcta a " + speed + " mph para una caja de cambios DEPORTIVA");
    }
}
```

Nuestra clase AbstractCar está definida para contener una referencia al tipo de interfaz (es decir, GearboxStrategy) y proporcionar métodos de acceso para que se puedan cambiar diferentes estrategias. También hay un método setSpeed() que delega cualquier estrategia que esté vigente. El código correspondiente está marcado en negrita:

```java
public abstract class AbstractCar extends AbstractVehicle {
    private GearboxStrategy gearboxStrategy;
 
    public AbstractCar(Engine engine) {
        this(engine, Vehicle.Colour.UNPAINTED);
    }
 
    public AbstractCar(Engine engine) {
        super(engine);
 
        //  Arranca en modo caja de cambios estándar (más económico)
        gearboxStrategy = new StandardGearboxStrategy();
    }
 
    // Permite cambiar la estrategia de la caja de cambios...
    public void setGearboxStrategy(GearboxStrategy gs) {
        gearboxStrategy = gs;
    }
 
    public GearboxStrategy getGearboxStrategy() {
        return getGearboxStrategy();
    }
 
    public void setSpeed(int speed) {
        // Delegar a la estrategia vigente...
        gearboxStrategy.ensureCorrectGear(getEngine(), speed);
    }
}
```

Los programas cliente simplemente establecen la estrategia requerida:

```java
AbstractCar myCar = new Sport(new StandardEngine(2000));
myCar.setSpeed(20);
myCar.setSpeed(40);

System.out.println("Activando la caja de cambios en modo deportivo...");
myCar.setGearboxStrategy(new SportGearboxStrategy());
myCar.setSpeed(20);
myCar.setSpeed(40);
```

Esto debería dar como resultado la siguiente salida:

```text
Calcula la marcha correcta a 20 mph para una caja de cambios ESTÁNDAR
Calcula la marcha correcta a 40 mph para una caja de cambios ESTÁNDAR
Activando la caja de cambios en modo deportivo...
Calcula la marcha correcta a 20 mph para una caja de cambios DEPORTIVA
Calcula la marcha correcta a 40 mph para una caja de cambios DEPORTIVA
```
