# 19. Recuerdo (Memento)

Tipo: Conductual

Objetivo: Sin violar la encapsulación, captura y externaliza el estado interno de un objeto para que pueda restaurarse a este estado más adelante.

Naturalmente, los vehículos de la Compañía de Motores Foobar tienen un velocímetro montado en el salpicadero, que no sólo registra la velocidad actual sino también la anterior. Ahora es necesario que el estado se almacene periódicamente en el exterior (para poder integrarlo, por ejemplo, en un tacógrafo para vehículos de mercancías).

Sin embargo, una de las variables de instancia en la clase `Speedometer` no tiene un método getter, pero para cumplir con los principios de encapsulación y ocultación de datos, se declara correctamente como privada. También queremos adherirnos al principio de que una clase no debe tener múltiples responsabilidades, por lo que no queremos tener que incorporar un mecanismo de guardar y restaurar estado en la clase. Entonces, ¿cómo podemos capturar el estado del objeto?

En este capítulo se presentarán dos enfoques diferentes, cada uno con sus ventajas y desventajas. En ambos casos, hacemos uso de una clase separada que realiza el guardado y restauración del estado, a la que llamaremos `SpeedometerMemento`. Esta clase toma una referencia al objeto `Speedometer` que necesita ser externalizado:

![Patrón Recuerdo](../images/000066.jpg)

Figura 19.1 : Patrón Recuerdo

## Enfoque 1: utilizar la visibilidad privada del paquete{#h2-10}

Cuando el modificador de acceso se omite en los miembros de la clase, adquiere visibilidad de 'paquete privado'. Esto significa que solo es accesible para otras clases en el mismo paquete, por lo que es un poco más abierto que la visibilidad privada, pero no tanto como `protected` (las subclases en diferentes paquetes no podrán acceder). Por lo tanto, podemos colocar la clase `Speedometer` en un paquete donde limitamos qué otras clases existen allí, que en nuestro caso será simplemente `SpeedometerMemento`.

Aquí está la clase `Speedometer` muy simple:

```java
package mementosubpackage;

public class Speedometer {
    // Visibilidad privada normal pero tiene método de acceso...
    private int currentSpeed;
 
    // visibilidad privada del paquete y sin método de acceso...
    int previousSpeed;
    public Speedometer() {
        currentSpeed = 0;
        previousSpeed = 0;
    }
 
    public void setCurrentSpeed(int speed) {
        previousSpeed = currentSpeed;
        currentSpeed = speed;
    }
 
    public int getCurrentSpeed() {
        return currentSpeed;
    }
}
```

La clase `SpeedometerMemento` existe en el mismo paquete. Guarda el estado del objeto `Speedometer` pasado en el constructor y define un método para restaurar ese estado:

```java
package mementosubpackage;

public class SpeedometerMemento {
    private Speedometer speedometer;
    private int copyOfCurrentSpeed;
    private int copyOfPreviousSpeed;
 
    public SpeedometerMemento(Speedometer speedometer) {
        this.speedometer = speedometer;
        copyOfCurrentSpeed = speedometer.getCurrentSpeed();
        copyOfPreviousSpeed = speedometer.previousSpeed;
    }
 
    public void restoreState() {
        speedometer.setCurrentSpeed(copyOfCurrentSpeed);
        speedometer.previousSpeed = copyOfPreviousSpeed;
    }
}
```

Tenga en cuenta que el método de acceso `getCurrentSpeed()` se usó para la variable de instancia `currentSpeed` pero se tuvo que acceder directamente a la variable `previousSpeed`, lo cual es posible porque el recuerdo existe en el mismo paquete.

Podemos probar el recuerdo con este código:

```java
Speedometer speedo = new Speedometer();

speedo.setCurrentSpeed(50);
speedo.setCurrentSpeed(100);
System.out.println("Velocidad actual: " + speedo.getCurrentSpeed());
System.out.println("Velocidad previa: " + speedo.previousSpeed);

// Salva el estado de 'speedo'...
SpeedometerMemento memento = new SpeedometerMemento(speedo);

// Cambia el estado 'speedo'...
speedo.setCurrentSpeed(80);
System.out.println("Después de configurar a 80...");
System.out.println("Velocidad actual: " + speedo.getCurrentSpeed());
System.out.println("Velocidad previa:" + speedo.previousSpeed);

// Restaura el estado del 'speedo'...
System.out.println("Ahora restaurando el estado...");
memento.restoreState();
System.out.println("Velocidad actual: " + speedo.getCurrentSpeed());
System.out.println("Velocidad previa: " + speedo.previousSpeed);
```

Al ejecutar lo anterior se obtiene el siguiente resultado:

```text
Velocidad actual: 100
Velocidad anterior: 50

Después de configurar a 80...
Velocidad actual: 80
Velocidad anterior: 100

Ahora restaurando el estado...
Velocidad actual: 100
Velocidad anterior: 50
```

La principal desventaja de este enfoque es que debe colocar el par de clases en su propio paquete especial o aceptar que otras clases en el paquete en el que se encuentran tendrán acceso directo a las variables de instancia.

## Enfoque 2: serialización de objetos{#h2-11}

Este enfoque le permite hacer que todas las variables de instancia sean privadas, recuperando así la encapsulación completa. La clase `Speedometer` se ha modificado para esto y ahora incluye un método `getPreviousSpeed()`, aunque esto es únicamente para ayudarnos a probar el recuerdo; Este enfoque no lo requiere. La clase también ha sido cambiada para implementar la interfaz `Serializable` (cambios marcados en negrita):


```java
public class Speedometer implements Serializable {
    private int currentSpeed;
    private int previousSpeed;
 
    public Speedometer() {
        currentSpeed = 0;
        previousSpeed = 0;
    }
 
    public void setCurrentSpeed(int speed) {
        previousSpeed = currentSpeed;
        currentSpeed = speed;
    }
 
    public int getCurrentSpeed() {
        return currentSpeed;
    }
 
    // Sólo se define para ayudar en las pruebas...
    public int getPreviousSpeed() {
        return previousSpeed;
    }
}
```

La clase `SpeedometerMemento` ahora utiliza la serialización de objetos para guardar y restaurar el estado:

```java
public class SpeedometerMemento {
    public SpeedometerMemento(Speedometer speedometer) throws IOException {
        // Guardamos...
        File speedometerFile = new File("speedometer.ser");
        oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(speedometerFile)));
        oos.writeObject(speedometer);
        oos.close();
    }
 
    public Speedometer restoreState() throws IOException, ClassNotFoundException {
        // Obtenemos...
        File speedometerFile = new File("speedometer.ser");
        ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(speedometerFile)));
        Speedometer speedo = (Speedometer) ois.readObject();
        ois.close();
        return speedo;
    }
}
```

Podemos verificar que esto logra lo mismo que el primer enfoque, la única diferencia es que el método `restoreState()` ahora devuelve la referencia del objeto restaurado:

```java
Speedometer speedo = new Speedometer();

speedo.setCurrentSpeed(50);
speedo.setCurrentSpeed(100);
System.out.println("Velocidad actual: " + speedo.getCurrentSpeed());
System.out.println("Velocidad anterior: " + speedo.previousSpeed);

// Salva el estado 'speedo'...
SpeedometerMemento memento = new SpeedometerMemento(speedo);

// Cambia el estado de 'speedo'...
speedo.setCurrentSpeed(80);
System.out.println("Después de configurar a 80...");
System.out.println("Velocidad actual: " + speedo.getCurrentSpeed());
System.out.println("Velocidad anterior: " + speedo.previousSpeed);

// Restaura el estado de 'speedo'...
System.out.println("Ahora restaurando el estado...");
speedo = memento.restoreState();
System.out.println("Velocidad actual: " + speedo.getCurrentSpeed());
System.out.println("Velocidad anterior: " + speedo.previousSpeed);
```

La ejecución de lo anterior debería dar como resultado el mismo resultado que se muestra para el primer enfoque. La principal desventaja de este enfoque es que escribir y leer desde un archivo de disco es mucho más lento. Tenga en cuenta también que, si bien hemos podido volver a hacer que todos los campos sean `private`, aún es posible que alguien que haya obtenido acceso al archivo serializado utilice un editor hexadecimal para leer o cambiar los datos.
