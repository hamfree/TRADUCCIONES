# 17. Iterador (Iterator)

Type: Conductual

Objetivo: Proporciona una forma de acceder a los elementos de un objeto agregado de forma secuencial sin exponer su representación subyacente.

La Compañía de Motores Foobar quería producir un folleto que enumerara su gama de vehículos a la venta y asignó la tarea a dos programadores separados, uno para proporcionar la gama de automóviles y el otro para proporcionar la gama de furgonetas.

El programador que codificó la clase `CarRange` decidió almacenar internamente el rango usando un objeto `List` de las colecciones de Java (específicamente un `ArrayList`):

```java
public class CarRange {
    private List<Vehicle> cars;

    public CarRange() {
        cars = new ArrayList<Vehicle>();

        cars.add(new Saloon(new StandardEngine(1300)));
        cars.add(new Saloon(new StandardEngine(1600)));
        cars.add(new Coupe(new StandardEngine(2000)));
        cars.add(new Sport(new TurboEngine(2500)));
    }

    public List<Vehicle> getRange() {
        return cars;
    }
}
```

Puede ver en lo anterior que el programador proporcionó un método `getRange()` que devuelve el objeto de la colección `List`.

El otro programador decidió almacenar las camionetas en una matriz al escribir la clase `VanRange` y, por lo tanto, su versión de `getRange()` devuelve una matriz de vehículos:

```java
public class VanRange {
    private Vehicle[] vans;

    public VanRange() {
        vans = new Vehicle[3];

        vans[0] = new BoxVan(new StandardEngine(1600));
        vans[1] = new BoxVan(new StandardEngine(2000));
        vans[2] = new Pickup(new TurboEngine(2200));
    }

    public Vehicle[] getRange() {
        return vans;
    }
}
```

El problema con esto es que la representación interna en ambas clases ha estado expuesta a objetos externos. Un mejor enfoque sería que cada una de las clases `CarRange` y `VanRange` proporcionara un objeto `Iterator` para que, además de ser coherente, no fuera necesario exponer la representación interna.

La interfaz de Java `Iterator` es una implementación del patrón _Iterator_ y tiene este aspecto (está en el paquete `java.util`):

```java
public interface Iterator<E> {
    public boolean hasNext();
    public E next();
    public void remove();
}
```

* El método _hasNext()_ devuelve verdadero si existe otro elemento
* El método _next()_ devuelve el siguiente elemento
* El método _remove()_ elimina el último elmento devuelto.

Para las listas, una subclase de `Iterator` llamada `ListIterator` proporciona algunos métodos adicionales.

Otra interfaz Java llamada `Iterable` (que está en `java.lang`) se puede implementar mediante clases que definen un método llamado `iterator()` que devuelve un objeto `Iterator`:

```java
public interface Iterable<T> {
    public Iterator<T> iterator();
}
```

Armados con este conocimiento, ahora podemos modificar nuestra clase `CarRange` para implementar la interfaz `Iterable` y proporcionar el nuevo método requerido (los cambios de código están en negrita):

```java
public class CarRange implements Iterable<Vehicle> {
    private List<Vehicle> cars;

    public CarRange() {
        cars = new ArrayList<Vehicle>();

        cars.add(new Saloon(new StandardEngine(1300)));
        cars.add(new Saloon(new StandardEngine(1600)));
        cars.add(new Coupe(new StandardEngine(2000)));
        cars.add(new Sport(new TurboEngine(2500)));
    }

    public List<Vehicle> getRange() {
        return cars;
    }

    public Iterator<Vehicle> iterator() {
        return cars.listIterator();
    }
}
```

La clase `VanRange` también se puede cambiar de manera similar, esta vez convirtiendo la matriz interna en un `Iterator`:

```java
public class VanRange implements Iterable<Vehicle> {
    private Vehicle[] vans;

    public VanRange() {
        vans = new Vehicle[3];

        Engine onePointSix = new StandardEngine(1600);
        Engine twoLitreTurbo = new TurboEngine(2000);

        vans[0] = new BoxVan(new StandardEngine(1600));
        vans[1] = new BoxVan(new StandardEngine(2000));
        vans[2] = new Pickup(new TurboEngine(2200));
    }

    public Vehicle[] getRange() {
        return vans;
    }

    public Iterator<Vehicle> iterator() {
        return Arrays.asList(vans).listIterator();
    }
}
```

Ahora podemos procesar tanto coches como furgonetas de forma coherente:

```java
System.out.println("=== Our Cars ===");
CarRange carRange = new CarRange();
printIterator(carRange.iterator());
System.out.println("=== Our Vans ===");
VanRange vanRange = new VanRange();
printIterator(vanRange.iterator());

public void printIterator(Iterator iter) {
    while (iter.hasNext()) {
    System.out.println(iter.next());
    }
}
```

## El bucle 'for-each'{#h2-8}

Varios de los otros capítulos de este libro han hecho uso de la construcción _for-each_ introducida con Java 5. Al implementar la interfaz `Iterable`, sus propias clases pueden hacer uso de esto, proporcionando una alternativa limpia a lo anterior, de la siguiente manera:

```java
System.out.println("=== Nuestros Coches ===");
CarRange carRange = new CarRange();
for (Vehicle currentVehicle : carRange.getRange()) {
    System.out.println(currentVehicle);
}

System.out.println("=== Nuestras Furgonetas ===");
VanRange vanRange = new VanRange();
for (Vehicle currentVehicle : vanRange.getRange()) {
    System.out.println(currentVehicle);
}
```
