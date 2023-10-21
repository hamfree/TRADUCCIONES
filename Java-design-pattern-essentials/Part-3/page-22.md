# 11. Fachada (Facade)  

Tipo: Estructural

Objetivo: Proporciona una interfaz unificada para un conjunto de interfaces en un subsistema. Facade define una interfaz de nivel superior que hace que el subsistema sea más fácil de usar.

A veces es necesario realizar una serie de pasos para realizar una tarea concreta, que a menudo implica varios objetos. El patrón Fachada implica la creación de un objeto separado que simplifica la ejecución de dichos pasos.

Por ejemplo, cuando la Compañía de Motores Foobar está preparando sus vehículos para la venta, debe seguir una serie de pasos que utilizan varios objetos. En este capítulo asumiremos que la interfaz Vehicle define los siguientes métodos adicionales además de los definidos en la introducción.

```java
// Métodos extra definidos en Vehicle...

public void cleanInterior();
public void cleanExteriorBody();
public void polishWindows();
public void takeForTestDrive();
```

Los métodos anteriores se implementan en AbstractVehicle de la siguiente manera:

```java
public void cleanInterior() {
    System.out.println("Limpiar interiores");
}

public void cleanExteriorBody() {
    System.out.println("Limpiar exterior");
}

public void polishWindows() {
    System.out.println("Pulir ventanas");
}

public void takeForTestDrive() {
    System.out.println("montar para prueba de conducción");
}
```

Introduciremos dos clases simples más llamadas Registration y Documentation:

```java
public class Registration {
    private Vehicle vehicle;

    public Registration(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void allocateLicensePlate() {
        // Código omitido...
        System.out.println("Matrícula asignada");
    }

    public void allocateVehicleNumber() {
        // Código omitido...
        System.out.println("Número de vehículo asignado");
    }
}
 

public class Documentation {
    public static void printBrochure(Vehicle vehicle) {
        // Código omitido...
        System.out.println("Folleto impreso");
    }
}
```

Para implementar el patrón, crearemos una clase VehicleFacade que define un método para preparar el vehículo especificado utilizando las clases anteriores en nuestro nombre:

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

Los programas cliente entonces solo necesitan invocar el método prepareForSale() en una instancia de VehicleFacade y, por lo tanto, no necesitan saber qué se debe hacer ni qué otros objetos se necesitan. Y si se necesita algo diferente en una circunstancia especial, entonces los métodos individuales todavía están disponibles para llamar según sea necesario.
