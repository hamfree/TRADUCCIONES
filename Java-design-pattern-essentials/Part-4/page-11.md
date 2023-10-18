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

Type: Behavioural

Purpose: Without violating encapsulation, capture and externalise an object's internal state so that it can be restored to this state later.

The Foobar Motor Company's vehicles naturally have a speedometer mounted on the dashboard, which not only records the current speed but also the previous speed. There is now a requirement for the state to be stored externally at periodic intervals (so that it could, for example, be integrated into a tachograph for goods vehicles).

However, one of the instance variables in the Speedometer class does not have a getter method, but to adhere to encapsulation and data-hiding principles it is correctly declared to be private. We also want to adhere to the principle that a class should not have multiple responsibilities, so don't want to also have to build in a state save & restore mechanism into the class. So how can we capture the state of the object?

This chapter will present two different approaches, each having its advantages and disadvantages. In both cases, we make use of a separate class that performs the state saving and restoration, which we shall call SpeedometerMemento. This class takes a reference to the Speedometer object that needs to be externalised:

(IMAGEN)

Figure 19.1 : Memento pattern

### Enfoque 1: utilizar la visibilidad privada del paquete{#h2-26}

When the access modifier is omitted from class members it takes on 'package-private' visibility. This means it is only accessible to other classes in the same package, so is thus slightly more open than private visibility but not as much as protected (subclasses in different packages will be unable to access). Therefore we can place the Speedometer class into a package where we limit what other classes exist there, which in our case will just be SpeedometerMemento.

Here is the very simple Speedometer class:

```java
package mementosubpackage;

public class Speedometer {
    // Normal private visibility but has accessor method...
    private int currentSpeed;
 
    // package-private visibility and no accessor method...
    int previousSpeed;
    public Speedometer() {
        currentSpeed = 0;
        previousSpeed = 0;
    }
 
    public void setCurrentSpeed(int speed) {
        previousSpeed = currentSpeed;
        currentSpeed = speed;
    }
 
    public int getCurrentSpeed() {
        return currentSpeed;
    }
}
```

The SpeedometerMemento class exists in the same package. It saves the state of the passed in Speedometer object in the constructor and defines a method to restore that state:

```java
package mementosubpackage;

public class SpeedometerMemento {
    private Speedometer speedometer;
    private int copyOfCurrentSpeed;
    private int copyOfPreviousSpeed;
 
    public SpeedometerMemento(Speedometer speedometer) {
        this.speedometer = speedometer;
        copyOfCurrentSpeed = speedometer.getCurrentSpeed();
        copyOfPreviousSpeed = speedometer.previousSpeed;
    }
 
    public void restoreState() {
        speedometer.setCurrentSpeed(copyOfCurrentSpeed);
        speedometer.previousSpeed = copyOfPreviousSpeed;
    }
}
```

Note that the accessor method getCurrentSpeed() was used for the currentSpeed instance variable but the previousSpeed variable had to be accessed directly, which is possible because the memento exists in the same package.

We can test the memento with this code:

```java
Speedometer speedo = new Speedometer();

speedo.setCurrentSpeed(50);
speedo.setCurrentSpeed(100);
System.out.println("Current speed: " + speedo.getCurrentSpeed());
System.out.println("Previous speed: " + speedo.previousSpeed);

// Save the state of 'speedo'...
SpeedometerMemento memento = new SpeedometerMemento(speedo);

// Change the state of 'speedo'...
speedo.setCurrentSpeed(80);
System.out.println("After setting to 80...");
System.out.println("Current speed: " + speedo.getCurrentSpeed());
System.out.println("Previous speed: " + speedo.previousSpeed);

// Restore the state of 'speedo'...
System.out.println("Now restoring state...");
memento.restoreState();
System.out.println("Current speed: " + speedo.getCurrentSpeed());
System.out.println("Previous speed: " + speedo.previousSpeed);
```

Running the above results in the following output:

```text
Current speed: 100
Previous speed: 50

After setting to 80...
Current speed: 80
Previous speed: 100

Now restoring state...
Current speed: 100
Previous speed: 50
```

The main disadvantage of this approach is that you either have to put the pair of classes in their own special package or accept that other classes in the package they are in will have direct access to the instance variables.

### Enfoque 2: serialización de objetos{#h2-27}

This approach allows you to make all the instance variables private, thus regaining full encapsulation. The Speedometer class has been modified for this and now includes a getPreviousSpeed() method, though this is purely to help us test the memento; it's not required by this approach. The class has also been changed to implement the Serializable interface (changes marked in bold):

```java
public class Speedometer implements Serializable {
    private int currentSpeed;
    private int previousSpeed;
 
    public Speedometer() {
        currentSpeed = 0;
        previousSpeed = 0;
    }
 
    public void setCurrentSpeed(int speed) {
        previousSpeed = currentSpeed;
        currentSpeed = speed;
    }
 
    public int getCurrentSpeed() {
        return currentSpeed;
    }
 
    // Only defined to help testing...
    public int getPreviousSpeed() {
        return previousSpeed;
    }
}
```

The SpeedometerMemento class now uses object serialisation for the state saving and restoration:

```java
public class SpeedometerMemento {
    public SpeedometerMemento(Speedometer speedometer) throws IOException {
        // Serialize...
        File speedometerFile = new File("speedometer.ser");
        oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(speedometerFile)));
        oos.writeObject(speedometer);
        oos.close();
    }
 
    public Speedometer restoreState() throws IOException, ClassNotFoundException {
        // Deserialize...
        File speedometerFile = new File("speedometer.ser");
        ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(speedometerFile)));
        Speedometer speedo = (Speedometer) ois.readObject();
        ois.close();
        return speedo;
    }
}
```

We can check that this achieves the same as the first approach, the only difference being that the restoreState() method now returns the restored object reference:

```java
Speedometer speedo = new Speedometer();

speedo.setCurrentSpeed(50);
speedo.setCurrentSpeed(100);
System.out.println("Current speed: " + speedo.getCurrentSpeed());
System.out.println("Previous speed: " + speedo.previousSpeed);

// Save the state of 'speedo'...
SpeedometerMemento memento = new SpeedometerMemento(speedo);

// Change the state of 'speedo'...
speedo.setCurrentSpeed(80);
System.out.println("After setting to 80...");
System.out.println("Current speed: " + speedo.getCurrentSpeed());
System.out.println("Previous speed: " + speedo.previousSpeed);

// Restore the state of 'speedo'...
System.out.println("Now restoring state...");
speedo = memento.restoreState();
System.out.println("Current speed: " + speedo.getCurrentSpeed());
System.out.println("Previous speed: " + speedo.previousSpeed);
```

Running the above should result in the same output as shown for the first approach. The main disadvantage of this approach is that writing to and reading from a disk file is much slower. Note also that while we have been able to make all fields private again, it might still be possible for someone who gained access to the serialized file to use a hex editor to read or change the data.

---

## 20. Observador (Observer){#h2-28}

Type: Behavioural

Purpose: Define a one-to-many dependency between objects so that when one object changes its state, all its dependants are notified and updated automatically.

The Foobar Motor Company has decided that an alert should sound to the driver whenever a certain speed is exceeded. They also envisage that other things may need to happen depending upon the current speed (such as an automatic gearbox selecting the appropriate gear to match the speed). But they realise the need to keep objects loosely-coupled, so naturally don't wish the Speedometer class to have any direct knowledge of speed monitors or automatic gearboxes (or any other future class that might be interested in the speed a vehicle is travelling).

The Observer pattern enables a loose-coupling to be established between a 'subject' (the object that is of interest; Speedometer on our example) and its 'observers' (any other class that needs to be kept informed when interesting stuff happens).

Because this is a very common need in object-oriented systems, the Java libraries already contains mechanisms that enable the pattern to be implemented. One of these is by utilising the Observable class and the Observer interface:

(IMAGEN)

Figure 20.1 : Observer pattern

The 'subject' (Speedometer) can have multiple observers (which can in fact be any class that implements the Observer interface, not just SpeedMonitor objects).

The Speedometer class looks like this:

```java
public class Speedometer extends Observable {
    private int currentSpeed;
 
    public Speedometer() {
        speed = 0;
    }
 
    public void setCurrentSpeed(int speed) {
        currentSpeed = speed;
 
        // Tell all observers so they know speed has changed...
        setChanged();
        notifyObservers();
    }
 
    public int getCurrentSpeed() {
        return currentSpeed;
    }
}
```

The Speedometer class extends java.util.Observable and thus inherits for our convenience its methods concerned with the registration and notification of observers. For our example, whenever the speed has changed we invoke the inherited setChanged() and notifyObservers() methods which takes care of the notifications for us.

The SpeedMonitor class implements the java.util.Observer interface and has the appropriate code for its required method update():

```java
public class SpeedMonitor implements Observer {
    public static final int SPEED_TO_ALERT = 70;
 
    public void update(Observable obs, Object obj) {
        Speedometer speedo = (Speedometer) obs;
        if (speedo.getCurrentSpeed() > SPEED_TO_ALERT) {
            System.out.println("** ALERT ** Driving too fast! (" + speedo.getCurrentSpeed() + ")");
        } else {
            System.out.println("... nice and steady ... (" + speedo.getCurrentSpeed() + ")");
        }
    }
}
```

Client programs simply pass a SpeedMonitor reference to an instance of Speedometer:

```java
// Create a monitor...
SpeedMonitor monitor = new SpeedMonitor();

// Create a speedometer and register the monitor to it...
Speedometer speedo = new Speedometer();
speedo.addObserver(monitor);

// Drive at different speeds...
speedo.setCurrentSpeed(50);
speedo.setCurrentSpeed(70);
speedo.setCurrentSpeed(40);
speedo.setCurrentSpeed(100);
speedo.setCurrentSpeed(69);
```

Running the above will result in the following output:

```text
... nice and steady ... (50)
... nice and steady ... (70)
... nice and steady ... (40)
** ALERT ** Driving too fast! (100)
... nice and steady ... (69)
```

The real power behind the Observer pattern is that any type of class can now become a monitor provided they implement the Observer interface, and without requiring any changes to be made to Speedometer. Let's create a simulation of an automatic gearbox:

```java
public class AutomaticGearbox implements Observer {
    public void update(Observable obs, Object obj) {
        Speedometer speedo = (Speedometer) obs;
 
        if (speedo.getCurrentSpeed() <= 10) {
            System.out.println("Now in first gear");
       
        } else if (speedo.getCurrentSpeed() <= 20) {
            System.out.println("Now in second gear");
       
        } else if (speedo.getCurrentSpeed() <= 30) {
            System.out.println("Now in third gear");
       
        } else {
            System.out.println("Now in fourth gear");
        }
    }
}
```

Our client program can now just add this as an additional observer and get notifications of speed changes as well:

```java
speedo.addObserver(new AutomaticGearbox());
```

### Un enfoque alternativo utilizando eventos y oyentes{#h2-29}

An alternative approach using events & listeners

The inherited code that makes Observable classes work does have an obvious downside; if your subject class already extends another class then you can't extend it as well, since Java only supports single inheritance (of classes). However, providing your own implementation is fairly straightforward, and here we will make use of an alternative approach provided by the Java libraries using 'events' and 'listeners'.

The first thing we shall do is separate out the events that can occur into a class called SpeedometerEvent that extends java.util.EventObject:

```java
public class SpeedometerEvent extends EventObject {
    private int speed;
 
    public SpeedometerEvent(Object source, int speed) {
        super(source);
        this.speed = speed;
    }
 
    public int getSpeed() {
        return speed;
    }
}
```

The only event of interest is when the speed changes. The inherited EventObject class provides a getSource() method so listeners will be able to tell the exact object that gave rise to the event, if they need to know it for some reason.

Going hand-in-hand with SpeedometerEvent is an interface we shall define called SpeedometerListener, which extends the java.util.EventListener interface:

```java
public interface SpeedometerListener extends EventListener {
    public void speedChanged(SpeedometerEvent event);
}
```

All classes that implement SpeedometerListener will need to provide appropriate code for the speedChanged() method. They can get any required data through the SpeedometerEvent reference passed in the argument. Note that our simple example only defines the one method, but it is common to have several methods that each notify a different piece of state that has changed.

The Speedometer class will now be modified to no longer extend java.util.Obervable and to instead handle listener registration and notification internally:

```java
public class Speedometer {
    private int currentSpeed;
    private List<SpeedometerListener> listeners;
 
    public Speedometer() {
        currentSpeed= 0;
        listeners = new ArrayList<SpeedometerListener>();
    }
 
    public void setCurrentSpeed(int speed) {
        currentSpeed = speed;
 
        // Tell all observers so they know speed has changed...
        fireSpeedChanged();
    }
 
    public int getCurrentSpeed() {
        return currentSpeed;
    }
 
    public void addSpeedometerListener(SpeedometerListener obj) {
        listeners.add(obj);
    }
 
    public void removeSpeedometerListener(SpeedometerListener obj) {
        listeners.remove(obj);
    }
 
    protected void fireSpeedChanged() {
        SpeedometerEvent speedEvent = new SpeedometerEvent(this, getCurrentSpeed());
 
        for (SpeedometerListener eachListener : listeners) {
            eachListener.speedChanged(speedEvent);
        }
    }
}
```

Note the use of an ArrayList to maintain the list of listeners, along with methods to add and remove them and loop through them when an SpeedometerEvent needs to be sent.

The SpeedMonitor class is our listener and now needs to implement the SpeedometerListener interface instead of java.util.Observable:

```java
public class SpeedMonitor implements SpeedometerListener {
    public static final int SPEED_TO_ALERT = 70;
 
    public void speedChanged(SpeedometerEvent event) {
        if (event.getSpeed() > SPEED_TO_ALERT) {
            System.out.println("** ALERT ** Driving too fast! (" + event.getSpeed() + ")");
        } else {
            System.out.println("... nice and steady ... (" + event.getSpeed() + ")");
        }
    }
}
```

Our client program is almost identical to before, the only change being a different method name when registering the listener:

```java
// Create a listener
SpeedMonitor monitor = new SpeedMonitor();

// Create a speedometer and register the monitor to it...
Speedometer speedo = new Speedometer();

speedo.addSpeedometerListener(monitor);

// Drive at different speeds...
speedo.setCurrentSpeed(50);
speedo.setCurrentSpeed(70);
speedo.setCurrentSpeed(40);
speedo.setCurrentSpeed(100);
speedo.setCurrentSpeed(69);
```

If your classes are JavaBeans then the Java libraries also supply a PropertyChangeEvent class and PropertyChangeListener interface that follow a similar approach.

---

## 21. Estado (State){#h2-30}

Type: Behavioural

Purpose: Allow an object to alter its behaviour when its internal state changes. The object will appear to change its class.

The Foobar Motor Company's vehicles each have a digital clock fitted that displays the current date and time. These values will need to be reset from time to time (such as after a change of battery) and this is accomplished by means of a particular knob on the dashboard. When the knob is initially pressed, the 'year' value can be set. Turning the knob to the left (i.e. anti-clockwise) causes the previous year to be show, whereas turning it to the right goes forward one year. When the knob is pressed again the year value becomes 'set' and the set-up process then automatically allows the month value to be set, also by making appropriate left or right movements with the knob.

This process continues for the day of the month, the hour and the minute. The following summarises the flow of events:

* _When the knob is first pushed the clock goes into "setup" mode for setting the year;_
* _If the knob is rotated left then 1 is deducted from the year value;_
* _If the knob is rotated right then 1 is added to the year value;_
* _When the knob is pushed the year becomes set and the clock goes into "setup" mode for setting the month;_
* _If the knob is rotated left then 1 is deducted from the month value;_
* _If the knob is rotated right then 1 is added to the month value;_
* _When the knob is pushed the month becomes set and the clock goes into "setup" mode for setting the day;_
* _If the knob is rotated left then 1 is deducted from the day value;_
* _If the knob is rotated right then 1 is added to the day value;_
* _When the knob is pushed the day becomes set and the clock goes into "setup" mode for setting the hour;_
* _If the knob is rotated left then 1 is deducted from the hour value;_
* _If the knob is rotated right then 1 is added to the hour value;_
* _When the knob is pushed the hour becomes set and the clock goes into "setup" mode for setting the minute;_
* _If the knob is rotated left then 1 is deducted from the minute value;_
* _If the knob is rotated right then 1 is added to the minute value;_
* _When the knob is pushed the minute becomes set and the clock goes into the "finished setup" mode;_
* _If the knob is pushed again the full selected date and time are displayed._

From the above steps it is clear that different parts of the date & time get set when the knob is turned or pressed, and that there are transitions between those parts. A naive approach when coding a class to accomplish this would be to have a 'mode' variable and then a series of if...else... statements in each method, which might look like this:

```java
// *** DON'T DO THIS! ***
public void rotateKnobLeft() {
    if (mode == YEAR_MODE) {
        year--;
    else if (mode == MONTH_MODE) {
        month--;

    else if (mode == DAY_MODE) {
        day--;
    else if (mode == HOUR_MODE) {
        hour--;
    else if (mode == MINUTE_MODE) {
        minute--;

    }

}
```

The problem with code such as the above is that the if...else... conditions would have to be repeated in each action method (i.e. rotateKnobRight(), pushKnob(), etc.). Apart from making the code look unwieldy it also becomes hard to maintain, as if for example we now need to record seconds we would need to change multiple parts of the class.

The State pattern enables a hierarchy to be established that allows for state transitions such as necessitated by our clock setting example. We will create a ClockSetup class that initiates the states through the interface ClockSetupState, which has an implementing class for each individual state:

(IMAGEN)

Figure 21.1 : State pattern

The ClockSetupState interface defines methods for handling changes to the state, plus methods that can provide user instructions and return the actual selected value:

```java
public interface ClockSetupState {
    public void previousValue();
    public void nextValue();
    public void selectValue();
 
    public String getInstructions();
    public int getSelectedValue();
}
```

Looking first at YearSetupState, you will notice that it takes a reference to a ClockSetup object in the constructor (which is known in the language of design patterns as its 'context') and manages the setting of the year. Note in particular in the selectValue() method how it transitions internally to a different state:

```java
public class YearSetupState implements ClockSetupState {
    private ClockSetup clockSetup;
    private int year;
 
    public YearSetupState(ClockSetup clockSetup) {
        this.clockSetup = clockSetup;
        year = Calendar.getInstance().get(Calendar.YEAR);
    }
 
    public void previousValue() {
        year--;
    }
 
    public void nextValue() {
        year++;
    }
 
    public void selectValue() {
        System.out.println("Year set to " + year);
        clockSetup.setState(clockSetup.getMonthSetupState());
    }
 
    public String getInstructions() {
        return "Please set the year...";
    }
 
    public int getSelectedValue() {
        return year;
    }
}
```

The other date & time state classes follow a similar process, each transitioning to the next appropriate state when required:

```java
public class MonthSetupState implements ClockSetupState {
    private ClockSetup clockSetup;
    private int month;
 
    public MonthSetupState(ClockSetup clockSetup) {
        this.clockSetup = clockSetup;
        month = Calendar.getInstance().get(Calendar.MONTH);
    }
 
    public void previousValue() {
        if (month > 0) {
            month--;
        }
    }
 
    public void nextValue() {
        if (month < 11) {
            month++;
        }
    }
 
    public void selectValue() {
        System.out.println("Month set to " + month);
        clockSetup.setState(clockSetup.getDaySetupState());
    }
 
    public String getInstructions() {
        return "Please set the month...";
    }
 
    public int getSelectedValue() {
        return month;
    }
}

public class DaySetupState implements ClockSetupState {
    private ClockSetup clockSetup;
    private int day;
 
    public DaySetupState(ClockSetup clockSetup) {
        this.clockSetup = clockSetup;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
 
    public void previousValue() {
        if (day > 1) {
            day--;
        }
    }
 
    public void nextValue() {
        if (day < Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) {
            day++;
        }
    }
 
    public void selectValue() {
        System.out.println("Day set to " + day);
        clockSetup.setState(clockSetup.getHourSetupState());
    }
 
    public String getInstructions() {
        return "Please set the day...";
    }
 
    public int getSelectedValue() {
        return day;
    }
}


public class HourSetupState implements ClockSetupState {
    private ClockSetup clockSetup;
    private int hour;
 
    public HourSetupState(ClockSetup clockSetup) {
        this.clockSetup = clockSetup;
        hour = Calendar.getInstance().get(Calendar.HOUR);
    }
 
    public void previousValue() {
        if (hour > 0) {
            hour--;
        }
    }
 
    public void nextValue() {
        if (hour < 23) {
            hour++;
        }
    }
 
    public void selectValue() {
        System.out.println("Hour set to " + hour);
        clockSetup.setState(clockSetup.getMinuteSetupState());
    }

    public String getInstructions() {
        return "Please set the hour...";
    }
 
    public int getSelectedValue() {
        return hour;
    }
}


public class MinuteSetupState implements ClockSetupState {
    private ClockSetup clockSetup;
    private int minute;
 
    public MinuteSetupState(ClockSetup clockSetup) {
        this.clockSetup = clockSetup;
        minute = Calendar.getInstance().get(Calendar.MINUTE);
    }
 
    public void previousValue() {
        if (minute > 0) {
            minute--;
        }
    }
 
    public void nextValue() {
        if (minute < 59) {
            minute++;
        }
    }
 
    public void selectValue() {
        System.out.println("Minute set to " + minute);
        clockSetup.setState(clockSetup.getFinishedSetupState());
    }
 
    public String getInstructions() {
        return "Please set the minute...";
    }
 
    public int getSelectedValue() {
        return minute;
    }
}
```

This just leaves the FinishedSetupState class which doesn't need to transition to a different state:

```java
public class FinishedSetupState implements ClockSetupState {
    private ClockSetup clockSetup;
 
    public FinishedSetupState(ClockSetup clockSetup) {
        this.clockSetup = clockSetup;
    }
 
    public void previousValue() {
        System.out.println("Ignored...");
    }
 
    public void nextValue() {
        System.out.println("Ignored...");

    }
 
    public void selectValue() {
        Calendar selectedDate = clockSetup.getSelectedDate();
        System.out.println("Date set to: " + selectedDate.getTime());
    }
 
    public String getInstructions() {
        return "Press knob to view selected date...";
    }
 
    public int getSelectedValue() {
        throw new UnsupportedOperationException("Clock setup finished");
    }
}
```

As mentioned, the 'context' class is ClockSetup, which holds references to each state and forwards to whichever is the current state:

```java
public class ClockSetup {
    // The various states the setup can be in...
    private ClockSetupState yearState;
    private ClockSetupState monthState;
    private ClockSetupState dayState;
    private ClockSetupState hourState;
    private ClockSetupState minuteState;
    private ClockSetupState finishedState;
 
    // The current state we are in...
    private ClockSetupState currentState;
  
    public ClockSetup() {
        yearState = new YearSetupState(this);
        monthState = new MonthSetupState(this);
        dayState = new DaySetupState(this);
        hourState = new HourSetupState(this);
        minuteState = new MinuteSetupState(this);
        finishedState = new FinishedSetupState(this);
 
        // Initial state is to set the year
        setState(yearState);
    }
 
    public void setState(ClockSetupState state) {
        currentState = state;
        System.out.println(currentState.getInstructions());
    }
 
    public void rotateKnobLeft() {
        currentState.previousValue();
    }
 
    public void rotateKnobRight() {
        currentState.nextValue();
    }
 
    public void pushKnob() {
        currentState.selectValue();
    }
 
    public ClockSetupState getYearSetupState() {
        return yearState;
    }
 
    public ClockSetupState getMonthSetupState() {
        return monthState;
    }
 
    public ClockSetupState getDaySetupState() {
        return dayState;
    }
 
    public ClockSetupState getHourSetupState() {
        return hourState;
    }
 
    public ClockSetupState getMinuteSetupState() {
        return minuteState;
    }
 
    public ClockSetupState getFinishedSetupState() {
        return finishedState;
    }
 
    public Calendar getSelectedDate() {
        return new GregorianCalendar(yearState.getSelectedValue(), monthState.getSelectedValue(), dayState.getSelectedValue(), hourState.getSelectedValue(), minuteState.getSelectedValue());
}
```

We can simulate a user's example actions like this:

```java
ClockSetup clockSetup = new ClockSetup();

// Setup starts in 'year' state
clockSetup.rotateKnobRight();
clockSetup.pushKnob(); // 1 year on

// Setup should now be in 'month' state
clockSetup.rotateKnobRight();
clockSetup.rotateKnobRight();
clockSetup.pushKnob(); // 2 months on

// Setup should now be in 'day' state
clockSetup.rotateKnobRight();
clockSetup.rotateKnobRight();
clockSetup.rotateKnobRight();
clockSetup.pushKnob(); // 3 days on

// Setup should now be in 'hour' state
clockSetup.rotateKnobLeft();
clockSetup.rotateKnobLeft();
clockSetup.pushKnob(); // 2 hours back

// Setup should now be in 'minute' state
clockSetup.rotateKnobRight();
clockSetup.pushKnob(); // 1 minute on

// Setup should now be in 'finished' state
clockSetup.pushKnob(); // to display selected date
```

Running the above should result in the following output relative to your current system date and time, with the above adjustments made.

```text
Please set the year...
Year set to 2013
Please set the month...
Month set to 10
Please set the day...
Day set to 25
Please set the hour...
Hour set to 0
Please set the minute...
Minute set to 4
Press knob to view selected date...
Date set to: Mon Nov 25 04:17:00 GMT 2013
```

---

## 22. Estrategia (Strategy){#h2-31}

Type: Behavioural

Purpose: Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

The Foobar Motor Company wishes to implement a new type of automatic gearbox for their cars that will be able to be switched between its standard mode and a special 'sport' mode. The different modes will base the decision of which gear should be selected depending upon the speed of travel, size of the engine and whether it is turbocharged. And it's quite possible they will want other modes in the future, such as for off-road driving.

Just as with the discussion in the chapter for the State pattern, it would be inflexible to use a series of if...else... statements to control the different gearbox modes directly inside our vehicle classes. Instead, we shall encapsulate the concept that varies and define a separate hierarchy so that each different gearbox mode is a separate class, each in effect being a different 'strategy' that gets applied. This approach allows the actual strategy being used to be isolated from the vehicle. In our example, we shall only apply this to the cars:

(IMAGEN)

Figure 22.1 : Strategy pattern

The GearboxStrategy interface defines the method to control the gear:

```java
public interface GearboxStrategy {
    public void ensureCorrectGear(Engine engine, int speed);
}
```

There are two implementing classes; StandardGearboxStrategy and SportGearboxStrategy:

```java
public class StandardGearboxStrategy implements GearboxStrategy {
    public void ensureCorrectGear(Engine engine, int speed) {
        int engineSize = engine.getSize();
        boolean turbo = engine.isTurbo();
 
        //  Some complicated code to determine correct gear
        //  setting based on engineSize, turbo & speed, etc.
        //  ... omitted ...
         System.out.println("Working out correct gear at " + speed + "mph for a STANDARD gearbox");
    }
}


public class SportGearboxStrategy implements GearboxStrategy {
    public void ensureCorrectGear(Engine engine, int speed) {
        int engineSize = engine.getSize();
        boolean turbo = engine.isTurbo();
 
        //  Some complicated code to determine correct gear
        //  setting based on engineSize, turbo & speed, etc.
        //  ... omitted ...
        System.out.println("Working out correct gear at " + speed + "mph for a SPORT gearbox");
    }
}
```

Our AbstractCar class is defined to hold a reference to the interface type (i.e. GearboxStrategy) and provide accessor methods so different strategies can be switched. There is also a setSpeed() method that delegates to whatever strategy is in effect. The pertinent code is marked in bold:

```java
public abstract class AbstractCar extends AbstractVehicle {
    private GearboxStrategy gearboxStrategy;
 
    public AbstractCar(Engine engine) {
        this(engine, Vehicle.Colour.UNPAINTED);
    }
 
    public AbstractCar(Engine engine) {
        super(engine);
 
        //  Starts in standard gearbox mode (more economical)
        gearboxStrategy = new StandardGearboxStrategy();
    }
 
    // Allow the gearbox strategy to be changed...
    public void setGearboxStrategy(GearboxStrategy gs) {
        gearboxStrategy = gs;
    }
 
    public GearboxStrategy getGearboxStrategy() {
        return getGearboxStrategy();
    }
 
    public void setSpeed(int speed) {
        // Delegate to strategy in effect...
        gearboxStrategy.ensureCorrectGear(getEngine(), speed);
    }
}
```

Client programs just set the required strategy:

```java
AbstractCar myCar = new Sport(new StandardEngine(2000));
myCar.setSpeed(20);
myCar.setSpeed(40);

System.out.println("Switching on sports mode gearbox...");
myCar.setGearboxStrategy(new SportGearboxStrategy());
myCar.setSpeed(20);
myCar.setSpeed(40);
```

This should result in the following output:

```text
Working out correct gear at 20mph for a STANDARD gearbox
Working out correct gear at 40mph for a STANDARD gearbox
Switching on sports mode gearbox...
Working out correct gear at 20mph for a SPORT gearbox
Working out correct gear at 40mph for a SPORT gearbox
```

---

## 23. Método de plantilla (Template Method){#h2-32}

Type: Behavioural

Purpose: Define the skeleton of an algorithm in a method, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

Each vehicle made by the Foobar Motor Company needs a small number of printed booklets to be produced and provided to the buyer, such as an Owner's Manual and a Service History booklet. The way booklets are produced always follows the same set of steps, but each different type of booklet might need to do each of the individual steps in a slightly different way.

The Template Method pattern enables the definition of one or more abstract methods that are called through a 'template method'. The simple hierarchy is as follows:

(IMAGEN)

Figure 23.1 : Template Method pattern

The AbstractBookletPrinter class defines several protected abstract methods and one public final 'template method' that makes use of the abstract methods (the method is made final to prevent it from being overridden):

```java
public abstract class AbstractBookletPrinter {
    protected abstract int getPageCount();
    protected abstract void printFrontCover();
    protected abstract void printTableOfContents();
    protected abstract void printPage(int pageNumber);
    protected abstract void printIndex();
    protected abstract void printBackCover();
    // This is the 'template method'
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

Each concrete subclass now only needs to provide the implementing code for each abstract method, for example the SaloonBooklet class below:

```java
public class SaloonBooklet extends AbstractBookletPrinter {
    protected int getPageCount() {
        return 100;
    }
 
    protected void printFrontCover() {
        System.out.println("Printing front cover for Saloon car booklet");
    }
 
    protected void printTableOfContents() {
        System.out.println("Printing table of contents for Saloon car booklet");
    }
 
    protected void printPage(int pageNumber) {
        System.out.println("Printing page " + pageNumber + " for Saloon car booklet");
    }
 
    protected void printIndex() {
        System.out.println("Printing index for Saloon car booklet");
    }
 
    protected void printBackCover() {
        System.out.println("Printing back cover for Saloon car booklet");
    }
}
```

The ServiceHistoryBooklet is very similar:

```java
public class ServiceHistoryBooklet extends AbstractBookletPrinter {
    protected int getPageCount() {
        return 12;
    }
 
    protected void printFrontCover() {
        System.out.println("Printing front cover for service history booklet");
    }
 
    protected void printTableOfContents() {
        System.out.println("Printing table of contents for service history booklet");
    }
 
    protected void printPage(int pageNumber) {
        System.out.println("Printing page " + pageNumber + " for service history booklet");
    }
 
    protected void printIndex() {
        System.out.println("Printing index for service history booklet");
    }
 
    protected void printBackCover() {
        System.out.println("Printing back cover for service history booklet");
    }
}
```

While it is not essential from the point of view of the pattern for the abstract methods to be protected, it is often the case that this is the most appropriate access level to assign since they are only intended for over-riding and not for direct invocation by client objects.

Also note that it's perfectly acceptable for some of the methods called from the 'template method' to not be abstract but have a default implementation provided. But when at least one abstract method is being called, it qualifies as the Template Method pattern.

Client programs merely need to instantiate the required concrete class and invoke the print() method:

```java
System.out.println("About to print a booklet for Saloon cars");
AbstractBookletPrinter saloonBooklet = new SaloonBooklet();
saloonBooklet.print();

System.out.println("About to print a service history booklet");
AbstractBookletPrinter serviceBooklet = new ServiceHistoryBooklet();
serviceBooklet.print();
```

---

## 24. Visitante (Visitor){#h2-33}

Type: Behavioural

Purpose: Represent a method to be performed on the elements of an object structure. Visitor lets you define a new method without changing the classes of the elements on which it operates.

Sometimes a class hierarchy and its code become substantive, and yet it is known that future requirements will be inevitable. An example for the Foobar Motor Company is the Engine hierarchy which looks like this:

(IMAGEN)

Figure 24.1 : Engine class hierarchy

In reality, the code within the AbstractEngine class is likely to be composed of a multitude of individual components, such as a camshaft, piston, some spark plugs, etc. If we need to add some functionality that traverses these components then the natural way is to just add a method to AbstractEngine. But maybe we know there are potentially many such new requirements and we would rather not have to keep adding methods directly into the hierarchy?

The Visitor pattern enables us to define just one additional method to add into the class hierarchy in such a way that lots of different types of new functionality can be added without any further changes. This is accomplished by means of a technique known as "double-despatch", whereby the invoked method issues a call-back to the invoking object.

The technique requires first the definition of an interface we shall call EngineVisitor:

```java
public interface EngineVisitor {
    public void visit(Camshaft camshaft);
    public void visit(Engine engine);
    public void visit(Piston piston);
    public void visit(SparkPlug sparkPlug);
}
```

We will also define an interface called Visitable with an acceptEngineVisitor() method:

```java
public interface Visitable {
    public void acceptEngineVisitor(EngineVisitor visitor);
}
```

The Engine interface you have met in previous chapters (although we will modify it slightly for this chapter). The Camshaft, Piston and SparkPlug classes are each very simple, as follows:

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

As you can see, each of these classes defines a method called acceptEngineVisitor() that takes a reference to an EngineVisitor object as its argument. All the method does is invoke the visit() method of the passed-in EngineVisitor, passing back the object instance.

Our modified Engine interface also now defines the acceptEngineVisitor() method:

```java
public interface Engine extends Visitable {
    public int getSize();
    public boolean isTurbo();
}
```

The AbstractEngine class therefore needs to implement this new method, which in this case traverses the individual components (camshaft, piston, spark plugs) invoking acceptEngineVisitor() on each:

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
 
        // Create a camshaft, piston and 4 spark plugs...
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
        // Visit each component first...
        camshaft.acceptEngineVisitor(visitor);
        piston.acceptEngineVisitor(visitor);
        for (SparkPlug eachSparkPlug : sparkPlugs) {
            eachSparkPlug.acceptEngineVisitor(visitor);
        }
 
        // Now visit the receiver...
        visitor.visit(this);
    }
 
    public String toString() {
        return getClass().getSimpleName() + " (" + size + ")");
    }
}
```

Now we shall create an actual implementation of EngineVisitor so you can see how we can easily add additional functionality to engines without any further changes to any engine hierarchy class. The first thing we shall do is to define some clever electronic gizmo that can be attached to an engine that will automatically check each component and diagnose any faults. We therefore define the EngineDiagnostics class:

```java
public class EngineDiagnostics implements EngineVisitor {
    public void visit(Camshaft camshaft) {
        System.out.println("Diagnosing the camshaft");
    }
 
    public void visit(Engine engine) {
        System.out.println("Diagnosing the unit engine");
    }
 
    public void visit(Piston piston) {
        System.out.println("Diagnosing the piston");
    }
    
    public void visit(SparkPlug sparkPlug) {
        System.out.println("Diagnosing a single spark plug");
    }
}
```

We also want to print an inventory of how many of each type of component there is within an engine, so we also have an EngineInventory class:

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
        System.out.println("The engine has: " + camshaftCount + " camshaft(s), " + pistonCount + " piston(s), and " + sparkPlugCount + " spark plug(s)");
    }
 
    public void visit(Piston p) {
        pistonCount++;
    }
 
    public void visit(SparkPlug sp) {
        sparkPlugCount++;
    }
}
```

The following diagram summarises how all of these classes interact:

(IMAGEN)
Figure 24.2 : Visitor pattern

Client programs now only need to invoke the acceptEngineVisitor() method on an instance of Engine, passing in the appropriate EngineVisitor object:

```java
// Create an engine...
Engine engine = new StandardEngine(1300);

// Run diagnostics on the engine...
engine.acceptEngineVisitor(new EngineDiagnostics());
```

The above will result in the following output:

```text
Diagnosing the camshaft
Diagnosing the piston
Diagnosing a single spark plug
Diagnosing a single spark plug
Diagnosing a single spark plug
Diagnosing a single spark plug
Diagnosing the unit engine
```

And to obtain the inventory (using the same Engine instance):

```java
// Run inventory on the engine...
engine.acceptEngineVisitor(new EngineInventory());
```

The output should show:

```text
The engine has: 1 camshaft(s), 1 piston(s), and 4 spark plug(s)
```
