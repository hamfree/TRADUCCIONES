# 25. Objeto nulo

Como cualquier programador de Java pronto descubre, las pruebas de software a menudo lanzan mensajes de NullPointerException. Algunas veces, la única forma de evitar esto es comprobar específicamente por null antes de realizar una operación, lo que supone una responsabilidad adicional para el programador.

Suponga que el panel de instrumentos de un vehículo contiene tres huecos para las luces de advertencia (tales como el bajo nivel de aceite o bajo nivel del líquido de frenos). Un vehículo en particular podría usar solo estas dos luces, con el tercer hueco vacío, representando por null en Java. Iterar a través de los huecos requiere una prueba específica para prevenir que se lanze una NullPointerException:

```java
OilLevelLight &  BrakeFluidLight son tipos de WarningLight
WarningLight[] lights = new WarningLight[3];
lights[0] = new OilLevelLight();
lights[1] = new BrakeFluidLight();
lights[2] = null; //  hueco vacío

for (WarningLight currentLight : lights) {
    if (currentLight != null) {
        currentLight.turnOn();
        currentLight.turnOff();
        System.out.println(currentLight.isOn());
    }
}
```
Un enfoque que puede ayudar a prevenir la necesidad de realizar pruebas para null es crear una clase de 'objeto nulo' como parte de la jerarquía de clases. Esta clase implementará la misma interfaz pero no realizará ninguna función real, como se ilustra en la siguiente figura:

![Patrón de Objeto Nulo](../images/000029.jpg)

Figura 25.1 : Patrón de Objeto Nulo

La interfaz WarningLight define los métodos turnOn(), turnOff() y isOn():

```java
public interface WarningLight {
    public void turnOn();
    public void turnOff();
    public boolean isOn();
}
```

Las clases OilLightLevel y BrakeFluidLevel implementan cada una la interfaz WarningLight y proporcionan el código apropiado para encender y apagar la luz:

```java
public class OilLevelLight implements WarningLight {
    private boolean on;
 
    public void turnOn() {
        on = true;
    System.out.println("Oil level light ENCENDIDO");
    }
 
    public void turnOff() {
        on = false;
        System.out.println("Oil level light APAGADO");
    }
   
    public boolean isOn() {
        return on;
    }
}


public class BrakeFluidLight implements WarningLight {
    private boolean on;
 
    public void turnOn() {
        on = true;
        System.out.println("Brake fluid light ENCENDIDO");
    }
 
    public void turnOff() {
        on = false;
        System.out.println("Brake fluid light APAGADO");
    }
   
    public boolean isOn() {
        return on;
    }
}
```

Para el patrón de Objeto Nulo también creamos una clase NullObjectLight que implementa la interfaz WarningLight pero no realiza ninguna función:

```java
public class NullObjectLight implements WarningLight {
    public void turnOn() {
        // No hace nada...
    }
 
    public void turnOff() {
        // No hace nada...
    }
   
    public boolean isOn() {
        return false;
    }
}
```

Ahora nuestro código cliente puede simplificarse ya que no necesitamos comprobar si un hueco es nulo, siempre que hagamos uso del objeto nulo:

```java
WarningLight[] lights = new WarningLight[3];
lights[0] = new OilLevelLight();
lights[1] = new BrakeFluidLight();
lights[2] = new NullObjectLight(); // hueco vacío

// No necesitamos comprobar null...
for (WarningLight currentLight : lights) {
    currentLight.turnOn();
    currentLight.turnOff();
    System.out.println(currentLight.isOn());
}
```

Tenga en cuenta que para los métodos de obtención de objetos nulos deberá devolver lo que parezca sensato como valor predeterminado; por lo tanto, el método isOn() anterior devuelve falso ya que representa una luz sin extensión.
