# 27. Modelo Vista Controlador

El sistema de navegación por satélite de Foobar Motor Company incluye una visualización de la ubicación actual, la dirección de viaje y una indicación de la velocidad actual. También hay un dispositivo de entrada; controla dónde estableces el destino, etc. Una simulación completa está mucho más allá del alcance de este libro, por lo que usaremos una interfaz muy simplificada que simplemente te permite establecer la dirección de viaje (Norte, Sur, Oeste y Este) y la velocidad actual (hasta 30 mph), sin tener en cuenta las carreteras, etc.

La interfaz de usuario se verá así:

![Interfaz de usuario de navegación por satélite](../images/000027.jpg)

Figura 27.1 : Interfaz de usuario de navegación por satélite

Como puede ver en lo anterior, hay botones para cambiar de dirección y un control deslizante para ajustar la velocidad. La sección "Comentarios" en la parte inferior de la pantalla se ajusta automáticamente a sus selecciones. Tenga en cuenta que cada vez que hace clic en uno de los botones de dirección, ese botón se desactiva y el botón seleccionado anteriormente se vuelve a activar. El programa inicialmente comienza apuntando al Norte pero con una velocidad cero.

Este es un programa sencillo que sería completamente posible codificar dentro de una sola clase. Pero a medida que las aplicaciones gráficas se vuelven más complejas, se simplifica enormemente el desarrollo y el mantenimiento si se separan las partes principales del programa.

El patrón Modelo Vista Controlador (a menudo abreviado como MVC) es una forma de lograr un acoplamiento más flexible entre las partes constituyentes y es un enfoque probado para aplicaciones gráficas. Normalmente hay tres partes en juego en las aplicaciones GUI:

1. El "Modelo". Estos son los 'datos' (es decir, el estado) y la aplicación asociada o la lógica 'comercial'. En nuestro ejemplo, esto comprende los valores de la dirección de viaje actual y la velocidad actual junto con métodos para actualizarlos y devolverlos.

2. La "Vista". Esta es la pantalla gráfica, como se muestra en la Figura 27.1, actualizándose automáticamente según sea necesario cada vez que el Modelo cambia su estado de alguna manera.

3. El "Controlador". Esta es la parte que responde a todas las entradas del usuario (clics en botones, movimiento del control deslizante, etc.) y sirve de enlace tanto con el Modelo como con la Vista.

Cada una de las tres partes anteriores estará en una clase separada, que se puede visualizar de la siguiente manera:

![Patrón Modelo Vista Controlador](../images/000011.jpg)

Figura 27.2 : Patrón Modelo Vista Controlador

Estas clases se interrelacionan de la siguiente manera:

* _SatNavModel_ contiene métodos para establecer y obtener tanto la dirección como la velocidad. Es "observable" (ver Capítulo 20) y notificará a los observadores interesados siempre que la dirección o la velocidad hayan cambiado, pero no tiene conocimiento directo de ninguna otra clase;
* _SatNavView_ define el marco gráfico y la visualización de la interfaz de usuario. Contiene una referencia a SatNavModel para que pueda escuchar los cambios de estado en el modelo y consultar su estado según sea necesario para mantener la pantalla actualizada automáticamente;
* _SatNavController_ contiene una referencia tanto a SatNavModel como a SatNavView. Maneja los clics de los botones y el movimiento del control deslizante de velocidad, actualizando el Modelo y interactuando con la Vista según sea necesario.

Al igual que con los otros patrones descritos en este libro, existen variaciones en cómo se puede estructurar MVC, y lo anterior podría describirse como el enfoque "clásico". Los componentes Java (incluidos los componentes Swing) a menudo usan una versión modificada de MVC en la que la Vista y el Controlador se combinan en una sola clase, pero para los propósitos de este libro usaremos la separación completa de tres clases para presentar el patrón.

Empezaremos por el Modelo, que en nuestro caso es la clase SatNavModel. Esto se ha implementado para que pueda convertirse fácilmente en un JavaBean, aunque ese no es un requisito de MVC. El punto importante es que no tiene conocimiento directo de la Vista ni del Controlador y, por lo tanto, podría conectarse a todo tipo de otras aplicaciones sin que sea necesario realizar ningún cambio.

```java
public class SatNavModel implements Serializable {
    // Se utiliza para notificar a los oyentes para que sepan qué ha cambiado.
    public static final String DIRECTION_CHANGE = "direction";
    public static final String SPEED_CHANGE = "speed";
 
    // Las direcciones en las que podemos viajar
    public enum Direction {NORTH, SOUTH, EAST, WEST};
 
    // La dirección y velocidad actual.
    private Direction currentDirection;
    private int currentSpeed;
 
    // Esta clase es observable.
    private PropertyChangeSupport changeSupport;
 
 
    public SatNavModel() {
        currentDirection = Direction.NORTH;
        currentSpeed = 0;
        changeSupport = new PropertyChangeSupport(this);
    }
 
    public void setDirection(Direction newDirection) {
        if (newDirection != currentDirection) {
            Direction previousDirection = currentDirection;
            currentDirection = newDirection;
            changeSupport.firePropertyChange(DIRECTION_CHANGE, previousDirection, currentDirection);
         }
    }
 
    public Direction getDirection() {
        return currentDirection;
    }
 
    public void setSpeed(int newSpeed) {
        if (newSpeed != currentSpeed) {
            int previousSpeed = currentSpeed;
            currentSpeed = newSpeed;
            changeSupport.firePropertyChange(SPEED_CHANGE, previousSpeed, currentSpeed);
         }
    }
 
    public int getSpeed() {
        return currentSpeed;
    }
 
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        changeSupport.addPropertyChangeListener(pcl);
    }
 
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        changeSupport.removePropertyChangeListener(pcl);
    }
}
```

Como puede ver, el único vínculo con otras clases es a través de sus observadores (estamos usando la clase PropertyChangeSupport en el paquete java.beans para facilitar esto). Cada vez que se modifica la dirección o la velocidad se notifica a sus observadores (también conocidos como oyentes).

La visualización gráfica la realiza la clase SatNavView utilizando componentes estándar de Swing. Toma una referencia al SatNavModel en su constructor para registrarse como observador del modelo (por lo que necesita implementar la interfaz PropertyChangeListener). Siempre que detecta un cambio de modelo, se llama al método propertyChange(), lo que permite que la Vista actualice su visualización en consecuencia. También existen métodos para permitir que se observen los controles de la interfaz de usuario (por ejemplo, mediante el Controlador).

```java
public class SatNavView implements PropertyChangeListener {
    // La vista necesita una referencia al modelo.
    private SatNavModel model;
 
    // La vista usa un JFrame
    private JFrame viewingFrame;
 
    // controles de IU para cambiar la dirección y la velocidad
    private JButton northButton, southButton, westButton, eastButton;
    private JSlider speedSlider;
 
    // Comentarios de la interfaz de usuario para mostrar la dirección y velocidad actuales
    private String directionString, speedString;
    private JLabel feedbackLabel;
 
 
    public SatNavView(SatNavModel model) {
        this.model = model;
       
        // La vista escucha los cambios en el modelo.
        model.addPropertyChangeListener(this);
 
        // Inicializa la interfaz de usuario
        viewingFrame = new JFrame("Navegación por Satélite");
        viewingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        northButton = new JButton("Norte");
        disableNorthButton(); // Dirección predeterminada
 
        southButton = new JButton("Sur");
        westButton = new JButton("Oeste");
        eastButton = new JButton("Este");
 
        speedSlider = new JSlider(JSlider.VERTICAL, 0, 30, 0);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
 
        directionString = "estas apuntando " + model.getDirection();
        speedString = "pero aún no se mueve. Utilice botones y control deslizante.";
        feedbackLabel = new JLabel(directionString + ", " + speedString);
 
        // Diseño de los botones de dirección.
        JPanel directionPanel = new JPanel(new GridLayout(3, 3));
        directionPanel.setBorder(new TitledBorder("Dirección"));
        directionPanel.add(new JLabel(""));
        directionPanel.add(northButton);
        directionPanel.add(new JLabel(""));
        directionPanel.add(westButton);
        directionPanel.add(new JLabel(""));
        directionPanel.add(eastButton);
        directionPanel.add(new JLabel(""));
        directionPanel.add(southButton);
        directionPanel.add(new JLabel(""));
        // Diseño del control deslizante
        JPanel speedPanel = new JPanel();
        speedPanel.setBorder(new TitledBorder("Velocidad"));
        speedPanel.add(speedSlider);
 
        // Diseño de los comentarios
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setBorder(new TitledBorder("Comentarios"));
        feedbackPanel.add(feedbackLabel);
 
        // Colocación de los paneles en el marco
        JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.add(directionPanel, BorderLayout.CENTER);
        framePanel.add(speedPanel, BorderLayout.EAST);
        framePanel.add(feedbackPanel, BorderLayout.SOUTH);
 
        viewingFrame.add(framePanel);
    }
 
    public void show() {
        // Mostrar la vista dimensionada y centrada
        viewingFrame.pack();
        viewingFrame.setLocationRelativeTo(null);
        viewingFrame.setVisible(true);
    }
 
    // El controlador se registrará como oyente utilizando estos métodos
    public void addNorthButtonListener(ActionListener al) {
        northButton.addActionListener(al);
    }
 
    public void addSouthButtonListener(ActionListener al) {
        southButton.addActionListener(al);
    }
 
    public void addWestButtonListener(ActionListener al) {
        westButton.addActionListener(al);
    }
 
    public void addEastButtonListener(ActionListener al) {
        eastButton.addActionListener(al);
    }
 
    public void addSliderListener(ChangeListener cl) {
        speedSlider.addChangeListener(cl);
    }
 
    // El controlador llamará a estos métodos para habilitar los controles de la interfaz de usuario.
    public void enableNorthButton() {
        northButton.setEnabled(true);
    }
 
    public void disableNorthButton() {
        northButton.setEnabled(false);
    }
 
    public void enableSouthButton() {
        southButton.setEnabled(true);
    }
 
    public void disableSouthButton() {
        southButton.setEnabled(false);
     }
 
    public void enableWestButton() {
        westButton.setEnabled(true);
    }
 
    public void disableWestButton() {
        westButton.setEnabled(false);
    }
 
    public void enableEastButton() {
        eastButton.setEnabled(true);
    }
 
    public void disableEastButton() {
        eastButton.setEnabled(false);
    }
 
    // Llamado por el modelo cuando cambia su estado.
    public void propertyChange(PropertyChangeEvent pce) {
        if (model.getSpeed() == 0) {
            directionString = "está apuntando a " + model.getDirection();
            speedString = "pero actualmente no se están moviendo.";
 
        } else if (pce.getPropertyName().equals(SatNavModel.DIRECTION_CHANGE)) {
            SatNavModel.Direction newDirection = (SatNavModel.Direction) pce.getNewValue();
            directionString = "Ahora viajando " + newDirection;
            speedString = "viajando a " + model.getSpeed();
 
        } else if (pce.getPropertyName().equals(SatNavModel.SPEED_CHANGE)) {
            int oldSpeed = (Integer) pce.getOldValue();
            int newSpeed = (Integer) pce.getNewValue();
            if (oldSpeed < newSpeed) {
                speedString = "y has acelerado hasta" + model.getSpeed();
 
            } else {
                speedString = "y has disminuido la velocidad hasta " + model.getSpeed();
            }
        }
 
        feedbackLabel.setText(directionString + ", " + speedString);
    }
}
```

La clase SatNavController es responsable de manejar la entrada del usuario, que en este caso puede ser hacer clic en uno de los botones de dirección o mover el control deslizante de velocidad. En respuesta a la entrada del usuario, es necesario actualizar el estado del modelo y, por lo tanto, hay una referencia tanto a SatNavView como a SatNavModel en el constructor. La clase se configura para escuchar las entradas del usuario y reacciona en consecuencia:

```java
public class SatNavController {
    // Necesita una referencia tanto para el modelo como para la vista.
    private SatNavModel model;
    private SatNavView view;
 
    public SatNavController(SatNavModel model, SatNavView view) {
        this.model = model;
        this.view = view;
 
        // El controlador necesita escuchar la vista.
        view.addNorthButtonListener(new NorthButtonListener());
        view.addSouthButtonListener(new SouthButtonListener());
         view.addWestButtonListener(new WestButtonListener());
        view.addEastButtonListener(new EastButtonListener());
        view.addSliderListener(new SliderListener());
    }
 
 
    // Clases internas que sirven como oyentes de vistas.
    private class NorthButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            view.disableNorthButton();
            view.enableSouthButton();
            view.enableWestButton();
            view.enableEastButton();
            model.setDirection(SatNavModel.Direction.NORTH);
        }
    }
 
    private class SouthButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            view.enableNorthButton();
            view.disableSouthButton();
            view.enableWestButton();
            view.enableEastButton();
            model.setDirection(SatNavModel.Direction.SOUTH);
        }
    }
 
    private class WestButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            view.enableNorthButton();
            view.enableSouthButton();
            view.disableWestButton();
            view.enableEastButton();
            model.setDirection(SatNavModel.Direction.WEST);
        }
    }
 
    private class EastButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            view.enableNorthButton();
            view.enableSouthButton();
            view.enableWestButton();
            view.disableEastButton();
            model.setDirection(SatNavModel.Direction.EAST);
        }
    }
 
    private class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent event) {
            JSlider slider = (JSlider) event.getSource();
            model.setSpeed(slider.getValue());
        }
    }
}
```

Ejecutar la aplicación ahora es tan simple como crear una instancia de las clases anteriores e invocar el comando show() definido en la Vista:

```java
// Crear las clases MVC
SatNavModel model = new SatNavModel();
SatNavView view = new SatNavView(model);
SatNavController controller = new SatNavController(model, view);
// Mostrar la vista
view.show();
```
