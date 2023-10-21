# 7. Adaptador (Adapter)

Tipo: Estructural

Objetivo: Convierta la interfaz de una clase en otra interfaz que los clientes esperan. El adaptador permite que clases trabajen juntas que de otro modo no podrían hacerlo debido a interfaces incompatibles.

Recordará de la introducción que la Compañía de Motores Foobar hace los motores para sus vehículos. Aquí hay un recordatorio de la jerarquía de Engine:

![Jerarquía de la clase Engine](../images/000049.jpg)

Figura 7.1 : Jerarquía de la clase Engine

Y aquí está el recordatorio del código de la clase abstracta AbstractEngine:

```java
public abstract class AbstractEngine implements Engine {
    private int size;
    private boolean turbo;

    public AbstractEngine(int size, boolean turbo) {
        this.size = size;
        this.turbo = turbo;
    }
    public int getSize() {
        return size;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public String toString() {
        return getClass().getSimpleName() + " (" + size + ")");
    }
}
```

Digamos que nuestro programa cliente toma los motores almacenados en una colección y los recorre uno a la vez mostrando el tamaño y el tipo de motor:

```java
List<Engine> engines = new ArrayList<Engine>();

engines.add(new StandardEngine(1300));
engines.add(new StandardEngine(1600));
engines.add(new TurboEngine(2000));

for (Engine engine : engines) {
System.out.println(engine);
}
```

Al ejecutar el código anterior se obtendría la siguiente pantalla:

```text
StandardEngine (1300)
StandardEngine (1600)
TurboEngine (2000)
```

Para este capítulo asumiremos que además de las dos subclases concretas (StandardEngine y TurboEngine) Foobar ha decidido usar otra clase de motor llamado SuperGreenEngine el cual fabrica un manufacturador diferente. Debido a que la clase SuperGreenEngine es proporcionada por un tercero,  no implementa nuestro interfaz Engine. Además, Foobar no tiene acceso al código fuente en Java, y por tanto, no puede modificarlo, pero los siguientes detalles de la clase se conocen a partir de la documentación:

* La clase extiende `Object`
* El constructor recibe un argumento para el tamaño del motor
* Hay un método `getEngineSize()` que devuelve el tamaño del motor como un `int`;
* Estos tipos de motores nunca tienen turbocompresor
* El método `toString()` devuelve una cadena en el formato: **SUPER ENGINE nnnn** (donde nnnn es el tamaño del motor).

Podemos, por lo tanto, ver que SuperGreenEngine usa un nombre de método diferente para acceder al tamaño del motor y que no hay método relacionado para saber si está turboalimentado, y que no está dentro de la jerarquía de Engine. Tal como está, no sería posible agregar instancias de SuperGreenEngine a la colección de informes en incluso si pudiera, los nombres de los métodos son diferentes.

El patrón Adaptador proporciona un enfoque para resolver esto mediante la definición de una nueva clase que "adapta" la clase que queremos usar al formato que requieren las clases existentes . Para nuestro propósitos, por lo tanto, crearemos una clase SuperGreenEngineAdapter:

![Jerarquía de la clase Adapter](../images/000032.jpg)

Figura 7.2 : Jerarquía de la clase Adapter

El código para el adaptador es como sigue:

```java
public class SuperGreenEngineAdapter extends AbstractEngine {
    public SuperGreenEngineAdapter(SuperGreenEngine greenEngine) {
        super(greenEngine.getEngineSize(), false);
    }
}
```

Observe lo siguiente del código de Java anterior:

* Extendemos la clase a la que nos estamos adaptando
* Aceptamos una referencia en el constructor a la clase desde la que nos estamos adaptando
* El constructor obtiene el estado necesario del objeto referenciado y lo pasa al constructor de la superclase.

Ahora estamos en condiciones de incluir los objetos SuperGreenEngine en nuestra colección de informes (el código adicional indicado en negrita):

```java
List<Engine> engines = new ArrayList<Engine>();
engines.add(new StandardEngine(1300));
engines.add(new StandardEngine(1600));
engines.add(new TurboEngine(2000));
engines.add(new StandardEngine(1600));
engines.add(new TurboEngine(2000));
```

**`// "Adaptamos" el nuevo tipo de motor...`**  
**`SuperGreenEngine greenEngine = new SuperGreenEngine(1200);`**  
**`engines.add(new SuperGreenEngineAdapter(greenEngine));`**  
**`// Sin cambios desde antes...`**  

```java
for (Engine engine : engines) {
    System.out.println(engine);
}
```

La salida ahora debería ser:

```text
StandardEngine (1300)
StandardEngine (1600)
TurboEngine (2000)
SuperGreenEngine (1200)
```

Observe cómo la salida utilizó el método toString() heredado de AbstractEngine en lugar del de SuperGreenEngine.

## Variaciones para implementar adaptadores{#h2-6}

Fuimos algo afortunados porque el diseño de las clases Engine y SuperGreenEngine facilitó que la clase adaptador hiciera el trabajo dentro de su constructor. Sin embargo, a menudo necesitamos realizar algunos pasos adicionales dentro del código de la clase de adaptador, por lo que aquí hay una fórmula general para aplicar:

_1. Extienda la clase que está adaptando (o impleméntela, si es una interfaz)_  
_2. Especifique la clase desde la que está adaptando en el constructor y almacene una referencia a ella en una variable de instancia_  
_3. Por cada método en la clase que está extendiendo (o la interfaz que está implementando), sobreescríbalo para delegar al método correspondiente de la clase desde la que está adaptando_  

Aquí hay un ejemplo genérico de la clase adaptadora:

```java
public class ObjectAdapter extends ClassAdaptingTo {
    private ClassAdaptingFrom fromObject;

    public ObjectAdapter(ClassAdaptingFrom fromObject) {
        this.fromObject = fromObject;
    }

    // Método sobreescrito
    public void methodInToClass() {
        fromObject.methodInFromClass();
    }
}
```
