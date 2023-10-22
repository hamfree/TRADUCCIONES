# 23. Método de plantilla (Template Method)

Tipo: conductual

Objetivo: Define el esqueleto de un algoritmo en un método, difiriendo algunos pasos a subclases. El método de plantilla permite a las subclases redefinir ciertos pasos de un algoritmo sin cambiar la estructura del algoritmo.

Cada vehículo fabricado por la Compañía de Motores Foobar necesita que se produzca una pequeña cantidad de folletos impresos y se los entregue al comprador, como un manual del propietario y un folleto del historial de servicio. La forma en que se producen los folletos siempre sigue el mismo conjunto de pasos, pero es posible que cada tipo diferente de folleto deba realizar cada uno de los pasos individuales de una manera ligeramente diferente.

El patrón Método de plantilla permite la definición de uno o más métodos abstractos que se llaman a través de un 'método de plantilla'. La jerarquía simple es la siguiente:

![Patrón Método de Plantilla](../images/000001.jpg)

Figura 23.1 : Patrón Método de Plantilla

La clase AbstractBookletPrinter define varios métodos abstractos protegidos y un 'método de plantilla' final público que hace uso de los métodos abstractos (el método se vuelve final para evitar que se pueda sobreescribir):

```java
public abstract class AbstractBookletPrinter {
    protected abstract int getPageCount();
    protected abstract void printFrontCover();
    protected abstract void printTableOfContents();
    protected abstract void printPage(int pageNumber);
    protected abstract void printIndex();
    protected abstract void printBackCover();
    
    // Este es el 'método plantilla'
    public final void print() {
        printFrontCover();
        printTableOfContents();
        for (int i = 1; i <= getPageCount(); i++) {
            printPage(i);
        }
        printIndex();
        printBackCover();
    }
}
```

Cada subclase concreta ahora solo necesita proporcionar el código de implementación para cada método abstracto, por ejemplo, la siguiente clase SaloonBooklet:

```java
public class SaloonBooklet extends AbstractBookletPrinter {
    protected int getPageCount() {
        return 100;
    }
 
    protected void printFrontCover() {
        System.out.println("Impresión de portada para folleto de berlina");
    }
 
    protected void printTableOfContents() {
        System.out.println("Impresión del índice del folleto de la berlina");
    }
 
    protected void printPage(int pageNumber) {
        System.out.println("Página de impresión" + pageNumber + "para el libro de berlina");
    }
 
    protected void printIndex() {
        System.out.println("Impresión del Índice del folleto de berlinas");
    }
 
    protected void printBackCover() {
        System.out.println("Impresión de contraportada para folleto de berlina");
    }
}
```

El ServiceHistoryBooklet es muy similar:

```java
public class ServiceHistoryBooklet extends AbstractBookletPrinter {
    protected int getPageCount() {
        return 12;
    }
 
    protected void printFrontCover() {
        System.out.println("Impresión de portada para folleto de historial de servicio");
    }
 
    protected void printTableOfContents() {
        System.out.println("Impresión del índice del folleto del historial de servicio");
    }
 
    protected void printPage(int pageNumber) {
        System.out.println("Impresión de la página " + pageNumber + " para el folleto del historial de servicio");
    }
 
    protected void printIndex() {
        System.out.println("Impresión del Índice para el folleto de historial de servicio.");
    }
 
    protected void printBackCover() {
        System.out.println("Impresión de contraportada para folleto de historial de servicio");
    }
}
```

Si bien no es esencial desde el punto de vista del patrón que los métodos abstractos estén protegidos, a menudo ocurre que este es el nivel de acceso más apropiado para asignar, ya que solo están pensados para sobreescribirlos y no para invocarlos directamente. por objetos de cliente.

También tenga en cuenta que es perfectamente aceptable que algunos de los métodos llamados desde el 'método de plantilla' no sean abstractos sino que tengan una implementación predeterminada proporcionada. Pero cuando se llama al menos a un método abstracto, se califica como patrón de método de plantilla.

Los programas cliente simplemente necesitan crear una instancia de la clase concreta requerida e invocar el método print():

```java
System.out.println("A punto de imprimir un folleto para turismos");
AbstractBookletPrinter saloonBooklet = new SaloonBooklet();
saloonBooklet.print();

System.out.println("A punto de imprimir un folleto de historial de servicio");
AbstractBookletPrinter serviceBooklet = new ServiceHistoryBooklet();
serviceBooklet.print();
```
