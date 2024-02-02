package java.generics.examples;

import java.util.List;

// fuente original: WildcardFixed.java
public class ComodinArreglado {

    void foo(List<?> i) {
        fooHelper(i);
    }

    private <T> void fooHelper(List<T> l) {
        l.set(0, l.get(0));
    }

}
