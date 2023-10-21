# 21. Estado (State)

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

![Patrón Estado](../images/000033.jpg)

Figura 21.1 : Patrón Estado

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
