# 14. Cadena de Responsabilidad (Chain of Responsability){#h2-17}

Type: Behavioural

Purpose: Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request. Chain the receiving objects and pass the request along the chain until an object handles it.

The Foobar Motor Company receives many emails each day, including servicing requests, sales enquiries, complaints, and of course the inevitable spam. Rather than employ someone specifically to sort through each email to determine which department it should be forwarded to, our task is to try and automate this by analysing the text in each email and making a "best guess".

In our simplified example, we will search the text of the email for a number of keywords and depending upon what we find will process accordingly. Here are the words we will search for and how they should be handled:

* If the email contains **"viagra"**, **"pills"** or **"medicines"** then it should be forwarded to a spam handler;
* If the email contains **"buy"**, or **"purchase"** then it should be forwarded to the sales department;
* If the email contains **"service"**, or **"repair"** then it should be forwarded to the servicing department;
* If the email contains **"complain"**, or **"bad"** then it should be forwarded to the manager;
* If the email contains none of the above words then it should be forwarded to the general enquiries department.

Note that only one object needs to handle the request, so if a particular email contains both "purchase" and "repair" it will be forwarded to the sales department only. The sequence in which to check the keywords is whatever seems most sensible for the application; so here we are trying to filter out spam before it reaches any other department.

Now it would be possible, of course, to just have a series of if...else... statements when checking for the keywords, but that would not be very object-oriented. The Chain of Responsibility pattern instead allows us to define separate 'handler' objects that all conform to an EmailHandler interface. This enables us to keep each handler independent and loosely-coupled.

The following diagram shows the pattern:

(IMAGEN)

Figure 14.1 : Chain of Responsibility pattern

EmailHandler is the interface at the top of the hierarchy:

```java
public interface EmailHandler {
    public void setNextHandler(EmailHandler handler);
    public void processHandler(String email);
}
```

The setNextHandler() method takes another EmailHandler object as its argument which represents the handler to call if the current object is unable to handle the email.

The processHandler() method takes the email text as its argument and determines if it is able to handle it (i.e. if it contains one of the keywords we are interested in). If the active object can handle the email it does so, otherwise it just forwards to the next in the chain.

The AbstractEmailHandler class implements the EmailHandler interface to provide useful default functionality:

```java
public abstract class AbstractEmailHandler implements EmailHandler {
    private EmailHandler nextHandler;

    public void setNextHandler(EmailHandler handler) {
    nextHandler = handler;
    }

    public void processHandler(String email) {
        boolean wordFound = false;

        // If no words to match against then this object can handle
        if (matchingWords().length == 0) {
            wordFound = true;
        } else {
            // Look for any of the matching words
            for (String word : matchingWords()) {
                if (email.indexOf(word) >= 0) {
                    wordFound = true;
                    break;
                }
            }
        }

        // Can we handle email in this object?
        if (wordFound) {
            handleHere(email);
        } else {
            // Unable to handle here so forward to next in chain
            nextHandler.processHandler(email);
        }
    }

    protected abstract String[] matchingWords();
    protected abstract void handleHere(String email);
}
```

The method setNextHandler() simply stores the argument in an instance variable; the decision making process is made in processHandler(). This has been written to utilise two protected helper methods that must be implemented by concrete subclasses:

matchingWords() will return an array of String objects that this handler is interested in;
handleHere() is only called if this object can actually handle the email and contains whatever code is required.

The concrete subclasses are straightforward:

```java
public class SpamEmailHandler extends AbstractEmailHandler {
    protected String[] matchingWords() {
        return new String[]{"viagra", "pills", "medicines"};
    }

    protected void handleHere(String email) {
        System.out.println("This is a spam email.");
    }
}

 

public class SalesEmailHandler extends AbstractEmailHandler {
    protected String[] matchingWords() {
        return new String[]{"buy", "purchase"};
    }

    protected void handleHere(String email) {
        System.out.println("Email handled by sales department.");
    }
}

 

public class ServiceEmailHandler extends AbstractEmailHandler {
    protected String[] matchingWords() {
        return new String[]{"service", "repair"};
    }

    protected void handleHere(String email) {
        System.out.println("Email handled by service department.");
    }
}

 

public class ManagerEmailHandler extends AbstractEmailHandler {
    protected String[] matchingWords() {
        return new String[]{"complain", "bad"};
    }

    protected void handleHere(String email) {
        System.out.println("Email handled by manager.");
    }
}

 

public class GeneralEnquiriesEmailHandler extends AbstractEmailHandler {
    protected String[] matchingWords() {
        return new String[0]; // match anything
    }

    protected void handleHere(String email) {
        System.out.println("Email handled by general enquiries.");
    }
}
```

We now need to define the sequence in which the handlers are called. For this example, the following static method has been added to AbstractEmailHandler:

```java
public static void handle(String email) {
    // Create the handler objects...
    EmailHandler spam = new SpamEmailHandler();

    mailHandler sales = new SalesEmailHandler();
    EmailHandler service = new ServiceEmailHandler();
    EmailHandler manager = new ManagerEmailHandler();
    EmailHandler general = new GeneralEnquiriesEmailHandler();

    // Chain them together...
    spam.setNextHandler(sales);
    sales.setNextHandler(service);
    service.setNextHandler(manager);
    manager.setNextHandler(general);

    // Start the ball rolling...
    spam.processHandler(email);
}
```

Putting a message through the handlers is now as simple as this:

```java
String email = "I need my car repaired.";
AbstractEmailHandler.handle(email);
```

This should produce the following output:

```text
Email handled by service department.
```

---

## 15. Comando (Command){#h2-18}

Type: Behavioural

Purpose: Encapsulate a request as an object, thereby letting you parameterise clients with different requests, queue or log requests, and support undoable operations.

The vehicles made by the Foobar Motor Company each have an installed radio; this is modelled by the following Radio class:

```java
public class Radio {
    public static final int MIN_VOLUME = 0;
    public static final int MAX_VOLUME = 10;
    public static final int DEFAULT_VOLUME = 5;

    private boolean on;
    private int volume;

    public Radio() {
        on = false;
        volume = DEFAULT_VOLUME;
    }

    public boolean isOn() {
        return on;
    }

    public int getVolume() {
        return volume;
    }

    public void on() {
        on = true;
        System.out.println("Radio now on, volume level " + getVolume());
    }

    public void off() {
        on = false;
        System.out.println("Radio now off");
    }

    public void volumeUp() {
        if (isOn()) {
            if (getVolume() < MAX_VOLUME) {
                volume++;
                System.out.println("Volume turned up to level " + getVolume());
            }
        }
    }

    public void volumeDown() {
        if (isOn()) {
            if (getVolume() > MIN_VOLUME) {
                volume--;
                System.out.println("Volume turned down to level " + getVolume());
            }
        }
    }
}
```

As you can see, the class enables the radio to be switched on and off, and provided it is switched on will enable the volume to be increased or decreased one level at a time, within the range 1 to 10.

Some of the vehicles also have electrically operated windows with simple up & down buttons, as modelled by the following ElectricWindow class.

```java
public class ElectricWindow {
    private boolean open;

    public ElectricWindow() {
        open = false;
        System.out.println("Window is closed");
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return (! open);
    }

    public void openWindow() {
        if (isClosed()) {
            open = true;
            System.out.println("Window is now open");
        }
    }

    public void closeWindow() {
        if (isOpen()) {
            open = false;
            System.out.println("Window is now closed");
        }
    }
}
```

Each of the devices (the radio and the electric window) has separate controls, typically buttons, to manage their state. But suppose the Foobar Motor Company now wishes to introduce speech recognition to their top-of-the-range vehicles and have them perform as follows:

* _If the speech-recognition system is in "radio" mode, then if it hears the word "up" or "down" it adjusts the radio volume; or_
* _If the speech-recognition system is in "window" mode, then if it hears the word "up" or "down" it closes or opens the driver's door window._

We therefore need the speech-recognition system to be able to handle either Radio objects or ElectricWindow objects, which are of course in completely separate hierarchies. We might also want it to handle other devices in the future, such as the vehicle's speed or the gearbox (e.g. upon hearing "up" it would increase the speed by 1mph or it would change to the next higher gear). For good object-oriented design we need to isolate the speech-recognition from the devices it controls, so it can cope with any device without directly knowing what they are.

The Command patterns allows us to uncouple an object making a request from the object that receives the request and performs the action, by means of a "middle-man" object known as a "command object".

In its simplest form, this requires us to create an interface (which we shall call Command) with one method:

```java
public interface Command {
    public void execute();
}
```

We now need to create implementing classes for each action that we wish to take. For example, to turn up the volume of the radio we can create a VolumeUpCommand class:

```java
public class VolumeUpCommand implements Command {
    private Radio radio;

    public VolumeUpCommand(Radio radio) {
        this.radio = radio;
    }

    public void execute() {
        radio.volumeUp();
    }
}
```

The class simply takes a reference to a Radio object in its constructor and invokes its volumeUp() method whenever execute() is called.

We likewise need to create a VolumeDownCommand class for when the volume is to be reduced:

```java
public class VolumeDownCommand implements Command {
    private Radio radio;

    public VolumeDownCommand(Radio radio) {
        this.radio = radio;
    }

    public void execute() {
        radio.volumeDown();
    }
}
```

 Controlling an electric window's up and down movement is just as easy: this time we create classes implementing Command passing in a reference to an ElectricWindow object:

```java
public class WindowUpCommand implements Command {
    private ElectricWindow window;

    public WindowUpCommand(ElectricWindow window) {
        this.window = window;
    }

    public void execute() {
        window.closeWindow();
    }
}

 

public class WindowDownCommand implements Command {
    private ElectricWindow window;

    public WindowDownCommand(ElectricWindow window) {
        this.window = window;
    }

    public void execute() {
        window.openWindow();
    }
}
```

We will now define a SpeechRecogniser class that only knows about Command objects; it knows nothing about radios or electric windows.

```java
public class SpeechRecogniser {
    private Command upCommand, downCommand;

    public void setCommands(Command upCommand, Command downCommand) {
        this.upCommand = upCommand;
        this.downCommand = downCommand;
    }

    public void hearUpSpoken() {
        upCommand.execute();
    }

    public void hearDownSpoken() {
        downCommand.execute();
    }
}
```

We can view what we have created diagrammatically as follows:

(IMAGEN)

Figure 15.1 : Command pattern

Client programs can now create Radio and ElectricWindow instances, along with their respective Command instances. The command instances are then passed to the SpeechRecogniser object so it knows what to do.

We will first create a Radio and an ElectricWindow and their respective commands:

```java
// Create a radio and its up/down command objects
Radio radio = new Radio();
radio.on();
Command volumeUpCommand = new VolumeUpCommand(radio);
Command volumeDownCommand = new VolumeDownCommand(radio);

// Create an electric window and its up/down command objects
ElectricWindow window = new ElectricWindow();
Command windowUpCommand = new WindowUpCommand(window);
Command windowDownCommand = new WindowDownCommand(window);
```

Now create a single SpeechRecogniser object and set it to control the radio:

```java
// Create a speech recognition object
SpeechRecogniser speechRecogniser = new SpeechRecogniser();

// Control the radio
speechRecogniser.setCommands(volumeUpCommand, volumeDownCommand);
System.out.println("Speech recognition controlling the radio");
speechRecogniser.hearUpSpoken();
speechRecogniser.hearUpSpoken();
speechRecogniser.hearUpSpoken();
speechRecogniser.hearDownSpoken();
```

Now set the same SpeechRecogniser object to control the window instead:

```java
// Control the electric window
speechRecogniser.setCommands(windowUpCommand, windowDownCommand);
System.out.println("Speech recognition will now control the window");
speechRecogniser.hearDownSpoken();
speechRecogniser.hearUpSpoken();
```

If you run all the above statements you should see output similar to this:

```text
Radio now on, volume level 5
Window is closed
Speech recognition controlling the radio
Volume turned up to level 6
Volume turned up to level 7
Volume turned up to level 8
Volume turned down to level 7
Speech recognition will now control the window
Window is now open
Window is now closed
```

### Usos típicos del patrón de comando{#h2-19}

One of the most frequent uses of the Command pattern is in UI toolkits. These provide pre-built components like graphical buttons and menu items that cannot possibly know what needs to be done when clicked, because that is always specific to your application. In the Java libraries this is implemented through the Action interface's actionPerformed() method. Graphical applications often define both a menubar item and a toolbar icon that perform the same action (e.g. the File | Open menu item, and an 'Open' icon on a toolbar), where a single command object handles the action that is taken when either is selected.

Another common aspect of graphical applications is the provision of an "undo" mechanism. The Command pattern is used to accomplish this too; using the example in this chapter, we would add a method to the Command interface like this:

```java
public interface Command {
    public void execute();
    public void undo();
}
```

Implementing classes then provide the code for the additional method to reverse the last action, as in this example for the VolumeUpCommand class:

```java
public class VolumeUpCommand implements Command {
    private Radio radio;

    public VolumeUpCommand(Radio r) {
        radio = r;
    }

    public void execute() {
        radio.volumeUp();
    }

    public void undo() {
        radio.volumeDown();
    }
}
```

Most applications would be slightly more involved than the above example, in that you would need to store the state of the object prior to performing the code in the execute() method, enabling you to restore that state when undo() is called.

---

## 16. Intérprete (Interpreter){#h2-20}

Type: Behavioural

Purpose: Given a language, define a representation for its grammar along with an interpreter that uses the representation to interpret sentences in the language.

The satellite-navigation systems fitted to some of the Foobar Motor Company's vehicles have a special feature that enables the user to enter a number of cities and let it calculate the most northerly, southerly, westerly or easterly, depending on which command string is entered. A sample command might look like this:

```text
london edinburgh manchester southerly
```

The above would result in "London" being returned, being the most southerly of the three entered cities. You can even enter the command string like this:

```text
london edinburgh manchester southerly aberdeen westerly
```

This would first determine that London was the most southerly and then use that result (London) and compare it to Aberdeen to tell you which of those two is the most westerly. Any number of cities can be entered before each of the directional commands of "northerly", "southerly", "westerly" and "easterly".

You can think of the above command string consisting of the city names and directional keywords as forming a simple "language" that needs to be interpreted by the satellite-navigation software. The Interpreter pattern is an approach that helps to decipher these kinds of relatively simple languages.

Before looking at the pattern itself, we shall create a class named City which models the essential points of interest for our example, which is just the name of the city and its latitude and longitude:

```java
public class City {
    private String name;
    private double latitude, longitude;

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (! (otherObject instanceof City)) return false;
        City otherCity = (City) otherObject;
        return getName().equals(otherCity.getName());
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public String toString() {
        return getName();
    }
}
```

You will notice that for simplicity the latitude and longitude are stored as doubles. Also note that for the latitude positive values represent North and negative values represent South. Similarly, a positive longitude represents East and negative values West. The example in this chapter only includes a small number of UK cities which are all Northern latitude and Western longitude, although any city should work should you wish to use your own.

The classes to interpret the language are structured as follows:

(IMAGEN)

Figure 16.1 : Interpreter pattern

The Interpreter pattern resembles the Composite pattern in that it comprises an interface (or abstract class) with two types of concrete subclass; one type that represents the individual elements and the other type that represents repeating elements. We create one subclass to handle each type of element in the language.

The Expression interface is very simple, merely declaring an interpret() method that returns a City object:

```java
public interface Expression {
    public City interpret();
}
```

The first concrete subclass we will look at is CityExpression, an instance of which will be created for each city name it recognises in the command string. All this class needs to do is store a reference to a City object and return it when interpret() is invoked:

```java
public class CityExpression implements Expression {
    private City city;

    public CityExpression(City city) {
        this.city = city;
    }

    public City interpret() {
        return city;
    }
}
```

The classes to handle each of the commands (e.g. "northerly") are slightly more involved:

```java
public class MostNortherlyExpression implements Expression {
    private List<Expression> expressions;

    public MostNortherlyExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public City interpret() {
        City resultingCity = new City("Nowhere", -999.9, -999.9);
        for (Expression currentExpression : expressions) {
            City currentCity = currentExpression.interpret();
            if (currentCity.getLatitude() > resultingCity.getLatitude()) {
                resultingCity = currentCity;
            }
        }
        return resultingCity;
    }
}
```

The list of Expression objects passed to the constructor will be of the CityExpression type. The interpret() method loops through each of these to determine the most northerly, by comparing their latitude values.

The MostSoutherlyExpression class is very similar, merely changing the comparison:

```java
public class MostSoutherlyExpression implements Expression {
    private List<Expression> expressions;

    public MostSoutherlyExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public City interpret() {
        City resultingCity = new City("Nowhere", 999.9, 999.9);
        for (Expression currentExpression : expressions) {
            City currentCity = currentExpression.interpret();
            if (currentCity.getLatitude() < resultingCity.getLatitude()) {
                resultingCity = currentCity;
            }
        }
        return resultingCity;
    }
}
```

Likewise the MostWesterlyExpression and MostEasterlyExpression classes compute and return the appropriate City:

```java
public class MostWesterlyExpression implements Expression {
    private List<Expression> expressions;

    public MostWesterlyExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public City interpret() {
        City resultingCity = new City("Nowhere", 999.9, 999.9);
        for (Expression currentExpression : expressions) {
            City currentCity = currentExpression.interpret();
            if (currentCity.getLongitude() < resultingCity.getLongitude()) {
                resultingCity = currentCity;
            }
        }
        return resultingCity;
    }
}

 

public class MostEasterlyExpression implements Expression {
    private List<Expression> expressions;

    public MostEasterlyExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public City interpret() {
        City resultingCity = new City("Nowhere", -999.9, -999.9);
        for (Expression currentExpression : expressions) {
            City currentCity = currentExpression.interpret();
            if (currentCity.getLongitude() < resultingCity.getLongitude()) {
                resultingCity = currentCity;
            }
        }
        return resultingCity;
    }
}
```

While the Interpreter pattern does not in itself cover the parsing of an expression, in practice we need to define a class to go through the command string (such as "london edinburgh manchester southerly") and create the appropriate Expression classes as we go along. These Expression classes are placed into a "syntax tree" which is normally implemented using a LIFO stack. We shall therefore define a DirectionalEvaluator class to do this parsing, and set-up a small sample of UK cities:

```java
public class DirectionalEvaluator {
    private Map<String, City> cities;

    public DirectionalEvaluator() {
        cities = new HashMap<String, City>();

        cities.put("aberdeen", new City("Aberdeen", 57.15, -2.15));
        cities.put("belfast", new City("Belfast", 54.62, -5.93));
        cities.put("birmingham", new City("Birmingham", 52.42, -1.92));
        cities.put("dublin", new City("Dublin", 53.33, -6.25));
        cities.put("edinburgh", new City("Edinburgh", 55.92, -3.02));
        cities.put("glasgow", new City("Glasgow", 55.83, -4.25));
        cities.put("london", new City("London", 51.53, -0.08));
        cities.put("liverpool", new City("Liverpool", 53.42, -3.0));
        cities.put("manchester", new City("Manchester", 53.5, -2.25));
        cities.put("southampton", new City("Southampton", 50.9, -1.38));
    }

    public City evaluate(String route) {
        // Define the syntax tree
        Stack<Expression> expressionStack = new Stack<Expression>();

        // Parse each token in route string
        for (String token : route.split(" ")) {
            // Is token a recognised city?
            if (cities.containsKey(token)) {
                City city = cities.get(token);
                expressionStack.push(new CityExpression(city));

            // Is token to find most northerly?
            } else if (token.equals("northerly")) {
                expressionStack.push(new MostNortherlyExpression(loadExpressions(expressionStack)));

            // Is token to find most southerly?
            } else if (token.equals("southerly")) {
                expressionStack.push(new MostSoutherlyExpression(loadExpressions(expressionStack)));

            // Is token to find most westerly?
            } else if (token.equals("westerly")) {
                expressionStack.push(new MostWesterlyExpression(loadExpressions(expressionStack)));

            // Is token to find most easterly?
            } else if (token.equals("easterly")) {
                expressionStack.push(new MostEasterlyExpression(loadExpressions(expressionStack)));
            }
        }

    // Resulting value
    return expressionStack.pop().interpret();
    }

    private List<Expression> loadExpressions(Stack<Expression> expressionStack) {
        List<Expression> expressions = new ArrayList<Expression>();
        while(! expressionStack.empty()) {
            expressions.add(expressionStack.pop());
        }
        return expressions;
    }
}
```

Within the evaluate() method, when the parser detects a directional command (such as "northerly") it removes the cities on the stack and passes them along with the command back to the stack.

Note that the use above of if...else... statements has been used simply so that the chapter concentrates on the Interpreter pattern. A better approach would be to use a separate pattern to handle each token such as that defined in Chain of Responsibility.

Now all that remains is for our client programs to utilise the DirectionalEvaluator passing the command to interpret:

```java
// Create the evaluator
DirectionalEvaluator evaluator = new DirectionalEvaluator();

// This should output "London"...
System.out.println(evaluator.evaluate("london edinburgh manchester southerly"));

// This should output "Aberdeen"...
System.out.println(evaluator.evaluate("london edinburgh manchester southerly aberdeen westerly"));
```

---

## 17. Iterador (Iterator){#h2-21}

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

### El bucle 'for-each'{#h2-22}

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

---

## 18. Mediador (Mediator){#h2-23}

Type: Behavioural

Purpose: Define an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and it lets you vary their interaction independently.

The Foobar Motor Company is looking to the future when vehicles can drive themselves. This, of course, would entail the various components (ignition, gearbox, accelerator and brakes, etc.) being controlled together and interacting in various ways. For example:

* _Until the ignition is switched on, the gearbox, accelerator and brakes do not operate (we will assume the parking brake is in effect);_
* _When accelerating, the brakes should be disabled;_
* _When braking the accelerator should be disabled;_
* _The appropriate gear should be engaged dependent upon the speed of the vehicle._

And all this should happen automatically so the driver can just enjoy the view! (we will assume the vehicle can sense it's position so as to avoid crashes, etc.).

We will naturally create Java classes to model the individual components, so there will be an Ignition class, a Gearbox class, an Accelerator class and a Brake class. But we can also see that there are some complex interactions between them, and yet one of our core object-oriented design principles is to keep classes loosely-coupled.

The Mediator pattern helps to solve this through the definition of a separate class (the mediator) that knows about the individual component classes and takes responsibility for managing their interaction. The component classes also each know about the mediator class, but this is the only coupling they have. For our example, we will call the mediator class EngineManagementSystem.

We can see the connections diagrammatically below:

(IMAGEN)

Figure 18.1 : Mediator pattern


The two-way communication is achieved via each of the component classes' constructors, in that they each accept a reference to the mediator object (so they can send messages to it) and register themselves with the mediator (so they can receive messages from it). But each component class has no knowledge of any other component class; they only know about the mediator.

We can see this by looking at the Ignition class:

```java
public class Ignition {
    private EngineManagementSystem mediator;
    private boolean on;

    // Constructor accepts mediator as an argument
    public Ignition(EngineManagementSystem mediator) {
        this.mediator = mediator;
        on = false;
        // Register back with the mediator...
        mediator.registerIgnition(this);
    }

    public void start() {
        on = true;
        mediator.ignitionTurnedOn();
        System.out.println("Ignition turned on");
    }

    public void stop() {
        on = false;
        mediator.ignitionTurnedOff();
        System.out.println("Ignition turned off");
    }

    public boolean isOn() {
        return on;
    }
}
```
 

Note how the constructor establishes the two-way communication, and then how methods that perform events notify the mediator of those events.

The Gearbox class applies the same principles:

```java
public class Gearbox {
    public enum Gear {NEUTRAL, FIRST, SECOND, THIRD, FOURTH, FIFTH, REVERSE};

    private EngineManagementSystem mediator;
    private boolean enabled;
    private Gear currentGear;

    public Gearbox(EngineManagementSystem mediator) {
        this.mediator = mediator;
        enabled = false;
        currentGear = Gear.NEUTRAL;
        mediator.registerGearbox(this);
    }

    public void enable() {
        enabled = true;
        mediator.gearboxEnabled();
        System.out.println("Gearbox enabled");
    }

    public void disable() {
        enabled = false;
        mediator.gearboxDisabled();
        System.out.println("Gearbox disabled");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setGear(Gear g) {
        if ((isEnabled()) && (getGear() != g)) {
            currentGear = g;
            mediator.gearChanged();
            System.out.println("Now in " + getGear() + " gear");
        }
    }

    public Gear getGear() {
        return currentGear;
    }
}
```

The Accelerator and Brake classes follow a similar process:

```java
public class Accelerator {
    private EngineManagementSystem mediator;
    private boolean enabled;
    private int speed;

    public Accelerator(EngineManagementSystem mediator) {
        this.mediator = mediator;
        enabled = false;
        speed = 0;
        mediator.registerAccelerator(this);
    }

    public void enable() {
        enabled = true;
        mediator.acceleratorEnabled();
        System.out.println("Accelerator enabled");
    }

    public void disable() {
        enabled = false;
        mediator.acceleratorDisabled();
        System.out.println("Accelerator disabled");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void accelerateToSpeed(int speed) {
        if (isEnabled()) {
            this.speed = speed;
            mediator.acceleratorPressed();
            System.out.println("Speed now " + getSpeed());
        }
    }

    public int getSpeed() {
        return speed;
    }
}

public class Brake {
    private EngineManagementSystem mediator;
    private boolean enabled;
    private boolean applied;

    public Brake(EngineManagementSystem mediator) {
        this.mediator = mediator;
        enabled = false;
        applied = false;
        mediator.registerBrake(this);
    }

    public void enable() {
        enabled = true;
        mediator.brakeEnabled();
        System.out.println("Brakes enabled");
    }

    public void disable() {
        enabled = false;
        mediator.brakeDisabled();
        System.out.println("Brakes disabled");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void apply() {
        if (isEnabled()) {
            applied = true;
            mediator.brakePressed();
            System.out.println("Now braking");
        }
    }

    public void release() {
        if (isEnabled()) {
            applied = false;
        }
    }
}
```

So we now need the EngineManagementSystem class to serve as the mediator. This will hold a reference to each of the component classes with methods enabling their registration with the mediator. It also has methods to handle the interaction between the various components when particular events occur:

```java
public class EngineManagementSystem {
    private Ignition ignition;
    private Gearbox gearbox;
    private Accelerator accelerator;
    private Brake brake;

    private int currentSpeed;

    public EngineManagementSystem() {
        currentSpeed = 0;
    }


    // Methods that enable registration with this mediator...
    public void registerIgnition(Ignition ignition) {
        this.ignition = ignition;
    }

    public void registerGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }

    public void registerAccelerator(Accelerator accelerator) {
        this.accelerator = accelerator;
    }

    public void registerBrake(Brake brake) {
        this.brake = brake;
    }

    // Methods that handle object interactions...
    public void ignitionTurnedOn() {
        gearbox.enable();
        accelerator.enable();
        brake.enable();
    }

    public void ignitionTurnedOff() {
        gearbox.disable();
        accelerator.disable();
        brake.disable();
    }

    public void gearboxEnabled() {
        System.out.println("EMS now controlling the gearbox");
    }

    public void gearboxDisabled() {
        System.out.println("EMS no longer controlling the gearbox");
    }

    public void gearChanged() {
        System.out.println("EMS disengaging revs while gear changing");
    }

    public void acceleratorEnabled() {
        System.out.println("EMS now controlling the accelerator");
    }

    public void acceleratorDisabled() {
        System.out.println("EMS no longer controlling the accelerator");
    }
    public void acceleratorPressed() {
        brake.disable();
        while (currentSpeed < accelerator.getSpeed()) {
            currentSpeed ++;
            System.out.println("Speed currently " + currentSpeed);

            // Set gear according to speed...
            if (currentSpeed <= 10) {
                gearbox.setGear(Gearbox.Gear.FIRST);
            } else if (currentSpeed <= 20) {
                gearbox.setGear(Gearbox.Gear.SECOND);
            } else if (currentSpeed <= 30) {
                gearbox.setGear(Gearbox.Gear.THIRD);
            } else if (currentSpeed <= 50) {
                gearbox.setGear(Gearbox.Gear.FOURTH);
            } else {
                gearbox.setGear(Gearbox.Gear.FIFTH);
            }
        }
        brake.enable();
    }

    public void brakeEnabled() {
        System.out.println("EMS now controlling the brakes");
    }

    public void brakeDisabled() {
        System.out.println("EMS no longer controlling the brakes");
    }

    public void brakePressed() {
        accelerator.disable();
        currentSpeed = 0;
    }

    public void brakeReleased() {
        gearbox.setGear(Gearbox.Gear.FIRST);
        accelerator.enable();
    }
}
```

### Usos comunes{#h2-24}

A common use of the Mediator pattern is to manage the interaction of graphical components on a dialog. This frequently involves controlling when buttons, text fields, etc. should be enabled or disabled, or for passing data between components.

Note that it may be possible to reduce the coupling further by using the Observer pattern in place of Mediator. This would mean that the component classes (i.e. Ignition, etc.) would not need to hold a reference to a mediator but would instead fire events. The EngineManagementSystem class would then be an observer of the component classes and would still be able to invoke messages on them.

---

## 19. Recuerdo (Memento){#h2-25}


### Enfoque 1: utilizar la visibilidad privada del paquete{#h2-26}


### Enfoque 2: serialización de objetos{#h2-27}

---

## 20. Observador (Observer){#h2-28}


### Un enfoque alternativo utilizando eventos y oyentes{#h2-29}

---

## 21. Estado (State){#h2-30}

---

## 22. Estrategia (Strategy){#h2-31}

---

## 23. Método de plantilla (Template Method){#h2-32}

---

## 24. Visitante (Visitor){#h2-33}
