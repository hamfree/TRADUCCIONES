package java.generics.examples;


import java.util.List;

// fuente original: WildcardErrorBad.java
public class ComodinErrorMalo {

    void swapFirst(List<? extends Number> l1, List<? extends Number> l2) {
      Number temp = l1.get(0);
      l1.set(0, l2.get(0)); // se espero un CAP#1 extends Number,
                            // se obtiene CAP#2 extends Number;
                            // mismo limite, pero diferentes tipos
      l2.set(0, temp);      // se espero un CAP#1 extends Number,
                            // se obtiene un Number
    }
}
