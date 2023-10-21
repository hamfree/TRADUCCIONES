# 6. Único (Singleton) {#h2-9}

Tipo: Creacional

Objetivo: Asegúra de que una clase permita que solo se cree un objeto, proporcionando un único punto de acceso al mismo.

La Compañía de Motores Foobar, al igual que todos los fabricantes de vehículos, necesita estampar un número de serie único en todos los vehículos que produce. Quieren modelar este requisito garantizando que haya un solo lugar sencillo donde se pueda obtener el siguiente número de serie disponible. Si tuviéramos más de un objeto que genere el siguiente número, existe el riesgo de que terminemos con secuencias de numeración separadas, por lo que debemos evitar esto.

El patrón Único proporciona una manera de garantizar que sólo se pueda crear una instancia de una clase particular. Entonces, ¿cómo podemos evitar que otros objetos simplemente invoquen new varias veces? Hay varias formas de lograr esto, y el enfoque "tradicional" que puede encontrar a menudo es hacer que su constructor sea privado pero proporcionar un método getter estático público que devuelva una instancia estática de la clase Singleton. Así es como podría verse:

```java
public class SerialNumberGeneratorTraditional {
    // miembros estáticos
    private static SerialNumberGeneratorTraditional instance;
    
    public synchronized static SerialNumberGeneratorTraditional getInstance() {
        if (instance == null) {
            instance = new SerialNumberGeneratorTraditional();
        }
        return instance;
    }
    
    // variables de instancia
    private int count;
     
    // constructor privado
    private SerialNumberGeneratorTraditional() {}
    
    // métodos de instancia
    public synchronized int getNextSerial() {
        return ++count;
    }
}
```

Tenga en cuenta que el método getInstance() solo creará una instancia del objeto una vez, por lo que siempre se devolverá la misma instancia. El constructor es privado para evitar que los programas cliente llamen a new, lo que refuerza el hecho de que solo se puede crear un objeto, ya que solo pueden pasar por el método getInstance(). Los métodos getInstance() y getNextSerial() se sincronizan en caso de que sean llamados por subprocesos separados. El patrón único podría usarse así:

```java
System.out.println("Usando el patrón único tradicional");
SerialNumberGeneratorTraditional generator = SerialNumberGeneratorTraditional.getInstance();
System.out.println("próxima serie: " + generator.getNextSerial());
System.out.println("próxima serie: " + generator.getNextSerial());
System.out.println("próxima serie: " + generator.getNextSerial());
```

Podría decirse que existe una forma mejor de codificar singletons (objetos únicos) desde Java 1.5 mediante la utilización del tipo enum. La clase anterior sería entonces:

```java
public enum SerialNumberGenerator {
    INSTANCE;
   
    private int count;
    
    public synchronized int getNextSerial() {
        return ++count;
    }
}
```

El nombre de constante INSTANCE representa el objeto único. Como en el enfoque tradicional, hay un recuento de variables de instancia y un método sincronizado getNextSerial(), pero ahora no hay necesidad de definir ningún miembro estático ni preocuparse por un constructor.

Usar el enum de objeto único es tan simple como esto:

```java
System.out.println("Usando enum de objeto único");
System.out.println("siguiente vehículo: " + SerialNumberGenerator.INSTANCE.getNextSerial());
System.out.println("siguiente vehículo: " + SerialNumberGenerator.INSTANCE.getNextSerial());
System.out.println("siguiente motor: " + SerialNumberGenerator.INSTANCE.getNextSerial());
```

A veces es posible que desee un número específico de singletons (objetos únicos) diferentes que realicen las mismas acciones (el llamado Multiton, una contracción de "singleton múltiple"). Esto no es tan sencillo con el enfoque tradicional, pero cuando se utiliza la técnica de enumeración es tan fácil como definir constantes separadas. Por ejemplo, supongamos que ahora necesita números de serie separados para vehículos y motores, donde cada uno se incrementa independientemente uno del otro:

```java
public enum SerialNumberGenerator {
    ENGINE, VEHICLE;
   
    private int count;
    
    public synchronized int getNextSerial() {
        return ++count;
    }
}
```

La constante INSTANCE ha sido renombrada como VEHICLE y se ha definido una segunda constante ENGINE. Puede acceder a estas como sigue:

```java
System.out.println("Usando enum de objeto unico (multiton)");
System.out.println("siguiente vehículo: " + SerialNumberGenerator.VEHICLE.getNextSerial());
System.out.println("siguiente vehículo: " + SerialNumberGenerator.VEHICLE.getNextSerial());
System.out.println("siguiente motor: " + SerialNumberGenerator.ENGINE.getNextSerial());
System.out.println("siguiente vehículo: " + SerialNumberGenerator.VEHICLE.getNextSerial());
System.out.println("siguiente motor: " + SerialNumberGenerator.ENGINE.getNextSerial());
```

The output of which should be:

```text
siguiente vehículo: 1
siguiente vehículo: 2
siguiente motor: 1
siguiente vehículo: 3
siguiente motor: 2
```
