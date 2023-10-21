# 17. Iterador (Iterator)

Type: Behavioural

Purpose: Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

The Foobar Motor Company wanted to produce a brochure listing their range of vehicles for sale and allocated the task to two separate programmers, one to provide the range of cars and the other to provide the range of vans.

The programmer that coded the CarRange class decided to internally store the range using a List object from the Java collections (specifically an ArrayList):

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

You can see from the above that the programmer provided a getRange() method that returns the List collection object.

The other programmer decided to store the vans in an array when writing the VanRange class, and therefore his version of getRange() returns an array of vehicles:

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

The problem with this is that the internal representation in both of these classes has been exposed to outside objects. A better approach would be for each of the CarRange and VanRange classes to provide an Iterator object so that as well as being consistent, the internal representation would not need to be exposed.

The Java Iterator interface is an implementation of the Iterator pattern, and looks like this (it is in the java.util package):

```java
public interface Iterator<E> {
    public boolean hasNext();
    public E next();
    public void remove();
}
```

The hasNext() method returns true if another item exists;
The next() method returns the next item;
The remove() method removes the last returned item.

For lists, a subclass of Iterator called ListIterator provides some additional methods.

Another Java interface called Iterable (which is in java.lang) can be implemented by classes that define a method called iterator() that returns an Iterator object:

```java
public interface Iterable<T> {
    public Iterator<T> iterator();
}
```

Armed with this knowledge we can now modify our CarRange class to implement the Iterable interface and provide the new required method (code changes in bold):

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

The VanRange class can also be changed along similar lines, this time converting the internal array into an Iterator:

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

Now we can process both cars and vans in a consistent manner:

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

Several of the other chapters in this book have made use of the for-each construct introduced with Java 5. By implementing the Iterable interface your own classes can make use of this, providing a clean alternative to the above, as follows:

```java
System.out.println("=== Our Cars ===");
CarRange carRange = new CarRange();
for (Vehicle currentVehicle : carRange.getRange()) {
    System.out.println(currentVehicle);
}

System.out.println("=== Our Vans ===");
VanRange vanRange = new VanRange();
for (Vehicle currentVehicle : vanRange.getRange()) {
    System.out.println(currentVehicle);
}
```
