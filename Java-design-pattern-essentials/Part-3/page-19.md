# 8. Puente (Bridge)

Tipo: Estructural  

Objetivo: Desacopla una abstracción de su implementación para que cada una pueda variar de forma independiente.

La Compañía de Motores Foobar manufactura motores para sus vehículos. Aquí tiene un recordatorio de la jerarquía de la clase Engine:

![Jerarquía de la clase Engine](../images/000049.jpg)

Figura 8.1 : Jerarquía de la clase Engine

La implementación de las clases Engine como se detallaron en la introducción, simplemente almacenan el tamaño del motor (por ejemplo, 1600cc) y si está turboalimentado. Para los propósitos de este capítulo esta clase será mejorada para permitir arrancar y detener el motor y aumentar y disminuir la potencia del motor.

La versión modificada de la interfaz Engine y la clase AbstractEngine se lista debajo con los cambios marcados en negrita:

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
        System.out.println("El motor arrancó");
    }

    public void stop() {
        running = false;
        power = 0;
        System.out.println("Motor parado");
    }

    public void increasePower() {
        if (running && (power < 10)) {
            power++;
            System.out.println("La potencia del motor aumentó a" + power);
        }
    }

    public void decreasePower() {
        if (running && (power > 0)) {
            power--;
            System.out.println("La potencia del motor disminuyó a" + power);
        }
    }

    public String toString() {
        return getClass().getSimpleName() + " (" + size ")");
    }
}
```

Dentro de un vehículo, el conductor controla las funciones del motor indirectamente mediante varios controles manuales y de pie, como el interruptor de encendido, el pedal del acelerador y el pedal del freno. Para conservar la flexibilidad, es importante diseñar la conexión entre el motor y los controles de modo que cada uno pueda variar independientemente del otro. En otras palabras:

Se puede diseñar y conectar un nuevo motor a un vehículo sin necesidad de cambiar ningún control del conductor. Y
se pueden diseñar nuevos controles del conductor (por ejemplo, para ayudar a los conductores discapacitados) y conectarlos a un vehículo sin necesidad de cambiar los motores.

El patrón Puente resuelve este requerimiento separando la "abstracción" de la "implementación" en dos jerarquías separadas pero conectadas, de tal forma que cada una puede variar independientemente de la otra. En nuestro ejemplo, la "abstracción" son los controles del conductor y la "implementación" es el motor.

El siguiente diagrama muestra esta relación:

![Patrón puente](../images/000014.jpg)

Figure 8.2 : Patrón puente

Como la figura de arriba muestra, hay una clase abstracta AbstractDriverControls con dos subclases concretas: StandardControls and SportControls:

La clase AbstractDriverControls requiere que se le pase un objeto Engine a su constructor y después delega al motor para cada uno de sus métodos:

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

Las subclases de AbstractDriverControls pueden usar los métodos de la superclase tal cual o definir funcionalidades adicionales. La clase StandardControls usa AbstractDriverControls tal cual:  

```java
public class StandardControls extends AbstractDriverControls {
    public StandardControls(Engine engine) {
    super(engine);
    }

    // Sin funciones adicionales
}
```

Mientras que la clase SportControls define un método adicional:

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

El punto importante a tener en cuenta de lo anterior es que el método adicional está codificado en términos de la superclase "abstracción" y no de "implementación" (motor). Entonces, en el ejemplo anterior, el método accelerateHard() invoca el método accelerate() tal como se define en AbstractDriverControls. Es este enfoque el que permite que la abstracción y la implementación varíen de forma independiente si es necesario.

De esta manera podríamos incorporar un nuevo tipo de motor sin modificar las clases de controles del conductor, siempre que el motor cumpla con el contrato de Engine. Por el contrario, podríamos desarrollar un nuevo conjunto de controles del conductor (como habilitar la activación por voz) sin tener que modificar nada en la jerarquía de Engine.

Los programas del cliente pueden usar el puente de la siguiente manera:

```java
Engine engine = new StandardEngine(1300);
StandardControls controls1 = new StandardControls(engine);
controls1.ignitionOn();
controls1.accelerate();
controls1.brake();
controls1.ignitionOff();

// Ahora usa controles deportivos
SportControls controls2 = new SportControls(engine);
controls2.ignitionOn();
controls2.accelerate();
controls2.accelerateHard();
controls2.brake();
controls2.ignitionOff();
```
