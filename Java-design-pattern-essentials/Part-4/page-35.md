# 24. Visitante (Visitor)

Tipo: conductual

Objetivo: Representa un método a realizar sobre los elementos de una estructura de objeto. Visitor te permite definir un nuevo método sin cambiar las clases de los elementos sobre los que opera.

A veces una jerarquía de clases y su código se vuelven sustanciales y, sin embargo, se sabe que los requisitos futuros serán inevitables. Un ejemplo para la Compañía de Motores Foobar es la jerarquía de motores que se ve así:

![Jerarquía de la clase Engine](../images/000049.jpg)

Figure 24.1 : Jerarquía de la clase Engine

En realidad, es probable que el código dentro de la clase AbstractEngine esté compuesto por una multitud de componentes individuales, como un árbol de levas, un pistón, algunas bujías, etc. Si necesitamos agregar alguna funcionalidad que atraviese estos componentes, entonces la forma natural es simplemente agregar un método a AbstractEngine. Pero, ¿tal vez sabemos que existen potencialmente muchos requisitos nuevos y preferiríamos no tener que seguir agregando métodos directamente a la jerarquía?

El patrón Visitante nos permite definir solo un método adicional para agregar a la jerarquía de clases de tal manera que se puedan agregar muchos tipos diferentes de nuevas funciones sin realizar más cambios. Esto se logra mediante una técnica conocida como "doble envío", mediante la cual el método invocado emite una devolución de llamada al objeto que lo invoca.

La técnica requiere primero la definición de una interfaz que llamaremos EngineVisitor:

```java
public interface EngineVisitor {
    public void visit(Camshaft camshaft);
    public void visit(Engine engine);
    public void visit(Piston piston);
    public void visit(SparkPlug sparkPlug);
}
```

También definiremos una interfaz llamada Visitable con un método acceptEngineVisitor():

```java
public interface Visitable {
    public void acceptEngineVisitor(EngineVisitor visitor);
}
```

La interfaz Engine la has conocido en capítulos anteriores (aunque la modificaremos ligeramente para este capítulo). Las clases Camshaft, Piston y SparkPlug son muy simples, como se muestra a continuación:

```java
public class Camshaft implements Visitable {
    public void acceptEngineVisitor(EngineVisitor visitor) {
        visitor.visit(this);
    }
}


public class Piston implements Visitable {
    public void acceptEngineVisitor(EngineVisitor visitor) {
        visitor.visit(this);
    }
}


public class SparkPlug implements Visitable {
    public void acceptEngineVisitor(EngineVisitor visitor) {
        visitor.visit(this);
    }
}
```

Como puede ver, cada una de estas clases define un método llamado acceptEngineVisitor() que toma una referencia a un objeto EngineVisitor como argumento. Todo lo que hace el método es invocar el método visit() del EngineVisitor pasado, devolviendo la instancia del objeto.

Nuestra interfaz Engine modificada ahora también define el método acceptEngineVisitor():

```java
public interface Engine extends Visitable {
    public int getSize();
    public boolean isTurbo();
}
```

Por lo tanto, la clase AbstractEngine necesita implementar este nuevo método, que en este caso recorre los componentes individuales (camshaft, piston, spark plugs) invocando acceptEngineVisitor() en cada uno:

```java
public abstract class AbstractEngine implements Engine {
    private int size;
    private boolean turbo;
 
    private Camshaft camshaft;
    private Piston piston;
    private SparkPlug[] sparkPlugs;
 
    public AbstractEngine(int size, boolean turbo) {
        this.size = size;
        this.turbo = turbo;
 
        // Crea un árbol de levas, un pistón y 4 bujías...
        camshaft = new Camshaft();
        piston = new Piston();
        sparkPlugs = new SparkPlug[]{new SparkPlug(), new SparkPlug(), new SparkPlug(), new SparkPlug()};
    }
 
    public int getSize() {
        return size;
    }
 
    public boolean isTurbo() {
        return turbo;
    }
 
    public void acceptEngineVisitor(EngineVisitor visitor) {
        // Visite cada componente primero...
        camshaft.acceptEngineVisitor(visitor);
        piston.acceptEngineVisitor(visitor);
        for (SparkPlug eachSparkPlug : sparkPlugs) {
            eachSparkPlug.acceptEngineVisitor(visitor);
        }
 
        // Ahora visita al receptor...
        visitor.visit(this);
    }
 
    public String toString() {
        return getClass().getSimpleName() + " (" + size + ")");
    }
}
```

Ahora crearemos una implementación real de EngineVisitor para que pueda ver cómo podemos agregar fácilmente funcionalidades adicionales a los motores sin realizar más cambios en ninguna clase de jerarquía del motor. Lo primero que haremos es definir algún artilugio electrónico inteligente que se pueda conectar a un motor y que comprobará automáticamente cada componente y diagnosticará cualquier fallo. Por tanto definimos la clase EngineDiagnostics:

```java
public class EngineDiagnostics implements EngineVisitor {
    public void visit(Camshaft camshaft) {
        System.out.println("Diagnóstico del árbol de levas");
    }
 
    public void visit(Engine engine) {
        System.out.println("Diagnóstico del motor de la unidad.");
    }
 
    public void visit(Piston piston) {
        System.out.println("Diagnóstico del pistón");
    }
    
    public void visit(SparkPlug sparkPlug) {
        System.out.println("Diagnóstico de una sola bujía");
    }
}
```

También queremos imprimir un inventario de cuántos componentes de cada tipo hay dentro de un motor, por lo que también tenemos una clase EngineInventory:

```java
public class EngineInventory implements EngineVisitor {
    private int camshaftCount;
    private int pistonCount;
    private int sparkPlugCount;
 
    public EngineInventory() {
        camshaftCount = 0;
        pistonCount = 0;
        sparkPlugCount = 0;
    }
 
    public void visit(Camshaft p) {
        camshaftCount++;
    }
 
    public void visit(Engine e) {
        System.out.println("El motor tiene: " + camshaftCount + " árbol(s) de levas, " + pistonCount + " pistón(s) y " + sparkPlugCount + " bujía(s)");
    }
 
    public void visit(Piston p) {
        pistonCount++;
    }
 
    public void visit(SparkPlug sp) {
        sparkPlugCount++;
    }
}
```

El siguiente diagrama resume cómo interactúan todas estas clases:

![Patrón Visitante](../images/000015.jpg)

Figura 24.2 : Patrón Visitante

Los programas cliente ahora solo necesitan invocar el método acceptEngineVisitor() en una instancia de Engine, pasando el objeto EngineVisitor apropiado:

```java
// Crea un motor...
Engine engine = new StandardEngine(1300);

// Ejecuta diagnóstico en el motor...
engine.acceptEngineVisitor(new EngineDiagnostics());
```

Lo anterior dará como resultado la siguiente salida:

```text
Diagnóstico del árbol de levas
Diagnóstico del pistón
Diagnóstico de una sola bujía
Diagnóstico de una sola bujía
Diagnóstico de una sola bujía
Diagnóstico de una sola bujía
Diagnóstico del motor de la unidad.
```

Y para obtener el inventario (usando la misma instancia de Engine):

```java
// Realiza inventario en el motor...
engine.acceptEngineVisitor(new EngineInventory());
```

El resultado debería mostrar:

```text
TEl motor tiene: 1 árbol(es) de levas, 1 pistón(es) y 4 bujías.
```
